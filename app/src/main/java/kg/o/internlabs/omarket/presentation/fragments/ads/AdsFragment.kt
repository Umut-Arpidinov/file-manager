package kg.o.internlabs.omarket.presentation.fragments.ads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.o.internlabs.omarket.databinding.FragmentAdsBinding

class AdsFragment : Fragment() {

    private lateinit var binding: FragmentAdsBinding
    private lateinit var viewModel: AdsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdsViewModel::class.java)
    }
}