package com.appiness.appinesstest.network

import com.appiness.appinesstest.model.BooksModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiInterface {
    @GET(EndPoints.GET_BOOKS)
    fun getBooks(): Single<List<BooksModel>>
}