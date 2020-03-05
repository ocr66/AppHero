package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class ComicItem(
    @SerializedName("resourceURI")
    @Expose
    val resourceURI: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null)