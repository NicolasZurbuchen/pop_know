package io.nicolaszurbuchen.pop_know.infra.database

import io.nicolaszurbuchen.pop_know.cache.AppDatabase
import io.nicolaszurbuchen.pop_know.cache.QuestionHistory
import io.nicolaszurbuchen.pop_know.util.stringListAdapter

fun createDatabase(driverFactory: DatabaseDriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    return AppDatabase(
        driver = driver,
        QuestionHistoryAdapter = QuestionHistory.Adapter(
            incorrect_answersAdapter = stringListAdapter
        )
    )
}