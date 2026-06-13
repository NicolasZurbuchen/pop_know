package io.nicolaszurbuchen.pop_know

import android.app.Application
import io.nicolaszurbuchen.pop_know.infra.di.initKoin
import io.nicolaszurbuchen.pop_know.infra.di.platformModule
import org.koin.android.ext.koin.androidContext

class PopKnowApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            additionalModules = listOf(platformModule),
            appDeclaration = {
                androidContext(this@PopKnowApplication)
            }
        )
    }
}