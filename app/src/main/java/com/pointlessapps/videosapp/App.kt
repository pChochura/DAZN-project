package com.pointlessapps.videosapp

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    internal val retrofit = Retrofit.Builder()
        .baseUrl("https://us-central1-dazn-sandbox.cloudfunctions.net")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
}