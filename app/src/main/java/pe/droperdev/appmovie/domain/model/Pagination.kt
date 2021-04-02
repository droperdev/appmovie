package pe.droperdev.appmovie.domain.model

class Pagination<T>(
    var page: Int,
    var data: T,
    var totalPages: Int,
    var totalResult: Int,
) {
}