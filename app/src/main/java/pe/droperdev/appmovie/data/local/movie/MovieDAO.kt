package pe.droperdev.appmovie.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDAO {
    @Insert
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    suspend fun get(): List<MovieEntity>
}