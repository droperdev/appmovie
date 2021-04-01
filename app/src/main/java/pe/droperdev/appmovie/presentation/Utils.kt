package pe.droperdev.appmovie.presentation

import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val message: String): Resource<Nothing>()
}

fun customException(ex: Exception): Resource.Failure {
    return when (ex) {
        is HttpException -> {
            when (ex.code()) {
                401 -> Resource.Failure("Credenciales invalidas")
                400 -> Resource.Failure(ex.toString())
                else -> Resource.Failure("Ocurrió un error inesperado, vuelve a intentarlo.")
            }
        }
        is SocketTimeoutException -> Resource.Failure("Tiempo de espera superado")
        is SocketException,
        is UnknownHostException,
        -> Resource.Failure("No hay conexión a Internet")
        else -> Resource.Failure("Ocurrió un error inesperado, vuelve a intentarlo.")
    }
}