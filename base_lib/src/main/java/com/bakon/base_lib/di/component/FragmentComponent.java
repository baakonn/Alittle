package com.bakon.base_lib.di.component;

import android.app.Activity;

import com.bakon.base_lib.di.module.FragmentModule;
import com.bakon.base_lib.di.scope.FragmentScope;

import dagger.Component;

/**
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

}
