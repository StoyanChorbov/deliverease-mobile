package xyz.deliverease.deliverease.util.datastore

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class JwtTokenDelegate(
    private val jwtTokenStorage: JwtTokenStorage
) : ReadOnlyProperty<Any, String?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return runBlocking { jwtTokenStorage.authTokenFlow.first() }
    }
}