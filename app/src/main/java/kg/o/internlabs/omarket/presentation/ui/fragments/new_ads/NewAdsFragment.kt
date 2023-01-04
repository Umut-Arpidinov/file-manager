package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomWithToggleCellViewClick
import kg.o.internlabs.omarket.databinding.FragmentNewAdsBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.LoadImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.utils.makeToast

class NewAdsFragment : BaseFragment<FragmentNewAdsBinding, NewAdsViewModel>(), CustomWithToggleCellViewClick, MainImageSelectHelper, DeleteImageHelper, LoadImageHelper {

    private var selectedImages = mutableListOf<String>()
    private var args: NewAdsFragmentArgs? = null
    private var imageListAdapter: ImageListAdapter? = null

    companion object {
        var mainImageIndex = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = NewAdsFragmentArgs.fromBundle(requireArguments())
        imageListAdapter = ImageListAdapter(requireActivity(), this@NewAdsFragment)
    }

    override val viewModel: NewAdsViewModel by lazy {
        ViewModelProvider(this)[NewAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentNewAdsBinding.inflate(inflater)

    override fun initView() = with(binding) {
        super.initView()

        with(btnCreateAd) {
            isCheckable = isButtonClickable()
            isEnabled = isCheckable
        }

        cusPriceIsNegotiable.setInterface(this@NewAdsFragment)
        cusOMoneyAccept.setInterface(this@NewAdsFragment, 1)
        cusWhatsApp.setInterface(this@NewAdsFragment, 2)
        cusTelegram.setInterface(this@NewAdsFragment, 3)
        cusLocationOnTheMap.setInterface(this@NewAdsFragment, 4)
    }

    override fun initListener() = with(binding) {
        super.initListener()

        cusCategory.setOnClickListener {
            //TODO if category has sub_category
            val category = ResultEntity()
            cusCategory.setText(category.name.toString())
            cusSubCategory.isVisible = category.subCategories?.isNotEmpty() == true
        }

        cusSubCategory.setOnClickListener {
            //TODO if category has sub_category
            val category = ResultEntity()
            cusCategory.setText(category.subCategories!![0].name.toString())
        }

        cusAdType.setOnClickListener {
            //cusAdType.setText()
        }

        cusLocation.setOnClickListener {
            //cusLocation.setText()
        }

        ivAddImage.isVisible = selectedImages.size < 1

        ivAddImage.setOnClickListener {
            println("...0....")
            loadImages()
        }
    }

    private fun isButtonClickable(): Boolean {
        with(binding) {
            if (cusTitle.getText().isEmpty() ||
                cusCategory.getText().isEmpty() ||
                cusDescription.getText().isEmpty() ||
                cusAdType.getText().isEmpty() ||
                cusPriceIsNegotiable.isChecked().not() &&
                (cusCurrency.getText().isEmpty() ||
                        cusPrice.getText().isEmpty())
            ) return false
        }
        return true
    }

    private fun loadImages() {
        val intent = Intent(Intent.ACTION_CHOOSER)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        resultLauncher.launch(Intent.createChooser(intent, "image"))
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
                        addImage(imageUri.toString())
                    }
                } else if (intent?.data != null) {
                    val imageUri: Uri = intent.data!!
                    addImage(imageUri.toString())
                }
                println("-----111-----")
                imageListAdapter?.initAdapter(selectedImages.toList())

                binding.rwToUploadImages.adapter = imageListAdapter/*imageListAdapter(
                    )*/
            }
        }
    )

    private fun addImage(uri: String) {
        if (selectedImages.size >= 10) {
            makeToast("Нельзя загружать больше 10 изображение")
            return
        }
        selectedImages.add(0, uri)
        binding.flAddImage.isVisible = selectedImages.size < 1
    }

    override fun toggleClicked(positionOfCell: Int) = with(binding){
        when(positionOfCell) {
            0 -> {
                with(cusPriceIsNegotiable.isChecked()) {
                    cusCurrency.isVisible = this.not()
                    cusPrice.isVisible = this.not()
                }
            }
            1 -> tvOMoneyPayAgreement.isVisible = cusOMoneyAccept.isChecked()
            2 -> {
                cusWhatsAppNumber.isVisible = cusWhatsApp.isChecked()
                if (cusWhatsAppNumber.isVisible) {
                    cusWhatsAppNumber.setText(args?.number)
                }
            }
            3 -> cusTelegramNick.isVisible = cusTelegram.isChecked()
            4 -> makeToast("В разработке.")
        }
    }

    override fun selectMainImage(index: Int) {
        println("=$selectedImages==1.1===$index")
        mainImageIndex = index
        imageListAdapter?.selectMainImage(index)
    }

    override fun deleteImage(index: Int) {
        selectedImages.removeAt(index)
        if (index == mainImageIndex) {
            mainImageIndex = --mainImageIndex
            selectMainImage(mainImageIndex)
        }
    }

    override fun loadImage() {
        loadImages()
    }
}