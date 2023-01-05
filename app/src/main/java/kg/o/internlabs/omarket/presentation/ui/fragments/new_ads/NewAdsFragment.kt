package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomWithToggleCellViewClick
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentNewAdsBinding
import kg.o.internlabs.omarket.domain.entity.EditAds
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.domain.entity.UploadImageResultEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.LoadImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.utils.checkPermission
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class NewAdsFragment : BaseFragment<FragmentNewAdsBinding, NewAdsViewModel>(),
    CustomWithToggleCellViewClick, MainImageSelectHelper, DeleteImageHelper, LoadImageHelper {

    private var selectedImages = mutableListOf<UploadImageResultEntity>()
    private var selected = mutableListOf<String>()
    private var args: NewAdsFragmentArgs? = null
    private var imageListAdapter: ImageListAdapter? = null

    companion object {
        var mainImageIndex = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = NewAdsFragmentArgs.fromBundle(requireArguments())
        imageListAdapter = ImageListAdapter(requireActivity(),
            this@NewAdsFragment, viewModel)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.initViewModel()
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
            val category = ResultEntity()
            cusCategory.setText(category.name.toString())
            cusSubCategory.isVisible = category.subCategories?.isNotEmpty() == true
        }

        cusSubCategory.setOnClickListener {
            val category = ResultEntity()
            cusCategory.setText(category.subCategories!![0].name.toString())
        }

        cusAdType.setOnClickListener {
            //cusAdType.setText()
        }

        cusLocation.setOnClickListener {
            //cusLocation.setText()
        }

        ivAddImage.isVisible = selectedImages.size < 10

        ivAddImage.setOnClickListener {
            loadImages()
        }

        btnCreateAd.setOnClickListener{
            editTheAd()
        }
    }

    private fun editTheAd() = with(binding){
        val editAds = EditAds(
            adType = cusAdType.getText(),
            category = cusCategory.getItemId(),
            contractPrice = cusPriceIsNegotiable.isChecked(),
            currency = cusCurrency.getText(),
            delivery = false, //TODO
            description = cusDescription.getText(),
            images = emptyList(), // TODO
            latitude = "",
            location = cusLocation.getItemId(),
            longitude = "",
            oMoneyPay = cusOMoneyAccept.isChecked(),
            price = cusPrice.getText(),
            promotionType = null,
            telegramProfile = cusTelegramNick.getText(),
            title = cusTitle.getText(),
            whatsappNum = cusWhatsAppNumber.getValue()
        )
        viewModel.createAd(editAds)

        isAdCreated()
    }

    private fun isAdCreated() {
        safeFlowGather {
            viewModel.editedAd.collectLatest {
                when(it) {
                    is ApiState.Success -> {
                        makeToast("Объявление создано")
                        findNavController().navigate(R.id.mainFragment)
                    }
                    is ApiState.Failure -> {
                        println("--....1.." + it.msg.message)
                    }
                    is ApiState.Loading -> {
                        println("--....2..Loading")
                        /*btnNonActive.isVisible = false
                        btnActive.isVisible = false*/
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        println("fOnDest------")
        super.onDestroy()
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
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.action = Intent.ACTION_GET_CONTENT
        //intent.type = "image/*"
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
                        addImage(imageUri)
                    }
                } else if (intent?.data != null) {
                    val imageUri: Uri = intent.data!!
                    addImage(imageUri)
                }
            }
        }
    )

    private fun uploadImage(imageUri: Uri) {
        checkPermission()
        viewModel.uploadImage(getFile(imageUri))

        selectedImages.add(UploadImageResultEntity(path = imageUri))

        imageListAdapter?.initAdapter(selectedImages.reversed().toList())
        binding.rwToUploadImages.adapter = imageListAdapter
        getUploadedImage(selectedImages.size - 1)
    }

    private fun getFile(imageUri: Uri): File? {
        val file: File? = imageUri.path?.let { File(it) }
        val filePath = file?.path?.split(":")?.toTypedArray()
        val imageId = filePath?.get(filePath.size - 1)

        val cursor: Cursor? = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(imageId),
            null
        )
        cursor?.moveToFirst()
        val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result: String? = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return result?.let { File(it) }
    }

    private fun getUploadedImage(i: Int) {
        safeFlowGather {
            viewModel.uploadImage.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        it.data.result?.let { it1 -> addIfNotContains(it1, i) }
                    }
                    is ApiState.Failure -> {
                        println("--....1.." + it.msg.message)
                    }
                    is ApiState.Loading -> {
                        println("--....2..Loading")
                        /*btnNonActive.isVisible = false
                        btnActive.isVisible = false*/
                    }
                }
                println("here..................")
            }
        }
    }

    private fun addIfNotContains(it1: UploadImageResultEntity, i: Int) {
        if (selectedImages.contains(it1)) return
        val index = selectedImages.size - i
        imageListAdapter?.imageLoaded(index)
        /*selectedImages.add(0, it1)
        imageListAdapter?.initAdapter(selectedImages.toList())
        binding.rwToUploadImages.adapter = imageListAdapter*/
    }

    private fun addImage(uri: Uri) {
        println("-----------------------------$uri")

        if (selectedImages.size >= 10) {
            makeToast("Нельзя загружать больше 10 изображение")
            return
        }
        uploadImage(uri)
        binding.flAddImage.isVisible = selectedImages.size < 10
    }

    override fun toggleClicked(positionOfCell: Int) = with(binding) {
        when (positionOfCell) {
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
        //selectedImages.removeAt(index)
        if (index == mainImageIndex) {
            mainImageIndex = --mainImageIndex
            selectMainImage(mainImageIndex)
        }
    }

    override fun loadImage() {
        loadImages()
    }
}