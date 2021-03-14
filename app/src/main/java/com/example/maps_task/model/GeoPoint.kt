package com.example.maps_task.model

import java.io.Serializable


data class GeoPoint(
        val lat: Double,
        val lng: Double
) : Serializable