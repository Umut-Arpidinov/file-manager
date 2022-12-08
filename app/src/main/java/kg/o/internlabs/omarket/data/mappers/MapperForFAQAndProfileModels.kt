package kg.o.internlabs.omarket.data.mappers

import kg.o.internlabs.omarket.data.remote.model.*
import kg.o.internlabs.omarket.domain.entity.*
import retrofit2.Response

class MapperForFAQAndProfileModels {

    //region Faq

    private fun mapDbModelToEntity(faq: FAQDto?) = FAQEntity(
        count = faq?.count,
        next = faq?.next,
        previous = faq?.previous,
        results = faq?.results?.map { mapDbModelToEntity(it) })

    private fun mapDbModelToEntity(res: ResultsDto?) = ResultsEntity(
        id = res?.id,
        title = res?.title,
        content = res?.content
    )

    fun mapRespDbModelToRespEntityForFaq(resp: Response<FAQDto?>) = if (resp.isSuccessful) {
        Response.success(mapDbModelToEntity(resp.body()))
    } else {
        resp.errorBody()?.let { Response.error(resp.code(), it) }
    }
    //endregion

    //region MyAds
    fun mapEntityToDbModel(faq: MyAdsEntity?) = MyAdsDto(
        count = faq?.count,
        next = faq?.next,
        previous = faq?.previous,
        result = mapEntityToDbModel(faq?.result),
        statuses = faq?.statuses
    )

    private fun mapEntityToDbModel(res: MyAdsResultEntity?) = MyAdsResultDto(
        next = res?.next,
        previous = res?.previous,
        count = res?.count,
        results = res?.results?.map { mapEntityToDbModel(it) }
    )

    private fun mapEntityToDbModel(res: MyAdsResultsEntity?) = MyAdsResultsDto(
        promotionType = mapEntityToDbModel(res?.promotionType),
        address = res?.address,
        author = mapEntityToDbModel(res?.author),
        latitude = res?.latitude,
        currencyUsd = res?.currencyUsd,
        description = res?.description,
        minifyImages = res?.minifyImages,
        title = res?.title,
        contractPrice = res?.contractPrice,
        uuid = res?.uuid,
        oMoneyPay = res?.oMoneyPay,
        price = res?.price,
        currency = res?.currency,
        location = mapEntityToDbModel(res?.location),
        id = res?.id,
        modifiedAt = res?.modifiedAt,
        category = mapEntityToDbModel(res?.category),
        favorite = res?.favorite,
        viewCount = res?.viewCount,
        longitude = res?.longitude,
        status = res?.status,
        isOwn = res?.isOwn
    )

    private fun mapEntityToDbModel(promotionType: PromotionTypeEntity?) =
        PromotionTypeDto(type = promotionType?.type)

    private fun mapEntityToDbModel(author: AuthorEntity?) =
        AuthorDto(verified = author?.verified)

    private fun mapEntityToDbModel(location: LocationEntity?) =
        LocationDto(name = location?.name)

    private fun mapEntityToDbModel(category: CategoryEntity?) =
        CategoryDto(name = category?.name)


    private fun mapDbModelToEntity(faq: MyAdsDto?) = MyAdsEntity(
        count = faq?.count,
        next = faq?.next,
        previous = faq?.previous,
        result = mapDbModelToEntity(faq?.result),
        statuses = faq?.statuses
    )

    private fun mapDbModelToEntity(res: MyAdsResultDto?) = MyAdsResultEntity(
        next = res?.next,
        previous = res?.previous,
        count = res?.count,
        results = res?.results?.map { mapDbModelToEntity(it) }
    )

    private fun mapDbModelToEntity(res: MyAdsResultsDto?) = MyAdsResultsEntity(
        promotionType = mapDbModelToEntity(res?.promotionType),
        address = res?.address,
        author = mapDbModelToEntity(res?.author),
        latitude = res?.latitude,
        currencyUsd = res?.currencyUsd,
        description = res?.description,
        minifyImages = res?.minifyImages,
        title = res?.title,
        contractPrice = res?.contractPrice,
        uuid = res?.uuid,
        oMoneyPay = res?.oMoneyPay,
        price = res?.price,
        currency = res?.currency,
        location = mapDbModelToEntity(res?.location),
        id = res?.id,
        modifiedAt = res?.modifiedAt,
        category = mapDbModelToEntity(res?.category),
        favorite = res?.favorite,
        viewCount = res?.viewCount,
        longitude = res?.longitude,
        status = res?.status,
        isOwn = res?.isOwn
    )

    private fun mapDbModelToEntity(promotionType: PromotionTypeDto?) =
        PromotionTypeEntity(type = promotionType?.type)

    private fun mapDbModelToEntity(author: AuthorDto?) =
        AuthorEntity(verified = author?.verified)

    private fun mapDbModelToEntity(location: LocationDto?) =
        LocationEntity(name = location?.name)

    private fun mapDbModelToEntity(category: CategoryDto?) =
        CategoryEntity(name = category?.name)

    fun mapRespDbModelToRespEntityForMyAds(resp: Response<MyAdsDto?>) = if (resp.isSuccessful) {
        Response.success(mapDbModelToEntity(resp.body()))
    } else {
        resp.errorBody()?.let { Response.error(resp.code(), it) }
    }
    // endregion

    //region  for avatar

     private fun mapDbModelToEntity(ava: AvatarDto?) = AvatarEntity(
        result = mapDbModelToEntity(ava?.result),
        resultCode = ava?.resultCode,
        details = ava?.details,
        errorCode = ava?.errorCode
    )

    private fun mapDbModelToEntity(res: AvatarResultDto?) = AvatarResultEntity(
       url = res?.url
    )

    fun mapRespDbModelToRespEntityForAvatar(resp: Response<AvatarDto?>) = if (resp.isSuccessful) {
        Response.success(mapDbModelToEntity(resp.body()))
    } else {
        resp.errorBody()?.let { Response.error(resp.code(), it) }
    }

    private fun mapDbModelToEntity(ava: AvatarDelDto?) = AvatarDelEntity(
        result = ava?.result,
        resultCode = ava?.resultCode,
        details = ava?.details,
        errorCode = ava?.errorCode
    )

    fun mapRespDbModelToRespEntityForAvatarDel(resp: Response<AvatarDelDto?>) =
        if (resp.isSuccessful) {
        Response.success(mapDbModelToEntity(resp.body()))
    } else {
        resp.errorBody()?.let { Response.error(resp.code(), it) }
    }
    //endregion
}