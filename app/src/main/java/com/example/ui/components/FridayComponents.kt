package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FridayTab
import com.example.ui.theme.*

@Composable
fun FridayHeader(
    currentTab: FridayTab,
    visualState: FridayVisualState = FridayVisualState.IDLE,
    isScouting: Boolean,
    isFridayWorkflow: Boolean,
    onRunFridayRoutine: () -> Unit,
    onPresenceClick: () -> Unit = {}
) {
    Surface(
        color = DarkNavySurface,
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Distinct animated AI face / presence
                    FridayAnimatedPresence(
                        state = visualState,
                        sizeDp = 42,
                        onPresenceClick = onPresenceClick
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "PROJECT FRIDAY",
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CyanPrimary.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "INTELLIGENCE & OOX1",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CyanPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            }
                        }
                        Text(
                            text = "State: ${when (visualState) {
                                FridayVisualState.IDLE -> "Standby (WAT Node)"
                                FridayVisualState.LISTENING -> "Listening to Owner"
                                FridayVisualState.THINKING -> "Reasoning & Verifying"
                                FridayVisualState.SPEAKING -> "Synthesizing Output"
                                FridayVisualState.WORKING -> "Executing Task Mesh"
                            }} • OOX1 Active",
                            style = MaterialTheme.typography.labelSmall,
                            color = when (visualState) {
                                FridayVisualState.LISTENING -> AccentAmber
                                FridayVisualState.THINKING -> AccentPurple
                                FridayVisualState.SPEAKING -> AccentGreen
                                else -> TextSecondary
                            },
                            fontSize = 10.sp
                        )
                    }
                }

                // Quick Action Button
                FilledTonalButton(
                    onClick = onRunFridayRoutine,
                    enabled = !isFridayWorkflow,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = DarkNavyCard,
                        contentColor = CyanGlow
                    ),
                    modifier = Modifier.testTag("friday_routine_button"),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = if (isFridayWorkflow) Icons.Default.Sync else Icons.Default.Bolt,
                        contentDescription = "Friday Routine",
                        modifier = Modifier.size(16.dp),
                        tint = if (isFridayWorkflow) AccentAmber else CyanPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isFridayWorkflow) "Running..." else "Friday 8AM WAT",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isFridayWorkflow) AccentAmber else CyanPrimary
                    )
                }
            }

            AnimatedVisibility(visible = isScouting || isFridayWorkflow) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(DarkNavyCard)
                        .border(1.dp, CyanPrimary.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                color = CyanPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isScouting) "Scouting business registry websites & mobile viewports..."
                                else "Running Friday Weekly Routine: Upwork audit + Weekend research...",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = accentColor,
                fontSize = 11.sp
            )
        }
    }
}
