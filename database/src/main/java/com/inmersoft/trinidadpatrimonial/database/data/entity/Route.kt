package com.inmersoft.trinidadpatrimonial.database.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "routes")
@JsonClass(generateAdapter = true)
data class Route(
    @PrimaryKey val route_id: Int = 0,
    val header_images: List<String>,
    val route_description: String,
    val route_name: String,
    val route_translations: List<RouteTranslation>,
    val video_promo: String
)