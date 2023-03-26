package uz.devapp.elonuz.data.model

data class RegionModel(
    val id: Int,
    val name_uz: String,
    val districts: List<DistrictModel>,
    var active: Boolean = false
)

data class DistrictModel(
    val id: Int,
    val name_uz: String,
    var active: Boolean = false
)