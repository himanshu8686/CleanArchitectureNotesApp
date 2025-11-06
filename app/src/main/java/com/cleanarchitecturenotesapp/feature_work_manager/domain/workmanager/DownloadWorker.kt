package com.cleanarchitecturenotesapp.feature_work_manager.domain.workmanager

import android.content.Context
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.cleanarchitecturenotesapp.feature_work_manager.data.remote.FileApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * WorkManager worker for downloading images from the network.
 * Downloads an image and saves it to the cache directory.
 *
 * @param fileApiService API service for downloading images
 * @param context Application context
 * @param workerParameters Worker parameters
 */
@HiltWorker
class DownloadWorker @AssistedInject constructor(
    private val fileApiService: FileApiService,
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    /**
     * Performs the download work.
     * Downloads an image and saves it to cache, returning the file URI.
     *
     * @return Result indicating success, failure, or retry
     */
    override suspend fun doWork(): Result {
        delay(10000L)
        val response = fileApiService.downloadImage()
        response.body()?.let { body ->
            return withContext(Dispatchers.IO) {
                val file = File(context.cacheDir, "image.jpg")
                val outputStream = FileOutputStream(file)
                outputStream.use { stream ->
                    try {
                        stream.write(body.bytes())
                    } catch (e: Exception) {
                        return@withContext Result.failure(
                            workDataOf(
                                WorkerKeys.ERROR_MSG to e.localizedMessage
                            )
                        )
                    }
                }
                Result.success(
                    workDataOf(WorkerKeys.IMAGE_URI to file.toUri().toString())
                )
            }
        }

        if (response.isSuccessful.not()) {
            if (response.code().toString().startsWith("5")) {
                return Result.retry()
            }
            return Result.failure(
                workDataOf(
                    WorkerKeys.ERROR_MSG to "Network error"
                )
            )
        }

        return Result.failure(
            workDataOf(
                WorkerKeys.ERROR_MSG to "Unknown error"
            )
        )
    }
}