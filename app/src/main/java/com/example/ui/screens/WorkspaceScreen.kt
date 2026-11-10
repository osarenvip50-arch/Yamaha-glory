package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Psychology
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
import com.example.data.model.MemoryItem
import com.example.data.model.ProjectItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun WorkspaceScreen(
    projects: List<ProjectItem>,
    memories: List<MemoryItem>,
    onAddMemory: (String, String, String, String) -> Unit
) {
    var selectedSection by remember { mutableStateOf(0) } // 0: Projects, 1: Structured Memory
    var showAddMemoryDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "WORKSPACE & STRUCTURED MEMORY",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Project separation • Permanent preferences • Decision history",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            if (selectedSection == 1) {
                Button(
                    onClick = { showAddMemoryDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase),
                    modifier = Modifier.testTag("add_memory_button")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Memory", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Selector
        TabRow(
            selectedTabIndex = selectedSection,
            containerColor = DarkNavyCard,
            contentColor = CyanPrimary,
            divider = { Divider(color = DarkNavyBorder) }
        ) {
            Tab(
                selected = selectedSection == 0,
                onClick = { selectedSection = 0 },
                text = { Text("Active Projects (${projects.size})", style = MaterialTheme.typography.labelMedium) }
            )
            Tab(
                selected = selectedSection == 1,
                onClick = { selectedSection = 1 },
                text = { Text("Persistent Knowledge (${memories.size})", style = MaterialTheme.typography.labelMedium) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (selectedSection == 0) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(projects) { project ->
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
                                    Icon(Icons.Default.Folder, contentDescription = null, tint = CyanPrimary, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = project.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                StatusBadge(text = "${project.progressPercent}% DONE", color = AccentGreen)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = project.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Folder: /${project.folderName} • ${project.totalFiles} tracked documents",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Notes: ${project.notes}",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyanGlow
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(memories) { memory ->
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
                                    Icon(Icons.Default.Psychology, contentDescription = null, tint = AccentPurple, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = memory.keyTitle,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                StatusBadge(text = memory.category, color = AccentPurple)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = memory.content,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Domain: ${memory.projectTag} • Permanent: ${if (memory.isPermanent) "Yes" else "No"}",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddMemoryDialog) {
        var cat by remember { mutableStateOf("User Preference") }
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var domain by remember { mutableStateOf("Upwork") }

        AlertDialog(
            onDismissRequest = { showAddMemoryDialog = false },
            title = { Text("Store Permanent Knowledge in Friday", style = MaterialTheme.typography.titleMedium, color = TextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Rule or Key Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Knowledge / Working Preference") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    OutlinedTextField(
                        value = domain,
                        onValueChange = { domain = it },
                        label = { Text("Domain / Project Tag") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            onAddMemory(cat, title, content, domain)
                            showAddMemoryDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase)
                ) {
                    Text("Store")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMemoryDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = DarkNavyCard
        )
    }
}
