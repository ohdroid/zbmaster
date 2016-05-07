package com.ohdroid.zbmaster.homepage.areamovie.view.fragment

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
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
import android.widget.Button
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.application.view.recycleview.OnRecycleViewItemClickListener
import com.ohdroid.zbmaster.application.view.recycleview.RecycleViewHeaderFooterAdapter
import com.ohdroid.zbmaster.application.view.recycleview.RecycleViewLoadMoreListener
import com.ohdroid.zbmaster.application.view.recycleview.RecyclerViewAddViewHelper
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areamovie.event.ListScrollEvent
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieListView
import com.ohdroid.zbmaster.homepage.areamovie.view.activity.MovieDetailActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.find
import java.util.*
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/4/11.
 */
class AreaMovieFragment : BaseFragment(), MovieListView {


    val mMovieGifList: RecyclerView by lazy { find<RecyclerView>(R.id.rv_movie) }
    val mFreshLayout: SwipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.refresh_layout) }
    val mLoadingHintView: View by lazy { find<View>(R.id.loading_view) }
    val mNoNetWork: View by lazy { find<View>(R.id.error_layout) }

    var mRecycleViewFootView: TextView? = null
    var mMovieListAdapter: MovieListAdapter? = null
    var mMovieListAdapterWrap: RecycleViewHeaderFooterAdapter<MovieViewHolder>? = null

    lateinit var presenter: MovieListPresenter
        @Inject set

    lateinit var rxBus: RxBus
        @Inject set

    var datas = ArrayList<MovieInfo>()

    companion object {
        val TAG: String = "AreaMovieFragment"

        fun launch(manager: FragmentManager, containerId: Int): Fragment {
            println("launch $TAG")

            var fragment: AreaMovieFragment

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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_area_movie, container, false)
        presenter.attachView(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //下拉刷新
        mFreshLayout.setOnRefreshListener { presenter.showMovieGifList() }
        mFreshLayout.setColorSchemeColors(Color.parseColor("#FF9966"), Color.parseColor("#FF6666"), Color.parseColor("#FFCCCC"))


        mMovieGifList.layoutManager = LinearLayoutManager(context)
        mMovieGifList.addOnScrollListener(loadMoreListener)
        mMovieGifList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                rxBus.send(ListScrollEvent(dy))
            }
        })

        if (null == mMovieListAdapter) {
            mMovieListAdapter = MovieListAdapter(datas)
            mMovieListAdapter!!.listener = object : OnRecycleViewItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    //TODO show detail
                    presenter.showMovieInfoDetail(position)
                }
            }
        }

        mMovieListAdapterWrap = RecycleViewHeaderFooterAdapter(mMovieListAdapter)
        mMovieGifList.adapter = mMovieListAdapterWrap

        presenter.showMovieGifList()
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
            presenter.loadMoreMovieGifList()
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
        mMovieListAdapterWrap?.removeAllFootView()
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
        if ( mNoNetWork.visibility == View.VISIBLE) {
            mNoNetWork.visibility = View.GONE
        }

        //        mMovieListAdapterWrap?.removeAllFootView()


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

        if ( mFreshLayout.isRefreshing) {
            mFreshLayout.isRefreshing = false
        }

        hideLoadingView()

        if (mMovieListAdapter!!.itemCount > 0) {
            showToastHint(errorMessage)
        } else {
            mNoNetWork.visibility = View.VISIBLE
            mNoNetWork.find<Button>(R.id.btn_retry).onClick { presenter.showMovieGifList() }
        }
    }

    override fun showEmpty() {
        RecyclerViewAddViewHelper.addNoDataFootView(getString(R.string.hint_no_data), context, mMovieListAdapterWrap!!)
    }

    override fun showMovieInfoDetail(movieInfo: MovieInfo) {
        MovieDetailActivity.launch(context, movieInfo)
    }

    override fun showToastHint(msg: String) {
        showToast(msg)
    }
}