package xyz.deliverease.deliverease

import io.ktor.client.engine.HttpClientEngine

expect fun getHttpEngine(): HttpClientEngine
expect fun getBaseUrl(): String