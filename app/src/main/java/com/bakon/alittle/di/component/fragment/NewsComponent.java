package com.bakon.alittle.di.component.fragment;

import com.bakon.alittle.di.moudle.fragment.NewsModule;
import com.bakon.alittle.mvp.main.NewsFragment;
import com.bakon.base_lib.di.component.AppComponent;
import com.bakon.base_lib.di.scope.FragmentScope;

import dagger.Component;

/**
 *
 */
@FragmentScope
@Component(modules = NewsModule.class, dependencies = AppComponent.class)
public interface NewsComponent {
    void inject(NewsFragment newsFragment);
}
