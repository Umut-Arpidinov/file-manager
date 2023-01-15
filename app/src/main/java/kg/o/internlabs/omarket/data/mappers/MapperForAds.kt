package kg.o.internlabs.omarket.data.mappers

import kg.o.internlabs.omarket.data.remote.model.EditAdsDto
import kg.o.internlabs.omarket.data.remote.model.DetailsAdDto
import kg.o.internlabs.omarket.data.remote.model.ads.*
import kg.o.internlabs.omarket.domain.entity.EditAds
import kg.o.internlabs.omarket.domain.entity.DetailsAd
import kg.o.internlabs.omarket.domain.entity.ads.*
import retrofit2.Response

class MapperForAds {
    fun toDbModel(v: Ads?) = AdsDto(
        details = v?.details,
        errorCode = v?.errorCode,
        result = toDbModel(v?.result),
        resultCode = v?.resultCode
    )

    fun toDbModel(v: EditAds?) = EditAdsDto(
        details = v?.details,
        errorCode = v?.errorCode,
        result = toDbModel(v?.result),
        resultCode = v?.resultCode,
        adType = v?.adType,
        address = v?.address,
        category = v?.category,
        contractPrice = v?.contractPrice,
        currency = v?.currency,
        delivery = v?.delivery,
        description = v?.description,
        images = v?.images,
        latitude = v?.latitude,
        location = v?.location,
        longitude = v?.longitude,
        oMoneyPay = v?.oMoneyPay,
        price = v?.price,
        promotionType = toDbModel(v?.promotionType),
        telegramProfile = v?.telegramProfile,
        telegramProfileIsIdent = v?.telegramProfileIsIdent,
        title = v?.title,
        whatsappNum = v?.whatsappNum,
        whatsappNumIsIdent = v?.whatsappNumIsIdent
    )

    fun toDbModel(v: DetailsAd?) = DetailsAdDto(
        details = v?.details,
        errorCode = v?.errorCode,
        resultX = toDbModel(v?.resultX),
        resultCode = v?.resultCode
    )


    fun toDbModel(v: AdsByCategory?) = AdsByCategoryDto(
        mainFilter = toDbModel(v?.mainFilter)
    )
    fun toDbModel(v: AdsByFilter?) = AdsByFilterDto(
        mainFilters = toDbModel(v?.mainFilters)
    )
    private fun toDbModel(v: MainFilters?) = MainFiltersDto(
        q = v?.q
    )


    private fun toDbModel(v: MainFilter?) = MainFilterDto(
        orderBy = v?.orderBy,
        categoryId = v?.categoryId,
        q = v?.q

    )

    private fun toDbModel(v: Author?) = AuthorAdsDto(
        avatar = v?.avatar,
        blockType = v?.blockType,
        contactNumIsIdent = v?.contactNumIsIdent,
        contactNumber = v?.contactNumber,
        hasPromotion = v?.hasPromotion,
        id = v?.id,
        location = toDbModel(v?.location),
        msisdn = v?.msisdn,
        partnerType = v?.partnerType,
        rating = v?.rating,
        username = v?.username,
        verified = v?.verified
    )

    private fun toDbModel(v: CategoryAds?) = CategoryAdsDto(
        adType = v?.adType,
        categoryType = v?.categoryType,
        darkIcon = v?.darkIcon,
        darkIconImg = v?.darkIconImg,
        delivery = v?.delivery,
        filters = v?.filters,
        hasDynamicFilter = v?.hasDynamicFilter,
        hasMap = v?.hasMap,
        icon = v?.icon,
        iconImg = v?.iconImg,
        id = v?.id,
        isPopular = v?.isPopular,
        linkedCategory = v?.linkedCategory,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = toDbModel(v?.parent),
        parentFilters = v?.parentFilters,
        requiredPrice = v?.requiredPrice
    )

    private fun toDbModel(v: LocationAds?) = LocationAdsDto(
        id = v?.id,
        locationType = v?.locationType,
        name = v?.name,
        parent = v?.parent
    )

    private fun toDbModel(v: LocationX?) = LocationXDto(
        id = v?.id,
        isPopular = v?.isPopular,
        locationType = v?.locationType,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = v?.parent,
        searchByName = v?.searchByName
    )

    private fun toDbModel(v: MainResult?) = MainResultDto(
        count = v?.count,
        next = v?.next,
        previous = v?.previous,
        results = v?.results?.map { toDbModel(it) }
    )

    private fun toDbModel(v: ParentAds?) = ParentAdsDto(
        adType = v?.adType,
        categoryType = v?.categoryType,
        darkIcon = v?.darkIcon,
        darkIconImg = v?.darkIconImg,
        delivery = v?.delivery,
        filters = v?.filters,
        hasDynamicFilter = v?.hasDynamicFilter,
        hasMap = v?.hasMap,
        icon = v?.icon,
        iconImg = v?.iconImg,
        id = v?.id,
        isPopular = v?.isPopular,
        linkedCategory = v?.linkedCategory,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = v?.parent,
        parentFilters = v?.parentFilters,
        requiredPrice = v?.requiredPrice
    )

    private fun toDbModel(v: PromotionType?) = PromotionTypeAdsDto(
        description = v?.description,
        id = v?.id,
        img = v?.img,
        type = v?.type
    )

    private fun toDbModel(v: ResultX?) = ResultXDto(
        adType = v?.adType,
        address = v?.address,
        author = toDbModel(v?.author),
        authorId = v?.authorId,
        category = toDbModel(v?.category),
        commentary = v?.commentary,
        complaintCount = v?.complaintCount,
        contractPrice = v?.contractPrice,
        createdAt = v?.createdAt,
        currency = v?.currency,
        currencyUsd = v?.currencyUsd,
        delivery = v?.delivery,
        description = v?.description,
        detail = v?.detail,
        favorite = v?.favorite,
        filters = v?.filters,
        hasImage = v?.hasImage,
        id = v?.id,
        images = v?.images,
        isOwn = v?.isOwn,
        latitude = v?.latitude,
        location = toDbModel(v?.location),
        longitude = v?.longitude,
        minifyImages = v?.minifyImages,
        moderatorId = v?.moderatorId,
        modifiedAt = v?.modifiedAt,
        numOfViewsInFeed = v?.numOfViewsInFeed,
        oMoneyPay = v?.oMoneyPay,
        oldPrice = v?.oldPrice,
        openingAt = v?.openingAt,
        price = v?.price,
        priceSort = v?.priceSort,
        promotionType = toDbModel(v?.promotionType),
        publishedAt = v?.publishedAt,
        removedAt = v?.removedAt,
        reviewCount = v?.reviewCount,
        status = v?.status,
        telegramProfile = v?.telegramProfile,
        telegramProfileIsIdent = v?.telegramProfileIsIdent,
        title = v?.title,
        uuid = v?.uuid,
        viewCount = v?.viewCount,
        whatsappNum = v?.whatsappNum,
        whatsappNumIsIdent = v?.whatsappNumIsIdent
    )
    //endregion

    //region *toEntity
    private fun toEntity(v: AdsDto?) = Ads(
        details = v?.details,
        errorCode = v?.errorCode,
        result = toEntity(v?.result),
        resultCode = v?.resultCode
    )
    fun toEntity(v: AdsByFilterDto?) = AdsByFilter(
        mainFilters = toEntity(v?.mainFilters)
    )
    private fun toEntity(v: MainFiltersDto?) = MainFilters(
        q = v?.q
    )


    fun toEntity(v: EditAdsDto?) = EditAds(
        details = v?.details,
        errorCode = v?.errorCode,
        result = toEntity(v?.result),
        resultCode = v?.resultCode,
        adType = v?.adType,
        address = v?.address,
        category = v?.category,
        contractPrice = v?.contractPrice,
        currency = v?.currency,
        delivery = v?.delivery,
        description = v?.description,
        images = v?.images,
        latitude = v?.latitude,
        location = v?.location,
        longitude = v?.longitude,
        oMoneyPay = v?.oMoneyPay,
        price = v?.price,
        promotionType = toEntity(v?.promotionType),
        telegramProfile = v?.telegramProfile,
        telegramProfileIsIdent = v?.telegramProfileIsIdent,
        title = v?.title,
        whatsappNum = v?.whatsappNum,
        whatsappNumIsIdent = v?.whatsappNumIsIdent
    )


    fun toEntity(v: AdsByCategoryDto?) = AdsByCategory(
        mainFilter = toEntity(v?.mainFilter)
    )

    private fun toEntity(v: MainFilterDto?) = MainFilter(
        orderBy = v?.orderBy,
        categoryId = v?.categoryId,
        q =v?.q
    )


    private fun toEntity(v: AuthorAdsDto?) = Author(
        avatar = v?.avatar,
        blockType = v?.blockType,
        contactNumIsIdent = v?.contactNumIsIdent,
        contactNumber = v?.contactNumber,
        hasPromotion = v?.hasPromotion,
        id = v?.id,
        location = toEntity(v?.location),
        msisdn = v?.msisdn,
        partnerType = v?.partnerType,
        rating = v?.rating,
        username = v?.username,
        verified = v?.verified
    )

    private fun toEntity(v: CategoryAdsDto?) = CategoryAds(
        adType = v?.adType,
        categoryType = v?.categoryType,
        darkIcon = v?.darkIcon,
        darkIconImg = v?.darkIconImg,
        delivery = v?.delivery,
        filters = v?.filters,
        hasDynamicFilter = v?.hasDynamicFilter,
        hasMap = v?.hasMap,
        icon = v?.icon,
        iconImg = v?.iconImg,
        id = v?.id,
        isPopular = v?.isPopular,
        linkedCategory = v?.linkedCategory,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = toEntity(v?.parent),
        parentFilters = v?.parentFilters,
        requiredPrice = v?.requiredPrice
    )

    private fun toEntity(v: LocationAdsDto?) = LocationAds(
        id = v?.id,
        locationType = v?.locationType,
        name = v?.name,
        parent = v?.parent
    )

    private fun toEntity(v: LocationXDto?) = LocationX(
        id = v?.id,
        isPopular = v?.isPopular,
        locationType = v?.locationType,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = v?.parent,
        searchByName = v?.searchByName
    )

    private fun toEntity(v: MainResultDto?) = MainResult(
        count = v?.count,
        next = v?.next,
        previous = v?.previous,
        results = v?.results?.map { toEntity(it) }
    )

    private fun toEntity(v: ParentAdsDto?) = ParentAds(
        adType = v?.adType,
        categoryType = v?.categoryType,
        darkIcon = v?.darkIcon,
        darkIconImg = v?.darkIconImg,
        delivery = v?.delivery,
        filters = v?.filters,
        hasDynamicFilter = v?.hasDynamicFilter,
        hasMap = v?.hasMap,
        icon = v?.icon,
        iconImg = v?.iconImg,
        id = v?.id,
        isPopular = v?.isPopular,
        linkedCategory = v?.linkedCategory,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = v?.parent,
        parentFilters = v?.parentFilters,
        requiredPrice = v?.requiredPrice
    )

    private fun toEntity(v: PromotionTypeAdsDto?) = PromotionType(
        description = v?.description,
        id = v?.id,
        img = v?.img,
        type = v?.type
    )

    private fun toEntity(v: ResultXDto?) = ResultX(
        adType = v?.adType,
        address = v?.address,
        author = toEntity(v?.author),
        authorId = v?.authorId,
        category = toEntity(v?.category),
        commentary = v?.commentary,
        complaintCount = v?.complaintCount,
        contractPrice = v?.contractPrice,
        createdAt = v?.createdAt,
        currency = v?.currency,
        currencyUsd = v?.currencyUsd,
        delivery = v?.delivery,
        description = v?.description,
        detail = v?.detail,
        favorite = v?.favorite,
        filters = v?.filters,
        hasImage = v?.hasImage,
        id = v?.id,
        images = v?.images,
        isOwn = v?.isOwn,
        latitude = v?.latitude,
        location = toEntity(v?.location),
        longitude = v?.longitude,
        minifyImages = v?.minifyImages,
        moderatorId = v?.moderatorId,
        modifiedAt = v?.modifiedAt,
        numOfViewsInFeed = v?.numOfViewsInFeed,
        oMoneyPay = v?.oMoneyPay,
        oldPrice = v?.oldPrice,
        openingAt = v?.openingAt,
        price = v?.price,
        priceSort = v?.priceSort,
        promotionType = toEntity(v?.promotionType),
        publishedAt = v?.publishedAt,
        removedAt = v?.removedAt,
        reviewCount = v?.reviewCount,
        status = v?.status,
        telegramProfile = v?.telegramProfile,
        telegramProfileIsIdent = v?.telegramProfileIsIdent,
        title = v?.title,
        uuid = v?.uuid,
        viewCount = v?.viewCount,
        whatsappNum = v?.whatsappNum,
        whatsappNumIsIdent = v?.whatsappNumIsIdent
    )

    private fun toEntity(v: DetailsAdDto?) = DetailsAd(
        details = v?.details,
        errorCode = v?.errorCode,
        resultX = toEntity(v?.resultX),
        resultCode = v?.resultCode
    )

    fun toRespEntityForAds(resp: Response<AdsDto?>) =
        if (resp.isSuccessful) {
            Response.success(toEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }

    fun toRespEntityForEditAd(resp: Response<EditAdsDto?>) =
        if (resp.isSuccessful) {
            Response.success(toEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }

    fun toRespEntityForDetailedAd(resp: Response<DetailsAdDto?>) =
        if (resp.isSuccessful) {
            Response.success(toEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }
}