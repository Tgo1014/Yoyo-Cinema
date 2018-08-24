package tgo1014.domain.usecases

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import tgo1014.domain.executor.PostExecutionThread

abstract class CompletableUseCase<Params>(private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    abstract fun buildUseCase(params: Params? = null): Completable

    fun execute(params: Params? = null,
                onComplete: () -> Unit,
                onError: (e: Throwable) -> Unit) {

        val completable = buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)

        addDisposable(completable.subscribeWith(object : DisposableCompletableObserver() {
            override fun onComplete() {
                onComplete()
            }

            override fun onError(e: Throwable) {
                onError(e)
            }
        }))

    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}