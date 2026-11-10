package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
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
import com.example.data.model.TaskItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun TaskManagerScreen(
    tasks: List<TaskItem>,
    onApproveTask: (Long) -> Unit,
    onCreateTask: (String, String, String, String) -> Unit
) {
    var filterCategory by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val categories = listOf("All", "Scouting", "Upwork", "Cloud Browser", "Research", "Workflow")
    val filteredTasks = if (filterCategory == "All") tasks else tasks.filter { it.category == filterCategory }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top action bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "AUTONOMOUS TASK ENGINE",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Continuous queue • Dependencies • Retry policies",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            Button(
                onClick = { showCreateDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase),
                modifier = Modifier.testTag("new_task_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Task", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Category filter chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.take(4).forEach { cat ->
                val selected = filterCategory == cat
                FilterChip(
                    selected = selected,
                    onClick = { filterCategory = cat },
                    label = { Text(cat, style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyanPrimary.copy(alpha = 0.2f),
                        selectedLabelColor = CyanGlow,
                        containerColor = DarkNavyCard,
                        labelColor = TextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selected,
                        borderColor = if (selected) CyanPrimary else DarkNavyBorder
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Task List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredTasks) { task ->
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

                        Spacer(modifier = Modifier.height(10.dp))

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
                                    text = "LOG: ${task.executionLog}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CyanGlow,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        if (task.status == "Waiting for Approval" || (task.requiresApproval && task.status != "Completed")) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { onApproveTask(task.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = AccentAmber, contentColor = DarkNavyBase),
                                    modifier = Modifier.testTag("task_authorize_button_${task.id}")
                                ) {
                                    Text("Authorize Task Execution", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newCategory by remember { mutableStateOf("Scouting") }
        var newPriority by remember { mutableStateOf("High") }
        var newDesc by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Dispatch New Task to Friday", style = MaterialTheme.typography.titleMedium, color = TextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Task Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newDesc,
                        onValueChange = { newDesc = it },
                        label = { Text("Description & Instructions") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = newCategory == "Scouting",
                            onClick = { newCategory = "Scouting" },
                            label = { Text("Scouting") }
                        )
                        FilterChip(
                            selected = newCategory == "Upwork",
                            onClick = { newCategory = "Upwork" },
                            label = { Text("Upwork") }
                        )
                        FilterChip(
                            selected = newCategory == "Cloud Browser",
                            onClick = { newCategory = "Cloud Browser" },
                            label = { Text("Cloud") }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            onCreateTask(newTitle, newCategory, newPriority, newDesc)
                            showCreateDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase)
                ) {
                    Text("Enqueue")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = DarkNavyCard
        )
    }
}
