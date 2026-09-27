package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.CustomAmountDialog
import com.example.ui.components.HydrationControls
import com.example.ui.components.HydrationHistorySection
import com.example.ui.components.HydrationProgressCircle
import com.example.ui.components.ResetConfirmationDialog
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.TurquoiseSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HydrationScreen(
    viewModel: HydrationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val formattedDate = remember {
        val sdf = SimpleDateFormat("EEEE d MMMM", Locale.FRENCH)
        val raw = sdf.format(Date())
        raw.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.FRENCH) else it.toString() }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = TurquoisePrimary.copy(alpha = 0.15f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Opacity,
                                    contentDescription = null,
                                    tint = TurquoisePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Suivi d'Hydratation",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.setResetDialogOpen(true) },
                        modifier = Modifier.testTag("top_bar_btn_reset")
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = "Réinitialiser",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DarkSurface
                )
            )
        },
        containerColor = DarkBackground,
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .widthIn(max = 600.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Date subtitle
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("date_label")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Celebration Card when goal reached
                AnimatedVisibility(
                    visible = uiState.isGoalReached,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkSurfaceVariant
                        ),
                        border = BorderStroke(1.dp, TurquoiseSecondary.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .testTag("card_celebration")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(TurquoiseSecondary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Celebration,
                                    contentDescription = null,
                                    tint = TurquoiseSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Objectif de 2L atteint !",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TurquoiseSecondary
                                    )
                                )
                                Text(
                                    text = "Excellent travail ! Vous avez bu vos 2 litres recommandés aujourd'hui.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }

                // Central Circular Progress Gauge
                HydrationProgressCircle(
                    currentMl = uiState.currentMl,
                    targetMl = uiState.targetMl,
                    progress = uiState.progress,
                    percentage = uiState.percentage,
                    remainingMl = uiState.remainingMl,
                    isGoalReached = uiState.isGoalReached,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                // Remaining status text
                if (!uiState.isGoalReached) {
                    Text(
                        text = "Encore ${uiState.remainingMl} ml pour atteindre votre objectif de 2L",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TurquoisePrimary,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 20.dp)
                            .testTag("remaining_status_text")
                    )
                } else {
                    Text(
                        text = "Objectif quotidien validé avec succès !",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TurquoiseSecondary,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 20.dp)
                            .testTag("remaining_status_text")
                    )
                }

                // Controls Section: "+250ml", quick chips, Reset
                HydrationControls(
                    onAddWater = { amount -> viewModel.addWater(amount) },
                    onOpenResetDialog = { viewModel.setResetDialogOpen(true) },
                    onOpenCustomDialog = { viewModel.setCustomDialogOpen(true) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(28.dp))

                // History Section
                HydrationHistorySection(
                    records = uiState.records,
                    onDeleteRecord = { id -> viewModel.deleteRecord(id) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Reset Confirmation Dialog
    ResetConfirmationDialog(
        isOpen = uiState.isResetDialogOpen,
        onConfirm = { viewModel.reset() },
        onDismiss = { viewModel.setResetDialogOpen(false) }
    )

    // Custom Amount Dialog
    CustomAmountDialog(
        isOpen = uiState.isCustomDialogOpen,
        amountInput = uiState.customAmountInput,
        onAmountChange = { viewModel.updateCustomAmountInput(it) },
        onConfirm = { viewModel.addCustomAmount() },
        onDismiss = { viewModel.setCustomDialogOpen(false) }
    )
}
