package pe.droperdev.appmovie.data.remote.response

class Response<T>(
    val page: Int,
    val results: T,
    val totalPages: Int,
    val totalResult: Int,
) {
}