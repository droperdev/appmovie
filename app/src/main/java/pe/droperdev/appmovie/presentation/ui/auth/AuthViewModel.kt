package pe.droperdev.appmovie.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pe.droperdev.appmovie.domain.model.UserModel
import pe.droperdev.appmovie.domain.repository.AuthRepository
import pe.droperdev.appmovie.presentation.Resource

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(userName: String, password: String): LiveData<Resource<UserModel?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            if (userName.isEmpty()) {
                emit(Resource.Failure("El usuario es obligatorio."))
                return@liveData
            }
            if (password.isEmpty()) {
                emit(Resource.Failure("La contraseña es obligatorio."))
                return@liveData
            }
            emit(authRepository.login(userName, password))

        }
    }
}

class AuthViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            AuthRepository::class.java
        ).newInstance(
            authRepository
        )
    }
}