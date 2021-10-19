package com.piconemarc.viewmodel.viewModel

import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import com.piconemarc.viewmodel.viewModel.globalState.GlobalActionDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val globalActionDispatcher: GlobalActionDispatcher
) : BaseScreenViewModel() {

    override fun dispatchAction(action: UiAction){
        when(action){
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction -> {
                globalActionDispatcher.dispatchAction(action)
            }
        }
    }

}

