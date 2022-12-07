package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomProfileCellViewClickers
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentProfileBinding
import kg.o.internlabs.omarket.utils.checkPermission
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(),
    CustomProfileCellViewClickers {

    private var args: ProfileFragmentArgs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = ProfileFragmentArgs.fromBundle(requireArguments())
    }

    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentProfileBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        binding.cusProfile.setInterface(this@ProfileFragment)
        viewModel.getAccessTokenFromPrefs()
        viewModel.getActiveAds()
        viewModel.getNonActiveAds()
        viewModel.getAvatarUrlFromPrefs()
    }

    override fun initListener() {
        super.initListener()
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        resultLauncher.launch(Intent.createChooser(intent, "image"))
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), fun(result: ActivityResult) {
            if (result.resultCode != AppCompatActivity.RESULT_OK) return
            val intent: Intent? = result.data
            if (intent?.data == null) return
            val imageUri: Uri = intent.data!!
            uploadImage(imageUri)
        }
    )

    private fun uploadImage(imageUri: Uri) {
        checkPermission()
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(
            requireActivity(), imageUri, proj,
            null, null, null
        )
        viewModel.uploadAvatar(loader)
        setAvatar()
    }

    private fun setImageToAvatar() {
        safeFlowGather {
            viewModel.avatarUrl.collectLatest {
                binding.cusProfile.setIcon(it)
            }
        }
    }

    private fun setAvatar() = with(binding){
        safeFlowGather {
            viewModel.avatar.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        cusProfile.isProgressVisible()
                        val uri = it.data.result?.url.toString()
                        cusProfile.setIcon(uri)
                    }
                    is ApiState.Failure -> {
                        cusProfile.isProgressVisible()
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                        cusProfile.isProgressVisible(true)
                    }
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        args?.number?.let { binding.cusProfile.setTitle(it) }
        setImageToAvatar()
        getActiveAds()
        getNonActiveAds()
        getMenu()
    }

    private fun getNonActiveAds() {
        safeFlowGather {
            viewModel.nonActiveAds.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        println("---------all active ads --------\n" + it.data.result?.results.toString())
                        //TODO it.date.result.results вернет List<MyAdsResultsEntity> в нем хранятся
                        //TODO все свои не активные объявление такие как заблокированние,
                        // в обработке, или просто деактивироаванные
                        //TODO it.data.result.count  количество не активных объявление
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

    private fun getActiveAds() {
        safeFlowGather {
            viewModel.activeAds.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        println("---------all non active ads --------\n" + it.data.result?.results.toString())
                        //TODO it.date.result.results вернет List<MyAdsResultsEntity> в нем хранятся
                        //TODO все свои активные объявление
                        //TODO it.data.result.count  количество активных объявление
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

    private fun getMenu() {
        binding.tbProfile.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_my_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.menu_faq_button -> {
                        findNavController().navigate(ProfileFragmentDirections
                            .actionProfileFragmentToFAQFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun iconClick() {
        openSomeActivityForResult()
    }

    override fun iconLongClick() = with(binding){
        viewModel.deleteAvatar()
        safeFlowGather {
            viewModel.deleteAvatar.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        cusProfile.isProgressVisible()
                        it.data.result?.let { it1 -> binding.cusProfile.setIcon(it1) }
                        requireActivity().makeToast(it.data.result.toString())
                    }
                    is ApiState.Failure -> {
                        cusProfile.isProgressVisible()
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                        cusProfile.isProgressVisible(true)
                    }
                }
            }
        }
    }
}