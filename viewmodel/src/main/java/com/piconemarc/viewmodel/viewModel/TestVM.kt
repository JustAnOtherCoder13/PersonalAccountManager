package com.piconemarc.viewmodel.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.core.domain.interactor.operation.AddNewOperationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestVM @Inject constructor(
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor
) : ViewModel() {

    fun getAllCategories(){
        viewModelScope.launch {
            getAllCategoriesInteractor.getAllCategories().collect {
                Log.i("TAG", "getAllCategories: $it")
            }
        }
    }
}