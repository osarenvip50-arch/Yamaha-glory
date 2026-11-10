package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.database.FridayDatabase
import com.example.data.repository.FridayRepository
import com.example.ui.FridayTab
import com.example.ui.FridayViewModel
import com.example.ui.FridayViewModelFactory
import com.example.ui.components.FridayHeader
import com.example.ui.screens.*
import com.example.ui.theme.DarkNavyBase
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.FridayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = FridayDatabase.getDatabase(this)
        val repository = FridayRepository(database.fridayDao())
        val viewModelFactory = FridayViewModelFactory(repository)

        setContent {
            FridayTheme {
                FridayApp(viewModelFactory)
            }
        }
    }
}

@Composable
fun FridayApp(factory: FridayViewModelFactory) {
    val viewModel: FridayViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val prospects by viewModel.prospects.collectAsStateWithLifecycle()
    val upworkJobs by viewModel.upworkJobs.collectAsStateWithLifecycle()
    val remoteSessions by viewModel.remoteSessions.collectAsStateWithLifecycle()
    val memories by viewModel.memories.collectAsStateWithLifecycle()
    val projects by viewModel.projects.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val backendConfig by viewModel.backendConfig.collectAsStateWithLifecycle()

    // Supporting Ecosystem State
    val oox1Models by viewModel.oox1Models.collectAsStateWithLifecycle()
    val creativeVideos by viewModel.creativeVideos.collectAsStateWithLifecycle()
    val voiceProductions by viewModel.voiceProductions.collectAsStateWithLifecycle()
    val musicTracks by viewModel.musicTracks.collectAsStateWithLifecycle()
    val codeProjects by viewModel.codeProjects.collectAsStateWithLifecycle()
    val verifications by viewModel.verifications.collectAsStateWithLifecycle()
    val specializedAgents by viewModel.specializedAgents.collectAsStateWithLifecycle()
    val orchestratorTelemetry by viewModel.orchestratorTelemetry.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkNavyBase,
        topBar = {
            FridayHeader(
                currentTab = uiState.currentTab,
                visualState = uiState.visualState,
                isScouting = uiState.isScoutingRunning,
                isFridayWorkflow = uiState.isFridayWorkflowRunning,
                onRunFridayRoutine = { viewModel.executeWeeklyFridayRoutine() },
                onPresenceClick = { viewModel.selectTab(FridayTab.CHAT) }
            )
        },
        bottomBar = {
            ScrollableTabRow(
                selectedTabIndex = uiState.currentTab.ordinal,
                containerColor = DarkNavySurface,
                edgePadding = 8.dp,
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("friday_navigation_bar")
            ) {
                Tab(
                    selected = uiState.currentTab == FridayTab.DASHBOARD,
                    onClick = { viewModel.selectTab(FridayTab.DASHBOARD) },
                    text = { Text("Overview") },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Overview", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_dashboard")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.CHAT,
                    onClick = { viewModel.selectTab(FridayTab.CHAT) },
                    text = { Text("Friday AI") },
                    icon = { Icon(Icons.Default.SmartToy, contentDescription = "Friday AI", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_chat")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.CREATIVE_STUDIO,
                    onClick = { viewModel.selectTab(FridayTab.CREATIVE_STUDIO) },
                    text = { Text("Studio") },
                    icon = { Icon(Icons.Default.Movie, contentDescription = "Creative Studio", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_studio")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.CODE_DEV,
                    onClick = { viewModel.selectTab(FridayTab.CODE_DEV) },
                    text = { Text("Code Dev") },
                    icon = { Icon(Icons.Default.Code, contentDescription = "Code Dev", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_code")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.VERIFICATION,
                    onClick = { viewModel.selectTab(FridayTab.VERIFICATION) },
                    text = { Text("Verifier") },
                    icon = { Icon(Icons.Default.FactCheck, contentDescription = "Verification", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_verification")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.OOX1_INTEGRATION,
                    onClick = { viewModel.selectTab(FridayTab.OOX1_INTEGRATION) },
                    text = { Text("OOX1 Mesh") },
                    icon = { Icon(Icons.Default.Hub, contentDescription = "OOX1 Mesh", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_oox1")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.TASKS,
                    onClick = { viewModel.selectTab(FridayTab.TASKS) },
                    text = { Text("Tasks") },
                    icon = { Icon(Icons.Default.TaskAlt, contentDescription = "Tasks", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_tasks")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.SCOUTING,
                    onClick = { viewModel.selectTab(FridayTab.SCOUTING) },
                    text = { Text("Scouting") },
                    icon = { Icon(Icons.Default.Radar, contentDescription = "Scout", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_scouting")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.UPWORK,
                    onClick = { viewModel.selectTab(FridayTab.UPWORK) },
                    text = { Text("Upwork") },
                    icon = { Icon(Icons.Default.WorkOutline, contentDescription = "Upwork", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_upwork")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.CLOUD_COMPUTER,
                    onClick = { viewModel.selectTab(FridayTab.CLOUD_COMPUTER) },
                    text = { Text("Cloud") },
                    icon = { Icon(Icons.Default.CloudQueue, contentDescription = "Cloud Browser", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_cloud")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.WORKSPACE,
                    onClick = { viewModel.selectTab(FridayTab.WORKSPACE) },
                    text = { Text("Memory") },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "Workspace", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_workspace")
                )
                Tab(
                    selected = uiState.currentTab == FridayTab.SYSTEM,
                    onClick = { viewModel.selectTab(FridayTab.SYSTEM) },
                    text = { Text("System") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "System", modifier = Modifier.size(20.dp)) },
                    modifier = Modifier.testTag("nav_system")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                FridayTab.DASHBOARD -> DashboardScreen(
                    tasks = tasks,
                    prospects = prospects,
                    upworkJobs = upworkJobs,
                    remoteSessions = remoteSessions,
                    oox1Models = oox1Models,
                    creativeVideos = creativeVideos,
                    verifications = verifications,
                    backendConfig = backendConfig,
                    visualState = uiState.visualState,
                    onNavigateTab = { viewModel.selectTab(it) },
                    onStartScout = { viewModel.runProspectingScout() },
                    onApproveAction = { type, id -> viewModel.approveAction(type, id) }
                )
                FridayTab.CHAT -> ChatScreen(
                    messages = messages,
                    visualState = uiState.visualState,
                    onSendMessage = { viewModel.sendMessage(it) }
                )
                FridayTab.CREATIVE_STUDIO -> CreativeStudioScreen(
                    videos = creativeVideos,
                    voices = voiceProductions,
                    musicTracks = musicTracks,
                    onCreateVideo = { title, prompt -> viewModel.createCreativeVideo(title, prompt) },
                    onSynthesizeVoice = { title, script -> viewModel.synthesizeVoice(title, script) },
                    onProduceMusic = { title, prompt -> viewModel.produceMusic(title, prompt) }
                )
                FridayTab.CODE_DEV -> CodeDevScreen(
                    projects = codeProjects,
                    onExecuteCode = { viewModel.executeSandboxTests(it) },
                    onSaveCode = { proj, code -> viewModel.updateCode(proj, code) }
                )
                FridayTab.VERIFICATION -> VerificationScreen(
                    verifications = verifications,
                    onTriggerVerification = { req, target -> viewModel.triggerIndependentVerification(req, target) }
                )
                FridayTab.OOX1_INTEGRATION -> Oox1IntegrationScreen(
                    models = oox1Models,
                    agents = specializedAgents,
                    telemetry = orchestratorTelemetry,
                    onTestModelConnection = { viewModel.testOox1Ping(it) },
                    onToggleApproval = { viewModel.toggleOox1Approval(it) }
                )
                FridayTab.TASKS -> TaskManagerScreen(
                    tasks = tasks,
                    onApproveTask = { viewModel.approveAction("TASK", it) },
                    onCreateTask = { title, cat, prio, desc -> viewModel.createNewTask(title, cat, prio, desc) }
                )
                FridayTab.SCOUTING -> ScoutingScreen(
                    prospects = prospects,
                    isScoutingRunning = uiState.isScoutingRunning,
                    onRunScout = { viewModel.runProspectingScout() },
                    onUpdateStatus = { prospect, status -> viewModel.updateProspectOutreachStatus(prospect, status) }
                )
                FridayTab.UPWORK -> UpworkScreen(
                    jobs = upworkJobs,
                    onAuthorizeProposal = { viewModel.approveAction("UPWORK", it) }
                )
                FridayTab.CLOUD_COMPUTER -> CloudComputerScreen(
                    sessions = remoteSessions,
                    onApproveSession = { viewModel.approveAction("SESSION", it) }
                )
                FridayTab.WORKSPACE -> WorkspaceScreen(
                    projects = projects,
                    memories = memories,
                    onAddMemory = { cat, title, content, tag -> viewModel.addNewMemory(cat, title, content, tag) }
                )
                FridayTab.SYSTEM -> SystemScreen(
                    backendConfig = backendConfig
                )
            }
        }
    }
}
