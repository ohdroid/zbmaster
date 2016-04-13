package com.ohdroid.zbmaster.login.model

import cn.bmob.v3.BmobUser
import java.io.Serializable

/**
 * Created by ohdroid on 2016/3/5.
 *账户数据
 */
class AccountInfo constructor() : BmobUser(), Serializable {

    var photoUrl: String? = ""

}