package io.nicolaszurbuchen.pop_know.infra.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
