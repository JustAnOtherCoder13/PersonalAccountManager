package com.piconemarc.personalaccountmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.baseComponent.DeleteOperationPopUp
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.AddOperationLeftSideIconEvent
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.AddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.AddOperationPopUpState
import com.piconemarc.personalaccountmanager.ui.baseComponent.addOperationPopUp.addOperationLeftSideIconEventManager
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalAccountManagerTheme {
                var addOperationPopUpState by remember {
                    mutableStateOf(AddOperationPopUpState.COLLAPSED)
                }
                Surface(
                    color = MaterialTheme.colors.secondary
                ) {
                        Button(onClick = {
                            addOperationPopUpState = AddOperationPopUpState.EXPANDED
                            addOperationLeftSideIconEventManager(AddOperationLeftSideIconEvent.OnInitPopUp())
                        },
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        ) {
                            Text(text = "Click")
                        }
                        AddOperationPopUp(
                            addOperationPopUpState = addOperationPopUpState,
                            onDismiss = { addOperationPopUpState = AddOperationPopUpState.COLLAPSED },
                            onAddOperation = {}
                        )
                        DeleteOperationPopUp(
                            onDeleteOperation = { },
                            onDismiss = { addOperationPopUpState = AddOperationPopUpState.COLLAPSED },
                            showDeleteOperationPopUp = false,
                            operationName = "test operation",
                            operationAmount = 50.00
                        )
                    }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PersonalAccountManagerTheme {
        Greeting("Android")

    }
}