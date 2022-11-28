package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {

    override val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    private val prefs: StoragePreferences by lazy {
        StoragePreferences(requireContext())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        //findNavController().navigate(R.id.loginStartFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Ray", "ACCESS token ${prefs.token}")
        Log.d("Ray", "REFRESH token ${prefs.refreshToken}")
    }
}