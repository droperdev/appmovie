package pe.droperdev.appmovie.data.repository

import pe.droperdev.appmovie.data.MovieDataSource
import pe.droperdev.appmovie.data.remote.response.toModel
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.domain.model.Pagination
import pe.droperdev.appmovie.domain.repository.MovieRepository
import pe.droperdev.appmovie.presentation.Resource
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieDataSource.Remote,
    private val movieLocalDataSource: MovieDataSource.Local
) : MovieRepository {
    override suspend fun getMovies(
        page: Int,
        refresh: Boolean
    ): Resource<Pagination<List<MovieModel>>> {
        val resource: Resource<Pagination<List<MovieModel>>>
        resource = try {
            val response = movieRemoteDataSource.getMovies(page)
            if (refresh) movieLocalDataSource.removeAll()
            movieLocalDataSource.saveMovies(response.results.map { it.toEntity() })
            Resource.Success((response.toModel()))
        } catch (ex: Exception) {
            return when (ex) {
                is HttpException -> {
                    when (ex.code()) {
                        400 -> Resource.Failure(ex.toString())
                        else -> Resource.Failure("Ocurrió un error inesperado, vuelve a intentarlo.")
                    }
                }
                is SocketTimeoutException -> Resource.Failure("Tiempo de espera superado")
                is SocketException,
                is UnknownHostException,
                -> Resource.Success(
                    Pagination(
                        -1,
                        movieLocalDataSource.getMovies().map { it.toModel() },
                        1, 0
                    )
                )
                else -> Resource.Failure("Ocurrió un error inesperado, vuelve a intentarlo.")
            }
        }
        return resource
    }

}

