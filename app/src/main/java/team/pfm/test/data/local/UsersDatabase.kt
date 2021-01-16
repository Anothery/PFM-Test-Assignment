package team.pfm.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import team.pfm.test.data.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}