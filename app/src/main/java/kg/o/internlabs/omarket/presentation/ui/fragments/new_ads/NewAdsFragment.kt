package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentNewAdsBinding

class NewAdsFragment : BaseFragment<FragmentNewAdsBinding, NewAdsViewModel>() {
    override val viewModel: NewAdsViewModel by lazy {
        ViewModelProvider(this)[NewAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentNewAdsBinding {
        return FragmentNewAdsBinding.inflate(inflater)
    }
}
  /*  private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_CHOOSER)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        resultLauncher.launch(Intent.createChooser(intent, "cho"))
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult(), fun(result: ActivityResult) {
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intent: Intent? = result.data
                if (intent?.clipData != null) {
                    val count = intent.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri: Uri = intent.clipData!!.getItemAt(i).uri
                        list.add(Picture(imageUri.toString()))
                    }
                } else if (intent?.data != null) {
                    val imageUri: Uri = intent.data!!
                    list.add(Picture(imageUri.toString()))
                }
                binding.mainRec.layoutManager = GridLayoutManager(requireContext(), 3)
                binding.mainRec.adapter = ListAdapter(list, this@MainFragment)
            }
        }
    )*/