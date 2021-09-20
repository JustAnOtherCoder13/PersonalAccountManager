package com.piconemarc.personalaccountmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.piconemarc.personalaccountmanager.ui.baseComponent.AddOperationPopUp
import com.piconemarc.personalaccountmanager.ui.baseComponent.DeleteOperationPopUp
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalAccountManagerTheme {
                var showPopUp: Boolean by remember {
                    mutableStateOf(false)
                }
                Surface(
                    color = MaterialTheme.colors.secondary
                ) {
                        Button(onClick = {
                            showPopUp = true
                        }) {
                            Text(text = "Click")
                        }
                        AddOperationPopUp(
                            showAddOperationPopUp = showPopUp,
                            onDismiss = { showPopUp = false },
                            onAddOperation = {}
                        )
                        DeleteOperationPopUp(
                            onDeleteOperation = { },
                            onDismiss = { showPopUp = false },
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