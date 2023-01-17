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
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.cells.CustomRoundedOneCellLineView
import kg.o.internlabs.core.custom_views.cells.Position
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomOneCellsTextWatcher
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomWithToggleCellViewClick
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.*
import kg.o.internlabs.omarket.domain.entity.*
import kg.o.internlabs.omarket.presentation.ui.fragments.main.CategoryClickHandler
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.adapters.CategoriesBottomSheetAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.adapters.ImageListAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.adapters.SubCategoriesBottomSheetAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.AddImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.SubCategoryClickHandler
import kg.o.internlabs.omarket.utils.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewAdsFragment : BaseFragment<FragmentNewAdsBinding, NewAdsViewModel>(),
    CustomWithToggleCellViewClick, MainImageSelectHelper, DeleteImageHelper, AddImageHelper,
    CategoryClickHandler, SubCategoryClickHandler, CustomOneCellsTextWatcher {

    private val selectedImages = mutableListOf(UploadImageResultEntity())
    private val selected = mutableListOf(UploadImageResultEntity())
    private val selectedPath = mutableListOf(UploadImageResultEntity())
    private val args: NewAdsFragmentArgs by lazy(::initArgs)
    private var imageListAdapter: ImageListAdapter? = null
    private var subCategoriesEntity: SubCategoriesEntity? = null
    private var categoryEntity: ResultEntity? = null
    private var adTypeEntity: AdTypeEntity? = null
    private var list: List<ResultEntity>? = null
    private var dialog: BottomSheetDialog? = null
    private var currency = ""

    companion object {
        var mainImageIndex = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
        imageListAdapter = ImageListAdapter(this@NewAdsFragment)
        dialog = BottomSheetDialog(requireContext())
    }

    private fun initArgs() = NewAdsFragmentArgs.fromBundle(requireArguments())

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

        isButtonClickable()
        cusTitle.setInterface(this@NewAdsFragment)
        cusDescription.setInterface(this@NewAdsFragment, 1)
        cusPrice.setInterface(this@NewAdsFragment, 2)
        cusDelivery.setInterface(this@NewAdsFragment)
        cusPriceIsNegotiable.setInterface(this@NewAdsFragment, 1)
        cusOMoneyAccept.setInterface(this@NewAdsFragment, 2)
        cusWhatsApp.setInterface(this@NewAdsFragment, 3)
        cusTelegram.setInterface(this@NewAdsFragment, 4)

        getCategories()
        getAdType()
    }

    override fun initListener() = with(binding) {
        super.initListener()

        tbFaq.setOnClickListener {
            findNavController().navigateUp()
        }

        cusCategory.setOnClickListener {
            callCategoryBottomSheet()
        }

        cusSubCategory.setOnClickListener {
            callSubCategoryBottomSheet()
        }

        cusAdType.setOnClickListener {
            if (categoryEntity == null) return@setOnClickListener
            if (subCategoriesEntity == null && cusSubCategory.isVisible) return@setOnClickListener
            callAdTypeBottomSheet()
        }

        cusCurrency.setOnClickListener {
            callCurrencyBottomSheet()
        }

        cusLocation.setOnClickListener {
            return@setOnClickListener
        }

        flAddImage.isVisible = selectedPath.size < 2

        ivAddImage.setOnClickListener {
            if(checkPermission()) {
                pickImages()
            } else {
                requestPermission()
            }
        }

        btnCreateAd.setOnClickListener {
            prepareValuesForAd()
        }
    }

    private fun callCategoryBottomSheet() {
        val lBinding = BottomSheetCategoriesBinding.inflate(LayoutInflater.from(context))
        lBinding.recyclerCategoryBs.adapter = CategoriesBottomSheetAdapter(list, this)
        dialog?.setContentView(lBinding.root)
        dialog?.show()

        lBinding.cancelIconCategories.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun callSubCategoryBottomSheet() {
        val lBinding = BottomSheetSubcategoriesBinding.inflate(LayoutInflater.from(context))
        lBinding.recyclerSubcategoryBs.adapter = SubCategoriesBottomSheetAdapter(
            categoryEntity?.subCategories, this
        )
        dialog?.setContentView(lBinding.root)
        dialog?.show()

        lBinding.cancelIconSubcategories.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun callCurrencyBottomSheet() {
        val lBinding = BottomSheetCurrencyBinding.inflate(LayoutInflater.from(context))
        dialog?.setContentView(lBinding.root)

        with(lBinding) {
            cancelIconAdType.setOnClickListener {
                dialog?.dismiss()
            }
            kgsCell.setOnClickListener {
                binding.cusCurrency.setText(kgsCell.getTitle())
                currency = "som"
                isButtonClickable()
                dialog?.dismiss()
            }
            dollarsCell.setOnClickListener {
                binding.cusCurrency.setText(dollarsCell.getTitle())
                currency = "usd"
                isButtonClickable()
                dialog?.dismiss()
            }
        }
        dialog?.show()
    }

    private fun callAdTypeBottomSheet() {
        val lBinding = BottomSheetAdTypeBinding.inflate(LayoutInflater.from(context))
        dialog?.setContentView(lBinding.root)

        with(lBinding) {
            cancelIconAdType.setOnClickListener {
                dialog?.dismiss()
            }
            setVisibilityAndPosition(lBinding)
            recruitingCellBottom.setOnClickListener {
                binding.cusAdType.setText(recruitingCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
            lookingCellBottom.setOnClickListener {
                binding.cusAdType.setText(lookingCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
            sellCellBottom.setOnClickListener {
                binding.cusAdType.setText(sellCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
            buyCellBottom.setOnClickListener {
                binding.cusAdType.setText(buyCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
            rentCellBottom.setOnClickListener {
                binding.cusAdType.setText(rentCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
            serviceCellBottom.setOnClickListener {
                binding.cusAdType.setText(serviceCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
            hiringCellBottom.setOnClickListener {
                binding.cusAdType.setText(hiringCellBottom.getTitle())
                isButtonClickable()
                dialog?.dismiss()
            }
        }
        dialog?.show()
    }

    private fun setVisibilityAndPosition(lBinding: BottomSheetAdTypeBinding) {
        val list = getAdTypeList()
        lBinding.apply {
            recruitingCellBottom.isVisible = "recruiting" in list.map { it.codeValue }
            lookingCellBottom.isVisible = "looking" in list.map { it.codeValue }
            setPosition(lookingCellBottom, "looking", list)
            sellCellBottom.isVisible = "sell" in list.map { it.codeValue }
            setPosition(sellCellBottom, "sell", list)
            buyCellBottom.isVisible = "buy" in list.map { it.codeValue }
            setPosition(buyCellBottom, "buy", list)
            rentCellBottom.isVisible = "rent" in list.map { it.codeValue }
            setPosition(rentCellBottom, "rent", list)
            serviceCellBottom.isVisible = "service" in list.map { it.codeValue }
            setPosition(serviceCellBottom, "service", list)
            hiringCellBottom.isVisible = "hiring" in list.map { it.codeValue }
            setPosition(hiringCellBottom, "hiring", list)
        }
    }

    private fun setPosition(
        v: CustomRoundedOneCellLineView,
        s: String,
        list: List<AdTypeResultsEntity>
    ) {
        when (s) {
            list.first().codeValue -> v.setPosition(Position.TOP)
            list.last().codeValue -> v.setPosition(Position.BOTTOM)
            else -> v.setPosition(Position.MIDDLE)
        }
    }

    private fun pickImages() {
        checkPermission()
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
        if (uri in selected) return
        if (containsModel(path)) return
        val itemIndex = selectedImages.indexOf(path)
        if (itemIndex < 0) return
        selectedPath.add(1, path)
        selected.add(1, uri)
        selectedImages[itemIndex] = UploadImageResultEntity(uri.url, path = imageUri)
        imageListAdapter?.imageLoaded(selectedImages)
    }

    private fun containsModel(m: UploadImageResultEntity) = selectedPath.any { it === m }


    private fun deleteModelFromLists(index: Int) {
        val uriOfDeletedImage = selectedImages[index].url
        selectedImages.removeAt(index)
        selected.removeAt(index)
        selectedPath.removeAt(index)

        viewModel.deleteImageFromAd(DeletedImageUrlEntity(url = uriOfDeletedImage))
    }

    override fun toggleClicked(positionOfCell: Int): Unit = with(binding) {
        when (positionOfCell) {
            1 -> {
                isButtonClickable()
                with(cusPriceIsNegotiable.isChecked()) {
                    cusCurrency.isVisible = this.not()
                    cusPrice.isVisible = this.not()
                }
            }
            2 -> tvOMoneyPayAgreement.isVisible = cusOMoneyAccept.isChecked()
            3 -> {
                cusWhatsAppNumber.isVisible = cusWhatsApp.isChecked()
                if (cusWhatsAppNumber.isVisible) {
                    cusWhatsAppNumber.setText(args.number)
                }
            }
            4 -> cusTelegramNick.isVisible = cusTelegram.isChecked()
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

    private fun getAdTypeNameCode(name: String) =
        adTypeEntity?.result?.results?.find { it.name == name }?.codeValue

    private fun getCategoryId(subName: String) =
        if (subCategoriesEntity?.name == subName) subCategoriesEntity?.id
        else {
            categoryEntity?.id
        }

    private fun prepareValuesForAd() = with(binding) {
        val editAds = EditAds(
            adType = getAdTypeNameCode(cusAdType.getText()),
            category = getCategoryId(cusSubCategory.getText()),
            contractPrice = cusPriceIsNegotiable.isChecked(),
            currency = if (cusPriceIsNegotiable.isChecked().not()) currency else null,
            delivery = cusDelivery.isChecked(),
            description = cusDescription.getText(),
            images = prepareUrlForAd(),
            location = 1,
            oMoneyPay = cusOMoneyAccept.isChecked(),
            price = if (cusPriceIsNegotiable.isChecked().not()) cusPrice.getText() else null,
            telegramProfile = if (cusTelegram.isChecked()) cusTelegramNick.getText()
                .drop(1) else null,
            title = cusTitle.getText(),
            whatsappNum = if (cusWhatsApp.isChecked()) cusWhatsAppNumber.getValue() else null,
        )
        viewModel.createAd(editAds)

        createAd()
    }

    private fun prepareUrlForAd(): List<String> {
        val mainImageModel = selectedImages.getOrNull(mainImageIndex)
        if (mainImageModel != null) {
            selectedImages.removeAt(mainImageIndex)
            selectedImages.add(1, mainImageModel)
            return getList()
        }
        return if (selectedImages.size > 1) getList() else emptyList()
    }

    private fun getList() =
        selectedImages.filter { it.url != null }.map { it.url!! }


    private fun getAdTypeList(): List<AdTypeResultsEntity> {
        val list = mutableListOf<AdTypeResultsEntity>()
        if (categoryEntity?.subCategories?.isEmpty() == true) {
            if (categoryEntity?.adType?.isEmpty() == true) {
                adTypeEntity?.result?.results?.map { it }?.let { list.addAll(it) }
                return list
            }
            categoryEntity?.adType?.forEach { i ->
                adTypeEntity?.result?.results?.filter { it.id == i }?.map { list.add(it) }
            }
            return list
        }
        if (subCategoriesEntity?.adType?.isEmpty() == true) {
            if (categoryEntity?.adType?.isEmpty() == true) {
                adTypeEntity?.result?.results?.map { it }?.let { list.addAll(it) }
                return list
            }
            categoryEntity?.adType?.forEach { i ->
                adTypeEntity?.result?.results?.filter { it.id == i }?.map { list.add(it) }
            }
            return list
        }
        subCategoriesEntity?.adType?.forEach { i ->
            adTypeEntity?.result?.results?.filter { it.id == i }?.map { list.add(it) }
        }
        return list
    }

    private fun isButtonClickable() {
        with(binding) {
            btnCreateAd.isEnabled = cusTitle.getText().isNotEmpty() and
                    cusCategory.getText().isNotEmpty() and
                    cusDescription.getText().isNotEmpty() and
                    cusAdType.getText().isNotEmpty() and
                    cusPriceIsNegotiable.isChecked() or
                    (cusPriceIsNegotiable.isChecked().not() and
                            cusCurrency.getText().isNotEmpty() and
                            cusPrice.getText().isNotEmpty())
            btnCreateAd.isClickable = btnCreateAd.isEnabled
        }
    }

    private fun createAd() = with(binding) {
        safeFlowGather {
            viewModel.editedAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        progressBar.isVisible = false
                        makeToast("Объявление создано")
                        try {
                            findNavController().navigate(R.id.mainFragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is ApiState.Failure -> {
                        println("--....1.." + it.msg.message)
                        makeToast(it.msg.message.toString())
                        progressBar.isVisible = false
                        btnCreateAd.isVisible = true
                    }
                    is ApiState.Loading -> {
                        progressBar.isVisible = true
                        btnCreateAd.isVisible = false
                    }
                }
            }
        }
    }

    private fun getCategories() {
        safeFlowGather {
            viewModel.categories.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        list = it.data.result
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

    private fun getAdType() {
        safeFlowGather {
            viewModel.adType.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        adTypeEntity = it.data
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

    override fun clickedCategory(item: ResultEntity?) = with(binding) {
        cusSubCategory.setHint(getString(R.string.sub_category))
        cusAdType.setHint(getString(R.string.ad_type_title))
        subCategoriesEntity = null
        categoryEntity = item
        dialog?.dismiss()
        cusCategory.setText(categoryEntity?.name.toString())
        isButtonClickable()
        cusSubCategory.isVisible = categoryEntity?.subCategories?.isNotEmpty() == true
        cusDelivery.isVisible = cusSubCategory.isVisible.not() && categoryEntity?.delivery == true
    }

    override fun subClickHandler(item: SubCategoriesEntity?) = with(binding) {
        subCategoriesEntity = item
        dialog?.dismiss()
        cusSubCategory.setText(subCategoriesEntity?.name.toString())
        isButtonClickable()
        cusDelivery.isVisible = item?.delivery == true
        if (item != null) {
            cusAdType.setText("")
            cusAdType.setHint(getString(R.string.ad_type_title))
        }
    }

    override fun onDestroy() {
        println("fOnDest------")
        dialog = null
        list = null
        super.onDestroy()
    }

    override fun textWatcher(isEmpty: Boolean, cellPosition: Int) {
        isButtonClickable()
    }
}