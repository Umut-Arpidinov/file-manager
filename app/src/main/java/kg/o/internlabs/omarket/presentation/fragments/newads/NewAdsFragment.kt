package kg.o.internlabs.omarket.presentation.fragments.newads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentNewAdsBinding

class NewAdsFragment : Fragment() {

    private lateinit var binding: FragmentNewAdsBinding
    private lateinit var viewModel: NewAdsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewAdsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewAdsViewModel::class.java)
    }
}