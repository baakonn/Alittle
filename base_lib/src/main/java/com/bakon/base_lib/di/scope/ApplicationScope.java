package com.bakon.base_lib.di.scope;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Singleton;

@Singleton
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}
