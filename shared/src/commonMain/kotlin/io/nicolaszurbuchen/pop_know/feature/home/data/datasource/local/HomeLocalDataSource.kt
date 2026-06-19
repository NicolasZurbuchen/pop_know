package io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun observeCountAll(): Flow<Long>

    fun observeCountCorrect(): Flow<Long>
}
