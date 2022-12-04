package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.loader.content.CursorLoader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentProfileBinding
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentProfileBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getAccessTokenFromPrefs()
        viewModel.getActiveAds()
        viewModel.getNonActiveAds()
    }

    override fun initListener() {
        super.initListener()
        binding.what.setOnClickListener {
            requireActivity().makeToast("hi")
            findNavController().navigate(R.id.FAQFragment)
        }

        binding.img.setOnClickListener {
            openSomeActivityForResult()
        }
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        resultLauncher.launch(Intent.createChooser(intent, "cho"))
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult(), fun(result: ActivityResult) {
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intent: Intent? = result.data
                println("-------2---"+intent.toString())
                println("-------3---"+intent?.data.toString())
                println("-------4---"+ intent?.data?.let { getRealPathFromURI(it) })
                if (intent?.clipData != null) {
                    val count = intent.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri: Uri = intent.clipData!!.getItemAt(i).uri
                        println("----im  "+imageUri)
                    }
                } else if (intent?.data != null) {
                    val imageUri: Uri = intent.data!!
                    println("----im2  "+imageUri)
                }
            }
        }
    )

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(requireActivity(), contentUri, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result: String? = column_index?.let { cursor?.getString(it) }
        cursor?.close()
        return result
    }

    private fun loadImage(uri: Uri) {

        lifecycleScope.launch {
            val stream = requireActivity().contentResolver.openInputStream(uri) ?: return@launch
            val request = RequestBody.create("image/*".toMediaTypeOrNull(), stream.readBytes()) // read all bytes using kotlin extension
            val filePart = MultipartBody.Part.createFormData(
                "file",
                "test.jpg",
                request
            )
            try {

                //api.uploadStream(filePart)
            }
            catch (e: Exception) {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                return@launch
            }
            Log.d("MyActivity", "on finish upload file")
        }
    }

    override fun initView() {
        super.initView()
        getActiveAds()
        getNonActiveAds()
    }

    private fun getNonActiveAds() {
        this@ProfileFragment.safeFlowGather {
            viewModel.nonActiveAds.collectLatest {
                when(it) {
                    is ApiState.Success -> {
                        println("---------all active ads --------\n"+it.data.result?.results.toString())
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
        this@ProfileFragment.safeFlowGather {
            viewModel.activeAds.collectLatest {
                when(it) {
                    is ApiState.Success -> {
                        println("---------all non active ads --------\n"+it.data.result?.results.toString())
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
}