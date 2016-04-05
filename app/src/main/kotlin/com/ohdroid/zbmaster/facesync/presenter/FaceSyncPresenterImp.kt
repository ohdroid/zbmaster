package com.ohdroid.zbmaster.facesync.presenter

import android.content.Context
import android.os.Environment
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.listener.UploadBatchListener
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity
import com.ohdroid.zbmaster.facesync.data.FaceSyncManager
import com.ohdroid.zbmaster.facesync.data.FileDirList
import com.ohdroid.zbmaster.facesync.view.FaceSyncView
import java.io.File

/**
 * Created by ohdroid on 2016/3/22.
 */
class FaceSyncPresenterImp constructor(@PerActivity context: Context, dataManager: DataManager) : FaceSyncPresenter {

    lateinit var faceSyncView: FaceSyncView
    val faceSyncManager: FaceSyncManager = dataManager.faceSyncManager
    val context: Context = context;
    override fun syncFace() {

        faceSyncManager.uploadFaceFile(object : UploadBatchListener {
            override fun onSuccess(files: MutableList<BmobFile>?, p1: MutableList<String>?) {
            }

            override fun onProgress(p0: Int, p1: Int, p2: Int, p3: Int) {
            }

            override fun onError(p0: Int, p1: String?) {
            }

        })
    }

    override fun attachView(view: FaceSyncView) {
        faceSyncView = view
    }

    override fun detachView() {

    }

}