package com.appiness.appinesstest.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksModel(
    @Json(name = "by")
    val `by`: String,
    @Json(name = "num.backers")
    val numBackers: String,
    @Json(name = "title")
    val title: String
)