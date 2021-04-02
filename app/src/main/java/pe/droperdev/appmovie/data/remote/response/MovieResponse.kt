package pe.droperdev.appmovie.data.remote.response

import com.google.gson.annotations.SerializedName
import pe.droperdev.appmovie.data.local.movie.MovieEntity
import pe.droperdev.appmovie.domain.model.MovieModel

class MovieResponse(
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
) {

    fun toModel() = MovieModel(
        id = id,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate
    )

    fun toEntity() = MovieEntity(
        id = id,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate
    )
}