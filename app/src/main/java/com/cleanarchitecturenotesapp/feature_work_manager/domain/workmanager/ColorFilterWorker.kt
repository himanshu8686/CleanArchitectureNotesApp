package com.cleanarchitecturenotesapp.feature_work_manager.domain.workmanager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * WorkManager worker for applying color filter to images.
 * Processes an image by applying a lighting color filter and saves the result.
 *
 * @param context Application context
 * @param workerParameters Worker parameters containing the input image URI
 */
@HiltWorker
class ColorFilterWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    /**
     * Performs the color filter work.
     * Applies a color filter to the input image and saves the result.
     *
     * @return Result indicating success or failure
     */
    override suspend fun doWork(): Result {
        val imageFile =
            workerParameters.inputData.getString(WorkerKeys.IMAGE_URI)?.toUri()?.toFile()

        delay(5000)

        return imageFile?.let { file ->
            val bmp = BitmapFactory.decodeFile(file.absolutePath)
            val resultBmp = bmp.config?.let { bmp.copy(it, true) }
            val paint = android.graphics.Paint()
            paint.colorFilter = android.graphics.LightingColorFilter(0xF44336, 1)
            val canvas = android.graphics.Canvas(resultBmp!!)
            canvas.drawBitmap(resultBmp, 0f, 0f, paint)

            withContext(Dispatchers.IO) {
                val resultingFile = File(context.cacheDir, "filtered-image.jpg")
                val outputStream = FileOutputStream(resultingFile)
                val successful = resultBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

                if (successful) {
                    Result.success(
                        workDataOf(
                            WorkerKeys.FILTER_URI to resultingFile.toUri().toString()
                        )
                    )
                } else {
                    Result.failure()
                }
            }
        } ?: Result.failure()
    }

}