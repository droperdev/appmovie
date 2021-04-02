package pe.droperdev.appmovie.data

import pe.droperdev.appmovie.BuildConfig
import pe.droperdev.appmovie.data.local.AppDatabase
import pe.droperdev.appmovie.data.local.movie.MovieEntity
import pe.droperdev.appmovie.data.remote.RetrofitClient
import pe.droperdev.appmovie.data.remote.WebService
import pe.droperdev.appmovie.data.remote.response.MovieResponse
import pe.droperdev.appmovie.data.remote.response.Response

class MovieRemoteDataSource() {
    suspend fun getMovies(page: Int): Response<List<MovieResponse>> {
        return RetrofitClient.retrofit.create(WebService.Movie::class.java)
            .getMovies(page, BuildConfig.API_KEY)
    }
}

class MovieLocalDataSource(private val appDatabase: AppDatabase) {
    suspend fun getMovies(): List<MovieEntity> {
        return appDatabase.movieDAO().get()
    }

    suspend fun saveMovies(movieEntity: List<MovieEntity>) {
        appDatabase.movieDAO().insertAll(movieEntity)
    }

    suspend fun removeAll() {
        appDatabase.movieDAO().deleteAll()
    }
}