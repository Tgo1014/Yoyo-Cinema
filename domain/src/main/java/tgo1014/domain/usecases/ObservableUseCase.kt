package tgo1014.domain.usecases

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import tgo1014.domain.executor.PostExecutionThread

abstract class ObservableUseCase<T, Params>(private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    abstract fun buildUseCase(params: Params? = null): Observable<T>

    fun execute(params: Params? = null,
                onNext: (T) -> Unit,
                onError: (e: Throwable) -> Unit,
                onComplete: (() -> Unit)? = null) {

        val observable = buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)

        addDisposable(observable.subscribeWith(object : DisposableObserver<T>() {
            override fun onComplete() {
                onComplete?.invoke()
            }

            override fun onNext(t: T) {
                onNext(t)
            }

            override fun onError(e: Throwable) {
                onError(e)
            }
        }))
    }

    fun dispose() {
        disposables.clear()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}