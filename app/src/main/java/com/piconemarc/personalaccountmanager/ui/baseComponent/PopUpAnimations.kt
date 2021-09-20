package com.piconemarc.personalaccountmanager.ui.baseComponent

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.Black

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.selectorOffsetAnimation(selectedOperationOption: String): Modifier =
    this.composed {
        val popUpLeftSidePanelIconSelectorTransition =
            updateTransition(targetState = selectedOperationOption, label = "")
        val selectorOffset by popUpLeftSidePanelIconSelectorTransition.animateOffset(
            label = "",
            transitionSpec = {
                spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            }
        ) { selectedOperationOption_ ->
            when (selectedOperationOption_) {
                stringResource(R.string.payment) -> Offset(0f, 58f)
                stringResource(R.string.transfer) -> Offset(0f, 116f)
                else -> Offset(0f, 0f)
            }
        }
        this.offset(y = selectorOffset.y.dp)
    }


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.expandablePopUpOptionAnimation(): Modifier =
    this.composed {
        this
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            )
            .fillMaxSize()
    }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShowHidePopUpAnimation(
    isVisible: Boolean,
    content: @Composable (enterExitPopUpAnimationModifier: Modifier) -> Unit

) {

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 700
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 700
            )
        )
    ) {
        Column(
            modifier = Modifier
                .background(Black.copy(alpha = 0.7f))
                .fillMaxSize()
        ) {
            content(
                enterExitPopUpAnimationModifier = Modifier
                    .animateEnterExit(
                        enter = expandVertically(
                            animationSpec = tween(delayMillis = 200)
                        ),
                        exit = shrinkVertically(),
                    )
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandCollapsePopUpOptionAnimation(
    isVisible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = Modifier.expandablePopUpOptionAnimation()
    ) {
        content()
    }
}

