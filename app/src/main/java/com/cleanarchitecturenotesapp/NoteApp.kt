package com.cleanarchitecturenotesapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Main application class for the Notes App.
 * Configures Hilt dependency injection and WorkManager.
 */
@HiltAndroidApp
class NoteApp: Application(), Configuration.Provider {

    /**
     * Hilt worker factory for dependency injection in WorkManager workers.
     */
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    /**
     * Provides WorkManager configuration with Hilt worker factory.
     *
     * @return Configuration instance for WorkManager
     */
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()
}