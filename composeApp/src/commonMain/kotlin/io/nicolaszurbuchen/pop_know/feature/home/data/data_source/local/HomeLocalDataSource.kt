package io.nicolaszurbuchen.pop_know.feature.home.data.data_source.local

interface HomeLocalDataSource {
    fun countAll(): Long
    fun countCorrect(): Long
}
