package pe.droperdev.appmovie.data

import pe.droperdev.appmovie.BuildConfig
import pe.droperdev.appmovie.data.remote.RetrofitClient
import pe.droperdev.appmovie.data.remote.WebService
import pe.droperdev.appmovie.data.remote.response.MovieResponse
import pe.droperdev.appmovie.data.remote.response.Response

class MovieDataSource{
    suspend fun getMovies(page: Int): Response<List<MovieResponse>> {
        return RetrofitClient.retrofit.create(WebService.Movie::class.java).getMovies(page, BuildConfig.API_KEY)
    }

}