package com.piconemarc.personalaccountmanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.piconemarc.personalaccountmanager.ui.component.screen.PAMMainScreen
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accountDetailViewModel: AccountDetailViewModel by viewModels()

        setContent {
            PersonalAccountManagerTheme {
                Surface(
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.fillMaxSize()
                ) {
                    PAMMainScreen(accountDetailViewModel)
                }
            }
        }
    }
}