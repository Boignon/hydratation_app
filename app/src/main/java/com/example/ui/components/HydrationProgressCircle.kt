package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CircleTrackColor
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.TurquoiseSecondary
import com.example.ui.theme.WaterWaveLight

@Composable
fun HydrationProgressCircle(
    currentMl: Int,
    targetMl: Int,
    progress: Float,
    percentage: Int,
    remainingMl: Int,
    isGoalReached: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 260.dp,
    strokeWidth: Dp = 22.dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "progress_animation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .testTag("progress_circle")
    ) {
        // Subtle background glow disc
        Box(
            modifier = Modifier
                .size(size - strokeWidth * 2)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TurquoisePrimary.copy(alpha = if (isGoalReached) 0.15f else 0.08f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Custom Canvas for the progress ring
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()
            val canvasSize = this.size.minDimension
            val radius = (canvasSize - strokePx) / 2f
            val topLeft = Offset((this.size.width - canvasSize + strokePx) / 2f, (this.size.height - canvasSize + strokePx) / 2f)
            val arcSize = Size(canvasSize - strokePx, canvasSize - strokePx)

            // Background track
            drawArc(
                color = CircleTrackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Progress gradient arc
            if (animatedProgress > 0f) {
                val gradientBrush = Brush.sweepGradient(
                    0.0f to TurquoiseSecondary,
                    0.5f to TurquoisePrimary,
                    1.0f to WaterWaveLight,
                    center = Offset(this.size.width / 2f, this.size.height / 2f)
                )

                drawArc(
                    brush = gradientBrush,
                    startAngle = -90f,
                    sweepAngle = animatedProgress * 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }
        }

        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(28.dp)
        ) {
            // Icon at top
            Surface(
                shape = CircleShape,
                color = if (isGoalReached) TurquoisePrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isGoalReached) Icons.Rounded.CheckCircle else Icons.Filled.WaterDrop,
                        contentDescription = "Statut hydratation",
                        tint = if (isGoalReached) TurquoiseSecondary else TurquoisePrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Current / Target text
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$currentMl",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
                Text(
                    text = " ml",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = TurquoisePrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            Text(
                text = "sur $targetMl ml (2L)",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Percentage Badge
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isGoalReached) TurquoiseSecondary.copy(alpha = 0.25f) else TurquoisePrimary.copy(alpha = 0.15f),
                modifier = Modifier.shadow(0.dp)
            ) {
                Text(
                    text = if (isGoalReached) "100% • Atteint !" else "$percentage%",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isGoalReached) TurquoiseSecondary else TurquoisePrimary
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}
