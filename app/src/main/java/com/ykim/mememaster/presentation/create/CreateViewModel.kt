package com.ykim.mememaster.presentation.create

import android.content.Context
import android.graphics.Picture
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ykim.mememaster.data.mapper.toByteArray
import com.ykim.mememaster.domain.HistoryManager
import com.ykim.mememaster.domain.ImageRepository
import com.ykim.mememaster.domain.MemeRepository
import com.ykim.mememaster.domain.model.ImageData
import com.ykim.mememaster.domain.model.MemeData
import com.ykim.mememaster.presentation.model.EditMode
import com.ykim.mememaster.presentation.model.OverlayText
import com.ykim.mememaster.presentation.navigation.Create
import com.ykim.mememaster.presentation.util.MemeFontType
import com.ykim.mememaster.presentation.util.dpToPx
import com.ykim.mememaster.presentation.util.toBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    private val historyManager: HistoryManager<List<OverlayText>>,
    private val imageRepository: ImageRepository,
    private val memeRepository: MemeRepository,
) : ViewModel() {

    var state by mutableStateOf(CreateState())
        private set

    private val eventChannel = Channel<CreateEvent>()
    val events = eventChannel.receiveAsFlow()


    init {
        val templateResId = savedStateHandle.toRoute<Create>().templateResId
        state = state.copy(templateResId = templateResId)
        historyManager.add(emptyList())
    }

    fun onAction(action: CreateAction) {
        when (action) {
            is CreateAction.OnEditModeChanged -> {
                state = state.copy(editMode = action.editMode)
            }

            is CreateAction.OnAddText -> {
                state = state.copy(
                    editMode = EditMode.NONE,
                    textList = state.textList + state.selectedText
                )
            }

            is CreateAction.OnTextChanged -> updateSelectedText(text = action.text)

            is CreateAction.OnTextOffsetChanged -> {
                dragText(action.imageSize, action.textSize, action.offset)
            }

            is CreateAction.OnSelectedTextChanged -> {
                state = state.copy(
                    selectedText = action.text,
                    editMode = EditMode.NONE
                )
            }

            CreateAction.OnRemoveText -> {
                state = state.copy(
                    textList = state.textList.filter { it.id != state.selectedText.id },
                    editMode = EditMode.ADD,
                    selectedText = OverlayText()
                )
            }

            is CreateAction.OnTextFontChanged -> updateSelectedText(font = action.font)
            is CreateAction.OnTextFontSizeChanged -> updateSelectedText(size = action.size)
            is CreateAction.OnTextColorChanged -> updateSelectedText(color = action.color)

            CreateAction.OnTextChangeDiscarded -> {
                stateToAddMode()
            }

            CreateAction.OnTextChangeApplied -> {
                applySelectedStyle()
                addHistoryState()
                stateToAddMode()
            }

            CreateAction.OnUndo -> undo()
            CreateAction.OnRedo -> redo()

            is CreateAction.SaveMeme -> saveMeme(action.picture, action.imageSize)
            is CreateAction.ShareMeme -> shareMeme(action.picture, action.imageSize)

            else -> Unit
        }
    }

    private fun dragText(imageSize: IntSize, textSize: IntSize, offset: Offset) {
        updateSelectedText(
            offset = coercedOffset(
                imageSize = imageSize,
                textSize = textSize,
                newOffset = state.selectedText.offset + offset
            )
        )
    }

    private fun coercedOffset(imageSize: IntSize, textSize: IntSize, newOffset: Offset): Offset {
        val padding = context.dpToPx(5f)
        val left = (-imageSize.center.x + textSize.center.x - padding)
        val right = (imageSize.center.x - textSize.center.x + padding)
        val top = (-imageSize.center.y + textSize.center.y - padding)
        val bottom = (imageSize.center.y - textSize.center.y + padding)
        val coercedX = newOffset.x.coerceIn(left, right)
        val coercedY = newOffset.y.coerceIn(top, bottom)
        return Offset(coercedX, coercedY)
    }

    private fun stateToAddMode() {
        state = state.copy(
            editMode = EditMode.ADD,
            selectedText = OverlayText(),
        )
    }

    private fun applySelectedStyle() {
        state = state.copy(
            textList = state.textList.map {
                if (state.selectedText.id == it.id) {
                    state.selectedText
                } else {
                    it
                }
            }
        )
    }

    private fun updateSelectedText(
        text: String? = null,
        offset: Offset? = null,
        font: MemeFontType? = null,
        size: Float? = null,
        color: Color? = null,
    ) {
        state = state.copy(
            selectedText = state.selectedText.copy(
                text = text ?: state.selectedText.text,
                offset = offset ?: state.selectedText.offset,
                style = state.selectedText.style.copy(
                    font = font ?: state.selectedText.style.font,
                    size = size ?: state.selectedText.style.size,
                    color = color ?: state.selectedText.style.color
                )
            )
        )
    }

    private fun addHistoryState() {
        historyManager.add(state.textList)
        updateUndoRedoState()
    }

    private fun undo() {
        if (!historyManager.canUndo()) return
        setUndoRedoState(historyManager.undo())
        updateUndoRedoState()
    }

    private fun redo() {
        if (!historyManager.canRedo()) return
        setUndoRedoState(historyManager.redo())
        updateUndoRedoState()
    }

    private fun updateUndoRedoState() {
        state = state.copy(
            canUndo = historyManager.canUndo(),
            canRedo = historyManager.canRedo()
        )
    }

    private fun setUndoRedoState(list: List<OverlayText>) {
        state = state.copy(
            textList = list
        )
    }

    private fun saveMeme(picture: Picture, imageSize: IntSize) {
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                picture.toBitmap(imageSize)
            }
            val fileName = getFileName()
            val imageUri = imageRepository.saveImage(
                ImageData(
                    fileName = fileName,
                    byteArray = bitmap.toByteArray()
                )
            )
            upsertMeme(imageUri)
        }
    }

    private fun shareMeme(picture: Picture, imageSize: IntSize) {
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                picture.toBitmap(imageSize)
            }
            val fileName = getFileName()
            val imageUri = imageRepository.saveImageInternalStorage(
                ImageData(
                    fileName = fileName,
                    byteArray = bitmap.toByteArray()
                )
            )
            upsertMeme(imageUri)
            eventChannel.send(CreateEvent.StartShareChooser(Uri.parse(imageUri)))
        }
    }

    private fun upsertMeme(imageUri: String) {
        viewModelScope.launch {
            memeRepository.upsertMeme(
                MemeData(
                    imageUri = imageUri,
                    isFavorite = false,
                    isSelected = false,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    private fun getFileName(): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "MEME_$timestamp.jpg"
    }
}