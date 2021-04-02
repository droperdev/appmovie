package pe.droperdev.appmovie.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity ORDER BY id ASC")
    suspend fun get(): List<MovieEntity>

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAll()
}