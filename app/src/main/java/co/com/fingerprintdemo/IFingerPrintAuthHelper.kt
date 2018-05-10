package co.com.fingerprintdemo

import android.hardware.fingerprint.FingerprintManager

/**
 * Created by oscarg798 on 5/9/18.
 */
interface IFingerPrintAuthHelper {

    fun auth(fingerprintManager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject)


    interface FingerPrintAuthHelperCallbacks {
        fun onAuthSuccess()

        fun onAuthError(error: String? = null)
    }

}