package pe.droperdev.appmovie.presentation.ui.main.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import pe.droperdev.appmovie.BuildConfig
import pe.droperdev.appmovie.R
import pe.droperdev.appmovie.domain.model.MovieModel

class MovieAdapter(
    private var movies: List<MovieModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(data: List<MovieModel>) {
        movies = data
        notifyDataSetChanged()
    }

}

interface OnItemClickListener {
    fun onItemClick(movie: MovieModel)
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(movieModel: MovieModel, listener: OnItemClickListener) {
        if (movieModel.posterPath.isNullOrEmpty()) {
            Glide.with(itemView.context)
                .load(R.drawable.ic_play)
                .into(itemView.iv_photo)
        } else {
            Glide.with(itemView.context)
                .load("${BuildConfig.BASE_URL_IMAGES}${movieModel.posterPath}")
                .into(itemView.iv_photo)
        }

        itemView.tv_title.text = movieModel.originalTitle

        itemView.setOnClickListener {
            listener.onItemClick(movieModel)
        }
    }

}