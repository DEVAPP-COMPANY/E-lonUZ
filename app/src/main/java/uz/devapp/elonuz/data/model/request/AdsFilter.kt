package uz.devapp.elonuz.data.model.request

data class AdsFilter(
    var region_id: Int = 0,
    var district_id: Int = 0,
    var category_id: Int = 0,
    var limit: Int = 0,
    var keyword: String = "",
)