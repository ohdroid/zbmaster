package com.ohdroid.zbmaster.homepage.areamovie.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieDetailFragment : BaseFragment() {

    companion object {
        val TAG: String = "MovieDetailFragment"

        fun launch(manager: FragmentManager, containerId: Int, movieInfo: MovieInfo) {
            var fragment: Fragment? = null
            if (null == manager.findFragmentByTag(TAG)) {
                fragment = MovieDetailFragment()
                var args: Bundle = Bundle()
                args.putParcelable("movieInfo", movieInfo)
                fragment.arguments = args
                manager.beginTransaction()
                        .replace(containerId, fragment)
                        .commit()
            } else {
                fragment = manager.findFragmentByTag(TAG)
                manager.beginTransaction()
                        .show(fragment)
                        .commit()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_movie_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: MovieInfo = arguments.getParcelable("movieInfo")
        println("${args.movieUrl}---------:----------${args.movieTitle}")
    }


}