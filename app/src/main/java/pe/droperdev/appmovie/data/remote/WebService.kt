package pe.droperdev.appmovie.data.remote

import pe.droperdev.appmovie.data.remote.response.MovieResponse
import pe.droperdev.appmovie.data.remote.response.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {
    interface Movie {
        @GET("3/movie/upcoming")
        suspend fun getMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Response<List<MovieResponse>>
    }
}