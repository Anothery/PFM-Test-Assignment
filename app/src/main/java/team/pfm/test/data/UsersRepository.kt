package team.pfm.test.data

import team.pfm.test.data.local.UsersDatabase
import team.pfm.test.data.model.User
import team.pfm.test.data.network.UsersApiService
import javax.inject.Inject


class UsersRepository @Inject constructor(
    private val usersApi: UsersApiService,
    private val db: UsersDatabase
) {
    suspend fun getUsers(): List<User> {
        val usersResponse = usersApi.getUsers()
        val users = usersResponse.data.map {
            User(
                it.id,
                it.avatar,
                it.email,
                it.firstName,
                it.lastName
            )
        }

        db.usersDao().insertUsers(users)
        return users
    }

    suspend fun removeUserById(userId: Int) {
        db.usersDao().deleteUserById(userId)
    }
}