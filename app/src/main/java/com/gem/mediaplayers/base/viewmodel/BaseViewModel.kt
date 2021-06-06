package com.gem.mediaplayers.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gem.mediaplayers.base.ui.ServerErrorListener
import com.gem.mediaplayers.data.DataManager
import com.gem.mediaplayers.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    open val dataManager: DataManager,
    open val schedulerProvider: SchedulerProvider
) : ViewModel() {

    internal val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var serverErrorListener: ServerErrorListener? = null

    fun setLoading(isLoading: Boolean) {
        isLoadingLiveData.value = isLoading
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}