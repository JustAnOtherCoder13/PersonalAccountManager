package com.piconemarc.personalaccountmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.piconemarc.model.entity.TEST_OPERATION_MODEL
import com.piconemarc.personalaccountmanager.ui.component.popUp.PAMAddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationScreenEvent
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationScreenViewState

class MainActivity : ComponentActivity() {

   // private val deleteOperationPopUpScreenModel : ConfirmDeleteOperationPopUpScreenModel

    init {
        instance = this
        //deleteOperationPopUpScreenModel =  ConfirmDeleteOperationPopUpScreenModel()
    }

    companion object {
        private var instance: MainActivity? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            PersonalAccountManagerTheme {
                val testList by remember {
                    mutableStateOf(TEST_OPERATION_MODEL)
                }
                Surface(
                    color = MaterialTheme.colors.secondary
                ) {
                    Column() {
                        LazyColumn() {
                            items(testList) { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = item.operationName)
                                        Text(text = item.operationAmount.toString())
                                    }
                                    Button(
                                        onClick = {
                                           /* deleteOperationPopUpScreenModel.expand(
                                                operationModel = item
                                            )*/
                                        }
                                    ) { Text(text = "Click") }
                                }
                            }
                        }
                        Button(onClick = { AddOperationScreenEvent.initPopUp() }) {
                            Text(text = "add operation")
                        }
                    }
                    //PAMDeleteOperationPopUp(deleteOperationPopUpScreenModel)
                    PAMAddOperationPopUp()
                }
            }
        }
    }
}