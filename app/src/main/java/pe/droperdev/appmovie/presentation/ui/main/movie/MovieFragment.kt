package pe.droperdev.appmovie.presentation.ui.main.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movie.*
import pe.droperdev.appmovie.R
import pe.droperdev.appmovie.data.MovieDataSource
import pe.droperdev.appmovie.data.repository.MovieRepositoryImpl
import pe.droperdev.appmovie.domain.model.MovieModel


class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val movieViewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(MovieDataSource())
        )
    }

    private var movieAdapter: MovieAdapter? = null

    private var isLoading = false;

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
        rv_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    val page = movieViewModel.getPage()
                    val totalPage = movieViewModel.getTotalPage()
                    if (page <= totalPage && linearLayoutManager?.findLastCompletelyVisibleItemPosition() == (page * 20) - 1) {
                        movieViewModel.setPage(page + 1)
                        movieViewModel.getMovies(page + 1)
                        isLoading = true
                    }
                }
            }
        })
    }


    private fun initObserver() {
        movieViewModel.movies.observe(viewLifecycleOwner, movieObserver)
        movieViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        movieViewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter(listOf())
        rv_movie.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            adapter = movieAdapter
        }
    }

    private val movieObserver = Observer<List<MovieModel>> {
        if (it.isNotEmpty()) {
            rv_movie.visibility = VISIBLE
            movieAdapter?.setData(it)
            isLoading = false
        }
    }

    private val loadingObserver = Observer<Boolean> {
        if (it) {
            progress_bar.visibility = VISIBLE
        } else {
            progress_bar.visibility = GONE
        }
    }

    private val errorObserver = Observer<String> {
        isLoading = false
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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