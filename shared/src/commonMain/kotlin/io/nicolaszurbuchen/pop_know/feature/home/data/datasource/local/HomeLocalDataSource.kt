package io.nicolaszurbuchen.pop_know.feature.home.data.datasource.local

interface HomeLocalDataSource {
    fun countAll(): Long
    fun countCorrect(): Long
}
