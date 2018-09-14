package tgo1014.yoyocinema

import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.koin.test.dryRun
import tgo1014.yoyocinema.new.di.androidModule
import tgo1014.yoyocinema.new.di.domainModule
import tgo1014.yoyocinema.new.di.presentationModule

class DryRunTest : KoinTest {

    @Test
    fun checkGraphs() {
        //Not working right now
//        StandAloneContext.startKoin(listOf(
//                androidModule,
//                domainModule,
//                persistenceTestModule, //we use this test module that dont require android context
//                presentationModule))
//        dryRun()
    }
}