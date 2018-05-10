package co.com.fingerprintdemo

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by oscarg798 on 5/9/18.
 */
@Module(includes = [(FingerPrintModule::class)])
class AppModule(private val mApplication: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context = mApplication


}