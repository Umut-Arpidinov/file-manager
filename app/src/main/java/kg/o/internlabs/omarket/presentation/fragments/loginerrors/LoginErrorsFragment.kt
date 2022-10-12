package kg.o.internlabs.omarket.presentation.fragments.loginerrors

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginErrorsBinding

class LoginErrorsFragment : Fragment() {

    private lateinit var binding: FragmentLoginErrorsBinding
    private lateinit var viewModel: LoginErrorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginErrorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginErrorsViewModel::class.java)
    }

}