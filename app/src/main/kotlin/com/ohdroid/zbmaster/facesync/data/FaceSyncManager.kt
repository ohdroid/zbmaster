package com.ohdroid.zbmaster.facesync.data

import android.content.Context
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.listener.UploadBatchListener
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication
import com.ohdroid.zbmaster.facesync.data.bmob.BmobFileSyncManager
import java.io.File
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/3/22.
 */
class FaceSyncManager @Inject constructor(@ForApplication context: Context) : IFaceSyncManager {


    lateinit var bmobFileSyncManger: BmobFileSyncManager

    init {
        //构建bmob 文件上传器
        bmobFileSyncManger = BmobFileSyncManager(context)
    }


    override fun uploadFaceFile(uploadFileListener: UploadBatchListener) {
        val files: Array<String>? = getUploadFileList() ?: return

        for (name in files!!) {
            println(name)
        }

        bmobFileSyncManger.uploadFaceFile(files, object : UploadBatchListener {
            override fun onSuccess(p0: MutableList<BmobFile>?, p1: MutableList<String>?) {
                println(p0?.size.toString() + "<<===============")
                //保存成功上传后的文件信息到数据库
            }

            override fun onProgress(p0: Int, p1: Int, p2: Int, p3: Int) {
                println(p3.toString() + "<<======progress=====")
            }

            override fun onError(p0: Int, p1: String?) {
                println(p1 + "<<======failed=====")
            }

        })
    }


    /**
     * 获取待上传文件列表
     */
    fun getUploadFileList(): Array<String>? {
        val qqDirPath = FileDirList.QQ_IMAGE_DIR
        val file: File = File(qqDirPath)

        if (!file.exists()) {

            println(qqDirPath.toString() + "\tnot exists!!")

            return null
        }

        var fileList: Array<File> = file.listFiles()
        val fileAbPath: Array<String> = Array(fileList.size, { "" })

        for (i in fileList.indices) {
            //检查文件是否已经上传
            checkFileIsUpload(fileList[i].name)
            if (i >= 2) {
                return fileAbPath
            }
            fileAbPath[i] = fileList[i].absolutePath
        }
        return fileAbPath
    }

    /**
     * 检查文件是否已经上传
     */
    fun checkFileIsUpload(fileName: String) {

    }


}