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
import com.example.data.model.ProspectItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun ScoutingScreen(
    prospects: List<ProspectItem>,
    isScoutingRunning: Boolean,
    onRunScout: () -> Unit,
    onUpdateStatus: (ProspectItem, String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredProspects = prospects.filter {
        it.businessName.contains(searchQuery, ignoreCase = true) ||
        it.category.contains(searchQuery, ignoreCase = true) ||
        it.country.contains(searchQuery, ignoreCase = true)
    }

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
                    text = "BUSINESS PROSPECTING ENGINE",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "50-250 businesses/day target • Mobile audits • Commercial opportunity evaluation",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            Button(
                onClick = onRunScout,
                enabled = !isScoutingRunning,
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase),
                modifier = Modifier.testTag("run_scout_button")
            ) {
                Icon(
                    imageVector = if (isScoutingRunning) Icons.Default.Sync else Icons.Default.Radar,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isScoutingRunning) "Scouting..." else "Scan Registry", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Filter by business name, category, or country...", color = TextMuted) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyanPrimary,
                unfocusedBorderColor = DarkNavyBorder,
                focusedContainerColor = DarkNavyCard,
                unfocusedContainerColor = DarkNavyCard,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Prospects List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredProspects) { item ->
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
                                Text(
                                    text = item.businessName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${item.category} • ${item.location}, ${item.country}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CyanPrimary.copy(alpha = 0.15f))
                                    .border(1.dp, CyanPrimary, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "SCORE: ${item.qualificationScore}/100",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CyanGlow,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Website & Mobile audit block
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
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.officialWebsite,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyanPrimary
                                    )
                                    StatusBadge(
                                        text = "Mobile Score: ${item.mobileUsabilityScore}/100",
                                        color = if (item.mobileUsabilityScore < 50) AccentRed else AccentAmber
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Audit: ${item.qualityAssessment}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Opportunity: ${item.identifiedOpportunities}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AccentGreen
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Contact person info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Contact: ${item.contactName} (${item.contactRole})",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Email: ${item.businessEmail} • WA: ${item.whatsappContact}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                            }

                            StatusBadge(
                                text = item.outreachStatus,
                                color = when (item.outreachStatus) {
                                    "Sent", "Replied" -> AccentGreen
                                    "Draft Prepared" -> AccentAmber
                                    else -> TextSecondary
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onUpdateStatus(item, "Draft Prepared") },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
                            ) {
                                Text("Prepare Pitch", style = MaterialTheme.typography.labelSmall)
                            }

                            Button(
                                onClick = { onUpdateStatus(item, "Sent") },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase)
                            ) {
                                Text("Stage Dispatch", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
