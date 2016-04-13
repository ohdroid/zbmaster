package com.ohdroid.zbmaster.base.view;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ohdroid.zbmaster.application.BaseApplication;
import com.ohdroid.zbmaster.application.di.ActivityComponent;
import com.ohdroid.zbmaster.application.di.ActivityModule;
import com.ohdroid.zbmaster.application.di.ApplicationModule;
import com.ohdroid.zbmaster.application.di.DaggerActivityComponent;

/**
 * Created by ohdroid on 2016/3/5.
 */
public abstract class BaseFragment extends Fragment {

    protected ActivityComponent component;

    public void onAttach(Context context) {
        super.onAttach(context);
        component = DaggerActivityComponent.builder()
                .applicationComponent(((BaseApplication) context.getApplicationContext()).getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build();
    }
}
