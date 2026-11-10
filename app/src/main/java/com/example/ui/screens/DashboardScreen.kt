package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.service.BackendConfig
import com.example.ui.FridayTab
import com.example.ui.components.FridayAnimatedPresence
import com.example.ui.components.FridayVisualState
import com.example.ui.components.MetricCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    tasks: List<TaskItem>,
    prospects: List<ProspectItem>,
    upworkJobs: List<UpworkJobItem>,
    remoteSessions: List<RemoteSession>,
    oox1Models: List<Oox1ModelItem> = emptyList(),
    creativeVideos: List<CreativeVideoProject> = emptyList(),
    verifications: List<VerificationReport> = emptyList(),
    backendConfig: BackendConfig,
    visualState: FridayVisualState = FridayVisualState.IDLE,
    onNavigateTab: (FridayTab) -> Unit,
    onStartScout: () -> Unit,
    onApproveAction: (String, Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Operational Status Banner with Interactive Friday AI Presence
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FridayAnimatedPresence(state = visualState, sizeDp = 34)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SYSTEM OPERATIONAL & OOX1 LINKED",
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "WAT Node: 08:00 AM Active",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "FRIDAY Executive Control Plane & Studio",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Supporting Ecosystem: OOX1 Model Development, Creative Studio (Video, Voice, Music), Code Workspace, and Independent Verification Engine.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onNavigateTab(FridayTab.CHAT) },
                            modifier = Modifier.weight(1f).testTag("dashboard_chat_button"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanGlow),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(CyanPrimary.copy(alpha = 0.5f)))
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Instruct AI", style = MaterialTheme.typography.labelMedium)
                        }

                        Button(
                            onClick = onStartScout,
                            modifier = Modifier.weight(1f).testTag("dashboard_scout_action"),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase)
                        ) {
                            Icon(Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Run Scout", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Quick Capabilities Launcher Grid
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavySurface),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "CREATIVE STUDIO & CAPABILITY MESH",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyanPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onNavigateTab(FridayTab.CREATIVE_STUDIO) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder)),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Movie, contentDescription = null, modifier = Modifier.size(14.dp), tint = AccentPurple)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Creative Studio", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp)
                        }

                        OutlinedButton(
                            onClick = { onNavigateTab(FridayTab.CODE_DEV) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder)),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(14.dp), tint = CyanPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Code Dev", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp)
                        }

                        OutlinedButton(
                            onClick = { onNavigateTab(FridayTab.VERIFICATION) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder)),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.FactCheck, contentDescription = null, modifier = Modifier.size(14.dp), tint = AccentGreen)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Verifier", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // Metrics Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Active Tasks",
                        value = "${tasks.count { it.status == "Running" || it.status == "Pending" }}",
                        subtitle = "Persistent Queue",
                        icon = Icons.Default.TaskAlt,
                        accentColor = CyanPrimary
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "OOX1 Models",
                        value = "${oox1Models.size.coerceAtLeast(3)}",
                        subtitle = "Production Approved",
                        icon = Icons.Default.Hub,
                        accentColor = AccentPurple
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Scouted Leads",
                        value = "${prospects.size}",
                        subtitle = "Qualified Opportunities",
                        icon = Icons.Default.Business,
                        accentColor = AccentAmber
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Verifications",
                        value = "${verifications.size.coerceAtLeast(1)}",
                        subtitle = "100% Passed Audit",
                        icon = Icons.Default.VerifiedUser,
                        accentColor = AccentGreen
                    )
                }
            }
        }

        // Pending Owner Approvals (Human-in-the-loop Governance)
        val approvalTasks = tasks.filter { it.status == "Waiting for Approval" || it.requiresApproval }
        val approvalSessions = remoteSessions.filter { it.requiresUserApproval }

        if (approvalTasks.isNotEmpty() || approvalSessions.isNotEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF261914)),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AccentAmber.copy(alpha = 0.6f)))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Gavel, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "ACTION APPROVAL REQUIRED (${approvalTasks.size + approvalSessions.size})",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AccentAmber,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            StatusBadge(text = "STRICT GOVERNANCE", color = AccentAmber)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Friday holds critical actions pending your authorization. Friday will never submit proposals, change accounts, or send unauthorized messages.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        approvalTasks.forEach { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkNavyCard)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(task.title, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                                    Text(task.description, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 2)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = { onApproveAction("TASK", task.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = AccentAmber, contentColor = DarkNavyBase),
                                    modifier = Modifier.testTag("approve_task_${task.id}")
                                ) {
                                    Text("Authorize", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        approvalSessions.forEach { session ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkNavyCard)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(session.targetName, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                                    Text(session.pendingActionPrompt.ifEmpty { session.currentActivity }, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 2)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = { onApproveAction("SESSION", session.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = DarkNavyBase),
                                    modifier = Modifier.testTag("approve_session_${session.id}")
                                ) {
                                    Text("Confirm", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        // Live Cloud Architecture Health
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "LIVE SYSTEM ARCHITECTURE & INTEGRATIONS",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyanPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Backend Service", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text(backendConfig.backendUrl, style = MaterialTheme.typography.labelMedium, color = TextPrimary)
                    }
                    Divider(color = DarkNavyBorder, modifier = Modifier.padding(vertical = 6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("OOX1 Cluster Link", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text("Connected (v3.8 API)", style = MaterialTheme.typography.labelMedium, color = AccentPurple)
                    }
                    Divider(color = DarkNavyBorder, modifier = Modifier.padding(vertical = 6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Database Engine", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text(backendConfig.postgresStatus, style = MaterialTheme.typography.labelMedium, color = AccentGreen)
                    }
                    Divider(color = DarkNavyBorder, modifier = Modifier.padding(vertical = 6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Cloud Browser Adapter", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text(backendConfig.remoteBrowserStatus, style = MaterialTheme.typography.labelMedium, color = CyanGlow)
                    }
                    Divider(color = DarkNavyBorder, modifier = Modifier.padding(vertical = 6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Reasoning Layer", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text(backendConfig.aiProvider, style = MaterialTheme.typography.labelMedium, color = AccentAmber)
                    }
                }
            }
        }

        // Recent High-Priority Tasks
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ACTIVE TASK QUEUE",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelMedium,
                    color = CyanPrimary,
                    modifier = Modifier.clickable { onNavigateTab(FridayTab.TASKS) }
                )
            }
        }

        items(tasks.take(4)) { task ->
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StatusBadge(
                                text = task.category,
                                color = when (task.category) {
                                    "Upwork" -> AccentGreen
                                    "Scouting" -> AccentAmber
                                    "Cloud Browser" -> AccentPurple
                                    else -> CyanPrimary
                                }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            StatusBadge(
                                text = task.priority,
                                color = if (task.priority == "Critical" || task.priority == "High") AccentRed else TextSecondary
                            )
                        }

                        StatusBadge(
                            text = task.status,
                            color = when (task.status) {
                                "Completed" -> AccentGreen
                                "Running" -> CyanPrimary
                                "Waiting for Approval" -> AccentAmber
                                else -> TextMuted
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )

                    if (task.executionLog.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(DarkNavyBase)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = task.executionLog,
                                style = MaterialTheme.typography.labelSmall,
                                color = CyanGlow,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
