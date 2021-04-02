package pe.droperdev.appmovie.presentation.ui.main.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.droperdev.appmovie.MainCoroutineRule
import pe.droperdev.appmovie.data.FakeMovieLocalDataSource
import pe.droperdev.appmovie.data.FakeMovieRemoteDataSource
import pe.droperdev.appmovie.data.repository.FakeMovieRepository
import pe.droperdev.appmovie.getOrAwaitValueTest

@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: FakeMovieRepository

    @Before
    fun setup() {
        val fakeMovieRemoteDataSource = FakeMovieRemoteDataSource()
        val fakeMovieLocalDataSource = FakeMovieLocalDataSource(mutableListOf())
        movieRepository =
            FakeMovieRepository(fakeMovieRemoteDataSource, fakeMovieLocalDataSource)
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun `lista de peliculas`() = mainCoroutineRule.runBlockingTest {
        viewModel.getMovies(1, false)
        val movies = viewModel.movies.getOrAwaitValueTest()
        assertThat(movies).isNotEmpty()
    }

    @Test
    fun `error al listar peliculas`() = mainCoroutineRule.runBlockingTest {
        movieRepository.setReturnError(true)
        viewModel.getMovies(1, false)
        val error = viewModel.error.getOrAwaitValueTest()
        assertThat(error).isNotEmpty()
    }

    @Test
    fun `obtener cantidad de paginas`() = mainCoroutineRule.runBlockingTest {
        viewModel.getMovies(1, false)
        val page = viewModel.getTotalPage()
        assertThat(page).isEqualTo(10)
    }

    @Test
    fun `obtener pagina actual`() = mainCoroutineRule.runBlockingTest {
        viewModel.getMovies(1, false)
        val page = viewModel.getPage()
        assertThat(page).isEqualTo(1)
    }

    @Test
    fun `loading es verdadero`() = mainCoroutineRule.runBlockingTest {
        viewModel.getMovies(1, false)
        val loading = viewModel.loading.getOrAwaitValueTest()
        assertThat(loading).isTrue()
    }


}