package pe.droperdev.appmovie.data.repository

import okhttp3.MediaType
import okhttp3.ResponseBody
import pe.droperdev.appmovie.data.MovieDataSource
import pe.droperdev.appmovie.data.remote.response.MovieResponse
import pe.droperdev.appmovie.data.remote.response.toModel
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.domain.model.Pagination
import pe.droperdev.appmovie.domain.repository.MovieRepository
import pe.droperdev.appmovie.presentation.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception


class FakeMovieRepository(
    private val fakeMovieRemoteDataSource: MovieDataSource.Remote,
    private val fakeMovieLocalDataSource: MovieDataSource.Local
): MovieRepository {


    private var returnError = false

    fun setReturnError(value: Boolean){
        returnError = value
    }

    override suspend fun getMovies(
        page: Int,
        refresh: Boolean
    ): Resource<Pagination<List<MovieModel>>> {
        val resource: Resource<Pagination<List<MovieModel>>>
        resource = if (returnError){
            try {
                val response = fakeMovieRemoteDataSource.getMovies(1)
                throw Exception("timeout")
            }catch (ex: Exception){
                Resource.Failure("Tiempo de espera superado")

            }
        }else{
            val response = fakeMovieRemoteDataSource.getMovies(1)
            Resource.Success(response.toModel())
        }

        return resource
    }
}