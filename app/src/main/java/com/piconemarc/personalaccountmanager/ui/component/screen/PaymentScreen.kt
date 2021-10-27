package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AccountPostIt
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AccountPostItBackground
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AccountPostItTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber

@Composable
fun PaymentScreen(){
    val paymentForAccount = listOf("one", "two")
    VerticalDispositionSheet(
        body = {
               LazyColumn(modifier = Modifier
                   .fillMaxWidth()
                   .padding(horizontal = RegularMarge),
                   horizontalAlignment = Alignment.CenterHorizontally
               ){
                   items(
                       //todo change with own state
                       AppSubscriber.AppUiState.myAccountScreenUiState.allAccounts
                   )
                   {
                       Box(
                           modifier = Modifier
                               .width(250.dp)
                               .height(150.dp)
                               .padding(vertical = RegularMarge)
                       ){
                           AccountPostItBackground(this)
                           VerticalDispositionSheet(
                               header = {
                                   Column() {
                                       AccountPostItTitle(
                                           account = it.name,
                                           onDeleteAccountButtonClicked = {  }
                                       )
                                   }

                               },
                               body = {
                                      Column(modifier = Modifier.fillMaxWidth()) {
                                          paymentForAccount.forEachIndexed { index, s ->
                                              Row(
                                                  modifier = Modifier
                                                      .fillMaxWidth()
                                                      .padding(horizontal = LittleMarge)
                                                      .background(color = if (index % 2 == 0) PastelYellowLight else Color.Transparent)
                                              ) {
                                                      Text(text = s, modifier = Modifier.weight(1.5f))
                                                      Text(text = s, modifier = Modifier.weight(.5f))
                                              }
                                          }
                                      }
                               },
                               footer = {
                                   Column(modifier = Modifier.fillMaxWidth()) {
                                       Divider(
                                           color = MaterialTheme.colors.onSecondary,
                                           thickness = ThinBorder,
                                           modifier = Modifier
                                               .fillMaxWidth()
                                               .padding(horizontal = LittleMarge)
                                       )
                                       Text(text = "Total = xxxxxx")
                                   }

                               }
                           )


                       }
                   }

               }
        }
    )
}