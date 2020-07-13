package com.example.coba.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coba.base.BaseViewModel
import com.example.coba.data.model.user.User
import com.example.coba.domain.user.GetUserSearchUseCase
import com.example.coba.util.wrapper.EventWrapper
import kotlinx.coroutines.launch
import com.example.coba.domain.Result
import com.example.coba.network.HttpStatus

class MainViewModel(
    private val getUserSearchUseCase: GetUserSearchUseCase
) : BaseViewModel() {

    sealed class Event {
        data class OnKeyboardTyped(val keyword: String) : Event()
        object LoadMore : Event()
        object OnButtonClicked : Event()
    }

    sealed class State {
        data class ShowLoading(val isLoading: Boolean) : State()
        data class Error(val code: Int, val message: String) : State()
        data class ShowData(val users: List<User>, val isFirstPage: Boolean) : State()
        data class EmptyResult(val isEmpty: Boolean) : State()
    }

    private val _state = MutableLiveData<EventWrapper<State>>()
    val state: LiveData<EventWrapper<State>> = _state

    var _currentPage = 1
    var _keyword = ""

    fun onEventReceived(event: Event) {
        when (event) {
            is Event.OnKeyboardTyped -> {
                _keyword = event.keyword
                handleSearch()
            }
            Event.LoadMore -> getUserSearch(false)
            Event.OnButtonClicked -> handleSearch()
        }
    }

    private fun handleSearch() {
        _currentPage = 1
        getUserSearch(true)
    }

    private fun getUserSearch(isFirstPage: Boolean) = launch {
        _state.value = EventWrapper(State.ShowLoading(true))
        val param = getUserSearchUseCase.createParams(keyword = _keyword, pages = _currentPage)
        when (val result = getUserSearchUseCase.execute(param)) {
            is Result.Success -> {
                val users = result.value.items
                val hasUsers = !users.isNullOrEmpty()
                if (hasUsers) {
                    _currentPage++
                    _state.value = EventWrapper(State.ShowData(users, isFirstPage))
                }
                _state.value = EventWrapper(State.EmptyResult(!hasUsers && isFirstPage))
            }
            is Result.Error -> checkErrorStatus(result.statusCode, result.errorMessage)
        }
        _state.value = EventWrapper(State.ShowLoading(false))
    }

    private fun checkErrorStatus(code: Int, message: String) {
        if (code == HttpStatus.UNPROCESSABLE_ENTITY)
            _state.value = EventWrapper(State.EmptyResult(true))
        else
            _state.value = EventWrapper(State.Error(code, message))
    }
}