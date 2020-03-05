package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Thumbnail(
    @SerializedName("path")
    @Expose
    val path: String? = null,
    @SerializedName("extension")
    @Expose
    val extension: String? = null)