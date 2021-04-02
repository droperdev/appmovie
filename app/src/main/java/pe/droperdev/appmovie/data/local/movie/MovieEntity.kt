package pe.droperdev.appmovie.data.local.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieEntity")
class MovieEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String?,
    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "posterPath")
    val posterPath: String?,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,
) {
}