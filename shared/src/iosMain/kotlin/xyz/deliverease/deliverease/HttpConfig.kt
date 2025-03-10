package xyz.deliverease.deliverease

import io.ktor.client.engine.darwin.Darwin

actual fun getHttpEngine() = Darwin.create()
actual fun getBaseUrl() = "http://localhost:8081"
