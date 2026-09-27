package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkOutline
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.TurquoiseOnPrimary
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.TurquoiseSecondary

@Composable
fun HydrationControls(
    onAddWater: (Int) -> Unit,
    onOpenResetDialog: () -> Unit,
    onOpenCustomDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main Prominent Button: "+ 250 ml" (User Request)
        Button(
            onClick = { onAddWater(250) },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TurquoisePrimary,
                contentColor = TurquoiseOnPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .testTag("btn_add_250")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.LocalDrink,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ajouter 250 ml",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Preset shortcuts row (+100ml, +500ml, Custom)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SuggestionChip(
                onClick = { onAddWater(100) },
                label = { Text("+100 ml", fontWeight = FontWeight.Medium) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = DarkSurfaceVariant,
                    labelColor = TurquoisePrimary
                ),
                border = BorderStroke(1.dp, TurquoisePrimary.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("btn_chip_100")
            )

            SuggestionChip(
                onClick = { onAddWater(500) },
                label = { Text("+500 ml", fontWeight = FontWeight.Medium) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = DarkSurfaceVariant,
                    labelColor = TurquoiseSecondary
                ),
                border = BorderStroke(1.dp, TurquoiseSecondary.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("btn_chip_500")
            )

            SuggestionChip(
                onClick = onOpenCustomDialog,
                label = { Text("Autre...", fontWeight = FontWeight.Medium) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = DarkSurfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = BorderStroke(1.dp, DarkOutline),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1.1f)
                    .height(44.dp)
                    .testTag("btn_chip_custom")
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Button (User Request)
        OutlinedButton(
            onClick = onOpenResetDialog,
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_reset")
        ) {
            Icon(
                imageVector = Icons.Default.RestartAlt,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Réinitialiser la journée",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun ResetConfirmationDialog(
    isOpen: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Réinitialiser le suivi ?",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        },
        text = {
            Text(
                text = "Cette action remettra à zéro toute votre consommation d'eau enregistrée pour aujourd'hui.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier.testTag("btn_confirm_reset")
            ) {
                Text("Réinitialiser")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("btn_cancel_reset")
            ) {
                Text("Annuler", color = TurquoisePrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.testTag("dialog_reset")
    )
}

@Composable
fun CustomAmountDialog(
    isOpen: Boolean,
    amountInput: String,
    onAmountChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Ajouter une quantité personnalisée",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        },
        text = {
            Column {
                Text(
                    text = "Entrez la quantité consommée en millilitres :",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = amountInput,
                    onValueChange = onAmountChange,
                    singleLine = true,
                    suffix = { Text("ml", color = TurquoisePrimary, fontWeight = FontWeight.Bold) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onConfirm() }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TurquoisePrimary,
                        unfocusedBorderColor = DarkOutline,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_custom_amount")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TurquoisePrimary,
                    contentColor = TurquoiseOnPrimary
                ),
                modifier = Modifier.testTag("btn_confirm_custom_amount")
            ) {
                Text("Valider")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("btn_cancel_custom_amount")
            ) {
                Text("Annuler", color = TurquoisePrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.testTag("dialog_custom_amount")
    )
}
