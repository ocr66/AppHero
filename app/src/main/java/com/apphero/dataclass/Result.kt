package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Result(
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    @SerializedName("modified")
    @Expose
    val modified: String? = null,
    @SerializedName("thumbnail")
    @Expose
    val thumbnail: Thumbnail? = null,
    @SerializedName("resourceURI")
    @Expose
    val resourceURI: String? = null,
    @SerializedName("comics")
    @Expose
    val comics: Comics? = null): Serializable