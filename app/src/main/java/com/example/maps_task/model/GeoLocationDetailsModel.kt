package com.example.maps_task.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GeoLocationDetailsModel(
        @SerializedName("batchcomplete") val batchComplete: String,
        val query: GeoPagesModel
) : Serializable