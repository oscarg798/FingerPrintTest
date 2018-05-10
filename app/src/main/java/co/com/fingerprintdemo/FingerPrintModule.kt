package co.com.fingerprintdemo

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by oscarg798 on 5/9/18.
 */
@Module
class FingerPrintModule {


    @Provides
    @Singleton
    fun providesFingerprintManager(context: Context): FingerprintManager {
        return context.getSystemService(FingerprintManager::class.java)
    }

    @Provides
    @Singleton
    fun providesSecurityProvider(): ISecurityProvider {
        return SecurityProvider()
    }


}