package com.apphero.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Comics(
    @SerializedName("available")
    @Expose
    val available: Int? = null,
    @SerializedName("collectionURI")
    @Expose
    val collectionURI: String? = null,
    @SerializedName("items")
    @Expose
    val items: List<ComicItem>? = null,
    @SerializedName("returned")
    @Expose
    val returned: Int? = null)