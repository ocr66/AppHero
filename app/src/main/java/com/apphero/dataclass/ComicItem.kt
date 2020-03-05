package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ComicItem(
    @SerializedName("resourceURI")
    @Expose
    val resourceURI: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null): Serializable