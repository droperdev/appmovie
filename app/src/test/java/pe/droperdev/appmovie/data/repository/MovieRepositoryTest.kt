package pe.droperdev.appmovie.data.repository


import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.droperdev.appmovie.data.FakeMovieLocalDataSource
import pe.droperdev.appmovie.data.FakeMovieRemoteDataSource
import pe.droperdev.appmovie.MainCoroutineRule
import pe.droperdev.appmovie.domain.model.Pagination
import pe.droperdev.appmovie.presentation.Resource

@ExperimentalCoroutinesApi
class MovieRepositoryTest {
    private lateinit var movieRemoteDataSource: FakeMovieRemoteDataSource
    private lateinit var movieLocalDataSource: FakeMovieLocalDataSource

    private lateinit var movieRepository: MovieRepositoryImpl

    @ExperimentalCoroutinesApi
    @get: Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        movieRemoteDataSource = FakeMovieRemoteDataSource()
        movieLocalDataSource = FakeMovieLocalDataSource()
        movieRepository = MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }

    @Test
    fun `validar lista de peliculas`() = mainCoroutineRule.runBlockingTest {
        when (val result = movieRepository.getMovies(1, false)) {
            is Resource.Success -> {
                assertThat(result.data).isInstanceOf(Pagination::class.java)
            }
        }
    }

    @Test
    fun `exception en lista de peliculas`() = mainCoroutineRule.runBlockingTest {
        when (val result = movieRepository.getMovies(1, false)) {
            is Resource.Failure -> {
                assertThat(result.message).isEqualTo("Tiempo de espera superado")
            }
        }
    }
}