package pe.droperdev.appmovie.data.repository

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.droperdev.appmovie.MainCoroutineRule
import pe.droperdev.appmovie.domain.model.UserModel
import pe.droperdev.appmovie.presentation.Resource

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepositoryImpl

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        authRepository = AuthRepositoryImpl()
    }

    @Test
    fun `credenciales  invalidas`() = mainCoroutineRule.runBlockingTest {
        val value = authRepository.login("jperez", "12345")
        assertThat(value).isEqualTo(Resource.Failure("Credenciales incorrectas, intentelo nuevamente."))
    }

    @Test
    fun `credenciales  validas`() = mainCoroutineRule.runBlockingTest {
        val value = authRepository.login("Admin", "Password*123")
        when (value) {
            is Resource.Success -> {
                assertThat(value.data).isInstanceOf(UserModel::class.java)
            }
        }

    }
}