package com.ohdroid.zbmaster.homepage.areamovie.view

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.view.RecycleViewHeaderFooterAdapter
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import java.util.*

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieDetailFragment : BaseFragment(), MovieDetailView {


    val mMovieDetailList: RecyclerView by lazy { find<RecyclerView>(R.id.rv_movie_detail) }
    val mHeadSdv: SimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.sdv_movie) }
    val mRefreshLayout: SwipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.srf_layout) }

    var mMovieAdapter: MovieDetailAdapter? = null
    var mMovieDetailAdapterWrap: RecycleViewHeaderFooterAdapter<MovieDetailViewHolder>? = null
    var mMovieComment: MutableList<MovieComment>? = null

    val mBtnFavorite: Button by lazy { find<Button>(R.id.btn_favorite) }
    val mBtnSend: Button by lazy { find<Button>(R.id.btn_send) }
    val mBtnShare: Button by lazy { find<Button>(R.id.btn_share) }
    val mEtComment: EditText by lazy { find<EditText>(R.id.et_comment) }

    val mBtnOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_send -> sendCommment()
        }
    }

    lateinit var presenter: MovieCommentPresenter

    lateinit var movieInfo: MovieInfo

    fun sendCommment() {
        //直接跳转
        //        LoginActivity.launch(context)
        if (TextUtils.isEmpty(mEtComment.text)) {
            showToast(getString(R.string.hint_no_comment_input))
            return
        }
        presenter.addComment(mEtComment.text.toString(), movieInfo)
    }

    companion object {
        val TAG: String = "MovieDetailFragment"

        fun launch(manager: FragmentManager, containerId: Int, movieInfo: MovieInfo) {
            var fragment: Fragment? = null
            if (null == manager.findFragmentByTag(TAG)) {
                fragment = MovieDetailFragment()
                var args: Bundle = Bundle()
                //                args.putParcelable("movieInfo", movieInfo)
                args.putSerializable("movieInfo", movieInfo)
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
        movieInfo = arguments.getSerializable("movieInfo") as MovieInfo
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = component.movieCommentPresenter()
        presenter.attachView(this)
        //        presenter.initMovieInfo(arguments.getSerializable("movieInfo") as MovieInfo)

        mRefreshLayout.setOnRefreshListener { presenter.getMovieCommentList() }

        mBtnSend.setOnClickListener(mBtnOnClickListener)

        mMovieDetailList.layoutManager = LinearLayoutManager(context)

        if (null == mMovieComment) {
            mMovieComment = ArrayList<MovieComment>()
        }

        mMovieAdapter = MovieDetailAdapter(mMovieComment!!)
        mMovieDetailAdapterWrap = RecycleViewHeaderFooterAdapter(mMovieAdapter)
        mMovieDetailList.adapter = mMovieDetailAdapterWrap

        val movieInfo: MovieInfo? = arguments.getSerializable("movieInfo") as MovieInfo
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(movieInfo?.movieUrl))
                .setTapToRetryEnabled(true)//点击重播
                .setAutoPlayAnimations(true)//自动播放
                .build()
        mHeadSdv.controller = controller

        presenter.getMovieCommentList()

    }

    var footTextView: TextView? = null

    /**
     * 设置footview提示语句
     */
    fun setFootTextViewHint(str: String) {
        println("======$str====$footTextView")

        if (footTextView == null) {

            println("=====================add=================")
            footTextView = TextView(context)
            footTextView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            footTextView!!.gravity = Gravity.CENTER
            val padding = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
            footTextView!!.setPadding(0, 0, 0, padding)
            footTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            mMovieDetailAdapterWrap?.addFootView(footTextView);

        }
        footTextView!!.text = str;
    }


    class MovieDetailAdapter(var movieComments: MutableList<MovieComment>) : RecyclerView.Adapter<MovieDetailViewHolder>() {

        override fun onBindViewHolder(holder: MovieDetailViewHolder?, position: Int) {
            holder?.build(movieComments[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieDetailViewHolder? {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_movie_comment, parent, false)
            return MovieDetailViewHolder(view)
        }

        override fun getItemCount(): Int {
            return movieComments.size
        }

    }

    class MovieDetailViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun build(comment: MovieComment) {
            val authorName = itemView.find<TextView>(R.id.comment_name)
            authorName.text = comment.commentAuthor?.username
            val commentTime = itemView.find<TextView>(R.id.comment_time)
            commentTime.text = comment.createdAt
            val commentContent = itemView.find<TextView>(R.id.comment_content)
            commentContent.text = comment.comment
        }
    }


    //=================================presneter 暴露接口===================================

    override fun showComment(commentList: MutableList<MovieComment>, isHasMore: Boolean) {
        if (mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = false
        }
        mMovieAdapter?.movieComments = commentList
        mMovieAdapter?.notifyDataSetChanged()

    }

    override fun showMovieInfo(movieInfo: MovieInfo) {
    }

    override fun showEmptComment() {
    }

    override fun showMoreComment(hasMore: Boolean) {
    }

}