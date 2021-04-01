package pe.droperdev.appmovie.data.repository

import kotlinx.coroutines.delay
import pe.droperdev.appmovie.domain.model.UserModel
import pe.droperdev.appmovie.domain.repository.AuthRepository
import pe.droperdev.appmovie.presentation.Resource

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(userName: String, password: String): Resource<UserModel> {
        delay(1000)
        return if (userName == "Admin" && password == "Password*123") {
            Resource.Success(UserModel(1, "Juan", "Perez"))
        } else {
            Resource.Failure("Credenciales incorrectas, intentelo nuevamente.")
        }
    }
}