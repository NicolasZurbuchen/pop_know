package io.nicolaszurbuchen.turnstile

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class PackageHierarchyTest {

    companion object {
        private val scope = Konsist.scopeFromModule("shared")
    }

    @Test // ok
    fun `Direct children of feature or common must be in allowed list`() {
        val allowed = listOf("presentation", "domain", "data", "di")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Direct children of presentation must be in allowed list`() {
        val allowed = listOf("screen", "component", "navigation", "model", "flow")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.presentation\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Direct children of domain must be in allowed list`() {
        val allowed = listOf("model", "repository", "usecase", "validation")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.domain\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Direct children of data must be in allowed list`() {
        val allowed = listOf("repository", "datasource")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.data\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Direct children of data datasource must be in allowed list`() {
        val allowed = listOf("remote", "local", "cache")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.data\\.datasource\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Direct children of data datasource remote must be in allowed list`() {
        val allowed = listOf("api", "dto", "mapper")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.data\\.datasource\\.remote\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Direct children of presentation screen screenName must be in allowed list`() {
        val allowed = listOf("component")

        scope.packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.presentation\\.screen\\.[^.]+\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test // ok
    fun `Top level packages must be in allowed list`() {
        val allowed = listOf("common", "feature", "infra")

        val allPackages = Konsist.scopeFromDirectory("shared/src/commonMain/kotlin").packages
        val allPackageNames = allPackages.map { it.name }

        val rootPrefix = allPackageNames
            .reduce { acc, name -> acc.commonPrefixWith(name).trimEnd('.') }

        allPackages
            .assertTrue { pkg ->
                pkg.name
                    .removePrefix("$rootPrefix.")
                    .split(".")
                    .first() in allowed
            }
    }

    @Test // ok
    fun `Leaf packages must not have child packages`() {
        val leafPackageNames = setOf("api", "cache", "component", "di", "dto", "flow", "local", "mapper", "model", "navigation", "repository", "usecase")

        val allPackages = Konsist.scopeFromProject().packages
        val allPackageNames = allPackages.map { it.name }.toSet()

        allPackages
            .filter { pkg ->
                pkg.name.contains(Regex("\\.(feature|common)\\.")) &&
                        pkg.name.split(".").last() in leafPackageNames
            }
            .assertTrue { pkg ->
                allPackageNames.none { it.startsWith("${pkg.name}.") }
            }
    }
}
