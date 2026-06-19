package io.nicolaszurbuchen.pop_know.infra.database

import io.nicolaszurbuchen.pop_know.cache.AppDatabase
import io.nicolaszurbuchen.pop_know.common.trivia.data.datasource.local.QuestionHistoryEntity

fun createDatabase(driverFactory: DatabaseDriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    return AppDatabase(
        driver = driver,
        QuestionHistoryEntityAdapter =
            QuestionHistoryEntity.Adapter(
                incorrect_answersAdapter = stringListAdapter,
            ),
    )
}
