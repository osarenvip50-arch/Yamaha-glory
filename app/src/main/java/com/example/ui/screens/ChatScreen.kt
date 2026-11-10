package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.ui.components.FridayAnimatedPresence
import com.example.ui.components.FridayVisualState
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun ChatScreen(
    messages: List<ChatMessage>,
    visualState: FridayVisualState = FridayVisualState.IDLE,
    onSendMessage: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var isVoiceActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // AI Reasoning banner with dynamic visual presence
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DarkNavyCard)
                .border(1.dp, CyanPrimary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FridayAnimatedPresence(state = visualState, sizeDp = 36)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "FRIDAY CONVERSATIONAL WORKSPACE",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "OOX1 Mesh • Creative Studio • Code Sandbox • Verifier",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanGlow,
                            fontSize = 10.sp
                        )
                    }
                }

                StatusBadge(
                    text = when (visualState) {
                        FridayVisualState.IDLE -> "READY"
                        FridayVisualState.LISTENING -> "LISTENING"
                        FridayVisualState.THINKING -> "REASONING"
                        FridayVisualState.SPEAKING -> "SPEAKING"
                        FridayVisualState.WORKING -> "EXECUTING"
                    },
                    color = when (visualState) {
                        FridayVisualState.LISTENING -> AccentAmber
                        FridayVisualState.THINKING -> AccentPurple
                        FridayVisualState.SPEAKING -> AccentGreen
                        else -> CyanPrimary
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Message Feed
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            reverseLayout = false
        ) {
            items(messages) { message ->
                val isFriday = message.sender == "Friday"
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (isFriday) Alignment.Start else Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = if (isFriday) Arrangement.Start else Arrangement.End
                    ) {
                        Text(
                            text = if (isFriday) "FRIDAY AI (CENTRAL INTELLIGENCE)" else "OWNER (LEAD ARCHITECT)",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isFriday) CyanPrimary else AccentAmber,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 14.dp,
                                    topEnd = 14.dp,
                                    bottomStart = if (isFriday) 2.dp else 14.dp,
                                    bottomEnd = if (isFriday) 14.dp else 2.dp
                                )
                            )
                            .background(if (isFriday) DarkNavyCard else DarkNavyBorder)
                            .border(
                                1.dp,
                                if (isFriday) CyanPrimary.copy(alpha = 0.35f) else Color.Transparent,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Column {
                            Text(
                                text = message.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary
                            )

                            if (message.toolCallInfo != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(DarkNavyBase)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = message.toolCallInfo,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyanGlow,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            if (message.isActionConfirmation) {
                                Spacer(modifier = Modifier.height(6.dp))
                                StatusBadge(text = "AUTHORIZED BY OWNER", color = AccentGreen)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Command Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SuggestionChip(
                onClick = { onSendMessage("Friday, render a 4K luxury cinematic video") },
                label = { Text("Create Video", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp) },
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = DarkNavyCard, labelColor = TextSecondary),
                border = SuggestionChipDefaults.suggestionChipBorder(enabled = true, borderColor = DarkNavyBorder)
            )
            SuggestionChip(
                onClick = { onSendMessage("Friday, produce an Amapiano beat with log drums") },
                label = { Text("Amapiano Beat", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp) },
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = DarkNavyCard, labelColor = TextSecondary),
                border = SuggestionChipDefaults.suggestionChipBorder(enabled = true, borderColor = DarkNavyBorder)
            )
            SuggestionChip(
                onClick = { onSendMessage("Friday, verify the code and test output") },
                label = { Text("Verify Output", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp) },
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = DarkNavyCard, labelColor = TextSecondary),
                border = SuggestionChipDefaults.suggestionChipBorder(enabled = true, borderColor = DarkNavyBorder)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Prompt input area with Voice & File attachment actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Voice toggle button
            IconButton(
                onClick = {
                    isVoiceActive = !isVoiceActive
                    if (isVoiceActive) {
                        onSendMessage("Friday, voice input received: 'Review project health and verify latest model benchmarks.'")
                        isVoiceActive = false
                    }
                },
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isVoiceActive) AccentAmber else DarkNavyCard)
                    .border(1.dp, if (isVoiceActive) AccentAmber else DarkNavyBorder, RoundedCornerShape(12.dp))
                    .testTag("chat_mic_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Input",
                    tint = if (isVoiceActive) DarkNavyBase else CyanGlow
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        "Ask Friday (e.g. 'Build website', 'Produce beat', 'Verify output')",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanPrimary,
                    unfocusedBorderColor = DarkNavyBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = DarkNavyCard,
                    unfocusedContainerColor = DarkNavyCard
                ),
                shape = RoundedCornerShape(12.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        onSendMessage(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CyanPrimary)
                    .testTag("chat_send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = DarkNavyBase
                )
            }
        }
    }
}
