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
}