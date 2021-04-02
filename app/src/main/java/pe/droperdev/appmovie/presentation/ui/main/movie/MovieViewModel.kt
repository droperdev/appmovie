package pe.droperdev.appmovie.presentation.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.domain.repository.MovieRepository
import pe.droperdev.appmovie.presentation.Resource

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies(page: Int): LiveData<Resource<List<MovieModel>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(movieRepository.getMovies(page))
        }
    }
}


class MovieViewModelFactory(
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            MovieRepository::class.java
        ).newInstance(
            movieRepository
        )
    }

}