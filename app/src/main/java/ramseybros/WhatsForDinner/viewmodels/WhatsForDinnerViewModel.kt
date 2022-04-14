package ramseybros.WhatsForDinner.viewmodels


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import ramseybros.WhatsForDinner.data.Recipe
import ramseybros.WhatsForDinner.data.database.WhatsForDinnerRepository
import ramseybros.WhatsForDinner.util.CharacterWorker

import java.util.*


class WhatsForDinnerViewModel(
    private val whatsForDinnerRepository: WhatsForDinnerRepository,
    context: Context
) : I_WhatsForDinnerViewModel() {
    private val workManager = WorkManager.getInstance(context)
    private val workRequest = CharacterWorker.buildOneTimeWorkRequest()
    override val outputWorkerInfo: LiveData<WorkInfo> =
        workManager.getWorkInfoByIdLiveData(workRequest.id)

    override fun requestWebRecipes() {
        workManager.enqueueUniqueWork(
            CharacterWorker.UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    private val _apiRecipeListLiveData = MutableLiveData<MutableList<Recipe>>()

    private val _recipeIdLiveData =
        MutableLiveData<UUID>()

    private val _ingredientIdLiveData =
        MutableLiveData<String>()

    override val recipeListLiveData = whatsForDinnerRepository.getRecipes()
    override val ingredientListLiveData = whatsForDinnerRepository.getIngredients()


    override val recipeLiveData = Transformations.switchMap(_recipeIdLiveData) { recipeId ->
        whatsForDinnerRepository.getRecipe(recipeId)
    }

    override val ingredientLiveData =
        Transformations.switchMap(_ingredientIdLiveData) { ingredientId ->
            whatsForDinnerRepository.getIngredient(ingredientId)
        }


    override fun getRecipeIngredientList(recipeId: UUID) =
        whatsForDinnerRepository.getIngredientList(recipeId)

    override fun getRecipeUtensilList(recipeId: UUID) =
        whatsForDinnerRepository.getUtensilList(recipeId)

    override fun getApiRecipeList(): MutableLiveData<MutableList<Recipe>> = _apiRecipeListLiveData

    //TODO: Add API Functionality Functions

//    override fun addCharacter(character: WhatsForDinnerCharacter) {
//
//        whatsForDinnerRepository.addCharacter(character = character)
//    }
//
//    override fun loadCharacter(Id: UUID) {
//        _characterIdLiveData.value = Id
//    }
//
//
//    override fun generateRandomCharacter(): WhatsForDinnerCharacter {
//        return CharacterGenerator.generateRandomCharacter()
//    }


}