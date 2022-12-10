package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.MainAdsDto
import kg.o.internlabs.omarket.databinding.FragmentMainBinding
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {

    private var args: MainFragmentArgs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = MainFragmentArgs.fromBundle(requireArguments())

//  Unnecessary lines start there
        val imgURL1 = "data:image/png;base64,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"
        val imgURL2 = "https://play-lh.googleusercontent.com/p51P1MutZJY9410vLPCsF-IAUVBmPxt8hi4W-3PTFwZBSPJmraaGyMT5Uv49cRZYSw0"

        val arrayOfString: List<String> = listOf(imgURL1, imgURL2)

        val listInfo = mutableListOf(
            MainAdsDto(1, arrayOfString, "UAZ 2107", "120000", "", "cars", "Bishkek", true, false, "som", true),
            MainAdsDto(2, arrayOfString, "Mercedes", "300000", "4000000", "cars", "Bishkek", true, true, "dollar", true),
            MainAdsDto(3, arrayOfString, "BMW", "300000", "4000000", "cars", "Osh", false, true, "som", true),
            MainAdsDto(4, arrayOfString, "Toyota", "1200000", null, "cars", "Bishkek", false, false, "dollar", false),
            MainAdsDto(4, arrayOfString, "Toyota", "1200000", null, "cars", "Bishkek", false, false, "dollar", false),
            MainAdsDto(4, arrayOfString, "Toyota", "1200000", null, "cars", "Bishkek", false, false, "dollar", false),
            MainAdsDto(4, arrayOfString, "Toyota", "1200000", null, "cars", "Bishkek", false, false, "dollar", false),
            MainAdsDto(4, arrayOfString, "Toyota", "1200000", null, "cars", "Bishkek", false, false, "dollar", false)
        )

        val mainAdapter = AdsListAdapter(listInfo, ViewGroup.LayoutParams.MATCH_PARENT, this)

//        binding.mainViewHolder.addItemDecoration(
//            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.item_margin_7dp))
//        )
        binding.mainViewHolder.adapter = mainAdapter
//  Unnecessary lines end there
    }

    override val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }



    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)

    override fun initListener() = with(binding) {
        super.initListener()
        tbMain.setNavigationOnClickListener {
            findNavController().navigate(MainFragmentDirections.goToProfile(args?.number)) }
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
                        println("-------"+it.data.result?.get(0)?.iconImg)
                        println("-------"+it.data.result)
                        it.data.result?.get(0)?.iconImg?.let { it1 ->
                            //add another binding.imgAds
//                            glide(this,
//                                it1, binding.imgAds)
                        }
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