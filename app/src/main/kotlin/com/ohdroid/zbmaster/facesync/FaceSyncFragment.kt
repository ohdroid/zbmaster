package com.ohdroid.zbmaster.facesync

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenter
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/3/22.
 */
class FaceSyncFragment : BaseFragment(), View.OnClickListener {


    val btnSync: Button by lazy { find<Button>(R.id.btn_sync) }
    lateinit var presenter: FaceSyncPresenter
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        component.inject(this)
        presenter = component.faceSyncPresenter();
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.face_sync_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSync.setOnClickListener(this@FaceSyncFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sync -> syncFace()
        }
    }


    /**
     * 同步表情
     */
    fun syncFace() {
        presenter.syncFace()
        showToast("同步表情！")
    }
}