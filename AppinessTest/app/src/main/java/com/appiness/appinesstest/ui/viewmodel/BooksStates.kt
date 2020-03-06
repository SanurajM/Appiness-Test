package com.appiness.appinesstest.ui.viewmodel

import com.appiness.appinesstest.model.BooksModel

sealed class BooksStates{
    object LoadingState: BooksStates()
    object NetWorkLost: BooksStates()
    data class ErrorState(val errorCode:Int, val errorMessage:String):
        BooksStates()
    data class SuccessState(val booksModel: List<BooksModel>?):
        BooksStates()

    data class SearchState(val booksModel: List<BooksModel>?):
        BooksStates()
}