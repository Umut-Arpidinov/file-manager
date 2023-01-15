package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadImageToAdUseCase @Inject constructor(
    private val crudAdsRepository: CRUDAdsRepository
) {
    operator fun invoke(token: String, loader: File?, uuid: String) =
        crudAdsRepository.uploadImageToAd(token, getBody(loader), uuid)

    private fun getBody(file: File?): MultipartBody.Part {
        val reqFile = file!!.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }
}