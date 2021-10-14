package com.piconemarc.viewmodel.viewModel

import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpActionDispatcher
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val addOperationPopUpActionDispatcher: AddOperationPopUpActionDispatcher
) : BaseScreenViewModel() {

    override fun dispatchAction(action: UiAction){
        when(action){
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction -> addOperationPopUpActionDispatcher.dispatchAction(action)
        }
    }

}

