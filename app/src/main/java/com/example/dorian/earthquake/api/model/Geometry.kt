package com.example.dorian.earthquake.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geometry(
        @SerializedName("type")
        @Expose
        var type: String? = null,
        @SerializedName("coordinates")
        @Expose
        var coordinates: List<Double>? = null
)
