package com.eb.kotlinwithdi.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.eb.kotlinwithdi.common.schedulers.SchedulerProvider
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel (val schedulerProvider: SchedulerProvider)  : ViewModel(){

    val compositeDisposable = CompositeDisposable()

    fun <X> applySchedulers(): ObservableTransformer<X, X> {
        return ObservableTransformer { up ->
            up.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
        }
    }

    fun add(disposable: () -> Disposable) {
        compositeDisposable.add(disposable())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}