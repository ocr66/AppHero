package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("offset")
    @Expose
    val offset: Int? = null,
    @SerializedName("limit")
    @Expose
    val limit: Int? = null,
    @SerializedName("total")
    @Expose
    val total: Int? = null,
    @SerializedName("count")
    @Expose
    val count: Int? = null,
    @SerializedName("results")
    @Expose
    val results: ArrayList<Result>? = null): Serializable