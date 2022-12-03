package kg.o.internlabs.omarket.data

import kg.o.internlabs.omarket.data.remote.model.*
import kg.o.internlabs.omarket.domain.entity.*
import retrofit2.Response

class MapperForModels {

    fun mapEntityToDbModel(reg: RegisterEntity?) = RegisterDto(
        accessToken = reg?.accessToken,
        refreshToken = reg?.refreshToken,
        message = reg?.message,
        msisdn = reg?.msisdn,
        otp = reg?.otp,
        success = reg?.success,
        password = reg?.password,
        password2 = reg?.password2
    )


    private fun mapDbModelToEntity(regDto: RegisterDto?) = RegisterEntity(
        accessToken = regDto?.accessToken,
        refreshToken = regDto?.refreshToken,
        message = regDto?.message,
        msisdn = regDto?.msisdn,
        otp = regDto?.otp,
        success = regDto?.success,
        password = regDto?.password,
        password2 = regDto?.password2
    )

    fun mapRespDbModelToRespEntity(list: Response<RegisterDto?>) = if (list.isSuccessful) {
        Response.success(mapDbModelToEntity(list.body()))
    } else {
        list.errorBody()?.let { Response.error(list.code(), it) }
    }

    //region *** mapping for categories ***
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

    // region Faq
    fun mapEntityToDbModel(faq: FAQEntity?) = FAQDto(count = faq?.count,
        next = faq?.next,
        previous = faq?.previous,
        results = faq?.results?.map { mapEntityToDbModel(it) })

    private fun mapEntityToDbModel(res: ResultsEntity?) = ResultsDto(
        id = res?.id, title = res?.title, content = res?.content
    )

    private fun mapDbModelToEntity(faq: FAQDto?) = FAQEntity(count = faq?.count,
        next = faq?.next,
        previous = faq?.previous,
        results = faq?.results?.map { mapDbModelToEntity(it) })

    private fun mapDbModelToEntity(res: ResultsDto?) = ResultsEntity(
        id = res?.id, title = res?.title, content = res?.content
    )

    fun mapRespDbModelToRespEntityForFaq(resp: Response<FAQDto?>) = if (resp.isSuccessful) {
        Response.success(mapDbModelToEntity(resp.body()))
    } else {
        resp.errorBody()?.let { Response.error(resp.code(), it) }
    }
    //endregion
}