package com.github.jliblbfgs;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Created by scr on 3/15/16.
 */
public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LBFGSVector.IFactory.class).to(LBFGSVector.Factory.class).in(Scopes.SINGLETON);
    }
}
