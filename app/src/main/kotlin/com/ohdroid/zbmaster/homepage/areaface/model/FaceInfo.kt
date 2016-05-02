package com.ohdroid.zbmaster.homepage.areaface.model

import android.os.Parcel
import android.os.Parcelable
import cn.bmob.v3.BmobObject
import java.io.Serializable

/**
 * Created by ohdroid on 2016/4/6.
 */
open class FaceInfo(var faceUrl: String = "", var faceTitle: String = "", var fileSize: Long) : BmobObject(), Serializable {

}