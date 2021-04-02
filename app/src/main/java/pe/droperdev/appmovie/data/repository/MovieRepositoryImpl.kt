package pe.droperdev.appmovie.data.repository

import pe.droperdev.appmovie.data.MovieDataSource
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.domain.repository.MovieRepository
import pe.droperdev.appmovie.presentation.Resource
import pe.droperdev.appmovie.presentation.customException

class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
    override suspend fun getMovies(page: Int): Resource<List<MovieModel>> {
        val resource: Resource<List<MovieModel>>
        resource = try {
            val response = movieDataSource.getMovies(page)
            Resource.Success((response.results.map { it.toModel() }))
        } catch (ex: Exception) {
            customException(ex)
        }
        return resource
    }

}