package uz.devapp.elonuz.data.model.response

data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val error_code: Int,
    val data: T
)
