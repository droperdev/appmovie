package pe.droperdev.appmovie.presentation.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.droperdev.appmovie.MainCoroutineRule
import pe.droperdev.appmovie.data.repository.AuthRepositoryImpl
import pe.droperdev.appmovie.getOrAwaitValueTest


@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel(AuthRepositoryImpl())
    }

    @Test
    fun `login credenciales validas`() = mainCoroutineRule.runBlockingTest {
        viewModel.login("Admin", "Password*123")
        val user = viewModel.user.getOrAwaitValueTest()
        assertThat(user).isNotNull()
        assertThat(user?.id).isEqualTo(1)
    }

    @Test
    fun `login credenciales invalidas`() = mainCoroutineRule.runBlockingTest {
        viewModel.login("user", "1234")
        val error = viewModel.error.getOrAwaitValueTest()
        assertThat(error).isNotEmpty()
    }

    @Test
    fun `login userName empty`() = mainCoroutineRule.runBlockingTest {
        viewModel.login("", "1234")
        val error = viewModel.error.getOrAwaitValueTest()
        assertThat(error).isNotEmpty()
    }

    @Test
    fun `login password empty`() = mainCoroutineRule.runBlockingTest {
        viewModel.login("11", "")
        val error = viewModel.error.getOrAwaitValueTest()
        assertThat(error).isNotEmpty()
    }

    @Test
    fun `loading es verdadero`() = mainCoroutineRule.runBlockingTest {
        viewModel.login("a", "a")
        val loading = viewModel.loading.getOrAwaitValueTest()
        assertThat(loading).isTrue()
    }
}