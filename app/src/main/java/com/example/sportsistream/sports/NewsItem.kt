package com.example.sportsistream.sports

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val id: String,
    val title: String,
    val description: String,
    val fullContent: String,
    val category: String,
    val imageUrl: String,
    val date: String,
    val source: String
) : Parcelable
