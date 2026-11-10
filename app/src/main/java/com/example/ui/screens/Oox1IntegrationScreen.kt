package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.model.Oox1ModelItem
import com.example.data.model.SpecializedAgentItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun Oox1IntegrationScreen(
    models: List<Oox1ModelItem>,
    agents: List<SpecializedAgentItem>,
    telemetry: com.example.service.OrchestratorTelemetry = com.example.service.OrchestratorTelemetry(),
    onTestModelConnection: (Oox1ModelItem) -> Unit,
    onToggleApproval: (Oox1ModelItem) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Models Registry, 1: Specialized Agents

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Live Central Orchestrator Mesh Telemetry Card
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkNavyBase),
            shape = RoundedCornerShape(12.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CyanPrimary.copy(alpha = 0.5f))),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Route, contentDescription = null, tint = CyanGlow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CENTRAL ORCHESTRATION ENGINE",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    StatusBadge(text = "STATE: ${telemetry.activeState}", color = when (telemetry.activeState) {
                        com.example.service.OrchestrationState.COMPLETED -> AccentGreen
                        com.example.service.OrchestrationState.AGENT_EXECUTING,
                        com.example.service.OrchestrationState.INDEPENDENT_VERIFICATION -> AccentAmber
                        else -> CyanPrimary
                    })
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Summary: ${telemetry.lastDispatchedSummary}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Last Agent: ${telemetry.lastActiveAgent}", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Text("Commands: ${telemetry.totalRoutedCommands} | Verified: ${telemetry.verifiedOutputsCount}", style = MaterialTheme.typography.labelSmall, color = CyanGlow)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        // System Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "OOX1 MODEL DEVELOPMENT & AGENT MESH",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "External system integration • Model training governance • Specialized agent orchestration",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Architectural Isolation Banner
        Card(
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Hub, contentDescription = null, tint = AccentPurple, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "OOX1 ECOSYSTEM LINK: CONNECTED & SYNCHRONIZED",
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentPurple,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    StatusBadge(text = "OOX1 v3.8 API", color = AccentGreen)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "OOX1 is preserved as the sovereign model-training engine. Friday only invokes models that pass independent benchmarking (>90%) and possess explicit production approval.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Selector
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DarkNavyCard,
            contentColor = CyanPrimary,
            divider = { Divider(color = DarkNavyBorder) }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Model Registry (${models.size})", style = MaterialTheme.typography.labelMedium) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Specialized Agents (${agents.size})", style = MaterialTheme.typography.labelMedium) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (selectedTab == 0) {
            // Model Registry List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(models) { model ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = model.modelName,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        StatusBadge(text = model.version, color = CyanGlow)
                                    }
                                    Text(
                                        text = "Domain: ${model.domain} • Size: ${model.parameterSize}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (model.isApprovedForFriday) AccentGreen.copy(alpha = 0.15f) else AccentAmber.copy(alpha = 0.15f))
                                        .border(1.dp, if (model.isApprovedForFriday) AccentGreen else AccentAmber, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${model.benchmarkScore}% BENCHMARK",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (model.isApprovedForFriday) AccentGreen else AccentAmber,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkNavyBase)
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Endpoint:", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                        Text(model.endpointUrl, style = MaterialTheme.typography.labelSmall, color = CyanPrimary)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = model.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatusBadge(
                                    text = if (model.isApprovedForFriday) "APPROVED FOR FRIDAY" else "PENDING EVALUATION",
                                    color = if (model.isApprovedForFriday) AccentGreen else AccentAmber
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedButton(
                                        onClick = { onToggleApproval(model) },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder)),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(if (model.isApprovedForFriday) "Revoke" else "Approve", style = MaterialTheme.typography.labelSmall)
                                    }

                                    Button(
                                        onClick = { onTestModelConnection(model) },
                                        colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        modifier = Modifier.testTag("test_model_${model.id}")
                                    ) {
                                        Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Test Ping", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Specialized Agents Mesh
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(agents) { agent ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                        shape = RoundedCornerShape(12.dp),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.PrecisionManufacturing, contentDescription = null, tint = CyanPrimary, modifier = Modifier.size(22.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = agent.agentName,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${agent.role} • ${agent.domain}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextSecondary
                                        )
                                    }
                                }

                                StatusBadge(
                                    text = if (agent.isOnline) "ACTIVE" else "OFFLINE",
                                    color = if (agent.isOnline) AccentGreen else AccentRed
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Current Activity: ${agent.currentActivity}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Execution Boundary: ${agent.permissionBoundary}",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Completed Operations: ${agent.tasksCompleted} autonomous tasks",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyanGlow
                            )
                        }
                    }
                }
            }
        }
    }
}
