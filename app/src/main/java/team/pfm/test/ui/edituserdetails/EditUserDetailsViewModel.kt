package team.pfm.test.ui.edituserdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.pfm.test.data.UsersRepository
import team.pfm.test.data.model.User
import javax.inject.Inject

class EditUserDetailsViewModel @Inject constructor(private val usersRepository: UsersRepository) :
    ViewModel() {
    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _lastName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _editFinished = MutableLiveData<User>()
    val editFinished: LiveData<User> get() = _editFinished

    private var user: User? = null

    fun showUserFields(userId: Int) {
        viewModelScope.launch {
            user = usersRepository.getUserById(userId)
            user?.let {
                _firstName.value = it.firstName
                _lastName.value = it.lastName
                _email.value = it.email
            }

        }
    }

    fun onSaveButtonClicked(userId: Int, firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            user?.let {
                val updatedUser =
                    it.copy(id = userId, firstName = firstName, lastName = lastName, email = email)
                usersRepository.updateUser(updatedUser)
                _editFinished.value = updatedUser
            }
        }
    }

}