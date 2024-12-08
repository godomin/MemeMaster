package com.ykim.mememaster.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.presentation.model.TextStyle
import com.ykim.mememaster.presentation.util.MemeFontType
import com.ykim.mememaster.presentation.util.applyIf
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun MemeTextEditor(
    isSelected: Boolean = true,
    data: TextStyle,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(IntrinsicSize.Min)
    ) {
        val selectedPadding = if (isSelected) 10.dp else 0.dp
        Box(
            modifier = Modifier
                .padding(top = 10.dp, end = selectedPadding)
        ) {
            BasicTextField(
                modifier = Modifier.applyIf(isSelected, {
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
                        .widthIn(min = 0.dp)
                        .width(IntrinsicSize.Min)
                        .padding(start = 12.dp, end = 12.dp)
                        .verticalScroll(rememberScrollState(), false)
                }, {
                    Modifier
                        .padding(start = 12.dp, end = 22.dp)
                }),
                enabled = isSelected,
                value = value,
                onValueChange = onValueChange,
                textStyle = data.getTextStyle(),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.inversePrimary),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (data.font == MemeFontType.STROKE) {
                        Text(
                            text = value, style = data.getTextStyle().copy(
                                color = Color.Black,
                                drawStyle = Stroke(
                                    miter = 10f,
                                    width = data.size / 10,
                                    join = StrokeJoin.Round
                                )
                            )
                        )
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
        } else {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xeeeeee)
@Composable
private fun MemeTextEditorPreview() {
    MemeMasterTheme {
        MemeTextEditor(
            isSelected = true,
            data = TextStyle(
                size = 80.sp.value,
                font = MemeFontType.STROKE,
                color = Color.White
            ),
            value = "TEST",
        )
    }
}