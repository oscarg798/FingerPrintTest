package co.com.fingerprintdemo

import android.hardware.fingerprint.FingerprintManager
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.Key
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec


/**
 * Created by oscarg798 on 5/9/18.
 */
class SecurityProvider : ISecurityProvider {

    private val KEY_NAME = "FINGERPRINT_KEY"

    private val KEY_STORE_PROVIDER = "AndroidKeyStore"

    private val mPurpose = Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE

    private val mPadding = KeyProperties.ENCRYPTION_PADDING_PKCS7

    private val mKeyStore = KeyStore.getInstance(KEY_STORE_PROVIDER)

    private val mCipherAlgorithm = "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/$mPadding"

    private var mCipher: Cipher? = null


    override fun generateKey() {
        try {
            val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE_PROVIDER)

            keyGen.init(KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(mPadding)
                    .build())

            keyGen.generateKey()
            initFingerPrintCipher()
        } catch (e: Exception) {
            throw FingerPrintException(e.message, e.cause)
        }


    }

    override fun getKey(): Key? {
        return try {
            mKeyStore.load(null)
            mKeyStore.getKey(KEY_NAME, null)
        } catch (e: Exception) {
            throw   FingerPrintException(e.message, e.cause)
        }
    }


    @Throws(FingerPrintException::class)
    override fun encrypt(textToEncrypt: String): String {
        mCipher?.apply {
            init(Cipher.ENCRYPT_MODE, getKey())
            return Base64.encodeToString(doFinal(textToEncrypt.toByteArray()), Base64.DEFAULT)

        }
        throw FingerPrintException("Cipher is null")

    }

    @Throws(FingerPrintException::class)
    override fun decrypt(encryptText: String): String {
        mCipher?.apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
            val decryptedBytes = doFinal(Base64.decode(encryptText, Base64.DEFAULT))
            return String(decryptedBytes)

        }

        throw FingerPrintException("Cipher is null")
    }


    @Throws(FingerPrintException::class)
    override fun getCryptoObject(): FingerprintManager.CryptoObject {
        initFingerPrintCipher()
        return try {
            mCipher?.init(mPurpose, getKey())
            FingerprintManager.CryptoObject(mCipher)
        } catch (e: Exception) {
            throw FingerPrintException(e.message ?: "Cipher is null", e.cause)
        }


    }

    @Throws(FingerPrintException::class)
    private fun initFingerPrintCipher() {
        try {
            if (mCipher == null) {
                mCipher = Cipher.getInstance(mCipherAlgorithm)

            }
        } catch (e: Exception) {
            throw  when (e) {
                is NoSuchPaddingException,
                is NoSuchAlgorithmException -> FingerPrintException(e.message, e.cause)
                else -> FingerPrintException()
            }
        }

    }

    override fun removeAuth() {
        val key = getKey()
        key?.let {
            mKeyStore.deleteEntry(KEY_NAME)
        }

    }


}