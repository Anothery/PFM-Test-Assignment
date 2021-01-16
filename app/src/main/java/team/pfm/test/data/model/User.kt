package team.pfm.test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int,
    val avatar: String,
    val email: String,
    val firstName: String,
    val lastName: String
)