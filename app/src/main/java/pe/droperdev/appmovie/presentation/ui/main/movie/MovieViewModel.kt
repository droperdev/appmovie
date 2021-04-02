package pe.droperdev.appmovie.presentation.ui.main.movie

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.domain.repository.MovieRepository
import pe.droperdev.appmovie.presentation.Resource

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private var data = mutableListOf<MovieModel>()

    private var _movies = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>> get() = _movies

    private var _page = MutableLiveData<Int>()

    private var _totalPages = MutableLiveData<Int>()

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        setPage(1)
        getMovies()
    }

    fun getMovies(page: Int = 1, refresh: Boolean = false) {
        viewModelScope.launch {
            _loading.value = !refresh
            if (refresh) data.clear()
            val result = withContext(Dispatchers.IO) {
                movieRepository.getMovies(page, refresh)
            }
            when (result) {
                is Resource.Success -> {
                    _loading.value = false
                    setTotalPage(result.data.totalPages)
                    data.addAll(result.data.data)
                    _movies.value = data
                }
                is Resource.Failure -> {
                    _error.value = result.message
                }
                else -> {
                }
            }
        }
    }


    private fun setTotalPage(total: Int) {
        _totalPages.value = total
    }

    fun getTotalPage(): Int? {
        return _totalPages.value
    }

    fun setPage(page: Int) {
        _page.value = page
    }

    fun getPage(): Int? {
        return _page.value
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