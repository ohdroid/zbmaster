package com.ohdroid.zbmaster.homepage.areaface.view

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
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
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.application.view.RecycleViewHeaderFooterAdapter
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/4/4.
 */
class AreaFaceFragment : BaseFragment(), AreaFaceView {


    val faceList: RecyclerView by lazy { find<RecyclerView>(R.id.rv_face) }
    val freshLayout: SwipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.refresh_layout) }
    var faceListAdapter: FaceRecycleViewAdapter? = null
    var faceListAdapterWrap: RecycleViewHeaderFooterAdapter<FaceViewHolder>? = null
    lateinit var presenter: AreaFacePresenter


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = component.faceAreaPresenter();
        presenter.attachView(this)
        presenter.loadFaceList()
    }


    override fun showFaceInfoDetail(faceInfo: FaceInfo) {
        AreaFaceDetailFragment.launch(activity.supportFragmentManager, R.id.face_fragment_container, faceInfo.faceUrl)
    }

    /**
     * 初始化显示表情数据
     */
    override fun showFaceList(faces: MutableList<FaceInfo>) {

        println("show face info data")

        if ( freshLayout.isRefreshing) {
            freshLayout.isRefreshing = false
        }

        if (faces.size == 0) {
            showEmpty()
            return
        }
        faceListAdapter?.faceUrls = faces
        faceListAdapter?.notifyDataSetChanged()
    }

    override fun isHasMoreData(hasMore: Boolean) {
        println("set has more data :$hasMore")
        loadMoreListener.canLoadingMore = hasMore
        loadMoreListener.isLoadingMore = false
        if (!hasMore) {
            showEmpty()
        }
    }

    val loadMoreListener = object : RecycleViewLoadMoreListener() {
        override fun onLoadMoreData() {
            println("view load more")
            presenter.loadMoreFaceInfo()
            showToast(resources.getString(R.string.hint_load_more_data))
        }
    }

    override fun showMoreFaceInfo(faces: MutableList<FaceInfo>) {
        println("show more data")
        faceListAdapter?.notifyDataSetChanged()
    }

    override fun showEmpty() {
        println(".........show empty foot..............")
        val emptyView: TextView = TextView(context)
        emptyView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        emptyView.setTextColor(R.color.material_grey_100)
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        emptyView.setText(R.string.hint_empty_data)
        emptyView.gravity = Gravity.CENTER
        faceListAdapterWrap?.addFootView(emptyView)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_area_face, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //下拉刷新初始化
        freshLayout.setOnRefreshListener { presenter.loadFaceList() }
        freshLayout.setColorSchemeColors(Color.parseColor("#FF9966"), Color.parseColor("#FF6666"), Color.parseColor("#FFCCCC"))


        faceList.addOnScrollListener(loadMoreListener)

        faceList.layoutManager = LinearLayoutManager(context)
        if (null == faceListAdapter) {
            faceListAdapter = FaceRecycleViewAdapter(arrayListOf())
            faceListAdapter!!.listener = object : OnRecycleViewItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    //跳转到表情详情页面
                    presenter.showFaceInfoDetail(position)
                }
            }
        }

        //        faceList.adapter = faceListAdapter
        faceListAdapterWrap = RecycleViewHeaderFooterAdapter<FaceViewHolder>(faceListAdapter)
        faceList.adapter = faceListAdapterWrap
    }

    inner class FaceRecycleViewAdapter(var faceUrls: MutableList<FaceInfo>) : RecyclerView.Adapter<FaceViewHolder>() {
        var listener: OnRecycleViewItemClickListener? = null

        override fun onBindViewHolder(holder: FaceViewHolder?, position: Int) {
            if (listener != null) {
                holder?.listener = listener
            }
            holder?.setImageViewUrl(faceUrls[position].faceUrl)
            holder?.setImageDescription(faceUrls[position].faceTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FaceViewHolder? {
            return FaceViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_area_face, parent, false))
        }

        override fun getItemCount(): Int {
            return faceUrls.size
        }

    }

    inner class FaceViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var listener: OnRecycleViewItemClickListener? = null
            set(value) {
                itemView.setOnClickListener({ v -> value!!.onItemClick(v!!, adapterPosition) })
            }

        fun setImageViewUrl(imageUrl: String?) {
            if (null == imageUrl) {
                //TODO 若无地址，那么显示数据异常图标
            }

            val imageView: SimpleDraweeView = itemView.findViewById(R.id.item_image) as SimpleDraweeView
            imageView.setImageURI(Uri.parse(imageUrl), null)//内部还是通过controllerbuidler控制的
        }

        fun setImageDescription(descrition: String?) {
            (itemView.findViewById(R.id.item_content) as TextView).text = descrition
        }
    }

    abstract class RecycleViewLoadMoreListener : RecyclerView.OnScrollListener() {
        /**
         * 当加载更多事件激活时候，调用此方法
         */
        abstract fun onLoadMoreData()

        /**
         * 是否正在加载更多数据
         */
        var isLoadingMore = false
        /**
         * 是否触发加载更多数据事件
         */
        var canLoadingMore = true

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            //加载更多的原理是:判断当前显示的内容是否是显示到了recycleview底部
            val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager//由于gridLayout是继承LinearLayout的所以这里可以强转成LinearLayout
            val itemCount = linearLayoutManager.itemCount
            val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

            if (!canLoadingMore || isLoadingMore || dy < 0) {
                //不能加载更多，加载中，向上滑，都直接返回
                return
            }

            if (lastVisibleItemPosition >= itemCount - 1) {
                isLoadingMore = true
                //我这里只做了事件触发，而是否记载更多就具体类来实现了
                onLoadMoreData()
            }
        }

    }
}