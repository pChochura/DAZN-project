package com.pointlessapps.videosapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Video(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: Date,
    val imageUrl: String,
    val videoUrl: String,
) : Comparable<Video>, Parcelable {
    override fun compareTo(other: Video) = compareValuesBy(this, other, { it.date })
}