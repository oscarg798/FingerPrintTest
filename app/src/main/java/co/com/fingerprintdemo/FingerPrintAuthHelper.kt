package co.com.fingerprintdemo

import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal

/**
 * Created by oscarg798 on 5/9/18.
 */
class FingerPrintAuthHelper(private val mCallback: IFingerPrintAuthHelper.FingerPrintAuthHelperCallbacks) : FingerprintManager.AuthenticationCallback(), IFingerPrintAuthHelper {


    @Throws(FingerPrintException::class)
    override fun auth(fingerprintManager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {
        fingerprintManager.authenticate(cryptoObject,
                CancellationSignal(), 0, this, null)
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        mCallback.onAuthError(errString?.toString())
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        mCallback.onAuthSuccess()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)
        mCallback.onAuthError(helpString?.toString())
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        mCallback.onAuthError()
    }
}