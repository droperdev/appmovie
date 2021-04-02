package pe.droperdev.appmovie.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieModel(
    val id: Int,
    val backdropPath: String?,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
): Parcelable {
}