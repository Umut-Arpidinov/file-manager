package kg.o.internlabs.omarket.data.mappers

import kg.o.internlabs.omarket.data.remote.model.*
import kg.o.internlabs.omarket.data.remote.model.ads.AdsDto
import kg.o.internlabs.omarket.domain.entity.*
import kg.o.internlabs.omarket.domain.entity.ads.Ads
import kg.o.internlabs.omarket.domain.entity.ads.Author
import kg.o.internlabs.omarket.domain.entity.ads.LocationAds
import retrofit2.Response

class MapperForAds {

    //region toDomain
    fun mapEntityToDbModel(v: Ads?) = AdsDto(
        details = v?.details,
        errorCode = v?.errorCode,
        result = v?.result,
        resultCode = v?.resultCode
    )

    fun mapEntityToDbModel(v: Author?) = AuthorDto(
        class Author(
    val avatar: String? = null,
    val blockType: String? = null,
    val contactNumIsIdent: Boolean? = null,
    val contactNumber: String? = null,
    val hasPromotion: Boolean? = null,
    val id: Int? = null,
    val location: LocationAds? = null,
    val msisdn: String? = null,
    val partnerType: String? = null,
    val rating: Double? = null,
    val username: String? = null,
    val verified: Boolean? = null
    )


    fun mapRespDbModelToRespEntity(list: Response<RegisterDto?>) = if (list.isSuccessful) {
        Response.success(mapDbModelToEntity(list.body()))
    } else {
        list.errorBody()?.let { Response.error(list.code(), it) }
    }
    //endregion

    //region *toEntity
    fun mapEntityToDbModel(cat: CategoriesEntity?) = CategoriesDto(
        result = cat?.result?.map { mapEntityToDbModel(it) },
        resultCode = cat?.resultCode,
        details = cat?.details,
        errorCode = cat?.errorCode
    )

    private fun mapEntityToDbModel(res: ResultEntity?) = ResultDto(id = res?.id,
        parent = res?.parent,
        name = res?.name,
        parentFilters = res?.parentFilters,
        orderNum = res?.orderNum,
        isPopular = res?.isPopular,
        delivery = res?.delivery,
        hasMap = res?.hasMap,
        requiredPrice = res?.requiredPrice,
        iconImg = res?.iconImg,
        darkIconImg = res?.darkIconImg,
        categoryType = res?.categoryType,
        linkedCategory = res?.linkedCategory,
        filters = res?.filters,
        adType = res?.adType,
        hasDynamicFilter = res?.hasDynamicFilter,
        subCategories = res?.subCategories?.map { mapEntityToDbModel(it) })

    private fun mapEntityToDbModel(subCat: SubCategoriesEntity?) = SubCategoriesDto(
        id = subCat?.id,
        parent = subCat?.parent,
        name = subCat?.name,
        parentFilters = subCat?.parentFilters,
        delivery = subCat?.delivery,
        orderNum = subCat?.orderNum,
        isPopular = subCat?.isPopular,
        hasMap = subCat?.hasMap,
        requiredPrice = subCat?.requiredPrice,
        iconImg = subCat?.iconImg,
        darkIconImg = subCat?.darkIconImg,
        categoryType = subCat?.categoryType,
        linkedCategory = subCat?.linkedCategory,
        filters = subCat?.filters,
        adType = subCat?.adType,
        hasDynamicFilter = subCat?.hasDynamicFilter,
        subCategories = subCat?.subCategories
    )

    private fun mapDbModelToEntity(cat: CategoriesDto?) = CategoriesEntity(
        result = cat?.result?.map { mapDbModelToEntity(it) },
        resultCode = cat?.resultCode,
        details = cat?.details,
        errorCode = cat?.errorCode
    )

    private fun mapDbModelToEntity(res: ResultDto?) = ResultEntity(id = res?.id,
        parent = res?.parent,
        name = res?.name,
        parentFilters = res?.parentFilters,
        orderNum = res?.orderNum,
        isPopular = res?.isPopular,
        delivery = res?.delivery,
        hasMap = res?.hasMap,
        requiredPrice = res?.requiredPrice,
        iconImg = res?.iconImg,
        darkIconImg = res?.darkIconImg,
        categoryType = res?.categoryType,
        linkedCategory = res?.linkedCategory,
        filters = res?.filters,
        adType = res?.adType,
        hasDynamicFilter = res?.hasDynamicFilter,
        subCategories = res?.subCategories?.map { mapDbModelToEntity(it) })

    private fun mapDbModelToEntity(subCat: SubCategoriesDto?) = SubCategoriesEntity(
        id = subCat?.id,
        parent = subCat?.parent,
        name = subCat?.name,
        parentFilters = subCat?.parentFilters,
        delivery = subCat?.delivery,
        orderNum = subCat?.orderNum,
        isPopular = subCat?.isPopular,
        hasMap = subCat?.hasMap,
        requiredPrice = subCat?.requiredPrice,
        iconImg = subCat?.iconImg,
        darkIconImg = subCat?.darkIconImg,
        categoryType = subCat?.categoryType,
        linkedCategory = subCat?.linkedCategory,
        filters = subCat?.filters,
        adType = subCat?.adType,
        hasDynamicFilter = subCat?.hasDynamicFilter,
        subCategories = subCat?.subCategories
    )


    fun mapRespDbModelToRespEntityForCategories(resp: Response<CategoriesDto?>) =
        if (resp.isSuccessful) {
            Response.success(mapDbModelToEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }
    // endregion


}