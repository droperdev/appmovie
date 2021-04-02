package pe.droperdev.appmovie.data.remote.response

import com.google.gson.annotations.SerializedName
import pe.droperdev.appmovie.domain.model.Pagination

class Response<T>(
    var page: Int,
    var results: T,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResult: Int,
)

fun Response<List<MovieResponse>>.toModel() = Pagination(
    page = page,
    data = results.map { it.toModel() },
    totalPages = totalPages,
    totalResult = totalResult
)
