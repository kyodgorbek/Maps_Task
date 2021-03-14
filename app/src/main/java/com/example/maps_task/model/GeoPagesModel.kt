package com.example.maps_task.model

import java.io.Serializable

data class GeoPagesModel(
        val pages: HashMap<String, GeoInfoModel>
) : Serializable