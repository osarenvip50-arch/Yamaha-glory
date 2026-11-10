package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friday_tasks")
data class TaskItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: String, // "Upwork", "Scouting", "Cloud Browser", "Workflow", "Research"
    val priority: String, // "Low", "Medium", "High", "Critical"
    val status: String, // "Pending", "Planning", "Running", "Waiting for Approval", "Paused", "Completed", "Failed", "Cancelled"
    val createdAt: Long = System.currentTimeMillis(),
    val deadline: Long? = null,
    val requiresApproval: Boolean = false,
    val executionLog: String = ""
)

@Entity(tableName = "friday_prospects")
data class ProspectItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val businessName: String,
    val category: String,
    val country: String,
    val location: String,
    val officialWebsite: String,
    val websiteStatus: String, // "Active", "Needs Redesign", "No Mobile Support", "Outdated"
    val qualityAssessment: String,
    val mobileUsabilityScore: Int, // 0 - 100
    val identifiedOpportunities: String,
    val contactName: String,
    val contactRole: String,
    val businessEmail: String,
    val publicPhone: String,
    val whatsappContact: String,
    val linkedinProfile: String,
    val discoverySource: String,
    val dateDiscovered: Long = System.currentTimeMillis(),
    val qualificationScore: Int, // 0 - 100
    val outreachStatus: String, // "Discovered", "Draft Prepared", "Sent", "Replied", "Follow-up", "Converted"
    val lastContactDate: Long? = null,
    val nextFollowUpDate: Long? = null,
    val conversationNotes: String = ""
)

@Entity(tableName = "friday_upwork_jobs")
data class UpworkJobItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val clientCountry: String,
    val budget: String,
    val jobType: String, // "Hourly", "Fixed-Price"
    val matchScore: Int, // 0 - 100
    val tags: String, // e.g., "Squarespace, Redesign, E-commerce"
    val problemAnalysis: String,
    val practicalImpact: String,
    val tailoredSolution: String,
    val proposedPrice: String,
    val status: String = "Identified", // "Identified", "Proposal Drafted", "Submitted", "Interviewing", "Declined"
    val proposalText: String = "",
    val closingCallToAction: String = ""
)

@Entity(tableName = "friday_remote_sessions")
data class RemoteSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetName: String, // e.g. "WhatsApp Web Session", "Squarespace Studio", "Clutch Scouting Agent"
    val targetUrl: String,
    val state: String, // "Running", "Paused", "Terminated", "Waiting Approval"
    val currentActivity: String,
    val logs: String,
    val startedAt: Long = System.currentTimeMillis(),
    val authenticatedUser: String = "Authorized Owner Session",
    val requiresUserApproval: Boolean = false,
    val pendingActionPrompt: String = ""
)

@Entity(tableName = "friday_memories")
data class MemoryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String, // "User Preference", "Project Info", "Business Contact", "Important Decision", "Working Context"
    val keyTitle: String,
    val content: String,
    val projectTag: String = "General",
    val isPermanent: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val confidenceScore: Float = 0.95f
)

@Entity(tableName = "friday_projects")
data class ProjectItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val folderName: String,
    val progressPercent: Int = 0,
    val totalFiles: Int = 0,
    val notes: String = "",
    val lastModified: Long = System.currentTimeMillis()
)

@Entity(tableName = "friday_conversations")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "Owner", "Friday"
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val toolCallInfo: String? = null,
    val isActionConfirmation: Boolean = false
)
