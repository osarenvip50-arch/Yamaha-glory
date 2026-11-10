package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.dao.FridayDao
import com.example.data.model.*
import com.example.data.repository.FridayRepository
import com.example.FridayApp
import com.example.ui.FridayViewModelFactory
import com.example.ui.theme.FridayTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst

class FakeFridayDao : FridayDao {
    override fun getAllTasks(): Flow<List<TaskItem>> = flowOf(emptyList())
    override suspend fun insertTask(task: TaskItem): Long = 1L
    override suspend fun updateTask(task: TaskItem) {}
    override suspend fun deleteTask(task: TaskItem) {}
    override suspend fun updateTaskStatus(id: Long, status: String) {}

    override fun getAllProspects(): Flow<List<ProspectItem>> = flowOf(emptyList())
    override suspend fun insertProspect(prospect: ProspectItem): Long = 1L
    override suspend fun updateProspect(prospect: ProspectItem) {}
    override suspend fun deleteProspect(prospect: ProspectItem) {}

    override fun getAllUpworkJobs(): Flow<List<UpworkJobItem>> = flowOf(emptyList())
    override suspend fun insertUpworkJob(job: UpworkJobItem): Long = 1L
    override suspend fun updateUpworkJob(job: UpworkJobItem) {}

    override fun getAllRemoteSessions(): Flow<List<RemoteSession>> = flowOf(emptyList())
    override suspend fun insertRemoteSession(session: RemoteSession): Long = 1L
    override suspend fun updateRemoteSession(session: RemoteSession) {}

    override fun getAllMemories(): Flow<List<MemoryItem>> = flowOf(emptyList())
    override suspend fun insertMemory(memory: MemoryItem): Long = 1L
    override suspend fun deleteMemory(memory: MemoryItem) {}

    override fun getAllProjects(): Flow<List<ProjectItem>> = flowOf(emptyList())
    override suspend fun insertProject(project: ProjectItem): Long = 1L
    override suspend fun updateProject(project: ProjectItem) {}

    override fun getAllMessages(): Flow<List<ChatMessage>> = flowOf(emptyList())
    override suspend fun insertMessage(message: ChatMessage): Long = 1L

    // Supporting Ecosystem Mock Queries
    override fun getAllOox1Models(): Flow<List<Oox1ModelItem>> = flowOf(emptyList())
    override suspend fun insertOox1Model(model: Oox1ModelItem): Long = 1L
    override suspend fun updateOox1Model(model: Oox1ModelItem) {}

    override fun getAllCreativeVideos(): Flow<List<CreativeVideoProject>> = flowOf(emptyList())
    override suspend fun insertCreativeVideo(video: CreativeVideoProject): Long = 1L
    override suspend fun updateCreativeVideo(video: CreativeVideoProject) {}

    override fun getAllVoiceProductions(): Flow<List<VoiceProductionItem>> = flowOf(emptyList())
    override suspend fun insertVoiceProduction(voice: VoiceProductionItem): Long = 1L

    override fun getAllMusicTracks(): Flow<List<MusicTrackItem>> = flowOf(emptyList())
    override suspend fun insertMusicTrack(track: MusicTrackItem): Long = 1L

    override fun getAllCodeProjects(): Flow<List<CodeProjectItem>> = flowOf(emptyList())
    override suspend fun insertCodeProject(code: CodeProjectItem): Long = 1L
    override suspend fun updateCodeProject(code: CodeProjectItem) {}

    override fun getAllVerifications(): Flow<List<VerificationReport>> = flowOf(emptyList())
    override suspend fun insertVerification(verification: VerificationReport): Long = 1L

    override fun getAllSpecializedAgents(): Flow<List<SpecializedAgentItem>> = flowOf(emptyList())
    override suspend fun insertSpecializedAgent(agent: SpecializedAgentItem): Long = 1L
    override suspend fun updateSpecializedAgent(agent: SpecializedAgentItem) {}
}

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDashboardAndNavigationDisplayed() {
        val fakeRepository = FridayRepository(FakeFridayDao())
        val factory = FridayViewModelFactory(fakeRepository)

        composeTestRule.setContent {
            FridayTheme {
                FridayApp(factory)
            }
        }

        composeTestRule.onNodeWithTag("friday_navigation_bar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("nav_dashboard").assertIsDisplayed()
        composeTestRule.onNodeWithTag("friday_routine_button").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("friday_animated_presence").onFirst().assertIsDisplayed()
    }
}
