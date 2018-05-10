package co.com.fingerprintdemo

import dagger.Component
import javax.inject.Singleton

/**
 * Created by oscarg798 on 5/9/18.
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(application: BaseApplication)
}