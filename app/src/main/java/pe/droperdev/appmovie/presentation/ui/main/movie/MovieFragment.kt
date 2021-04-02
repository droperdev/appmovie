package pe.droperdev.appmovie.presentation.ui.main.movie

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movie.*
import pe.droperdev.appmovie.R
import pe.droperdev.appmovie.data.MovieDataSource
import pe.droperdev.appmovie.data.repository.MovieRepositoryImpl
import pe.droperdev.appmovie.domain.model.MovieModel
import pe.droperdev.appmovie.presentation.Resource

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val movieViewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(MovieDataSource())
        )
    }

    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        eventUI()
        initObserver()
    }

    private fun initUI() {
        setupAdapter()
    }

    private fun eventUI() {
    }

    private fun initObserver() {
        movieViewModel.getMovies(1).observe(viewLifecycleOwner, movieObserver)
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter(listOf())
        rv_movie.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            adapter = movieAdapter
        }
    }

    private val movieObserver = Observer<Resource<List<MovieModel>>> {
        when (it) {
            is Resource.Loading -> {
                progress_bar.visibility = VISIBLE
                rv_movie.visibility = GONE
            }
            is Resource.Success -> {
                progress_bar.visibility = GONE
                if (it.data.isNotEmpty()){
                    rv_movie.visibility = VISIBLE
                    movieAdapter?.setData(it.data)
                }else{

                }
            }
            is Resource.Failure -> {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}