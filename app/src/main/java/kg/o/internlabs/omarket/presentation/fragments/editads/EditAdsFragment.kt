package kg.o.internlabs.omarket.presentation.fragments.editads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentEditAdsBinding

class EditAdsFragment : Fragment() {

    private lateinit var binding: FragmentEditAdsBinding
    private lateinit var viewModel: EditAdsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAdsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[EditAdsViewModel::class.java]
    }
}