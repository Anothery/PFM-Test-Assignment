package team.pfm.test.data.network

import retrofit2.http.GET

interface UsersApiService {
    @GET("users?page=2")
    suspend fun getUsers(): UsersResponse
}