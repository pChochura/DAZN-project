package com.pointlessapps.videosapp.repositories

import com.pointlessapps.videosapp.App
import com.pointlessapps.videosapp.models.Video
import com.pointlessapps.videosapp.services.ServiceVideos
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class RepositoryVideos(app: App) {

    private val serviceVideos: ServiceVideos = app.retrofit.create(ServiceVideos::class.java)

    suspend fun getEvents(): List<Video> = serviceVideos.getEvents().sorted()

    fun getSchedule(
        onRefreshInvokedCallback: () -> Unit,
        interval: Long = TimeUnit.SECONDS.toMillis(30)
    ): Flow<List<Video>> {
        return flow {
            while (coroutineContext[Job]?.isActive == true) {
                onRefreshInvokedCallback()
                emit(serviceVideos.getSchedule().sorted())
                delay(interval)
            }
        }
    }
}