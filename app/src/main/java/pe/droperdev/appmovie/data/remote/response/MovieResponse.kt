package pe.droperdev.appmovie.data.remote.response

import com.google.gson.annotations.SerializedName
import pe.droperdev.appmovie.domain.model.MovieModel

class MovieResponse(
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
) {

    fun toModel() = MovieModel(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate
    )
}