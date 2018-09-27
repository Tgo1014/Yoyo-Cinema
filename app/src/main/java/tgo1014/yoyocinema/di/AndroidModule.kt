package tgo1014.yoyocinema.di

import org.koin.dsl.module.module
import tgo1014.domain.executor.PostExecutionThread
import tgo1014.yoyocinema.ui.executor.UiThread

val androidModule = module {
    single { this }
    single { UiThread() as PostExecutionThread }
}