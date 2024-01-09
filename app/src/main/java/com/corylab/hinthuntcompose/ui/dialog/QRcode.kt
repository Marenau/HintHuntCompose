package com.corylab.hinthuntcompose.ui.dialog

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.corylab.hinthuntcompose.R

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