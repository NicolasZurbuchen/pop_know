package io.nicolaszurbuchen.pop_know.infra

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform