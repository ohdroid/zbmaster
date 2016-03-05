package com.ohdroid.zbmaster.login.presenter;

import com.ohdroid.zbmaster.application.BasePresenter;
import com.ohdroid.zbmaster.login.model.AccountInfo;
import com.ohdroid.zbmaster.login.view.LoginView;

/**
 * Created by ohdroid on 2016/3/5.
 */
public interface LoginPresenter extends BasePresenter<LoginView> {

    void login(AccountInfo accountInfo);

    void register(AccountInfo accountInfo);
}
