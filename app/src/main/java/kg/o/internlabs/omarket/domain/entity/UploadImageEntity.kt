package kg.o.internlabs.omarket.domain.entity

import android.net.Uri

data class UploadImageEntity(
    val result: UploadImageResultEntity? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class UploadImageResultEntity(
    val url: String? = null,
    var isUploaded: Boolean = false,
    val path: Uri? = null
)
