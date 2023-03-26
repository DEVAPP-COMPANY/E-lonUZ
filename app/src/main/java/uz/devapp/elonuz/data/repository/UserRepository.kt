package uz.devapp.elonuz.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.devapp.elonuz.data.api.NetworkObject
import uz.devapp.elonuz.data.model.request.AdsFilter
import uz.devapp.elonuz.data.model.request.LoginRequest
import uz.devapp.elonuz.data.model.request.RegistrationRequest
import uz.devapp.elonuz.data.repository.sealed.DataResult
import java.io.File


class UserRepository : BaseRepository() {
    val api = NetworkObject.getClientInstance()

    suspend fun getCategories() = withContext(Dispatchers.IO) {
        try {
            wrapResponse(api.getCategories())
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getRegions() = withContext(Dispatchers.IO) {
        try {
            wrapResponse(api.getRegions())
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun getAds(filter: AdsFilter) = withContext(Dispatchers.IO) {
        try {
            wrapResponse(api.getAds(filter))
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun registration(request: RegistrationRequest) = withContext(Dispatchers.IO) {
        try {
            wrapResponse(api.registration(request))
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun login(request: LoginRequest) = withContext(Dispatchers.IO) {
        try {
            wrapResponse(api.login(request))
        } catch (e: Exception) {
            return@withContext DataResult.Error(e.localizedMessage)
        }
    }

    suspend fun addAds(
        mainImage: String,
        title: String,
        comment: String,
        address: String,
        phone: String,
        price: Double,
        categoryId: Int,
        districtId: Int,
    ) = withContext(Dispatchers.IO) {
        try {
            val file = File(mainImage)

            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

            val body = MultipartBody.Part.createFormData("main_image", file.name, requestFile)

            val titleBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                title
            )

            val commentBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                comment
            )

            val addressBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                address
            )

            val phoneBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                phone
            )

            val priceBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                price.toString()
            )

            val categoryIdBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                categoryId.toString()
            )

            val districtIdBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                districtId.toString()
            )

            wrapResponse(api.addAds(body, categoryIdBody, districtIdBody, districtIdBody, titleBody, commentBody, priceBody, addressBody, phoneBody))

        } catch (e: Exception) {
            return@withContext DataResult.Error(e?.localizedMessage ?: "Unknown Error")
        }
    }

}