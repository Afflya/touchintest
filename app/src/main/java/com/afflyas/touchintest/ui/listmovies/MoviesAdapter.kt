package com.afflyas.touchintest.ui.listmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afflyas.touchintest.R
import com.afflyas.touchintest.databinding.ItemMovieBinding
import com.afflyas.touchintest.db.MovieEntity
import com.afflyas.touchintest.ui.common.ItemClickCallback

class MoviesAdapter(private val onItemClickCallback: ItemClickCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies: List<MovieEntity>? = null

    /**
     *
     * Set items to display in the recycler view
     *
     * @param newMovies - new list of {@link MovieEntity} to display in the view
     */
    fun setMoviesList(newMovies: List<MovieEntity>?) {
        if (newMovies == null || newMovies.isEmpty()) {
            movies = null
            notifyItemRangeInserted(0, 0)
        } else {
            movies = newMovies
            notifyDataSetChanged()
        }
    }

    /**
     *
     * Set item count based on the size of a list
     *
     * set 0 if it is null
     *
     * @return size of a albums list or 0
     */
    override fun getItemCount(): Int {
        val movies = this.movies
        return movies?.size ?: 0
    }

    /**
     * set corresponding {@link MovieEntity} object to item
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies?.get(position)
        if (movie != null) {
            val itemViewHolder = holder as ItemViewHolder
            itemViewHolder.binding.movie = movie
            itemViewHolder.binding.executePendingBindings()
        }
    }


    /**
     * create ViewHolder with data binding
     * and set callback
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
        binding.callback = onItemClickCallback
        return ItemViewHolder(binding)
    }

    companion object {
        /**
         * ViewHolder that contains binding with `item_movie.xml` layout
         */
        class ItemViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
    }



}