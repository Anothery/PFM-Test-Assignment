package team.pfm.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.pfm.test.data.UsersRepository
import team.pfm.test.data.model.User
import team.pfm.test.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean> get() = _refreshing

    private val _showDetails = SingleLiveEvent<Int>()
    val showDetails: LiveData<Int> get() = _showDetails

    private val _editDetails = SingleLiveEvent<Int>()
    val editDetails: LiveData<Int> get() = _editDetails

    init {
        viewModelScope.launch {
            _users.value = usersRepository.getUsers()
        }
    }

    fun onUserItemClicked(userId: Int) {
        _showDetails.value = userId
    }

    fun onUserItemRemoved(userId: Int) {
        viewModelScope.launch {
            usersRepository.removeUserById(userId)
        }
    }

    fun onEditButtonClicked(userId: Int) {
        _editDetails.value = userId
    }

    fun onSwipeRefresh() {
        viewModelScope.launch {
            _refreshing.value = true
            _users.value = usersRepository.getUsers()
            _refreshing.value = false
        }
    }
}