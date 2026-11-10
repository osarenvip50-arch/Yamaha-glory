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
import com.example.data.model.CodeProjectItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun CodeDevScreen(
    projects: List<CodeProjectItem>,
    onExecuteCode: (CodeProjectItem) -> Unit,
    onSaveCode: (CodeProjectItem, String) -> Unit
) {
    var selectedProject by remember { mutableStateOf(projects.firstOrNull()) }
    var currentCode by remember(selectedProject) { mutableStateOf(selectedProject?.codeContent ?: "") }

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
                    text = "CODING & DEVELOPMENT WORKSPACE",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Python • FastAPI • React/TypeScript • PostgreSQL • Secure Containerized Sandbox",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Project selector chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            projects.forEach { proj ->
                val isSelected = selectedProject?.id == proj.id
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedProject = proj
                        currentCode = proj.codeContent
                    },
                    label = { Text(proj.name, style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyanPrimary.copy(alpha = 0.2f),
                        selectedLabelColor = CyanGlow,
                        containerColor = DarkNavyCard,
                        labelColor = TextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) CyanPrimary else DarkNavyBorder
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        selectedProject?.let { proj ->
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder)),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Code, contentDescription = null, tint = CyanPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${proj.techStack} • ${proj.activeFile}",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        StatusBadge(text = proj.testStatus, color = AccentGreen)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Code Editor Area
                    OutlinedTextField(
                        value = currentCode,
                        onValueChange = {
                            currentCode = it
                            onSaveCode(proj, it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .testTag("code_editor_field"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyanPrimary,
                            unfocusedBorderColor = DarkNavyBorder,
                            focusedContainerColor = DarkNavyBase,
                            unfocusedContainerColor = DarkNavyBase,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        textStyle = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Terminal / Sandbox Execution Output
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkNavyBase)
                            .border(1.dp, DarkNavyBorder, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("SANDBOX EXECUTION RUNNER", style = MaterialTheme.typography.labelSmall, color = AccentAmber, fontWeight = FontWeight.Bold)
                                Text("Exit Code: 0", style = MaterialTheme.typography.labelSmall, color = AccentGreen)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = proj.executionOutput,
                                style = MaterialTheme.typography.labelSmall,
                                color = CyanGlow,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { onExecuteCode(proj) },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase),
                            modifier = Modifier.testTag("run_tests_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Run Sandbox Tests", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
