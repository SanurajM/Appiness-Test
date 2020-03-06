package com.appiness.appinesstest.ui

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appiness.appinesstest.R
import com.appiness.appinesstest.adapter.BooksAdapter
import com.appiness.appinesstest.model.BooksModel
import com.appiness.appinesstest.ui.viewmodel.BooksStates
import com.appiness.appinesstest.ui.viewmodel.BooksViewModel
import com.appiness.appinesstest.utils.setListItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt


class MainActivity : BaseActivity() {

    private lateinit var mBooksViewModel: BooksViewModel
    private lateinit var mBooksAdapter: BooksAdapter
    private var alertDialogBuilder: AlertDialog.Builder? = null

    override fun getLayoutId() = R.layout.activity_main

    override fun initViews() {
        setViewModel()
        setAdapter()
        setListeners()
        mBooksViewModel.getBooks()
    }

    /**
     *  Setting event listeners
     * */
    private fun setListeners() {
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            mBooksViewModel.getBooks(true)
        }
    }

    /**
     *  Initializing view model class
     * */
    private fun setViewModel() {
        mBooksViewModel = ViewModelProviders.of(this).get(BooksViewModel::class.java)
        mBooksViewModel.getBooksObserver()?.observe(this,
            Observer<BooksStates> {
                processState(it)
            })
    }

    /**
     *  Managing various state of application. The states are mapped with the sealed class.
     *  The state are set from controller class. Controller class is initializes in View Model class
     * */
    private fun processState(state: BooksStates) {
        when (state) {
            is BooksStates.LoadingState -> {
                showProgress(true)
            }
            is BooksStates.SuccessState -> {
                showProgress(false)
                mBooksViewModel.setBooksList(state.booksModel)
                mBooksAdapter.setBooks(state.booksModel as ArrayList<BooksModel>)
            }
            is BooksStates.SearchState -> {
                showProgress(false)
                mBooksAdapter.setBooks(state.booksModel as ArrayList<BooksModel>)
            }
            is BooksStates.ErrorState -> {
                showProgress(false)
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_SHORT).show()
            }

            is BooksStates.NetWorkLost -> {
                showProgress(false)
                showAlert()
            }
        }
    }

    /**
     *  Setting Books adapter
     * */

    private fun setAdapter() {
        rvBooks.setListItemDecoration(
            resources.getDimension(R.dimen.margin_vertical).roundToInt(),
            resources.getDimension(R.dimen.margin_horizontal).roundToInt()
        )
        mBooksAdapter = BooksAdapter(this)
        rvBooks.adapter = mBooksAdapter
    }

    /**
     *  Hiding and showing progress bar and refresh layout
     * */
    private fun showProgress(show: Boolean) {
        if (show) {
            smoothProgressBar.visibility = View.VISIBLE
            smoothProgressBar.progressiveStart()
        } else {
            smoothProgressBar.visibility = View.GONE
            smoothProgressBar.progressiveStop()
        }
        refreshLayout.isRefreshing = false
    }


    /**
     *  Setting search bar for filtering content.
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.action_search)
            .actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    mBooksViewModel.search(it)
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

    /**
     *  Method to show if network connectivity is lost
     * */
    private fun showAlert() {
        if(null==alertDialogBuilder) {
            alertDialogBuilder = AlertDialog.Builder(
                this@MainActivity
            )
            alertDialogBuilder?.setTitle(getString(R.string.connection_Lost))
            alertDialogBuilder?.setMessage(getString(R.string.network_lost_message))
            alertDialogBuilder?.setPositiveButton("Ok") { dialog, which ->
                dialog.dismiss()
            }
            alertDialogBuilder?.setOnDismissListener { alertDialogBuilder=null }
            alertDialogBuilder?.show()

        }
    }
}
