package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// --- OOX1 Model Development Registry ---
@Entity(tableName = "oox1_models")
data class Oox1ModelItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val modelName: String, // e.g. "OOX1-VisionCore-v2.1", "OOX1-AudioSynapse-v4", "OOX1-CodeReason-7B"
    val version: String, // "v2.4.1"
    val domain: String, // "Video Generation", "Voice & Speech", "Music & Beats", "Code & Dev", "Verification", "Vision"
    val parameterSize: String, // "14B", "32B", "Diffusion-XL"
    val trainingStatus: String, // "Training Completed", "Fine-Tuning", "Benchmarking", "Approved for Production", "Experimental"
    val benchmarkScore: Float, // e.g. 94.6f
    val isApprovedForFriday: Boolean = true,
    val endpointUrl: String = "https://oox1-cluster.internal/v1/inference",
    val description: String = "",
    val registeredAt: Long = System.currentTimeMillis()
)

// --- Creative Production: Video Generation & Multi-Track Editing ---
@Entity(tableName = "friday_creative_videos")
data class CreativeVideoProject(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val promptOrScript: String,
    val generationMode: String, // "Text-to-Video", "Image-to-Video", "Reference Continuity", "Commercial 4K"
    val status: String, // "Queued", "Generating", "Editing in Timeline", "Rendering", "Completed"
    val resolution: String = "4K 60fps Cinema",
    val aspect: String = "16:9",
    val cameraControl: String = "Dynamic Orbital Pan + Dolly Zoom",
    val lightingMood: String = "Cinematic Golden Hour / Cyberpunk Neon",
    val multiTrackLayers: Int = 4, // Multi-track timeline (Video, B-Roll, Voiceover, Music)
    val colorGradePreset: String = "Teal & Orange Hollywood LUT",
    val vfxApplied: String = "Keyframing, Green-Screen Masking, Motion Blur",
    val videoLengthSeconds: Int = 30,
    val defectsDetected: String = "None (Defect Audit 100% Passed)",
    val previewUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

// --- Creative Production: Voice & Speech Production ---
@Entity(tableName = "friday_voice_productions")
data class VoiceProductionItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val speakerName: String, // e.g. "FRIDAY Hologram", "Afrobeat Narrator (Nigerian English)", "Executive Docent"
    val accent: String, // "Nigerian English (Lagos)", "British Received Pronunciation", "Neutral International"
    val scriptText: String,
    val emotion: String, // "Confident & Articulate", "Warm & Engaging", "Cinematic Dramatic"
    val authorizedConsent: Boolean = true, // Voice cloning authorization
    val pitchModulation: Float = 1.0f,
    val noiseReductionDb: Float = -24.0f,
    val vocalIsolationActive: Boolean = true,
    val status: String = "Synthesized", // "Queued", "Synthesized", "Mastered"
    val durationSeconds: Float = 42.5f,
    val createdAt: Long = System.currentTimeMillis()
)

// --- Creative Production: Music & Beat Production ---
@Entity(tableName = "friday_music_tracks")
data class MusicTrackItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val genre: String, // "Afrobeats", "Amapiano", "Highlife", "Fuji", "Gospel", "Hip-hop", "R&B", "Cinematic Score"
    val bpm: Int = 104,
    val musicalKey: String = "F# Minor",
    val mood: String = "Uplifting & Vibrant",
    val instrumentSetup: String = "Talking Drum, Log Drum (Amapiano), Rhodes Piano, Brass Section, 808 Bass",
    val structure: String = "Intro (4b) -> Verse 1 (8b) -> Chorus Hook (8b) -> Log Drum Drop -> Outro",
    val mixingStatus: String = "Mastered (LUFS -14dB Broadcast Standard)",
    val midiExportAvailable: Boolean = true,
    val status: String = "Completed",
    val createdAt: Long = System.currentTimeMillis()
)

// --- Coding & Software Development Workspace ---
@Entity(tableName = "friday_code_projects")
data class CodeProjectItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String, // e.g. "FastAPI OOX1 Integration Proxy", "React Squarespace Custom Widget", "PostgreSQL Migration Runner"
    val techStack: String, // "Python / FastAPI", "React / TypeScript", "PostgreSQL / SQLAlchemy"
    val activeFile: String = "main.py",
    val codeContent: String,
    val testStatus: String = "5/5 Unit Tests Passing",
    val executionOutput: String = "Process exited with code 0. [FastAPI server listening on port 8000]",
    val isSandboxRunning: Boolean = true,
    val lastUpdated: Long = System.currentTimeMillis()
)

// --- Independent Verification & Output Validation System ---
@Entity(tableName = "friday_verifications")
data class VerificationReport(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val originalRequest: String,
    val targetType: String, // "Code Execution", "Factual Claim", "Video/Image Artifact", "Upwork Proposal", "Calculations"
    val acceptanceCriteria: String,
    val independentTestsRun: String,
    val detectedErrors: String = "None",
    val correctionsApplied: String = "N/A - Output strictly verified on first iteration",
    val remainingUncertainties: String = "Zero discrepancies detected",
    val verificationStatus: String = "VERIFIED_PASSED", // "VERIFIED_PASSED", "VERIFIED_CORRECTED", "FAILED_REJECTED", "PENDING"
    val confidenceScore: Float = 0.99f,
    val verifiedAt: Long = System.currentTimeMillis()
)

// --- Specialized AI Agents Roster ---
@Entity(tableName = "friday_specialized_agents")
data class SpecializedAgentItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val agentName: String, // "VideoGen Agent", "SoundMaster Agent", "CodeArchitect Agent", "FactVerifier Agent"
    val role: String,
    val domain: String,
    val permissionBoundary: String,
    val currentActivity: String,
    val isOnline: Boolean = true,
    val tasksCompleted: Int = 0
)
