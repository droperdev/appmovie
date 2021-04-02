package pe.droperdev.appmovie.domain.repository

import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.presentation.Resource

interface MovieRepository {
    suspend fun getMovies(page: Int): Resource<List<MovieModel>>
}