package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.content.Intent
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
import kg.o.internlabs.omarket.domain.entity.*
import kg.o.internlabs.omarket.presentation.ui.fragments.main.CategoryClickHandler
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.AddImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.utils.checkPermission
import kg.o.internlabs.omarket.utils.getFile
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewAdsFragment : BaseFragment<FragmentNewAdsBinding, NewAdsViewModel>(),
    CustomWithToggleCellViewClick, MainImageSelectHelper, DeleteImageHelper, AddImageHelper,
    CategoryClickHandler {

    private var selectedImages = mutableListOf(UploadImageResultEntity())
    private var selected = mutableListOf(UploadImageResultEntity())
    private var selectedPath = mutableListOf(UploadImageResultEntity())
    private var args: NewAdsFragmentArgs? = null
    private var imageListAdapter: ImageListAdapter? = null
    private var subCategoriesEntity = SubCategoriesEntity()
    private var categoryEntity = ResultEntity()
    private var list: List<ResultEntity>? = null

    companion object {
        var mainImageIndex = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = NewAdsFragmentArgs.fromBundle(requireArguments())
        imageListAdapter = ImageListAdapter(this@NewAdsFragment)
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

        cusDelivery.setInterface(this@NewAdsFragment)
        cusPriceIsNegotiable.setInterface(this@NewAdsFragment, 1)
        cusOMoneyAccept.setInterface(this@NewAdsFragment, 2)
        cusWhatsApp.setInterface(this@NewAdsFragment, 3)
        cusTelegram.setInterface(this@NewAdsFragment, 4)
        cusLocationOnTheMap.setInterface(this@NewAdsFragment, 5)

        getCategories()
    }

    override fun initListener() = with(binding) {
        super.initListener()

        cusCategory.setOnClickListener {
            bottomSheetCategory.root.isVisible = true
            cusCategory.setText(categoryEntity.name.toString())
            cusSubCategory.isVisible = categoryEntity.subCategories?.isNotEmpty() == true
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

        flAddImage.isVisible = selectedPath.size < 2

        ivAddImage.setOnClickListener {
            pickImages()
        }

        btnCreateAd.setOnClickListener {
            prepareValuesForAd()
        }
    }

    private fun pickImages() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(Intent.createChooser(intent, "image"))
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts
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
    })

    private fun addImage(uri: Uri) {
        if (selectedImages.size >= 11) {
            makeToast("Нельзя загружать больше 10 изображение")
            return
        }
        uploadImage(uri)
    }

    private fun uploadImage(imageUri: Uri) {
        checkPermission()
        viewModel.uploadImage(getFile(imageUri))

        val model = UploadImageResultEntity(path = imageUri)
        selectedImages.add(1, model)
        binding.flAddImage.isVisible = false
        imageListAdapter?.initAdapter(selectedImages.toList())
        binding.rwToUploadImages.adapter = imageListAdapter

        getUploadedImage(model, imageUri)
    }

    private fun getUploadedImage(path: UploadImageResultEntity, imageUri: Uri) {
        safeFlowGather {
            viewModel.uploadImage.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        it.data.result?.let { it1 -> addIfNotContains(it1, path, imageUri) }
                    }
                    is ApiState.Failure -> {
                        println("--....1.." + it.msg.message)
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun addIfNotContains(
        uri: UploadImageResultEntity,
        path: UploadImageResultEntity, imageUri: Uri
    ) {
        if (selected.contains(uri)) return
        if (containsModel(path)) return
        val itemIndex = selectedImages.indexOf(path)
        if (itemIndex < 0) return
        selectedPath.add(1, path)
        selected.add(1, uri)
        selectedImages[itemIndex] = UploadImageResultEntity(uri.url, path = imageUri)
        imageListAdapter?.imageLoaded(selectedImages)
    }

    private fun containsModel(m: UploadImageResultEntity): Boolean {
        selectedPath.forEach {
            if (it === m) return true
        }
        return false
    }

    private fun deleteModelFromLists(index: Int) {
        val uriOfDeletedImage = selectedImages[index].url
        selectedImages.removeAt(index)
        selected.removeAt(index)
        selectedPath.removeAt(index)

        viewModel.deleteImageFromAd(DeletedImageUrlEntity(url = uriOfDeletedImage))
    }

    override fun toggleClicked(positionOfCell: Int) = with(binding) {
        when (positionOfCell) {
            0 -> cusDelivery.isVisible = subCategoriesEntity.delivery ?: false
            1 -> {
                with(cusPriceIsNegotiable.isChecked()) {
                    cusCurrency.isVisible = this.not()
                    cusPrice.isVisible = this.not()
                }
            }
            2 -> tvOMoneyPayAgreement.isVisible = cusOMoneyAccept.isChecked()
            3 -> {
                cusWhatsAppNumber.isVisible = cusWhatsApp.isChecked()
                if (cusWhatsAppNumber.isVisible) {
                    cusWhatsAppNumber.setText(args?.number)
                }
            }
            4 -> cusTelegramNick.isVisible = cusTelegram.isChecked()
            5 -> makeToast("В разработке.")
        }
    }

    override fun deleteImage(index: Int) {
        deleteModelFromLists(index)
        selectMainImage(mainImageIndex)
    }

    override fun addImage() {
        pickImages()
    }

    override fun selectMainImage(index: Int, uri: String?) {
        mainImageIndex = index
        imageListAdapter?.selectMainImage(index)
    }

    private fun prepareValuesForAd() = with(binding) {
        val editAds = EditAds(
            adType = cusAdType.getText(),
            category = cusCategory.getItemId(),
            contractPrice = cusPriceIsNegotiable.isChecked(),
            currency = cusCurrency.getText(),
            delivery = cusDelivery.isChecked(),
            description = cusDescription.getText(),
            images = prepareUrlForAd(),
            location = cusLocation.getItemId(),
            oMoneyPay = cusOMoneyAccept.isChecked(),
            price = cusPrice.getText(),
            telegramProfile = cusTelegramNick.getText(),
            title = cusTitle.getText(),
            whatsappNum = cusWhatsAppNumber.getValue()
        )
        viewModel.createAd(editAds)

        createAd()
    }

    private fun prepareUrlForAd(): List<String> {
        try {
            val mainImageModel = selectedImages[mainImageIndex]
            selectedImages.removeAt(mainImageIndex)
            selectedImages.add(1, mainImageModel)
        } catch (e: Exception) {
            if (selectedImages.size > 1) {
                return getList()
            }
            return emptyList()
        }
        return getList()
    }

    private fun getList(): List<String> {
        val list = mutableListOf<String>()
        selectedImages.forEachIndexed { index, it ->
            if (index != 0) {
                it.url?.let { it1 -> list.add(0, it1) }
            }
        }
        println("-----$list")
        return list.toList()
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

    private fun createAd() {
        safeFlowGather {
            viewModel.editedAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        makeToast("Объявление создано")
                        findNavController().navigate(R.id.mainFragment)
                    }
                    is ApiState.Failure -> {
                        println("--....1.." + it.msg.message)
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun initCategoryAdapter(list: List<ResultEntity>?) {
        binding.bottomSheetCategory.recyclerCategoryBs.adapter =
            CategoriesBottomSheetAdapter(list, this)
    }

    private fun getCategories() {
        safeFlowGather {
            viewModel.categories.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        list = it.data.result
                        initCategoryAdapter(list)
                    }
                    is ApiState.Failure -> {
                        makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    override fun clickedCategory(item: ResultEntity?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        println("fOnDest------")
        super.onDestroy()
    }
}