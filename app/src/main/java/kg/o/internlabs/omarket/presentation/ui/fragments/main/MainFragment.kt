package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.databinding.FragmentMainBinding
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {

    override val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getAccessTokenFromPrefs()
        viewModel.getCategories()
    }

    override fun initView() {
        super.initView()
        getCategories()
    }

    private fun getCategories() {
        this@MainFragment.safeFlowGather {
            viewModel.categories.collectLatest {
                when(it) {
                    is ApiState.Success -> {
                        //TODO it.date.result вернет List<ResultEntity> в нем хранятся
                       //TODO it.data.result?.get(0)?.name  categoryName
                       //TODO it.data.result?.get(0)?.iconImg  icon
                       //TODO it.data.result   это для категорий все
                    }
                    is ApiState.Failure -> {
                        // если что то пошло ни так
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                        // запрос обрабатывается сервером
                    }
                }
            }
        }
    }
}