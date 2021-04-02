package pe.droperdev.appmovie.domain.model

class MovieModel(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
) {
}