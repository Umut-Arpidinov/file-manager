package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentPhotoViewBinding
import kg.o.internlabs.omarket.presentation.ui.fragments.ads.DetailedImageAdapter

class PhotoViewFragment : BaseFragment<FragmentPhotoViewBinding, DetailAdViewModel>() {
    val imgURL2 =
        "https://play-lh.googleusercontent.com/p51P1MutZJY9410vLPCsF-IAUVBmPxt8hi4W-3PTFwZBSPJmraaGyMT5Uv49cRZYSw0"

    val arrayOfString: List<String> = listOf(imgURL2, imgURL2, imgURL2)

    override val viewModel: DetailAdViewModel by lazy {
        ViewModelProvider(this)[DetailAdViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentPhotoViewBinding {
        return FragmentPhotoViewBinding.inflate(layoutInflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        tbMedia.setOnClickListener {
            findNavController().navigateUp()
        }
        initViewPagerAdapter(viewpager, currentPos)
    }

    override fun initViewModel() {
        super.initViewModel()
    }

    private fun initViewPagerAdapter(imageViewPager: ViewPager2, currentPos: TextView) {
        val pagerAdapter = DetailedImageAdapter(
            requireContext(),
            arrayOfString,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        imageViewPager.adapter = pagerAdapter

        currentPos.text =
            String.format(getString(coreString.sample_indicator), 1, pagerAdapter.itemCount)

        imageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPos.text = String.format(
                    getString(coreString.sample_indicator),
                    position + 1,
                    pagerAdapter.itemCount
                )
            }
        })
    }
}