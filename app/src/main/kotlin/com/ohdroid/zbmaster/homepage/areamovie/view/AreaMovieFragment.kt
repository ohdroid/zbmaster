package com.ohdroid.zbmaster.homepage.areamovie.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.view.RecycleViewHeaderFooterAdapter
import com.ohdroid.zbmaster.application.view.RecycleViewLoadMoreListener
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.view.OnRecycleViewItemClickListener
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter
import org.jetbrains.anko.support.v4.find
import java.util.*

/**
 * Created by ohdroid on 2016/4/11.
 */
class AreaMovieFragment : BaseFragment(), MovieListView {


    val mMovieGifList: RecyclerView by lazy { find<RecyclerView>(R.id.rv_movie) }
    val mFreshLayout: SwipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.refresh_layout) }
    val mLoadingHintView: View by lazy { find<View>(R.id.loading_view) }

    var mRecycleViewFootView: TextView? = null
    var mMovieListAdapter: MovieListAdapter? = null
    var mMovieListAdapterWrap: RecycleViewHeaderFooterAdapter<AreaMovieFragment.MovieViewHolder>? = null

    var presenter: MovieListPresenter? = null

    var datas = ArrayList<MovieInfo>()

    companion object {
        val TAG: String = "AreaMovieFragment"

        fun launch(manager: FragmentManager, containerId: Int): Fragment {
            println("launch $TAG")

            var fragment: AreaMovieFragment? = null

            if (null == manager.findFragmentByTag(TAG)) {
                fragment = AreaMovieFragment()
                manager.beginTransaction()
                        .add(containerId, fragment, TAG)
                        .commit()
            } else {
                fragment = manager.findFragmentByTag(TAG) as AreaMovieFragment
                manager.beginTransaction()
                        .show(fragment)
                        .commit()
            }


            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_area_movie, container, false)

        presenter = component.movieListPresenter()
        presenter!!.attachView(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //下拉刷新
        mFreshLayout.setOnRefreshListener { presenter?.showMovieGifList() }
        mFreshLayout.setColorSchemeColors(Color.parseColor("#FF9966"), Color.parseColor("#FF6666"), Color.parseColor("#FFCCCC"))


        mMovieGifList.layoutManager = LinearLayoutManager(context)
        mMovieGifList.addOnScrollListener(loadMoreListener)
        if (null == mMovieListAdapter) {
            mMovieListAdapter = MovieListAdapter(datas)
            mMovieListAdapter!!.listener = object : OnRecycleViewItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    //TODO show detail
                    presenter?.showMovieInfoDetail(position)
                }
            }
        }

        mMovieListAdapterWrap = RecycleViewHeaderFooterAdapter(mMovieListAdapter)
        mMovieGifList.adapter = mMovieListAdapterWrap

        presenter?.showMovieGifList()
    }

    class MovieListAdapter(var movieList: MutableList<MovieInfo>) : RecyclerView.Adapter<MovieViewHolder>() {
        var listener: OnRecycleViewItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder? {
            val itemView: View = LayoutInflater.from(parent?.context).inflate(R.layout.item_movie_list, parent, false)
            return MovieViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {

            if (listener != null) {
                holder?.listener = listener
            }

            holder?.image?.setImageURI(Uri.parse(movieList[position].movieUrl), null)
            holder?.title?.text = movieList[position].movieTitle
        }

        override fun getItemCount(): Int {
            return movieList.size
        }

    }

    class MovieViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        lateinit var image: SimpleDraweeView
        lateinit var title: TextView

        var listener: OnRecycleViewItemClickListener? = null
            set(value) {
                itemView.setOnClickListener({ v -> value!!.onItemClick(v!!, adapterPosition) })
            }

        init {
            image = itemView?.findViewById(R.id.item_image) as SimpleDraweeView
            title = itemView?.findViewById(R.id.item_content) as TextView
        }


    }


    val loadMoreListener = object : RecycleViewLoadMoreListener() {
        override fun onLoadMoreData() {
            //loading more data
            setFootTextViewHint(context.resources.getString(R.string.hint_load_more))
            presenter?.loadMoreMovieGifList()
        }
    }

    /**
     * 设置footview提示语句
     */
    fun setFootTextViewHint(str: String) {

        if (mRecycleViewFootView == null) {

            mRecycleViewFootView = TextView(context)
            mRecycleViewFootView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            mRecycleViewFootView!!.gravity = Gravity.CENTER
            val padding = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
            mRecycleViewFootView!!.setPadding(0, 0, 0, padding)
            mRecycleViewFootView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            mMovieListAdapterWrap?.addFootView(mRecycleViewFootView);

        }
        mRecycleViewFootView!!.text = str;
    }

    override fun onDestroy() {
        mMovieListAdapterWrap?.removeFootView()
        super.onDestroy()
    }

    fun hideLoadingView() {
        val animator: ObjectAnimator = ObjectAnimator.ofFloat(mLoadingHintView, "alpha", 1f, 0f)
        animator.duration = 250
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mLoadingHintView.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {
                mLoadingHintView.visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animator.start()
    }

    //===========================对presenter层暴露的接口==================================

    override fun showMovieList(movieInfos: MutableList<MovieInfo>, hasMore: Boolean) {
        if (mFreshLayout.isRefreshing) {
            mFreshLayout.isRefreshing = false
        }
        hideLoadingView()

        mMovieListAdapter?.movieList = movieInfos
        mMovieListAdapter?.notifyDataSetChanged()
        loadMoreListener.canLoadingMore = hasMore
    }

    override fun showMoreMovieInfo(hasMore: Boolean) {
        mMovieListAdapter?.notifyDataSetChanged()

        loadMoreListener.canLoadingMore = hasMore
        loadMoreListener.isLoadingMore = false

        if (hasMore) {
            setFootTextViewHint(resources.getString(R.string.hint_load_more))
        } else {
            setFootTextViewHint(resources.getString(R.string.hint_no_more_data))
        }
    }

    override fun showErrorView(errorState: Int, errorMessage: String) {
    }

    override fun showEmpty() {
    }

    override fun showMovieInfoDetail(movieInfo: MovieInfo) {
        MovieDetailActivity.launch(context, movieInfo)
    }

}