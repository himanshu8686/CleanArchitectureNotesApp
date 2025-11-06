package com.cleanarchitecturenotesapp.feature_work_manager.presentation.image_color_filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkInfo
import coil.compose.rememberAsyncImagePainter
import com.cleanarchitecturenotesapp.feature_work_manager.domain.workmanager.WorkerKeys

/**
 * Screen demonstrating WorkManager usage for downloading and filtering images.
 * Shows download progress and displays the processed image.
 *
 * @param imageColorFilterViewModel ViewModel for managing work status
 */
@Composable
fun ImageColorFilterScreen(
    imageColorFilterViewModel: ImageColorFilterViewModel = hiltViewModel()
) {
    val workInfoList by imageColorFilterViewModel.workStatusList.collectAsState()

    val downloadInfo = remember(workInfoList) {
        workInfoList.find { it.id == imageColorFilterViewModel.downloadRequestId }
    }

    val filterInfo = remember(workInfoList) {
        workInfoList.find { it.id == imageColorFilterViewModel.colorFilterRequestId }
    }

    val imageUri by remember(downloadInfo, filterInfo) {
        derivedStateOf {
            val downloadUri = downloadInfo?.outputData?.getString(WorkerKeys.IMAGE_URI)?.toUri()
            val filterUri = filterInfo?.outputData?.getString(WorkerKeys.FILTER_URI)?.toUri()
            filterUri ?: downloadUri
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    imageColorFilterViewModel.startWorkers()
                },
                enabled = downloadInfo?.state != WorkInfo.State.RUNNING,
                content = {
                    Text("Start Download")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Download State: ${downloadInfo?.state ?: "Not started"}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("Filter State: ${filterInfo?.state ?: "Not started"}")

        }


        if (downloadInfo?.state == WorkInfo.State.RUNNING) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
    }

}