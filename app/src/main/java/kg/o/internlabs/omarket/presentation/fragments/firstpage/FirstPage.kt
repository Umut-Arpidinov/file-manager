package kg.o.internlabs.omarket.presentation.fragments.firstpage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentFirstPageBinding

class FirstPage : Fragment() {

    private lateinit var viewModel: FirstPageViewModel
    private lateinit var binding: FragmentFirstPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstPageBinding.inflate(layoutInflater)
        return binding.root   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FirstPageViewModel::class.java]
    }

}