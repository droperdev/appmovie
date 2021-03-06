package pe.droperdev.appmovie.data

import pe.droperdev.appmovie.data.local.movie.MovieEntity
import pe.droperdev.appmovie.data.remote.response.MovieResponse
import pe.droperdev.appmovie.data.remote.response.Response

class FakeMovieRemoteDataSource() : MovieDataSource.Remote {
    override suspend fun getMovies(page: Int): Response<List<MovieResponse>> {
        return Response(
            1,
            listOf(
                MovieResponse(
                    1,
                    "",
                    "",
                    "",
                    "",
                    "",
                    3.4
                )
            ),
            10,
            200
        )
    }

}

class FakeMovieLocalDataSource(private var list: MutableList<MovieEntity>) : MovieDataSource.Local {
    override suspend fun getMovies(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                id = 1,
                backdropPath = "/aaa.png",
                originalTitle = "Pelicula AAA",
                overview = "detalle de pelicula",
                posterPath = "/bbbb.png",
                releaseDate = "2021-02-10",
                voteAverage = 1.5
            )
        )
    }

    override suspend fun saveMovies(movieEntity: List<MovieEntity>) {
        list.addAll(movieEntity)
    }

    override suspend fun removeAll() {
        list.clear()
    }
}