package team.pfm.test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import team.pfm.test.data.network.UserDetails

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: Int,
    val avatar: String,
    val email: String,
    val firstName: String,
    val lastName: String
) {
    companion object {
        fun mapFromResponse(ud: UserDetails) =
            User(ud.id, ud.avatar, ud.email, ud.firstName, ud.lastName)
    }
}