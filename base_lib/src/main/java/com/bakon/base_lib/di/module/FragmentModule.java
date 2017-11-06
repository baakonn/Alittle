package com.bakon.base_lib.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.bakon.base_lib.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bakon_ma on 2017-10-24
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }

}
