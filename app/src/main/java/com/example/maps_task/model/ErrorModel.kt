package com.example.maps_task.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorModel(
        @SerializedName("error") val error: ErrorDescriptionModel?,
        @SerializedName("servedby") val servedBy: String?
) : Serializable