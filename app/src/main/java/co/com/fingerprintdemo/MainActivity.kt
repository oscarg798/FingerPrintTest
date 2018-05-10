package co.com.fingerprintdemo

import android.hardware.fingerprint.FingerprintManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mFingerprintManager: FingerprintManager

    @Inject
    lateinit var mSecurityProvider: ISecurityProvider

    private lateinit var mFingerPrintAuthHelper: IFingerPrintAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as BaseApplication).getAppComponent().inject(this)

        mFingerPrintAuthHelper = FingerPrintAuthHelper(object : IFingerPrintAuthHelper.FingerPrintAuthHelperCallbacks {
            override fun onAuthSuccess() {
                Toast.makeText(this@MainActivity, "Auth success", Toast.LENGTH_LONG)
                        .show()

                val encryptText = mSecurityProvider.encrypt("Oscar and Stephany for ever")
                tvEncryptText?.text = encryptText
                tvDecryptText?.text = mSecurityProvider.decrypt(encryptText)


            }

            override fun onAuthError(error: String?) {
                Toast.makeText(this@MainActivity, "Auth error ${error
                        ?: "Error"}", Toast.LENGTH_LONG)
                        .show()
            }
        })

        btnAuth?.setOnClickListener {
            mFingerPrintAuthHelper.auth(mFingerprintManager, mSecurityProvider.getCryptoObject())
            Toast.makeText(this@MainActivity, "Please use your signature", Toast.LENGTH_LONG)
                    .show()
        }
    }
}
