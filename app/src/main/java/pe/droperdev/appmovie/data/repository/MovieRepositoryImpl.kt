package pe.droperdev.appmovie.data.repository

import pe.droperdev.appmovie.data.MovieDataSource
import pe.droperdev.appmovie.data.remote.response.toModel
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.domain.model.Pagination
import pe.droperdev.appmovie.domain.repository.MovieRepository
import pe.droperdev.appmovie.presentation.Resource
import pe.droperdev.appmovie.presentation.customException

class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
    override suspend fun getMovies(page: Int): Resource<Pagination<List<MovieModel>>> {
        val resource: Resource<Pagination<List<MovieModel>>>
        resource = try {
            val response = movieDataSource.getMovies(page)
            Resource.Success((response.toModel()))
        } catch (ex: Exception) {
            customException(ex)
        }
        return resource
    }

}

