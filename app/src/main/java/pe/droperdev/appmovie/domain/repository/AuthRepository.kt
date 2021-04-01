package pe.droperdev.appmovie.domain.repository

import pe.droperdev.appmovie.domain.model.UserModel
import pe.droperdev.appmovie.presentation.Resource

interface AuthRepository {
    suspend fun login(userName: String, password: String): Resource<UserModel>
}