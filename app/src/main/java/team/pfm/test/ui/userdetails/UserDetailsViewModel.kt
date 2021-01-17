package team.pfm.test.ui.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.pfm.test.data.UsersRepository
import team.pfm.test.data.model.User
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun showUserDetails(userId: Int) {
        viewModelScope.launch {
            _user.value = usersRepository.getUserById(userId)
        }
    }
}