package com.globeandi.toeat.dependencies.component;

import android.app.Application;

import com.globeandi.toeat.ToeatApplication;
import com.globeandi.toeat.data.remote.AuthAuthenticator;
import com.globeandi.toeat.dependencies.builder.ActivityBindingBuilder;
import com.globeandi.toeat.dependencies.module.AppModule;
import com.globeandi.toeat.dependencies.module.NetworkAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by jay on 3/20/2018.
 * defines the connection between the provider of objects (modules)
 * and the objects which express a dependency. The class for this connection is generated by the Dagger
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,AppModule.class,ActivityBindingBuilder.class, NetworkAppModule.class})
public interface AppComponent {

    //activities, services, or fragments that are allowed to request the dependencies declared by the modules
    //should be declared in this class with individual inject() methods
    void inject(ToeatApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
