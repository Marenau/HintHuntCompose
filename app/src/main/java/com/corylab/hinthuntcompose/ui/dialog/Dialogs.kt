package com.corylab.hinthuntcompose.ui.dialog

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.MainText

@Composable
fun DialogWithImage(
    openDialog: MutableState<Boolean>,
    bitmap: Bitmap,
) {
    Dialog(onDismissRequest = {}) {
        Column {
            Card(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_gray))
            ) {
                Image(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "imageDescription",
                    contentScale = ContentScale.Fit,
                )
            }
            IconButton(
                onClick = { openDialog.value = false },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_close),
                    contentDescription = "Show cards",
                    tint = colorResource(id = R.color.dark_gray)
                )
            }
        }
    }
}

@Composable
fun DialogWithText(
    onDismiss: () -> Unit = {},
    openDialog: MutableState<Boolean>,
    title: String,
    text: String,
    buttonText: String,
    teamColor: Color = colorResource(id = R.color.light_gray),
    buttonOnClick: () -> Unit = { openDialog.value = false }
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_gray))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MainText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = text,
                    style = MainText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Button(
                    onClick = buttonOnClick,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 8.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = teamColor,
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text(text = buttonText, style = MainText)
                }
            }
        }
    }
}

@Composable
fun DialogWithChoice(
    onDismiss: () -> Unit = {},
    title: String,
    text: String,
    firstButtonText: String,
    secondButtonText: String,
    firstButtonOnClick: () -> Unit,
    secondButtonOnClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_gray))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MainText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = text,
                    style = MainText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    val modifier = Modifier
                        .weight(1F)
                        .padding(start = 8.dp, top = 16.dp, bottom = 8.dp, end = 8.dp)
                    val colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_gray),
                        contentColor = colorResource(id = R.color.white)
                    )
                    Button(
                        onClick = firstButtonOnClick,
                        modifier = modifier,
                        shape = RoundedCornerShape(6.dp),
                        colors = colors
                    ) {
                        Text(text = firstButtonText, style = MainText)
                    }
                    Button(
                        onClick = secondButtonOnClick,
                        modifier = modifier,
                        shape = RoundedCornerShape(6.dp),
                        colors = colors
                    ) {
                        Text(text = secondButtonText, style = MainText)
                    }
                }
            }
        }
    }
}