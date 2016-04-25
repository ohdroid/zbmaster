package com.ohdroid.zbmaster.homepage.areamovie.view.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.jakewharton.rxbinding.view.RxView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.ShareHelper
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.view.recycleview.RecycleViewHeaderFooterAdapter
import com.ohdroid.zbmaster.application.view.recycleview.RecycleViewLoadMoreListener
import com.ohdroid.zbmaster.application.view.progress.CircleProgress
import com.ohdroid.zbmaster.application.view.progress.ImageViewProgressController
import com.ohdroid.zbmaster.application.view.recycleview.RecycelViewAddViewHelper
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieDataManager
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieDetailView
import com.ohdroid.zbmaster.utils.NetUtils
import com.ohdroid.zbmaster.utils.SPUtils
import com.rengwuxian.materialedittext.MaterialEditText
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieDetailFragment : BaseFragment(), MovieDetailView {


    val mMovieDetailList: RecyclerView by lazy { find<RecyclerView>(R.id.rv_movie_detail) }
    val mHeadSdv: SimpleDraweeView by lazy { find<SimpleDraweeView>(R.id.sdv_movie) }
    val mRefreshLayout: SwipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.srf_layout) }
    val mLoadingView: CircleProgress by lazy { find<CircleProgress>(R.id.loading_view) }
    var mMovieCommentAdapter: MovieDetailAdapter? = null
    var mMovieDetailAdapterWrap: RecycleViewHeaderFooterAdapter<MovieDetailViewHolder>? = null
    var mMovieComment: MutableList<MovieComment>? = null
    val mRootLayout: LinearLayout by lazy { find<LinearLayout>(R.id.root_layout) }

    val mBtnFavorite: Button by lazy { find<Button>(R.id.btn_favorite) }
    val mBtnSend: Button by lazy { find<Button>(R.id.btn_send) }
    val mBtnShare: Button by lazy { find<Button>(R.id.btn_share) }
    val mEtLayout: View by lazy { find<View>(R.id.layout_comment_edit) }
    val mEtComment: MaterialEditText by lazy { find<MaterialEditText>(R.id.et_comment) }

    val mBtnOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_send -> sendCommment()
        }
    }

    lateinit var presenter: MovieCommentPresenter
        @Inject set

    lateinit var movieInfo: MovieInfo

    var shareHelper: ShareHelper? = null
        @Inject set

    fun sendCommment() {

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
                args.putSerializable("movieInfo", movieInfo)
                fragment.arguments = args
                manager.beginTransaction()
                        .add(containerId, fragment)
                        .commit()
            } else {
                fragment = manager.findFragmentByTag(TAG)
                manager.beginTransaction()
                        .show(fragment)
                        .commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("on create = saved instance state=======>>$savedInstanceState")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        component.inject(this)
        //        presenter = component.movieCommentPresenter()
        presenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("on activity create = saved instance state=======>>$savedInstanceState")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_movie_detail, container, false)
        movieInfo = arguments.getSerializable("movieInfo") as MovieInfo
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {


        //        val preComment: String? = savedInstanceState?.getString("preEtContent")
        //        mEtComment.setText(preComment ?: "")

        //rx set,TODO unsubscribe
        RxView.clicks(mBtnSend).throttleFirst(3, TimeUnit.SECONDS)//防止短时间类刷屏行为
                .subscribe({ sendCommment() })
        RxView.clicks(mBtnShare).throttleFirst(3, TimeUnit.SECONDS)
                .subscribe { shareGif() }

        presenter.initMovieInfo(movieInfo)

        mLoadingView.startAnim()

        mRefreshLayout.setOnRefreshListener { presenter.getMovieCommentList() }
        mRefreshLayout.setColorSchemeColors(Color.parseColor("#FF9966"), Color.parseColor("#FF6666"), Color.parseColor("#FFCCCC"))

        mMovieDetailList.layoutManager = LinearLayoutManager(context)

        if (null == mMovieComment) {
            mMovieComment = ArrayList<MovieComment>()
        }

        mMovieCommentAdapter = MovieDetailAdapter(mMovieComment!!)
        mMovieDetailAdapterWrap = RecycleViewHeaderFooterAdapter(mMovieCommentAdapter)
        mMovieDetailList.adapter = mMovieDetailAdapterWrap

        //图像加载进度控制
        val builder: GenericDraweeHierarchyBuilder = GenericDraweeHierarchyBuilder(activity.resources)
        builder.progressBarImage = object : ImageViewProgressController() {
            override fun onLevelChange(level: Int): Boolean {
                if (10000 == level) {
                    mLoadingView.postDelayed({
                        println("=======hiding view======")
                        mLoadingView.stopAnim()
                        mLoadingView.visibility = View.GONE
                    }, 3600)
                }
                return super.onLevelChange(level)
            }
        }
        builder.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP
        mHeadSdv.hierarchy = builder.build()
        val isFastMode = SPUtils.get(context, SPUtils.FAST_MODE_KEY, true) as Boolean
        println(message = "is fastMode===>>$isFastMode")
        movieInfo.movieUrl = QiniuApi.getDynamicURL(movieInfo.movieUrl, movieInfo.fileSize, isFastMode)

        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(movieInfo.movieUrl))
                .setTapToRetryEnabled(true)//点击重播
                .setAutoPlayAnimations(true)//自动播放
                .build()
        mHeadSdv.controller = controller

        mMovieDetailList.addOnScrollListener(loadMoreListener)

        presenter.getMovieCommentList()

        //根据布局改变来监听软键盘弹起和收缩
        mRootLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            var isAdjust = false
            var preEtLayoutY = 0
            var preRootViewHeight = 0
            var adjustOffset = 0
            override fun onGlobalLayout() {
                val offset: Int = mRootLayout.rootView.height - mRootLayout.height
                if (0 == preEtLayoutY) {
                    preEtLayoutY = mEtLayout.y.toInt()
                    preRootViewHeight = mRootLayout.height
                }

                if (offset > 800) {
                    if (!isAdjust) {
                        //                        println("键盘弹起")
                        if (adjustOffset == 0) {
                            adjustOffset = (preRootViewHeight - mRootLayout.height) - (preEtLayoutY - mEtLayout.y.toInt())
                        }
                        mEtLayout.translationY -= adjustOffset
                        isAdjust = true
                    }
                } else {
                    if (isAdjust) {
                        //                        println("键盘收起")
                        mEtLayout.translationY += adjustOffset
                        adjustOffset = 0
                        isAdjust = false
                    }
                }
            }

        })

    }


    val loadMoreListener = object : RecycleViewLoadMoreListener() {
        override fun onLoadMoreData() {
            //loading more data
            println("load more data")
            setFootTextViewHint(context.resources.getString(R.string.hint_load_more))
            presenter.getMoreMovieCommentList()
        }
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

    override fun onDestroy() {
        println("movie detail fragment destroy~~~~~~~~~~")
        presenter.detachView()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        println("on save instance state:${mEtComment.text.toString()}")
        if (!TextUtils.isEmpty(mEtComment.text.toString())) {
            outState?.putString("preEtContent", mEtComment.text.toString())
        }
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

    fun shareGif() {
        val listener = object : IUiListener {
            override fun onComplete(p0: Any?) {
            }

            override fun onCancel() {
            }

            override fun onError(p0: UiError?) {
                this@MovieDetailFragment.showToast(p0?.errorMessage ?: "error")
            }

        }

        shareHelper?.share2QQ(movieInfo.movieUrl, activity, listener)
    }


    //=================================presneter 暴露接口===================================

    override fun showComment(commentList: MutableList<MovieComment>, isHasMore: Boolean) {
        mMovieDetailAdapterWrap?.removeAllFootView()

        if (activity.isFinishing) {
            return
        }

        if (mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = false
        }
        loadMoreListener.canLoadingMore = isHasMore

        mMovieCommentAdapter?.movieComments = commentList
        mMovieCommentAdapter?.notifyDataSetChanged()

    }

    override fun showMovieInfo(movieInfo: MovieInfo) {
        if (activity.isFinishing) {
            return
        }
    }

    override fun showEmptyComment() {
        if (activity.isFinishing) {
            return
        }
        if (mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = false
        }

        RecycelViewAddViewHelper.addNoDataFootView(context.getString(R.string.hint_no_comment_data), context, mMovieDetailAdapterWrap!!)
    }

    override fun showMoreComment(hasMore: Boolean) {
        if (activity.isFinishing) {
            return
        }
        loadMoreListener.isLoadingMore = false
        loadMoreListener.canLoadingMore = hasMore
        if (!hasMore) {
            //            setFootTextViewHint(getString(R.string.hint_load_more))
            setFootTextViewHint(getString(R.string.hint_no_more_data))
        }
        mMovieCommentAdapter?.notifyDataSetChanged()

    }

    override fun showError(state: Int, errorMessage: String) {
        if (activity.isFinishing) {
            return
        }
        showToast("$state:$errorMessage")
    }

    override fun showAddCommentResult(state: Int, result: String) {
        if (state > 0) {
            //执行成功才刷新
            presenter.getMovieCommentList()
            mEtComment.setText("")
        }
        showToast(result)
    }

}