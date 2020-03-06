package com.appiness.appinesstest.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.appiness.appinesstest.model.BooksModel
import com.appiness.appinesstest.network.RetrofitClient
import com.appiness.appinesstest.utils.isInternetOn
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.single.SingleToObservable
import io.reactivex.rxjava3.schedulers.Schedulers

class BooksController(val application: Application, val liveData: MutableLiveData<BooksStates>, val compositeDisposable: CompositeDisposable?) {
    fun getBooks() {
        RetrofitClient.getRetrofitClient().getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<BooksModel>> {
                override fun onSuccess(data: List<BooksModel>?) {
                    val sortArray = data?.sortedBy { it.title }
                    liveData.value =
                        BooksStates.SuccessState(
                            sortArray?.toList()
                        )
                }

                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable?.add(d)
                    liveData.value = BooksStates.LoadingState
                }

                override fun onError(e: Throwable?) {
                    if (application.isInternetOn()) {
                        liveData.value = BooksStates.ErrorState(0, e?.localizedMessage ?: "")
                    } else {
                        liveData.value = BooksStates.NetWorkLost
                    }
                }

            })
    }

    fun search(books: List<BooksModel>, searchText: String) {
        SingleToObservable.create<List<BooksModel>> { emitter ->
            emitter.onNext(books.filter { it.title.contains(searchText, true) })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<BooksModel>> {

                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable?.add(d)
                    liveData.value = BooksStates.LoadingState
                }

                override fun onError(e: Throwable?) {

                }

                override fun onComplete() {

                }

                override fun onNext(data: List<BooksModel>?) {
                    liveData.value =
                        BooksStates.SearchState(
                            data
                        )
                }

            })
    }
}