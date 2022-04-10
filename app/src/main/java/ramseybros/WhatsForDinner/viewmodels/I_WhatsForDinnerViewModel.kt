package ramseybros.WhatsForDinner.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import ramseybros.WhatsForDinner.data.*
import java.util.*

abstract class I_WhatsForDinnerViewModel: ViewModel(){
    abstract val recipeListLiveData: LiveData<List<Recipe>>
    abstract val recipeLiveData: LiveData<Recipe?>

    abstract val ingredientListLiveData: LiveData<List<Ingredient>>
    abstract val ingredientLiveData: LiveData<Ingredient?>
    abstract val outputWorkerInfo: LiveData<WorkInfo>


//    abstract fun addCharacter(character: WhatsForDinnerCharacter)
//    abstract fun loadCharacter(Id: UUID)
//    abstract fun generateRandomCharacter(): WhatsForDinnerCharacter
//    abstract fun requestWebCharacter()
}