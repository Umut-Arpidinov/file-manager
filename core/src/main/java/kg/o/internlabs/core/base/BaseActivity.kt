package kg.o.internlabs.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM: ViewModel, VB: ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected abstract val viewModel: VM

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding(layoutInflater)
        operationBeforeSetContent()
        setContentView(binding.root)

        checkInternet()
        initViewModel()
        initView()
        initListener()
    }

    open fun operationBeforeSetContent() {}  // Этот метод выпонится до задание setContentView
    open fun checkInternet() {} // Провека интернета
    open fun initViewModel() {}// Обрабатываем все обзерверы
    open fun initView() {} // Инициализации вьюшек
    open fun initListener() {} // Прописываем все логику кликов
}