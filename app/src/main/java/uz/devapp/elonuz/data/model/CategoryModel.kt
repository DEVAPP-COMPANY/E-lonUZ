package uz.devapp.elonuz.data.model

data class CategoryModel(
    val id: Int,
    val parent_id: Int,
    val title: String,
    val image: String,
    var active: Boolean = false
): java.io.Serializable
