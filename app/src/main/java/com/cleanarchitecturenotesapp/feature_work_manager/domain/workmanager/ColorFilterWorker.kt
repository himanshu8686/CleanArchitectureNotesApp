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

@HiltWorker
class ColorFilterWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

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