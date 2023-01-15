package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface CRUDAdsRepository {
    fun getInitiatedAnAD(token: String): Flow<ApiState<InitiateAdEntity>>

    fun uploadImageToAd(token: String, body: MultipartBody.Part, uuid: String):
            Flow<ApiState<UploadImageEntity>>

    fun deleteImageFromAd(token: String, body: DeletedImageUrlEntity, uuid: String):
            Flow<ApiState<DeleteImageEntity>>

    fun editAnAd(token: String, editAds: EditAds, uuid: String): Flow<ApiState<EditAds>>
}