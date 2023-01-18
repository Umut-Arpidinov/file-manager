package kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads

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
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.adapters.CategoriesBottomSheetAdapterForEditAd
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.adapters.ImageListAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.adapters.SubCategoriesBottomSheetAdapterForEditAd
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.AddImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.main.CategoryClickHandler
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.SubCategoryClickHandler
import kg.o.internlabs.omarket.utils.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditAdsFragment : BaseFragment<FragmentEditAdsBinding, EditAdsViewModel>(),
    CustomWithToggleCellViewClick, MainImageSelectHelper, DeleteImageHelper, AddImageHelper,
    CategoryClickHandler, SubCategoryClickHandler, CustomOneCellsTextWatcher {

    private var listImageUrlInRemote = mutableListOf<UploadImageResultEntity>()
    private val selectedImages = mutableListOf(UploadImageResultEntity())
    private val selectedUrl = mutableListOf<UploadImageResultEntity>()
    private val selectedPath = mutableListOf<UploadImageResultEntity>()
    private val args: EditAdsFragmentArgs by lazy(::initArgs)
    private var imageListAdapter: ImageListAdapter? = null
    private var categories: List<ResultEntity>? = null
    private var subCategoriesEntity: SubCategoriesEntity? = null
    private var categoryEntity: ResultEntity? = null
    private var adTypeEntity: AdTypeEntity? = null
    private var dialog: BottomSheetDialog? = null
    private var currency = ""

    companion object {
        var mainImageIndex = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
        imageListAdapter = ImageListAdapter(this@EditAdsFragment)
        dialog = BottomSheetDialog(requireContext())
    }

    private fun initArgs() = EditAdsFragmentArgs.fromBundle(requireArguments())

    override val viewModel: EditAdsViewModel by lazy {
        ViewModelProvider(this)[EditAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentEditAdsBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        args.uuid?.let { viewModel.setUuid(it) }
    }

    override fun initView() = with(binding) {
        super.initView()

        isButtonClickable()

        cusTitle.setInterface(this@EditAdsFragment)
        cusDescription.setInterface(this@EditAdsFragment, 1)
        cusPrice.setInterface(this@EditAdsFragment, 2)
        cusDelivery.setInterface(this@EditAdsFragment)
        cusPriceIsNegotiable.setInterface(this@EditAdsFragment, 1)
        cusOMoneyAccept.setInterface(this@EditAdsFragment, 2)
        cusWhatsApp.setInterface(this@EditAdsFragment, 3)
        cusTelegram.setInterface(this@EditAdsFragment, 4)

        getAdType()
        getAdsDetail()

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
            if (checkPermission()) {
                pickImages()
            } else {
                requestPermission()
            }
        }

        btnDeleteAd.setOnClickListener {
            viewModel.deleteAd()

            isTheAdDeleted()
        }

        btnCreateAd.setOnClickListener {
            prepareValuesForAd()
        }
    }

    private fun setField(item: ResultX) = with(binding) {
        setData(item)

        item.title?.let {
            if (it.isNotEmpty()) cusTitle.setText(it) else
                cusTitle.setHint(getString(R.string.title))
        }

        with(item.category) {
            val category = if (this?.parent?.name == null) this?.name else this.parent.name
            val subCategory = if (this?.parent?.name != null) this.name else null

            if (category.isNullOrEmpty()) cusCategory.setHint(getString(R.string.category))
            else cusCategory.setText(category)

            if (subCategory.isNullOrEmpty()) {
                cusSubCategory.isVisible = false
            } else {
                cusSubCategory.isVisible = true
                cusSubCategory.setText(subCategory)
            }
        }

        item.description?.let {
            if (it.isNotEmpty()) cusDescription.setText(it) else
                cusDescription.setHint(getString(R.string.description))
        }

        item.adType?.let {
            if (it.isNotEmpty()) getAdTypeName(it)?.let { it1 ->
                cusAdType.setText(it1) }
            else cusTitle.setHint(getString(R.string.ad_type_title))
        }

        item.contractPrice?.let {
            cusPriceIsNegotiable.isChecked(it)
            cusCurrency.isVisible = it.not()
            cusPrice.isVisible = it.not()
        }
        item.currency?.let {
            if (it.isNotEmpty()) {
                if (it == "som") cusCurrency.setText("Сомы")
                if(it == "usd") cusCurrency.setText("Доллары")
            } else
                cusCategory.setHint(getString(R.string.currency))
        }

        item.price?.let {
            if (it.isNotEmpty()) cusPrice.setText(it) else
                cusPrice.setHint(getString(R.string.price))
        }

        item.location?.name?.let {
            if (it.isNotEmpty()) cusLocation.setText(it) else
                cusLocation.setText(getString(R.string.bishkek))
        }

        item.oMoneyPay?.let {
            cusOMoneyAccept.isChecked(it)
            tvOMoneyPayAgreement.isVisible = it
        }

        item.delivery?.let {
            cusDelivery.isChecked(it)
            cusDelivery.isVisible = it
        }

        item.whatsappNum?.let {
            cusWhatsApp.isChecked(it.isNotEmpty())
            cusWhatsAppNumber.isVisible = it.isNotEmpty()
            cusWhatsAppNumber.setText(it)
        }

        item.telegramProfile?.let {
            cusTelegram.isChecked(it.isNotEmpty())
            cusTelegramNick.isVisible = it.isNotEmpty()
            cusTelegramNick.setText(it)
        }

        item.hasImage?.let {
            item.images?.let { img -> uploadImage(img) }
        }
        isButtonClickable()
    }

    private fun setData(item: ResultX) {
        if(item.category?.parent?.name == null) {
            categoryEntity = categories?.find { it.id == item.category?.id }
            return
        } else {
            categoryEntity = categories?.find { it.id == item.category.parent.id }
            subCategoriesEntity = categoryEntity?.subCategories?.find { it.id == item.category.id }
        }

        currency = item.currency.toString()
        isButtonClickable()
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
                    this addImage imageUri
                }
            } else if (intent?.data != null) {
                val imageUri: Uri = intent.data!!
                this addImage imageUri
            }
        }
    })

    private infix fun addImage(uri: Uri) {
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

    private fun uploadImage(imageUri: List<String>) {
        imageUri.map { selectedImages.addAll(1, listOf(UploadImageResultEntity(url = it))) }
        if(selectedImages.isEmpty()) {
            selectedUrl.add(UploadImageResultEntity())
            selectedPath.add(UploadImageResultEntity())
        }
        else {
            selectedUrl.addAll(selectedImages)
            selectedPath.addAll(selectedImages)
        }
        listImageUrlInRemote = imageUri.map { UploadImageResultEntity(url = it) }.toMutableList()
        binding.flAddImage.isVisible = false
        imageListAdapter?.initAdapter(selectedImages.toList())
        binding.rwToUploadImages.adapter = imageListAdapter
    }

    private fun addIfNotContains(
        uri: UploadImageResultEntity,
        path: UploadImageResultEntity, imageUri: Uri
    ) {
        if (uri in selectedUrl) return
        if (containsModel(path)) return
        val itemIndex = selectedImages.indexOf(path)
        if (itemIndex < 0) return
        selectedPath.add(1, uri)
        selectedUrl.add(1, uri)
        selectedImages[itemIndex] = UploadImageResultEntity(uri.url, path = imageUri)
        imageListAdapter?.imagesLoaded(selectedImages)
    }

    private fun containsModel(m: UploadImageResultEntity) = selectedPath.any { (it === m) }

    private fun deleteModelFromLists(index: Int) {
        val urlOfDeletedImage = selectedImages[index]
        selectedImages.removeAt(index)
        selectedUrl.removeAt(index)
        selectedPath.removeAt(index)
        imageListAdapter?.initAdapter(selectedImages.toList())
        if (urlOfDeletedImage in selectedImages) return
        viewModel.deleteImageFromAd(DeletedImageUrlEntity(url = urlOfDeletedImage.url))
    }

    override fun toggleClicked(positionOfCell: Int) = with(binding) {
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
                    cusWhatsAppNumber.setText(args.uuid)
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
        if (checkPermission()) {
            pickImages()
        } else {
            requestPermission()
        }
    }

    override fun selectMainImage(index: Int, uri: String?) {
        mainImageIndex = index
        imageListAdapter?.selectMainImage(index)
    }

    private fun getAdTypeNameCode(name: String) =
        adTypeEntity?.result?.results?.find { it.name == name }?.codeValue

    private fun getAdTypeName(name: String) =
        adTypeEntity?.result?.results?.find { it.codeValue == name }?.name

    private fun getCategoryId(name: String) =
        if (subCategoriesEntity?.name == name) subCategoriesEntity?.id
        else {
            categoryEntity?.id
        }

    private fun prepareValuesForAd() = with(binding) {

        val editAds = EditAds(
            promotionType = null,
            adType = getAdTypeNameCode(cusAdType.getText()),
            category = getCategoryId(cusSubCategory.getText()),
            contractPrice = cusPriceIsNegotiable.isChecked(),
            currency = if (cusPriceIsNegotiable.isChecked().not()) {
                if(cusAdType.getText() == "Сомы") "som" else "usd"
            } else null,
            delivery = cusDelivery.isChecked(),
            description = cusDescription.getText(),
            images = prepareUrlForAd(),
            location = 1,
            oMoneyPay = cusOMoneyAccept.isChecked(),
            price = if (cusPriceIsNegotiable.isChecked().not()) cusPrice.getText() else null,
            telegramProfile = if (cusTelegram.isChecked()) {
                if (cusTelegramNick.getText().startsWith("@")) cusTelegramNick.getText().drop(1)
                else cusTelegramNick.getText()
            }
                 else null,
            title = cusTitle.getText(),
            whatsappNum = if (cusWhatsApp.isChecked()) cusWhatsAppNumber.getValue() else null,
        )
        viewModel.createAd(editAds)
        deleteImagesFromRemote()
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
                        deleteImagesFromRemote()
                        try {
                            findNavController().navigate(R.id.mainFragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is ApiState.Failure -> {
                        makeToast(it.msg.message.toString())
                        progressBar.isVisible = false
                        btnCreateAd.isVisible = true
                        btnDeleteAd.isVisible = true
                    }
                    is ApiState.Loading -> {
                        progressBar.isVisible = true
                        btnCreateAd.isVisible = false
                        btnDeleteAd.isVisible = false
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
                        categories = it.data.result
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
                        getCategories()
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

    private fun getAdsDetail() {
        safeFlowGather {
            viewModel.detailAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        binding.bigProgressBar.isVisible = false
                        binding.nsv.isVisible = true
                        it.data.resultX?.let { it1 -> setField(it1) }
                    }
                    is ApiState.Failure -> {
                        binding.bigProgressBar.isVisible = false
                        binding.nsv.isVisible = true
                    }
                    is ApiState.Loading -> {
                        binding.bigProgressBar.isVisible = true
                        binding.nsv.isVisible = false
                    }
                }
            }
        }
    }
    private fun getUploadedImage(path: UploadImageResultEntity, imageUri: Uri) {
        safeFlowGather {
            viewModel.uploadImage.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        it.data.result?.let { it1 -> addIfNotContains(it1, path, imageUri) }
                    }
                    is ApiState.Failure -> {
                        println("${this.javaClass.simpleName}--....1.." + it.msg.message)
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun isTheAdDeleted() = with(binding) {
        safeFlowGather {
            viewModel.deleteAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        progressBar.isVisible = false
                        try {
                            findNavController().navigate(R.id.mainFragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is ApiState.Failure -> {
                        println("${this.javaClass.simpleName}--....1.." + it.msg.message)
                        if (it.msg.message == "1000") {
                            findNavController().navigate(R.id.mainFragment)
                            return@collectLatest
                        }
                        progressBar.isVisible = false
                        btnDeleteAd.isVisible = true
                        btnCreateAd.isVisible = true
                    }
                    is ApiState.Loading -> {
                        progressBar.isVisible = true
                        btnDeleteAd.isVisible = false
                        btnCreateAd.isVisible = false
                    }
                }
            }
        }
    }

    private fun deleteImagesFromRemote() {
        listImageUrlInRemote.filter { it in selectedImages }.forEach {
            viewModel.deleteImageFromAd(DeletedImageUrlEntity(it.url))
        }
    }

    private fun callCategoryBottomSheet() {
        val lBinding = BottomSheetCategoriesBinding.inflate(LayoutInflater.from(context))
        lBinding.recyclerCategoryBs.adapter =
            CategoriesBottomSheetAdapterForEditAd(categories, this)
        dialog?.setContentView(lBinding.root)
        dialog?.show()

        lBinding.cancelIconCategories.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun callSubCategoryBottomSheet() {
        val lBinding = BottomSheetSubcategoriesBinding.inflate(LayoutInflater.from(context))
        lBinding.recyclerSubcategoryBs.adapter = SubCategoriesBottomSheetAdapterForEditAd(
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
            try {
                setVisibilityAndPosition(lBinding)
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    private fun setPosition(v: CustomRoundedOneCellLineView,
        s: String, list: List<AdTypeResultsEntity>) {
        when (s) {
            list.first().codeValue -> v.setPosition(Position.TOP)
            list.last().codeValue -> v.setPosition(Position.BOTTOM)
            else -> v.setPosition(Position.MIDDLE)
        }
    }

    override fun onDestroy() {
        println("fOnDest------")
        dialog = null
        categories = null
        super.onDestroy()
    }

    override fun textWatcher(isEmpty: Boolean, cellPosition: Int) {
        isButtonClickable()
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
}