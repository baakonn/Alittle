package com.bakon.base_lib.di.component;

import android.app.Activity;

import com.bakon.base_lib.di.module.ActivityModule;
import com.bakon.base_lib.di.scope.ActivityScope;

import dagger.Component;

/**
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();
}
