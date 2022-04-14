package ramseybros.WhatsForDinner.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ramseybros.WhatsForDinner.data.database.WhatsForDinnerRepository

class WhatsForDinnerFactory(private val context: Context) : ViewModelProvider.Factory {
    fun getViewModelClass() = WhatsForDinnerViewModel::class.java
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(getViewModelClass()))
            return modelClass
                .getConstructor(WhatsForDinnerRepository::class.java, Context::class.java)
                .newInstance(WhatsForDinnerRepository.getInstance(context), context)
        throw IllegalArgumentException("Unknown ViewModel")
    }
}