package kg.o.internlabs.omarket.data.mappers

import kg.o.internlabs.omarket.data.remote.model.ads.*
import kg.o.internlabs.omarket.domain.entity.ads.*
import retrofit2.Response

class MapperForAds {

    //region toDomain
    fun toDbModel(v: Ads?) = AdsDto(
        details = v?.details,
        errorCode = v?.errorCode,
        result = toDbModel(v?.result),
        resultCode = v?.resultCode
    )

    fun toDbModel(v: Author?) = AuthorAdsDto(
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

    fun toDbModel(v: CategoryAds?) = CategoryAdsDto(
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

    fun toDbModel(v: LocationAds?) = LocationAdsDto(
        id = v?.id,
        locationType = v?.locationType,
        name = v?.name,
        parent = v?.parent
    )

    fun toDbModel(v: LocationX?) = LocationXDto(
        id = v?.id,
        isPopular = v?.isPopular,
        locationType = v?.locationType,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = v?.parent,
        searchByName = v?.searchByName
    )

    fun toDbModel(v: MainResult?) = MainResultDto(
        count = v?.count,
        next = v?.next,
        previous = v?.previous,
        results = v?.results?.map { toDbModel(it) }
    )

    fun toDbModel(v: ParentAds?) = ParentAdsDto(
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

    fun toDbModel(v: PromotionType?) = PromotionTypeAdsDto(
        description = v?.description,
        id = v?.id,
        img = v?.img,
        type = v?.type
    )

    fun toDbModel(v: ResultX?) = ResultXDto(
        adType = v?.adType,
        address = v?.address,
        author = toDbModel(v?.author),
        authorId = v?.authorId,
        category = toDbModel(v?.category),
        commentary= v?.commentary,
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
    fun toEntity(v: AdsDto?) = Ads(
        details = v?.details,
        errorCode = v?.errorCode,
        result = toEntity(v?.result),
        resultCode = v?.resultCode
    )

    fun toEntity(v: AuthorAdsDto?) = Author(
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

    fun toEntity(v: CategoryAdsDto?) = CategoryAds(
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

    fun toEntity(v: LocationAdsDto?) = LocationAds(
        id = v?.id,
        locationType = v?.locationType,
        name = v?.name,
        parent = v?.parent
    )

    fun toEntity(v: LocationXDto?) = LocationX(
        id = v?.id,
        isPopular = v?.isPopular,
        locationType = v?.locationType,
        name = v?.name,
        orderNum = v?.orderNum,
        parent = v?.parent,
        searchByName = v?.searchByName
    )

    fun toEntity(v: MainResultDto?) = MainResult(
        count = v?.count,
        next = v?.next,
        previous = v?.previous,
        results = v?.results?.map { toEntity(it) }
    )

    fun toEntity(v: ParentAdsDto?) = ParentAds(
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

    fun toEntity(v: PromotionTypeAdsDto?) = PromotionType(
        description = v?.description,
        id = v?.id,
        img = v?.img,
        type = v?.type
    )

    fun toEntity(v: ResultXDto?) = ResultX(
        adType = v?.adType,
        address = v?.address,
        author = toEntity(v?.author),
        authorId = v?.authorId,
        category = toEntity(v?.category),
        commentary= v?.commentary,
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

    fun toRespEntityForAds(resp: Response<AdsDto?>) =
        if (resp.isSuccessful) {
            Response.success(toEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }
    // endregion
}