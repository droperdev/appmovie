package pe.droperdev.appmovie.presentation.ui.auth

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.droperdev.appmovie.domain.model.UserModel
import pe.droperdev.appmovie.domain.repository.AuthRepository
import pe.droperdev.appmovie.presentation.Resource

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private var _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> get() = _user

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(userName: String, password: String) {
        if (userName.isEmpty()) {
            _error.value = "El usuario es obligatorio."
            return
        }
        if (password.isEmpty()) {
            _error.value = "La contraseña es obligatorio."
            return
        }

        viewModelScope.launch {
            _loading.value = true
            val result = withContext(Dispatchers.IO) {
                authRepository.login(userName, password)
            }
            when (result) {
                is Resource.Success -> {
                    _loading.value = false
                    _user.value = result.data
                }
                is Resource.Failure -> {
                    _loading.value = false
                    _error.value = result.message

                }
            }
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