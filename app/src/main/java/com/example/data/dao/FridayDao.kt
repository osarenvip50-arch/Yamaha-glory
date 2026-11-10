package com.example.data.dao

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FridayDao {
    // Tasks
    @Query("SELECT * FROM friday_tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItem): Long

    @Update
    suspend fun updateTask(task: TaskItem)

    @Delete
    suspend fun deleteTask(task: TaskItem)

    @Query("UPDATE friday_tasks SET status = :status WHERE id = :id")
    suspend fun updateTaskStatus(id: Long, status: String)

    // Prospects
    @Query("SELECT * FROM friday_prospects ORDER BY qualificationScore DESC")
    fun getAllProspects(): Flow<List<ProspectItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProspect(prospect: ProspectItem): Long

    @Update
    suspend fun updateProspect(prospect: ProspectItem)

    @Delete
    suspend fun deleteProspect(prospect: ProspectItem)

    // Upwork
    @Query("SELECT * FROM friday_upwork_jobs ORDER BY matchScore DESC")
    fun getAllUpworkJobs(): Flow<List<UpworkJobItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpworkJob(job: UpworkJobItem): Long

    @Update
    suspend fun updateUpworkJob(job: UpworkJobItem)

    // Remote sessions
    @Query("SELECT * FROM friday_remote_sessions ORDER BY id DESC")
    fun getAllRemoteSessions(): Flow<List<RemoteSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteSession(session: RemoteSession): Long

    @Update
    suspend fun updateRemoteSession(session: RemoteSession)

    // Memories
    @Query("SELECT * FROM friday_memories ORDER BY id DESC")
    fun getAllMemories(): Flow<List<MemoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryItem): Long

    @Delete
    suspend fun deleteMemory(memory: MemoryItem)

    // Projects
    @Query("SELECT * FROM friday_projects ORDER BY lastModified DESC")
    fun getAllProjects(): Flow<List<ProjectItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectItem): Long

    @Update
    suspend fun updateProject(project: ProjectItem)

    // Chat
    @Query("SELECT * FROM friday_conversations ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage): Long

    // --- Supporting Ecosystem DAOs ---

    // OOX1 Models
    @Query("SELECT * FROM oox1_models ORDER BY benchmarkScore DESC")
    fun getAllOox1Models(): Flow<List<Oox1ModelItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOox1Model(model: Oox1ModelItem): Long

    @Update
    suspend fun updateOox1Model(model: Oox1ModelItem)

    // Creative Videos & Timeline
    @Query("SELECT * FROM friday_creative_videos ORDER BY id DESC")
    fun getAllCreativeVideos(): Flow<List<CreativeVideoProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreativeVideo(video: CreativeVideoProject): Long

    @Update
    suspend fun updateCreativeVideo(video: CreativeVideoProject)

    // Voice Productions
    @Query("SELECT * FROM friday_voice_productions ORDER BY id DESC")
    fun getAllVoiceProductions(): Flow<List<VoiceProductionItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVoiceProduction(voice: VoiceProductionItem): Long

    // Music & Beats
    @Query("SELECT * FROM friday_music_tracks ORDER BY id DESC")
    fun getAllMusicTracks(): Flow<List<MusicTrackItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusicTrack(track: MusicTrackItem): Long

    // Code Projects
    @Query("SELECT * FROM friday_code_projects ORDER BY lastUpdated DESC")
    fun getAllCodeProjects(): Flow<List<CodeProjectItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCodeProject(code: CodeProjectItem): Long

    @Update
    suspend fun updateCodeProject(code: CodeProjectItem)

    // Independent Verification
    @Query("SELECT * FROM friday_verifications ORDER BY id DESC")
    fun getAllVerifications(): Flow<List<VerificationReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerification(verification: VerificationReport): Long

    // Specialized Agents
    @Query("SELECT * FROM friday_specialized_agents ORDER BY tasksCompleted DESC")
    fun getAllSpecializedAgents(): Flow<List<SpecializedAgentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecializedAgent(agent: SpecializedAgentItem): Long

    @Update
    suspend fun updateSpecializedAgent(agent: SpecializedAgentItem)
}
