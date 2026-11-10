package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

enum class FridayVisualState {
    IDLE,
    LISTENING,
    THINKING,
    SPEAKING,
    WORKING
}

@Composable
fun FridayAnimatedPresence(
    state: FridayVisualState = FridayVisualState.IDLE,
    modifier: Modifier = Modifier,
    sizeDp: Int = 56,
    onPresenceClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "friday_presence_transition")

    // Rotation angle
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (state) {
                    FridayVisualState.THINKING, FridayVisualState.WORKING -> 2500
                    FridayVisualState.SPEAKING -> 4000
                    FridayVisualState.LISTENING -> 3000
                    else -> 7000
                },
                easing = LinearEasing
            )
        ),
        label = "rotation"
    )

    // Pulse scale
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (state) {
                    FridayVisualState.SPEAKING -> 500
                    FridayVisualState.THINKING -> 800
                    FridayVisualState.LISTENING -> 700
                    else -> 1800
                },
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Dynamic state colors
    val coreColor = when (state) {
        FridayVisualState.LISTENING -> AccentAmber
        FridayVisualState.THINKING -> AccentPurple
        FridayVisualState.SPEAKING -> AccentGreen
        FridayVisualState.WORKING -> CyanGlow
        FridayVisualState.IDLE -> CyanPrimary
    }

    Box(
        modifier = modifier
            .size(sizeDp.dp)
            .clip(CircleShape)
            .clickable { onPresenceClick() }
            .testTag("friday_animated_presence"),
        contentAlignment = Alignment.Center
    ) {
        // Futuristic Hologram Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val baseRadius = (size.minDimension / 2f) * 0.82f * pulse

            // Outer Orbit Ring
            rotate(rotation) {
                drawCircle(
                    color = coreColor.copy(alpha = 0.25f),
                    radius = baseRadius,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Holographic Nodes along ring
                val nodeCount = 6
                for (i in 0 until nodeCount) {
                    val angle = (i * (2 * Math.PI / nodeCount)).toFloat()
                    val nodeX = center.x + baseRadius * cos(angle)
                    val nodeY = center.y + baseRadius * sin(angle)
                    drawCircle(
                        color = coreColor,
                        radius = 2.5.dp.toPx(),
                        center = Offset(nodeX, nodeY)
                    )
                }
            }

            // Counter-rotating Inner Neural Ring
            rotate(-rotation * 1.5f) {
                drawCircle(
                    color = coreColor.copy(alpha = 0.45f),
                    radius = baseRadius * 0.65f,
                    center = center,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }

            // Glowing Core Radial
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        coreColor.copy(alpha = 0.9f),
                        coreColor.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = baseRadius * 0.5f
                ),
                radius = baseRadius * 0.5f,
                center = center
            )
        }

        // Center state icon or glyph
        Icon(
            imageVector = when (state) {
                FridayVisualState.LISTENING -> Icons.Default.Mic
                FridayVisualState.SPEAKING -> Icons.Default.GraphicEq
                else -> Icons.Default.SmartToy
            },
            contentDescription = "Friday Active State",
            tint = DarkNavyBase,
            modifier = Modifier.size((sizeDp * 0.38f).dp)
        )
    }
}

@Composable
fun FridayPresenceBadge(
    state: FridayVisualState = FridayVisualState.IDLE,
    onPresenceClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(DarkNavyCard)
            .border(1.dp, CyanPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            .clickable { onPresenceClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FridayAnimatedPresence(state = state, sizeDp = 28)
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = "FRIDAY AI",
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
            Text(
                text = when (state) {
                    FridayVisualState.IDLE -> "Standby"
                    FridayVisualState.LISTENING -> "Listening..."
                    FridayVisualState.THINKING -> "Reasoning..."
                    FridayVisualState.SPEAKING -> "Synthesizing..."
                    FridayVisualState.WORKING -> "Executing..."
                },
                style = MaterialTheme.typography.labelSmall,
                color = when (state) {
                    FridayVisualState.LISTENING -> AccentAmber
                    FridayVisualState.THINKING -> AccentPurple
                    FridayVisualState.SPEAKING -> AccentGreen
                    else -> CyanGlow
                },
                fontSize = 9.sp
            )
        }
    }
}
