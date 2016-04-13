package com.ohdroid.zbmaster.homepage.areamovie.model

import android.os.Parcel
import android.os.Parcelable
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import java.io.Serializable

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieComment(var commentAuthor: BmobUser? = null
                   , var comment: String? = ""
                   , var movieInfo: MovieInfo? = null) : BmobObject(), Serializable {

    //    override fun writeToParcel(dest: Parcel?, flags: Int) {
    //        dest?.writeSerializable(commentAuthor)
    //        dest?.writeString(comment)
    //        dest?.writeParcelable(movieInfo, flags)
    //    }
    //
    //    override fun describeContents(): Int {
    //        return 0
    //    }
    //
    //    companion object {
    //        @JvmField val CREATOR = object : Parcelable.Creator<MovieComment> {
    //            override fun newArray(size: Int): Array<out MovieComment>? {
    //                return Array(size, { MovieComment() })
    //            }
    //
    //            override fun createFromParcel(source: Parcel?): MovieComment? {
    //                val commentAuthor = source?.readSerializable() as BmobUser
    //                val comment = source?.readString()
    //                val favour = source?.readInt()
    //                val movieInfo = source?.readParcelable<MovieInfo>(MovieInfo::class.java.classLoader)
    //                return MovieComment(commentAuthor, comment, favour, movieInfo)
    //            }
    //
    //        }
    //    }


}