package io.nicolaszurbuchen.pop_know

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform