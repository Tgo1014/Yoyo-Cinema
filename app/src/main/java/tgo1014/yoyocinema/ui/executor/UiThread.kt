package tgo1014.yoyocinema.ui.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import tgo1014.domain.executor.PostExecutionThread

class UiThread : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}