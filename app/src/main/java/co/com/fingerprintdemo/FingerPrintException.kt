package co.com.fingerprintdemo

/**
 * Created by oscarg798 on 5/9/18.
 */
class FingerPrintException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {

    override val message: String?
        get() = super.message ?: "Sorry something went wrong"
}