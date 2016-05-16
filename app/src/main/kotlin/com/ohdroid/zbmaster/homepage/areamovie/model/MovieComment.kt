package com.ohdroid.zbmaster.homepage.areamovie.model

import android.os.Parcel
import android.os.Parcelable
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import com.ohdroid.zbmaster.login.model.AccountInfo
import java.io.Serializable

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieComment(var commentAuthor: AccountInfo? = null
                   , var comment: String? = ""
                   , var movieInfo: MovieInfo? = null) : BmobObject(), Serializable {
}