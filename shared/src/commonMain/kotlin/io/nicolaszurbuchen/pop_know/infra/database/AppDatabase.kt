package io.nicolaszurbuchen.pop_know.infra.database

import io.nicolaszurbuchen.pop_know.cache.AppDatabase
import io.nicolaszurbuchen.pop_know.cache.QuestionHistory

fun createDatabase(driverFactory: DatabaseDriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    return AppDatabase(
        driver = driver,
        QuestionHistoryAdapter = QuestionHistory.Adapter(
            incorrect_answersAdapter = stringListAdapter
        )
    )
}