package com.ohdroid.zbmaster.homepage.areamovie.model

import android.os.Parcel
import android.os.Parcelable
import cn.bmob.v3.BmobObject
import java.io.Serializable

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieInfo(var movieUrl: String = "", var movieTitle: String = "", var fileSize: Long) : BmobObject(), Serializable {

    //    override fun writeToParcel(dest: Parcel?, flags: Int) {
    //        dest?.writeString(movieUrl)
    //        dest?.writeString(movieTitle)
    //    }
    //
    //    override fun describeContents(): Int {
    //        return 0
    //    }
    //
    //    companion object {
    //        @JvmField val CREATOR = object : Parcelable.Creator<MovieInfo> {
    //            override fun createFromParcel(source: Parcel?): MovieInfo? {
    //                val movieUrl = source?.readString() ?: ""
    //                val movieTitle = source?.readString() ?: ""
    //                return MovieInfo(movieUrl, movieTitle)
    //            }
    //
    //            override fun newArray(size: Int): Array<out MovieInfo>? {
    //                return Array(size, { MovieInfo() })
    //            }
    //
    //        }
    //    }


}