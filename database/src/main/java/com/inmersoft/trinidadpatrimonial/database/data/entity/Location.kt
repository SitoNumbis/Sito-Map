package com.inmersoft.trinidadpatrimonial.database.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val latitude: Double,
    val longitude: Double
)