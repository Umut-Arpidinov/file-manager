package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName

data class DeletedImageUrlDto(
    @SerializedName("url") val url: String? = null
)