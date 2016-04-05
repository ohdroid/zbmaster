package com.ohdroid.zbmaster.facesync.data

import android.os.Environment

/**
 * Created by ohdroid on 2016/3/23.
 */
class FileDirList{
    companion object{
        val rootPath= Environment.getExternalStorageDirectory().absolutePath.toString();
        val QQ_IMAGE_DIR = rootPath+"/tencent/QQ_Images"
    }
}