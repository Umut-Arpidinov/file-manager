package kg.o.internlabs.core.custom_views

interface CustomTextWatcher {
    fun numberWatcher(empty: Boolean, fieldsNumber: Int)
    fun passwordWatcher(empty: Boolean, fieldsNumber: Int)
}