package ramseybros.WhatsForDinner.data

data class RecipeSearchModelState(
    val searchText: String = "",
    val users: List<Recipe> =  emptyList(),
    val showProgressBar: Boolean = false
) {

    companion object {
        val Empty = RecipeSearchModelState()
    }

}