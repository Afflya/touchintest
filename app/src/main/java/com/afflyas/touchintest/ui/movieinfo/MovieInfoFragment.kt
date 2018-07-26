package com.afflyas.touchintest.ui.movieinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.afflyas.touchintest.R
import com.afflyas.touchintest.core.MainActivity
import com.afflyas.touchintest.databinding.FragmentMovieInfoBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Fragment to display general information about movie
 */
class MovieInfoFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMovieInfoBinding

    @Inject
    lateinit var mainActivity: MainActivity
    /**
     * Enable injections
     */
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(arguments == null) throw IllegalArgumentException("arguments must contain MovieEntity object")

        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false)
        fragmentBinding.movie = MovieInfoFragmentArgs.fromBundle(arguments).movie

        mainActivity.setSupportActionBar(fragmentBinding.toolbar)

        val actionBar = mainActivity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)

        return fragmentBinding.root
    }

    /**
     * Subscribe arrow back button in toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) mainActivity.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
