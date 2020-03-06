package com.appiness.appinesstest.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.appiness.appinesstest.model.BooksModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BooksViewModel(application: Application) : AndroidViewModel(application) {

    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private val mBooksLiveData = MutableLiveData<BooksStates>()
    private val mBooksListLiveData = MutableLiveData<List<BooksModel>>()
    private var mBooksController =
        BooksController(
            application,
            mBooksLiveData,
            mCompositeDisposable
        )

    fun getBooksObserver() = mBooksLiveData

    /**
     *  Method to fetch data from network
     * */
    fun getBooks() {
        mBooksController.getBooks()
    }

    /**
     *  Method to search content.
     * */
    fun search(searchText: String) {
        mBooksController.search(mBooksListLiveData.value ?: emptyList(), searchText)
    }

    /**
     *  Method to preserve original data to avoid loss of original list while search data filtering.
     * */
    fun setBooksList(books: List<BooksModel>?) {
        mBooksListLiveData.value = books
    }

    /**
     *  Safely removing disposables
     * */
    override fun onCleared() {
        if (mCompositeDisposable?.isDisposed == false) {
            mCompositeDisposable?.dispose()
            mCompositeDisposable = null
        }
        super.onCleared()
    }
}