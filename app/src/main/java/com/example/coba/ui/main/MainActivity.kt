package com.example.coba.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba.R
import com.example.coba.base.BaseActivity
import com.example.coba.data.model.user.User
import com.example.coba.util.RecyclerViewLoadMoreScroll
import com.example.coba.util.afterTextChanged
import com.example.coba.util.subscribeSingleLiveEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var _loadMoreScroll: RecyclerViewLoadMoreScroll
    private val _userAdapter by lazy { UserAdapter() }

    override fun getScreenName() = javaClass.simpleName

    override fun layoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupView() {
        initUserAdapter()
    }

    override fun setupViewEvent() {
        search_input?.afterTextChanged {
            viewModel.onEventReceived(MainViewModel.Event.OnKeyboardTyped(it))
        }
        search_btn?.setOnClickListener {
            viewModel.onEventReceived(MainViewModel.Event.OnButtonClicked)
        }
    }

    override fun subscribeState() {
        subscribeSingleLiveEvent(viewModel.state) {
            when (it) {
                is MainViewModel.State.ShowData -> onShowData(it.users, it.isFirstPage)
                is MainViewModel.State.Error -> longToast(it.message).show()
                is MainViewModel.State.ShowLoading -> progress?.isVisible = it.isLoading
                is MainViewModel.State.EmptyResult -> emptyLayout(it.isEmpty)
            }
        }
    }

    private fun initUserAdapter() {
        val mLayoutManager = LinearLayoutManager(this)
        _loadMoreScroll =
            RecyclerViewLoadMoreScroll(mLayoutManager) {
                viewModel.onEventReceived(MainViewModel.Event.LoadMore)
            }
        user_rv?.apply {
            layoutManager = mLayoutManager
            adapter = _userAdapter
            setHasFixedSize(true)
            addOnScrollListener(_loadMoreScroll)
        }
    }

    private fun onShowData(data: List<User>, isFirstPage: Boolean) {
        _loadMoreScroll.setLoaded()
        _userAdapter.apply { if (isFirstPage) setNewData(data) else addData(data) }
    }

    private fun emptyLayout(isEmpty: Boolean) {
        empty_txt?.isVisible = isEmpty
        user_rv?.isVisible = !isEmpty
    }
}