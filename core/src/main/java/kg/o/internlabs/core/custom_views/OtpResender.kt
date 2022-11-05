package kg.o.internlabs.core.custom_views

interface OtpResend {
    fun sendOtpAgain()
    fun watcher(empty: Boolean)
}