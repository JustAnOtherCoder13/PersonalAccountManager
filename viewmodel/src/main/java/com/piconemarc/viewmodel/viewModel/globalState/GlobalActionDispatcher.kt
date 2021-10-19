package com.piconemarc.viewmodel.viewModel.globalState

import com.piconemarc.core.domain.interactor.account.GetAllAccountsInteractor
import com.piconemarc.core.domain.interactor.category.GetAllCategoriesInteractor
import com.piconemarc.viewmodel.viewModel.ActionDispatcher
import com.piconemarc.viewmodel.viewModel.DefaultStore
import com.piconemarc.viewmodel.viewModel.StoreSubscriber
import com.piconemarc.viewmodel.viewModel.UiAction
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpUtilsProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GlobalActionDispatcher @Inject constructor(
    override val store: DefaultStore<GlobalUtilProvider.GlobalVmState>,
    private val getAllAccountsInteractor: GetAllAccountsInteractor,
    private val getAllCategoriesInteractor: GetAllCategoriesInteractor
) : ActionDispatcher<UiAction, GlobalUtilProvider.GlobalVmState> {

    override val subscriber: StoreSubscriber<GlobalUtilProvider.GlobalVmState> =
        GlobalUtilProvider().globalStoreSubscriber

    //flow job
    private var getAllAccountsJob: Job? = null
    private var getAllCategoriesJob: Job? = null



    override fun dispatchAction(action: UiAction) {
        when (action) {

            //BaseAppScreen -----------------------------------------------------------------------------------
            is GlobalUtilProvider.GlobalAction.UpdateBaseAppScreenVmState ->
                updateBaseAppState(action)

            //Add pop up -----------------------------------------------------------------------------------------
            is AddOperationPopUpUtilsProvider.AddOperationPopUpAction -> {
                updateAddPopState(action)
                when (action){
                    is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.InitPopUp ->
                        getAllCategoriesJob = scope.launch {
                            getAllCategoriesInteractor.getAllCategoriesToDataUiModelList().collect {
                                updateAddPopState(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.UpdateCategoriesList(it))
                            }
                        }

                    is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ExpandTransferOption ->
                        getAllAccountsJob = scope.launch {
                            getAllAccountsInteractor.getAllAccountsToPresentationDataModel().collect {
                                updateAddPopState(AddOperationPopUpUtilsProvider.AddOperationPopUpAction.UpdateAccountList(it))
                            }}

                    is AddOperationPopUpUtilsProvider.AddOperationPopUpAction.ClosePopUp ->{
                        getAllAccountsJob?.cancel()
                        getAllCategoriesJob?.cancel()
                    }
                }
            }
        }
    }

    //implement actions -------------------------------------------------------------------------------------

    private fun updateBaseAppState(action: UiAction) {
        store.add(subscriber)
        store.dispatch(
            GlobalUtilProvider.GlobalAction.UpdateBaseAppScreenVmState(action)
        )
    }

    private fun updateAddPopState(action: UiAction) {
        store.add(subscriber)
        store.dispatch(
            GlobalUtilProvider.GlobalAction.UpdateAddPopUpState(action)
        )
    }
}