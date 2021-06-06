package com.gem.mediaplayers.utils.extension

import com.gem.mediaplayers.utils.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun completableTimer(func: () -> Unit, timer: Long = 2000L): Disposable {
    return Completable.timer(timer, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            func()
        }
}

fun <T> Single<T>.backgroundThreadProcess(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


fun <T> Single<T>.backgroundThreadProcess(schedulerProvider: SchedulerProvider): Single<T> =
    subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
