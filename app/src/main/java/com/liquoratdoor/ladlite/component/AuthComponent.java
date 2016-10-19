package com.liquoratdoor.ladlite.component;


import com.liquoratdoor.ladlite.fragment.AuthFragment;
import com.liquoratdoor.ladlite.modules.ActivityModule;

import dagger.Component;

/**
 * Created by ashqures on 8/20/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface AuthComponent extends ActivityComponent{

    void inject(AuthFragment authFragment);
}
