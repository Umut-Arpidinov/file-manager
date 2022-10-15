package kg.o.internlabs.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM: ViewModel, VB: ViewBinding> : AppCompatActivity() {
    private lateinit var binding: VB
    protected abstract val viewModel: VM

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInternet()
        binding = inflateViewBinding(layoutInflater)

        setContentView(binding.root)

        initViewModel()
        initView()
        initListener()
    }

    open fun checkInternet() {}
    open fun initViewModel() {}// Обрабатываем все обзерверы
    open fun initView() {} // Инициализации вьюшек
    open fun initListener() {} // Прописываем все логику кликов
}