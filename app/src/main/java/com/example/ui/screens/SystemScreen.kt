package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.service.BackendConfig
import com.example.service.SystemMonitorService
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun SystemScreen(
    backendConfig: BackendConfig
) {
    var backendUrl by remember { mutableStateOf(backendConfig.backendUrl) }
    var selectedAiProvider by remember { mutableStateOf(backendConfig.aiProvider) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Column {
            Text(
                text = "SYSTEM ARCHITECTURE & SECURITY",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "FastAPI endpoints • PostgreSQL schema • Provider-agnostic AI adapters",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Backend & Database Telemetry
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "PRIMARY BACKEND & DATA STACK",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = backendUrl,
                            onValueChange = {
                                backendUrl = it
                                SystemMonitorService.updateBackendUrl(it)
                            },
                            label = { Text("Python FastAPI Core Endpoint") },
                            modifier = Modifier.fillMaxWidth().testTag("backend_url_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CyanPrimary,
                                unfocusedBorderColor = DarkNavyBorder,
                                focusedContainerColor = DarkNavyBase,
                                unfocusedContainerColor = DarkNavyBase,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Database Persistence", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                            Text("PostgreSQL (Active)", style = MaterialTheme.typography.labelMedium, color = AccentGreen)
                        }
                        Divider(color = DarkNavyBorder, modifier = Modifier.padding(vertical = 6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Background Task Queue", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                            Text("Celery / Redis Queue (14 active workers)", style = MaterialTheme.typography.labelMedium, color = TextPrimary)
                        }
                        Divider(color = DarkNavyBorder, modifier = Modifier.padding(vertical = 6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Owner Role & Session", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                            Text("VIP Owner (Full Grant)", style = MaterialTheme.typography.labelMedium, color = AccentAmber)
                        }
                    }
                }
            }

            // AI Model Configuration
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "AI REASONING PROVIDER ARCHITECTURE",
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentPurple,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Provider-independent AI layer. Friday separates natural-language planning from execution tools.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val providers = listOf(
                            "Dual-Engine (Gemini Pro & Local Reasoning)",
                            "Anthropic Claude 3.5 Sonnet Adapter",
                            "OpenAI GPT-4o Working Context",
                            "Self-Hosted Local LLM (Ollama)"
                        )

                        providers.forEach { provider ->
                            val isSelected = selectedAiProvider == provider
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) DarkNavyBase else Color.Transparent)
                                    .border(1.dp, if (isSelected) CyanPrimary else DarkNavyBorder, RoundedCornerShape(8.dp))
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = provider,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected) CyanGlow else TextPrimary
                                )
                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        selectedAiProvider = provider
                                        SystemMonitorService.updateAiProvider(provider)
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = CyanPrimary)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }

            // Security & Privacy Auditing
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "STRICT GOVERNANCE BOUNDARIES",
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Passwords, cookies, and tokens are encrypted at rest.\n• Friday never fabricates contact info when data is missing.\n• Cloud browser halts before any message sending or contract changes until the owner confirms in the UI.\n• Audit logs are persistently committed to the database.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}
