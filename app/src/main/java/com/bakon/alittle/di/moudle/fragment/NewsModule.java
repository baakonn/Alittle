package com.bakon.alittle.di.moudle.fragment;

import com.bakon.alittle.mvp.main.MainContract;
import com.bakon.base_lib.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class NewsModule {
//    @Provides
//    @Singleton
//    public Adapter provideAdapter() {
//        return null;
//    }


    private MainContract.View view;

    public NewsModule(MainContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MainContract.View provideMainContractView() {
        return this.view;
    }
}
