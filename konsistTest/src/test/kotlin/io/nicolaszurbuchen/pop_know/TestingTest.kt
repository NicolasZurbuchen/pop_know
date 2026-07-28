package io.nicolaszurbuchen.pop_know

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class TestingTest {
    private val scope = Konsist.scopeFromModule("shared")

    @Test
    fun `every mapper file has a corresponding test file`() {
        scope
            .files
            .filter { it.path.contains("/mapper/") && it.name.endsWith("Mapper.kt") }
            .assertTrue { mapperFile ->
                scope.files.any { it.name == mapperFile.name.replace(".kt", "Test.kt") }
            }
    }

    @Test
    fun `test files reside in commonTest mirroring their subject's package`() {
        scope
            .files
            .filter { it.name.endsWith("Test.kt") }
            .assertTrue { testFile ->
                testFile.path.contains("/commonTest/") &&
                        scope.files.any {
                            it.path.contains("/commonMain/") &&
                                    it.packagee?.name == testFile.packagee?.name
                        }
            }
    }

    @Test
    fun `every file in androidUnitTest ends with Test`() {
        scope
            .files
            .filter { it.path.contains("/androidUnitTest/") }
            .assertTrue { it.name.endsWith("Test.kt") }
    }

    @Test
    fun `every RepoImpl and DataSourceImpl file has a corresponding test file`() {
        scope
            .files
            .filter { it.name.endsWith("RepoImpl.kt") || it.name.endsWith("DataSourceImpl.kt") }
            .assertTrue { implFile ->
                scope.files.any { it.name == implFile.name.replace(".kt", "Test.kt") }
            }
    }

    @Test
    fun `classes prefixed Fake implement an interface from the same feature`() {
        scope
            .classes()
            .filter { it.name.startsWith("Fake") }
            .assertTrue { fakeClass ->
                fakeClass.parents().isNotEmpty() &&
                        fakeClass.resideInPackage(fakeClass.packagee?.name ?: "")
            }
    }
}