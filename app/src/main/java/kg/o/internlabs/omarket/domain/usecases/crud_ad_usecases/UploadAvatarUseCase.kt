package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import android.database.Cursor
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val crudAdsRepository: CRUDAdsRepository
) {
    operator fun invoke(token: String, loader: CursorLoader, uuid: String) =
        crudAdsRepository.uploadImageToAd(token, getBody(loader), uuid)

    private fun getBody(loader: CursorLoader): MultipartBody.Part {
        val file = getRealPathFromURI(loader)?.let { File(it) }
        val reqFile = file!!.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }

    private fun getRealPathFromURI(loader: CursorLoader): String? {
        val cursor: Cursor? = loader.loadInBackground()
        val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result: String? = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return result
    }
}