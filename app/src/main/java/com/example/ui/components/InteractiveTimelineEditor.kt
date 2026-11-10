package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.data.model.CreativeVideoProject
import com.example.ui.theme.*

@Composable
fun InteractiveTimelineEditor(
    video: CreativeVideoProject,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var currentPlaybackSec by remember { mutableFloatStateOf(8.5f) }
    var activeTool by remember { mutableStateOf("Cut") } // Cut, Split, Color, FX, Mask

    val playProgress by animateFloatAsState(
        targetValue = if (isPlaying) 1f else (currentPlaybackSec / video.videoLengthSeconds.coerceAtLeast(1)),
        animationSpec = if (isPlaying) infiniteRepeatable(
            animation = tween(durationMillis = video.videoLengthSeconds * 1000, easing = LinearEasing)
        ) else spring(),
        label = "playback_progress"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkNavyBase),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DarkNavyBorder))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Layers, contentDescription = null, tint = AccentPurple, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MULTI-TRACK TIMELINE ENGINE",
                        style = MaterialTheme.typography.titleSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusBadge(text = "${String.format("%.1f", currentPlaybackSec)}s / ${video.videoLengthSeconds}s", color = CyanGlow)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (isPlaying) AccentAmber else CyanPrimary)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = DarkNavyBase,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Editing Tools Toolbar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val tools = listOf("Select", "Razor / Cut", "Keyframe", "Mask", "Grade LUT", "Vocal Sync")
                tools.forEach { tool ->
                    val isSelected = activeTool == tool
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) CyanPrimary.copy(alpha = 0.2f) else DarkNavyCard)
                            .border(1.dp, if (isSelected) CyanPrimary else DarkNavyBorder, RoundedCornerShape(6.dp))
                            .clickable { activeTool = tool }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = tool,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) CyanGlow else TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Visual Scrub Bar / Playhead
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(DarkNavyCard)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(playProgress)
                        .fillMaxHeight()
                        .background(CyanPrimary)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tracks Canvas View
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                // Video Track 1 (4K Cinema Master)
                TrackLane(
                    trackLabel = "V1 [4K Master]",
                    trackColor = Color(0xFF1E3A8A),
                    contentName = "Orbital Shot A • ${video.cameraControl}",
                    durationRatio = 1.0f,
                    icon = Icons.Default.Videocam
                )

                // Video Track 2 (B-Roll & VFX)
                TrackLane(
                    trackLabel = "V2 [VFX / B-Roll]",
                    trackColor = Color(0xFF581C87),
                    contentName = "Teal & Orange Grade • ${video.vfxApplied}",
                    durationRatio = 0.85f,
                    icon = Icons.Default.AutoAwesome
                )

                // Audio Track 1 (Studio Voiceover)
                TrackLane(
                    trackLabel = "A1 [Voice Sync]",
                    trackColor = Color(0xFF065F46),
                    contentName = "Amaka Executive Voiceover (Lagos Accent, -24dB Noise Guard)",
                    durationRatio = 0.7f,
                    icon = Icons.Default.Mic
                )

                // Audio Track 2 (Afrobeats Master)
                TrackLane(
                    trackLabel = "A2 [Afrobeat Mix]",
                    trackColor = Color(0xFF78350F),
                    contentName = "Lagos Sunrise Amapiano • Dual Log Drums (-14 LUFS Broadcast)",
                    durationRatio = 1.0f,
                    icon = Icons.Default.MusicNote
                )
            }
        }
    }
}

@Composable
private fun TrackLane(
    trackLabel: String,
    trackColor: Color,
    contentName: String,
    durationRatio: Float,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(DarkNavyCard)
            .border(1.dp, DarkNavyBorder, RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(86.dp)
                .fillMaxHeight()
                .background(DarkNavySurface)
                .padding(horizontal = 6.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = trackLabel,
                style = MaterialTheme.typography.labelSmall,
                color = CyanPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(durationRatio)
                .fillMaxHeight()
                .padding(2.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(trackColor.copy(alpha = 0.85f))
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = contentName,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}
