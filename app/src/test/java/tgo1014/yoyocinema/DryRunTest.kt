package tgo1014.yoyocinema

import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.koin.test.dryRun
import tgo1014.yoyocinema.new.di.androidModule
import tgo1014.yoyocinema.new.di.domainModule
import tgo1014.yoyocinema.new.di.persistenceModule

class DryRunTest : KoinTest {

    @Test
    fun checkGraphs() {
        StandAloneContext.startKoin( listOf(androidModule, domainModule, persistenceModule))
        dryRun()
    }
}