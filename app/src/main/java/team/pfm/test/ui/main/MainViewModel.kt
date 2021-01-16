package team.pfm.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.pfm.test.data.UsersRepository
import team.pfm.test.data.model.User
import javax.inject.Inject

class MainViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        viewModelScope.launch {
            _users.value = usersRepository.getUsers()
        }
    }

    fun onUserItemClicked(userId: Int) {
        TODO("onUserItemClicked")
    }

    fun onUserItemRemoved(userId: Int) {
        viewModelScope.launch {
            usersRepository.removeUserById(userId)
        }
    }
}