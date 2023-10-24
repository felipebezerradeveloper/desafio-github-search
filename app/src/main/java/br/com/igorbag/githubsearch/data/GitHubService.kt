package br.com.igorbag.githubsearch.data

import br.com.igorbag.githubsearch.domain.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}/repos")
    fun <abstract> getAllRepositoriesByUser(@Path("user") https://github.com/felipebezerradeveloper: String): Call<List<https://github.com/felipebezerradeveloper?tab=repositories>>

    abstract fun

    abstract fun getRepositoriesForUser(felipebezerradeveloper: String): Any getRepositoriesForUser(p0: Any): Any
    companion object {
        fun create(): GitHubService {

        }
    }
}
