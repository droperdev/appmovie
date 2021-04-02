package pe.droperdev.appmovie.presentation.ui.main.movieDetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import pe.droperdev.appmovie.BuildConfig
import pe.droperdev.appmovie.R
import pe.droperdev.appmovie.domain.model.MovieModel

private const val ARG_MOVIE = "movie"

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var movie: MovieModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        eventUI()
    }

    private fun initUI() {
        iv_photo_poster.transitionName = movie?.id.toString()
        tv_title.transitionName = movie?.originalTitle.toString()
        movie?.let { setMovie(it) }

    }

    private fun eventUI() {
    }

    private fun setMovie(movie: MovieModel) {
        Glide.with(requireContext()).load(
            if (!movie.backdropPath.isNullOrEmpty()) {
                "${BuildConfig.BASE_URL_IMAGES}${movie.backdropPath}"
            } else {
                R.drawable.ic_play
            }
        )
            .into(iv_photo_backdrop)
        Glide.with(requireContext()).load(
            if (!movie.posterPath.isNullOrEmpty()) {
                "${BuildConfig.BASE_URL_IMAGES}${movie.posterPath}"
            } else {
                R.drawable.ic_play
            }
        )
            .into(iv_photo_poster)
        tv_title.text = movie.originalTitle
        tv_date.text = movie.releaseDate
        tv_vote_average.text = movie.voteAverage.toString()
        tv_overview.text = movie.overview
    }

}