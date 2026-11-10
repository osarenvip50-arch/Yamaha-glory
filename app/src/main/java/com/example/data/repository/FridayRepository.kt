package com.example.data.repository

import com.example.data.dao.FridayDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class FridayRepository(private val dao: FridayDao) {
    val tasks: Flow<List<TaskItem>> = dao.getAllTasks()
    val prospects: Flow<List<ProspectItem>> = dao.getAllProspects()
    val upworkJobs: Flow<List<UpworkJobItem>> = dao.getAllUpworkJobs()
    val remoteSessions: Flow<List<RemoteSession>> = dao.getAllRemoteSessions()
    val memories: Flow<List<MemoryItem>> = dao.getAllMemories()
    val projects: Flow<List<ProjectItem>> = dao.getAllProjects()
    val messages: Flow<List<ChatMessage>> = dao.getAllMessages()

    suspend fun insertTask(task: TaskItem) = dao.insertTask(task)
    suspend fun updateTask(task: TaskItem) = dao.updateTask(task)
    suspend fun deleteTask(task: TaskItem) = dao.deleteTask(task)
    suspend fun updateTaskStatus(id: Long, status: String) = dao.updateTaskStatus(id, status)

    suspend fun insertProspect(prospect: ProspectItem) = dao.insertProspect(prospect)
    suspend fun updateProspect(prospect: ProspectItem) = dao.updateProspect(prospect)
    suspend fun deleteProspect(prospect: ProspectItem) = dao.deleteProspect(prospect)

    suspend fun insertUpworkJob(job: UpworkJobItem) = dao.insertUpworkJob(job)
    suspend fun updateUpworkJob(job: UpworkJobItem) = dao.updateUpworkJob(job)

    suspend fun insertRemoteSession(session: RemoteSession) = dao.insertRemoteSession(session)
    suspend fun updateRemoteSession(session: RemoteSession) = dao.updateRemoteSession(session)

    suspend fun insertMemory(memory: MemoryItem) = dao.insertMemory(memory)
    suspend fun deleteMemory(memory: MemoryItem) = dao.deleteMemory(memory)

    suspend fun insertProject(project: ProjectItem) = dao.insertProject(project)
    suspend fun updateProject(project: ProjectItem) = dao.updateProject(project)

    suspend fun insertMessage(message: ChatMessage) = dao.insertMessage(message)

    // Supporting Ecosystem Methods
    val oox1Models: Flow<List<Oox1ModelItem>> = dao.getAllOox1Models()
    val creativeVideos: Flow<List<CreativeVideoProject>> = dao.getAllCreativeVideos()
    val voiceProductions: Flow<List<VoiceProductionItem>> = dao.getAllVoiceProductions()
    val musicTracks: Flow<List<MusicTrackItem>> = dao.getAllMusicTracks()
    val codeProjects: Flow<List<CodeProjectItem>> = dao.getAllCodeProjects()
    val verifications: Flow<List<VerificationReport>> = dao.getAllVerifications()
    val specializedAgents: Flow<List<SpecializedAgentItem>> = dao.getAllSpecializedAgents()

    suspend fun insertOox1Model(model: Oox1ModelItem) = dao.insertOox1Model(model)
    suspend fun updateOox1Model(model: Oox1ModelItem) = dao.updateOox1Model(model)

    suspend fun insertCreativeVideo(video: CreativeVideoProject) = dao.insertCreativeVideo(video)
    suspend fun updateCreativeVideo(video: CreativeVideoProject) = dao.updateCreativeVideo(video)

    suspend fun insertVoiceProduction(voice: VoiceProductionItem) = dao.insertVoiceProduction(voice)

    suspend fun insertMusicTrack(track: MusicTrackItem) = dao.insertMusicTrack(track)

    suspend fun insertCodeProject(code: CodeProjectItem) = dao.insertCodeProject(code)
    suspend fun updateCodeProject(code: CodeProjectItem) = dao.updateCodeProject(code)

    suspend fun insertVerification(verification: VerificationReport) = dao.insertVerification(verification)

    suspend fun insertSpecializedAgent(agent: SpecializedAgentItem) = dao.insertSpecializedAgent(agent)
    suspend fun updateSpecializedAgent(agent: SpecializedAgentItem) = dao.updateSpecializedAgent(agent)
}
