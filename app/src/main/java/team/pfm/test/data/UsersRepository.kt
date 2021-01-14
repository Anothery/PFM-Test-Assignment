package team.pfm.test.data

import team.pfm.test.data.network.UsersApiService
import team.pfm.test.data.network.User
import javax.inject.Inject


class UsersRepository @Inject constructor(private val usersApi: UsersApiService) {
    suspend fun getUsers(): List<User> {
        val usersResponse = usersApi.getUsers()
        return usersResponse.data
    }
}