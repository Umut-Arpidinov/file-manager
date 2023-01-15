package kg.o.internlabs.omarket.data.remote.model.ads

import com.google.gson.annotations.SerializedName

data class AdsByFilterDto(
    @SerializedName("main_filters")
    val mainFilters: MainFiltersDto?
)


data class MainFiltersDto(
    @SerializedName("q")
    val q: String?
)