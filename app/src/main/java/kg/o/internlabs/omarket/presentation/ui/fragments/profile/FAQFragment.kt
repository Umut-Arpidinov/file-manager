package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.databinding.FragmentFAQBinding
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

class FAQFragment : BaseFragment<FragmentFAQBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentFAQBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getAccessTokenFromPrefs()
        viewModel.getFaq()
    }

    override fun initView() {
        super.initView()
        getCategories()
    }

    private fun getCategories() {
        this@FAQFragment.safeFlowGather {
            viewModel.faqs.collectLatest {
                when(it) {
                    is ApiState.Success -> {
                        //TODO it.date.result вернет List<ResultsEntity> в нем хранятся
                        //TODO it.data.result?.get(0)?.title  questions
                        //TODO it.data.result?.get(0)?.id  id
                        //TODO it.data.result?.get(0)?.content  answers
                        //TODO it.data.results   это для получение всего списка вопрос-ответов все
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