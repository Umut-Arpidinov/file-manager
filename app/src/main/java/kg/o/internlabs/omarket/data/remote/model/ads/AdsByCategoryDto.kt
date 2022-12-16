package kg.o.internlabs.omarket.data.remote.model.ads

import com.google.gson.annotations.SerializedName

data class AdsByCategoryDto(
    @SerializedName("main_filters")
    val mainFilter: MainFilterDto? = null
)

data class MainFilterDto(
    @SerializedName("order_by")
    val orderBy: String? = null,
    @SerializedName("category_id")
    val categoryId: Int? = null
)