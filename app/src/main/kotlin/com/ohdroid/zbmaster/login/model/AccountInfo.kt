package com.ohdroid.zbmaster.login.model

/**
 * Created by ohdroid on 2016/3/5.
 *账户数据
 */
data class AccountInfo constructor(
        val uesrName: String,
        val password: String) {
    var photoUrl: String? = ""
}