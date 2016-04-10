package com.ohdroid.zbmaster.homepage.areaface.model

import android.os.Parcel
import android.os.Parcelable
import cn.bmob.v3.BmobObject

/**
 * Created by ohdroid on 2016/4/6.
 */
class FaceInfo(var faceUrl: String = "", var faceTitle: String = "") : BmobObject(), Parcelable {


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(faceUrl)
        dest?.writeString(faceTitle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        fun createFaceInfo(source: Parcel?): FaceInfo {
            if (null == source) {
                return FaceInfo()
            }
            return FaceInfo(source.readString(), source.readString())
        }

        @JvmField val CREATOR: Parcelable.Creator<FaceInfo> = object : Parcelable.Creator<FaceInfo> {
            override fun createFromParcel(source: Parcel?): FaceInfo? {
                return createFaceInfo(source)
            }

            override fun newArray(size: Int): Array<out FaceInfo>? {
                return Array(size, { FaceInfo() })
            }

        }
    }


}