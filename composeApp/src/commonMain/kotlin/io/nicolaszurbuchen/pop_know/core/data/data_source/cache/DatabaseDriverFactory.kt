package io.nicolaszurbuchen.pop_know.core.data.data_source.cache

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}