package com.corylab.hinthuntcompose.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.data.qrcode.QRCodeAnalyzer
import com.corylab.hinthuntcompose.ui.dialog.DialogWithChoice
import com.corylab.hinthuntcompose.ui.theme.MainText
import com.corylab.hinthuntcompose.ui.theme.Title

@Composable
fun ConnectGame(navController: NavController) {
    val qrText = rememberSaveable {
        mutableStateOf("")
    }

    val unsupportedText = rememberSaveable {
        mutableStateOf("")
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val localContext = LocalContext.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }

    val hasCamPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                localContext, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCamPermission.value = granted })
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    val openDialog = rememberSaveable { mutableStateOf(false) }

    if (openDialog.value) {
        DialogWithChoice(
            title = stringResource(id = R.string.fragment_connect_game_choice_title),
            text = stringResource(id = R.string.fragment_connect_game_choice_text),
            firstButtonText = stringResource(id = R.string.fragment_connect_game_leader),
            secondButtonText = stringResource(id = R.string.fragment_connect_game_player),
            firstButtonOnClick = {
                navController.popBackStack()
                navController.navigate("leader/${qrText.value}")
            },
            secondButtonOnClick = {
                navController.popBackStack()
                navController.navigate("player/${qrText.value}")
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.fragment_connect_game_title),
            style = Title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            fontSize = 40.sp
        )
        if (hasCamPermission.value) {
            AndroidView(modifier = Modifier
                .padding(top = 24.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(6.dp))
                .align(Alignment.CenterHorizontally),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector =
                        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder().build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context), QRCodeAnalyzer(context, qrText)
                    )
                    runCatching {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner, selector, preview, imageAnalysis
                        )
                    }
                    previewView
                })
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = unsupportedText.value,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
            if (qrText.value.isNotEmpty()) {
                if (qrText.value.count { it == ';' } == 2) {
                    unsupportedText.value = ""
                    ButtonWithText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = stringResource(id = R.string.fragment_connect_game_connect),
                        onClick = { openDialog.value = true }
                    )
                } else {
                    unsupportedText.value =
                        stringResource(id = R.string.fragment_connect_unsupported_qr)
                }
            }
        }
    }
}