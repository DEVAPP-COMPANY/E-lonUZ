package uz.devapp.elonuz.data.model

data class AdsModel(
    val id: Int,
    val name: String,
    val main_image: String,
    val comment: String,
    val address: String,
    val phone: String,
    val price: Double,
    val images: List<String>,
)