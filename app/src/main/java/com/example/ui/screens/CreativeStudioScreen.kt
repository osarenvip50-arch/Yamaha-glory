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
import com.example.data.model.CreativeVideoProject
import com.example.data.model.MusicTrackItem
import com.example.data.model.VoiceProductionItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun CreativeStudioScreen(
    videos: List<CreativeVideoProject>,
    voices: List<VoiceProductionItem>,
    musicTracks: List<MusicTrackItem>,
    onCreateVideo: (String, String) -> Unit,
    onSynthesizeVoice: (String, String) -> Unit,
    onProduceMusic: (String, String) -> Unit
) {
    var studioSection by remember { mutableStateOf(0) } // 0: Video & Timeline, 1: Voice & Speech, 2: Music & Beats
    var showCreateDialog by remember { mutableStateOf(false) }

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
                    text = "FRIDAY CREATIVE PRODUCTION STUDIO",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cinematic video generation • Multi-track editing • Afrobeats/Amapiano production • Voice cloning",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            Button(
                onClick = { showCreateDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase),
                modifier = Modifier.testTag("creative_new_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Generate", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Studio Navigation
        TabRow(
            selectedTabIndex = studioSection,
            containerColor = DarkNavyCard,
            contentColor = CyanPrimary,
            divider = { Divider(color = DarkNavyBorder) }
        ) {
            Tab(
                selected = studioSection == 0,
                onClick = { studioSection = 0 },
                text = { Text("Video & Edit Engine (${videos.size})", style = MaterialTheme.typography.labelMedium) }
            )
            Tab(
                selected = studioSection == 1,
                onClick = { studioSection = 1 },
                text = { Text("Voice & Speech (${voices.size})", style = MaterialTheme.typography.labelMedium) }
            )
            Tab(
                selected = studioSection == 2,
                onClick = { studioSection = 2 },
                text = { Text("Music & Beats (${musicTracks.size})", style = MaterialTheme.typography.labelMedium) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        when (studioSection) {
            0 -> {
                // Video Production & Multi-track Timeline
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(videos) { video ->
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
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = video.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${video.generationMode} • ${video.resolution} • ${video.aspect}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = CyanGlow
                                        )
                                    }

                                    StatusBadge(text = video.status, color = AccentGreen)
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Prompt: ${video.promptOrScript}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Interactive Multi-Track Timeline Engine
                                com.example.ui.components.InteractiveTimelineEditor(video = video)
                            }
                        }
                    }
                }
            }
            1 -> {
                // Voice & Speech Production
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(voices) { voice ->
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
                                            text = voice.speakerName,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Accent: ${voice.accent} • Emotion: ${voice.emotion}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AccentAmber
                                        )
                                    }

                                    StatusBadge(text = if (voice.authorizedConsent) "CONSENT VERIFIED" else "UNAUTHORIZED", color = AccentGreen)
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(DarkNavyBase)
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "\"${voice.scriptText}\"",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Noise: ${voice.noiseReductionDb}dB • Vocal Isolation: Active",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                    Text(
                                        text = "Length: ${voice.durationSeconds}s",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CyanGlow
                                    )
                                }
                            }
                        }
                    }
                }
            }
            2 -> {
                // Music & Beat Production
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(musicTracks) { track ->
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
                                            text = track.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Genre: ${track.genre} • ${track.bpm} BPM • Key: ${track.musicalKey}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = CyanGlow
                                        )
                                    }

                                    StatusBadge(text = track.mixingStatus, color = AccentGreen)
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Arrangement Structure: ${track.structure}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Instruments: ${track.instrumentSetup}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Mood: ${track.mood} • MIDI Export: Available",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AccentAmber
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        var title by remember { mutableStateOf("") }
        var details by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = {
                Text(
                    text = when (studioSection) {
                        0 -> "Generate Cinema Video & Staged Timeline"
                        1 -> "Synthesize Voice Narration"
                        else -> "Compose Original Music / Beat"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title / Asset Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = details,
                        onValueChange = { details = it },
                        label = {
                            Text(
                                when (studioSection) {
                                    0 -> "Scene description, camera motion, lighting"
                                    1 -> "Narration script & desired accent"
                                    else -> "Genre (Afrobeats, Amapiano, Fuji), BPM, mood"
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            when (studioSection) {
                                0 -> onCreateVideo(title, details)
                                1 -> onSynthesizeVoice(title, details)
                                2 -> onProduceMusic(title, details)
                            }
                            showCreateDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = DarkNavyBase)
                ) {
                    Text("Render")
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
