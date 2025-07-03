package com.cleanarchitecturenotesapp.feature_work_manager.presentation.image_color_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.cleanarchitecturenotesapp.feature_work_manager.domain.workmanager.ColorFilterWorker
import com.cleanarchitecturenotesapp.feature_work_manager.domain.workmanager.DownloadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ImageColorFilterViewModel @Inject constructor(
    private val workManager: WorkManager
): ViewModel() {

    private val _workStatusList = MutableStateFlow<List<WorkInfo>>(emptyList())
    val workStatusList: StateFlow<List<WorkInfo>> = _workStatusList

    private var _downloadRequestId: UUID? = null
    private var _colorFilterRequestId: UUID? = null

    val downloadRequestId: UUID?
        get() = _downloadRequestId

    val colorFilterRequestId: UUID?
        get() = _colorFilterRequestId

    private val constraintsBuilder = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    init {
        workManager.getWorkInfosByTagFlow("image_work")
            .onEach { _workStatusList.value = it }
            .launchIn(viewModelScope)
    }

    fun startWorkers() {
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraintsBuilder)
            .addTag("image_work")
            .build()

        val colorFilterRequest = OneTimeWorkRequestBuilder<ColorFilterWorker>()
            .addTag("image_work")
            .build()

        _downloadRequestId = downloadRequest.id
        _colorFilterRequestId = colorFilterRequest.id

        workManager
            .beginUniqueWork(
                "download",
                ExistingWorkPolicy.REPLACE,
                downloadRequest
            )
            .then(colorFilterRequest)
            .enqueue()
    }

}