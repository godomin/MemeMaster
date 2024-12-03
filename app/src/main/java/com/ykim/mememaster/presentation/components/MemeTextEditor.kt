package com.ykim.mememaster.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.presentation.model.MemeTextEditorData
import com.ykim.mememaster.presentation.util.MemeText
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun MemeTextEditor(
    isSelected: Boolean = true,
    data: MemeTextEditorData,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val selectedPadding = if (isSelected) 10.dp else 0.dp
        Box(
            modifier = Modifier
                .padding(top = selectedPadding, end = selectedPadding)
        ) {
            BasicTextField(
                modifier = when {
                    isSelected -> Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
                        .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp)
                    else -> Modifier
                },
                value = value,
                onValueChange = onValueChange,
                textStyle = data.font.style.copy(
                    fontSize = data.fontSize.sp,
                    color = data.color,
                    letterSpacing = 0.em
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.inversePrimary),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (data.font.data == MemeText.STROKE) {
                        Text(text = value, style = data.font.style.copy(
                            color = Color.Black,
                            fontSize = data.fontSize.sp,
                            letterSpacing = 0.em,
                            drawStyle = Stroke(
                                miter = 10f,
                                width = data.fontSize / 10,
                                join = StrokeJoin.Round
                            )
                        ))
                    }
                }
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
                    .clickable(onClick = onDeleteClicked)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemeTextEditorPreview() {
    MemeMasterTheme {
        MemeTextEditor(
            isSelected = false,
            data = MemeTextEditorData(
                fontSize = 80.sp.value
            ),
            value = "TAP TWICE TO EDIT"
        )
    }
}