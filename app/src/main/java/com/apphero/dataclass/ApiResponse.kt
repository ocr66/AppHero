package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ApiResponse(
    @SerializedName("code")
    @Expose
    val code: Int? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("copyright")
    @Expose
    val copyright: String? = null,
    @SerializedName("attributionText")
    @Expose
    val attributionText: String? = null,
    @SerializedName("attributionHTML")
    @Expose
    val attributionHTML: String? = null,
    @SerializedName("etag")
    @Expose
    val etag: String? = null,
    @SerializedName("data")
    @Expose
    val data: Data? = null): Serializable