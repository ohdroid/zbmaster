package com.ohdroid.zbmaster.homepage.areaface

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceView
import org.jetbrains.anko.imageURI
import org.jetbrains.anko.support.v4.find
import java.util.*

/**
 * Created by ohdroid on 2016/4/4.
 */
class AreaFaceFragment : BaseFragment(), AreaFaceView {

    val faceList: RecyclerView by lazy { find<RecyclerView>(R.id.rv_face) }
    val freshLayout: SwipeRefreshLayout by lazy { find<SwipeRefreshLayout>(R.id.refresh_layout) }
    var faceListAdapter: FaceRecycleViewAdapter? = null;

    override fun showFaceList(faces: ArrayList<String>) {
        faceListAdapter?.addAll(faces)
    }

    override fun showEmpty() {
        throw UnsupportedOperationException()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //获取文件列表
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("=================create view =============");
        return inflater?.inflate(com.ohdroid.zbmaster.R.layout.fragment_area_face, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        println("=================on View Create =============");

        faceList.layoutManager = LinearLayoutManager(context)

        if (null == faceListAdapter) {
            faceListAdapter = FaceRecycleViewAdapter(ArrayList())
        }
        faceList.adapter = faceListAdapter

        val faces: ArrayList<String> = ArrayList()
        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/005zXVmagw1f1ctjte2g2g30fk06ekjm%20%281%29.gif")
        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        //        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        //        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        //        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        //        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        //        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        //        faces.add("http://7xslkd.com2.z0.glb.clouddn.com/image/gif/5cfc088egw1f2g03nev15g205k06xe81.gif");
        faces.add("http://img.zcool.cn/community/01e24e56321cc26ac7259e0f136ad6.jpg")
        faces.add("http://img.zcool.cn/community/01e24e56321cc26ac7259e0f136ad6.jpg")
        faces.add("http://img.zcool.cn/community/01e24e56321cc26ac7259e0f136ad6.jpg")
        faces.add("http://img.zcool.cn/community/01e24e56321cc26ac7259e0f136ad6.jpg")
        showFaceList(faces)
    }

    inner class FaceRecycleViewAdapter(var faceUrls: ArrayList<String>) : RecyclerView.Adapter<FaceViewHolder>() {
        fun clear() {
            faceUrls = ArrayList<String>()
            notifyDataSetChanged()
        }

        fun addAll(data: ArrayList<String>) {
            faceUrls.addAll(data)
            notifyDataSetChanged()
        }


        override fun onBindViewHolder(holder: FaceViewHolder?, position: Int) {
            holder?.itemView?.setOnClickListener({ println(it.toString() + "<<-----view") })
            holder?.setImageViewUrl(faceUrls[position])
            holder?.setImageDescription(faceUrls[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FaceViewHolder? {
            return FaceViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_area_face, parent, false))
        }

        override fun getItemCount(): Int {
            return faceUrls.size
        }


    }

    inner class FaceViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun setImageViewUrl(imageUrl: String) {
            val imageView: SimpleDraweeView = itemView.findViewById(R.id.item_image) as SimpleDraweeView

            val builder: PipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder()
            builder.autoPlayAnimations = true
            builder.setUri(imageUrl)
            imageView.controller = builder.build()
        }

        fun setImageDescription(descrition: String) {
            (itemView.findViewById(R.id.item_content) as TextView).text = descrition
        }

        //        fun setLis(listener: OnRecycleViewItemClickListener){
        //            itemClickListener = listener
        //        }

    }
}