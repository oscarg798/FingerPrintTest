package co.com.fingerprintdemo

import android.app.Application
import javax.inject.Inject

/**
 * Created by oscarg798 on 5/9/18.
 */
class BaseApplication : Application() {

    @Inject
    lateinit var mSecurityProvider: ISecurityProvider

    private val mAppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this))
                .fingerPrintModule(FingerPrintModule())
                .build()

    }


    override fun onCreate() {
        super.onCreate()
        mAppComponent.inject(this)
        mSecurityProvider.generateKey()
    }

    fun getAppComponent(): AppComponent = mAppComponent
}