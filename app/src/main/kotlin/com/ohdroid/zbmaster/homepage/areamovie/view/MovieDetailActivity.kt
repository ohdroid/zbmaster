package com.ohdroid.zbmaster.homepage.areamovie.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieDetailActivity : BaseActivity() {


    companion object {
        fun launch(context: Context, movieInfo: MovieInfo) {
            val intent: Intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movieInfo", movieInfo as Parcelable)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        showMovieDetailFragment()
    }

    fun showMovieDetailFragment() {
        val movieInfo: MovieInfo = intent.extras.getParcelable("movieInfo")
        MovieDetailFragment.launch(supportFragmentManager, R.id.movie_detail_container, movieInfo)
    }

}