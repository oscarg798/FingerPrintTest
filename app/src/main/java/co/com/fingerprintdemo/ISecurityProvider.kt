package co.com.fingerprintdemo

import android.hardware.fingerprint.FingerprintManager
import java.security.Key

/**
 * Created by oscarg798 on 5/9/18.
 */
interface ISecurityProvider {

    fun generateKey()

    fun getKey(): Key?

    @Throws(FingerPrintException::class)
    fun getCryptoObject(): FingerprintManager.CryptoObject

    fun encrypt(textToEncrypt: String): String

    fun decrypt(encryptText: String): String
}