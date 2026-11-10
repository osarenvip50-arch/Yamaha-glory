package com.example.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BackendConfig(
    val backendUrl: String = "https://api.friday-core.internal",
    val postgresStatus: String = "Connected (schema v2.4)",
    val workerQueueCount: Int = 14,
    val remoteBrowserStatus: String = "Active (Playwright Cloud Adapter v1.42)",
    val authStatus: String = "Authenticated (Owner Role: VIP)",
    val aiProvider: String = "Dual-Engine (Gemini Pro & Local Reasoning)"
)

object SystemMonitorService {
    private val _config = MutableStateFlow(BackendConfig())
    val config: StateFlow<BackendConfig> = _config.asStateFlow()

    fun updateBackendUrl(url: String) {
        _config.value = _config.value.copy(backendUrl = url)
    }

    fun updateAiProvider(provider: String) {
        _config.value = _config.value.copy(aiProvider = provider)
    }
}
