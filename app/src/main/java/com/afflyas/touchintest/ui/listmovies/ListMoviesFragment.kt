package com.afflyas.touchintest.ui.listmovies

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.afflyas.touchintest.R
import com.afflyas.touchintest.core.MainActivity
import com.afflyas.touchintest.databinding.FragmentListMoviesBinding
import com.afflyas.touchintest.db.MovieEntity
import com.afflyas.touchintest.ui.common.ItemClickCallback
import com.afflyas.touchintest.ui.common.RetryCallback
import com.afflyas.touchintest.ui.common.SortResponse
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A fragment to load and display list of movies from api
 *
 *
 * Implemented RetryCallback to handle retry button click
 *
 * Implemented ItemClickCallback to handle recycler view item click
 */
class ListMoviesFragment : Fragment(), RetryCallback, ItemClickCallback {

    private lateinit var fragmentBinding: FragmentListMoviesBinding

    @Inject
    lateinit var mainActivity: MainActivity

    /**
     * Custom factory to enable injecting into ViewModel
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewModel: ListMoviesViewModel

    private var moviesAdapter: MoviesAdapter? = null


    /**
     * Enable injections
     */
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_movies, container, false)
        fragmentBinding.callback = this
        mainActivity.setSupportActionBar(fragmentBinding.toolbar)

        setHasOptionsMenu(true)

        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListMoviesViewModel::class.java)
        subscribeUI()
    }

    /**
     * Expand appBar when fragment resumes
     */
    override fun onResume() {
        super.onResume()
        fragmentBinding.appBar.setExpanded(true)
    }

    /**
     * set adapter to recyclerView
     *
     * subscribe observer for searchResult
     * to change data in the view's binding and searchAdapter
     *
     * subscribe swipeRefresh
     *
     * load movies if searchResult is null (when fragment opens for the 1st time)
     *
     */
    private fun subscribeUI() {

        moviesAdapter = MoviesAdapter(this)
        fragmentBinding.recyclerView.adapter = moviesAdapter


        mViewModel.searchResult.observe(this, Observer {
            fragmentBinding.response = it
            moviesAdapter?.setMoviesList(it.getData())
        })

        fragmentBinding.swipeRefresh.setOnRefreshListener {
            mViewModel.loadMovies()
        }

        if (mViewModel.searchResult.value == null) {
            mViewModel.loadMovies()
        }

    }

    /**
     * call to repeat search request
     *
     * Retry button displayed when api request was failed and db does not have data
     */
    override fun retry() {
        mViewModel.loadMovies()
    }

    /**
     * Navigate to MovieInfoFragment
     * after clicking one of the RecyclerView's items
     *
     * Movie object that represents clicked item is passed as an argument
     */
    override fun onItemClick(movieEntity: MovieEntity) {
        val action = ListMoviesFragmentDirections.actionListMoviesFragmentToMovieInfoFragment(movieEntity)
        NavHostFragment.findNavController(this).navigate(action)
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Load sorted data from database
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemSortByDefault -> mViewModel.sortBy = SortResponse.DEFAULT
            R.id.itemSortByDate -> mViewModel.sortBy = SortResponse.DATE
            R.id.itemSortByRating -> mViewModel.sortBy = SortResponse.RATING
        }
        mViewModel.loadMoviesFromDb()
        return super.onOptionsItemSelected(item)
    }

}
