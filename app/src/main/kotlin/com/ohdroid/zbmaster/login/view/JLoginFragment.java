package com.ohdroid.zbmaster.login.view;

import android.content.Context;

import com.ohdroid.zbmaster.application.di.ActivityComponent;
import com.ohdroid.zbmaster.application.di.ActivityModule;
import com.ohdroid.zbmaster.application.di.ApplicationModule;
import com.ohdroid.zbmaster.application.di.DaggerActivityComponent;
import com.ohdroid.zbmaster.base.view.BaseFragment;

/**
 * Created by ohdroid on 2016/3/5.*
 */
public class JLoginFragment extends BaseFragment {

//    protected LoginActivityComponent component;
//    protected ActivityComponent component;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        component = DaggerLoginActivityComponent.builder()
//                .applicationModule(new ApplicationModule(context.getApplicationContext()))
//                .loginModule(new LoginModule(getActivity()))
//                .build();
//        component = DaggerActivityComponent.builder()
//                .applicationModule(new ApplicationModule(context.getApplicationContext()))
//                .activityModule(new ActivityModule(getActivity()))
//                .build();

    }
}
