package com.pointlessapps.videosapp.services

import com.pointlessapps.videosapp.models.Video
import retrofit2.http.GET

interface ServiceVideos {

    @GET("/getEvents")
    suspend fun getEvents(): List<Video>

    @GET("/getSchedule")
    suspend fun getSchedule(): List<Video>
}