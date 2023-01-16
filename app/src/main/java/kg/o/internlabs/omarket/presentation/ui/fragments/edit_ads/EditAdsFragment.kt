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
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomWithToggleCellViewClick
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentEditAdsBinding
import kg.o.internlabs.omarket.domain.entity.DeletedImageUrlEntity
import kg.o.internlabs.omarket.domain.entity.EditAds
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.domain.entity.UploadImageResultEntity
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.AddImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.utils.checkPermission
import kg.o.internlabs.omarket.utils.getFile
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditAdsFragment : BaseFragment<FragmentEditAdsBinding, EditAdsViewModel>(),
    CustomWithToggleCellViewClick, MainImageSelectHelper, DeleteImageHelper, AddImageHelper {

    private var listImageUrlInRemote = mutableListOf<UploadImageResultEntity>()
    private val selectedImages = mutableListOf(UploadImageResultEntity())
    private val selectedUrl = mutableListOf<UploadImageResultEntity>()
    private val selectedPath = mutableListOf<UploadImageResultEntity>()
    private val args: EditAdsFragmentArgs by lazy(::initArgs)
    private var imageListAdapter: ImageListAdapter? = null
    private var categoriesEntity = mutableListOf<ResultEntity>()

    companion object {
        var mainImageIndex = 1
    }

    override val viewModel: EditAdsViewModel by lazy {
        ViewModelProvider(this)[EditAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentEditAdsBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
        imageListAdapter = ImageListAdapter(this@EditAdsFragment)
    }

    private fun initArgs() = EditAdsFragmentArgs.fromBundle(requireArguments())

    override fun initViewModel() {
        super.initViewModel()
        viewModel.initViewModel()
        args.uuid?.let { it -> viewModel.getDetailAd(it) }
    }

    override fun initView() = with(binding) {
        super.initView()

        with(btnCreateAd) {
            isCheckable = !isButtonClickable()
            isEnabled = isCheckable
        }

        cusDelivery.setInterface(this@EditAdsFragment)
        cusPriceIsNegotiable.setInterface(this@EditAdsFragment, 1)
        cusOMoneyAccept.setInterface(this@EditAdsFragment, 2)
        cusWhatsApp.setInterface(this@EditAdsFragment, 3)
        cusTelegram.setInterface(this@EditAdsFragment, 4)

        getAdsDetail()
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

        flAddImage.isVisible = selectedPath.size < 2

        ivAddImage.setOnClickListener {
            pickImages()
        }

        btnCreateAd.setOnClickListener {
            prepareValuesForAd()
        }
    }

    private fun setField(item: ResultX) = with(binding) {
        item.title?.let { if(it.isNotEmpty()) cusTitle.setText(it) else
            cusTitle.setHint(getString(R.string.title)) }

        item.category?.parent?.name?.let { if(it.isNotEmpty()) cusCategory.setText(it) else
            cusCategory.setHint(getString(R.string.category)) }

        item.category?.name?.let { if(it.isNotEmpty()) {
            cusSubCategory.setText(it)
            cusSubCategory.isVisible = it.isNotEmpty()
        } else
            cusSubCategory.setHint(getString(R.string.sub_category)) }

        item.description?.let {if(it.isNotEmpty()) cusDescription.setText(it) else
            cusDescription.setHint(getString(R.string.description)) }

        item.adType?.let { if(it.isNotEmpty()) cusAdType.setText(it) else
            cusTitle.setHint(getString(R.string.ad_type)) }

        item.contractPrice?.let {
            cusPriceIsNegotiable.isChecked(it)
            cusCurrency.isVisible = it.not()
            cusPrice.isVisible = it.not()
        }
        item.currency?.let { if(it.isNotEmpty()) cusCurrency.setText(it) else
            cusCategory.setHint(getString(R.string.currency)) }

        item.price?.let { if(it.isNotEmpty()) cusPrice.setText(it) else
            cusPrice.setHint(getString(R.string.price)) }

        item.location?.name?.let { if(it.isNotEmpty()) cusLocation.setText(it) else
            cusLocation.setHint(getString(R.string.bishkek)) }

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

        item.hasImage?.let { it ->
            if (it) {
                item.images?.let { img -> uploadImage(img) }
            }
        }
    }

    private fun getAdsDetail() {
        safeFlowGather {
            viewModel.detailAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        it.data.resultX?.let { it1 -> setField(it1) }
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
        checkPermission()
        viewModel.uploadImage(getFile(imageUri))

        val model = UploadImageResultEntity(path = imageUri)
        selectedImages.add(1, model)
        binding.flAddImage.isVisible = false
        imageListAdapter?.initAdapter(selectedImages.toList())
        binding.rwToUploadImages.adapter = imageListAdapter

        getUploadedImage(model, imageUri)
    }

    private fun uploadImage(imageUri: List<String>) {
        println("${this.javaClass.simpleName}====1====   $imageUri ")
        println("${this.javaClass.simpleName}==${selectedImages.size}==2====   $selectedImages ")
        imageUri.map { selectedImages.addAll(1, listOf(UploadImageResultEntity(url = it))) }
        selectedUrl.addAll(selectedImages)
        selectedPath.addAll(selectedImages)
        listImageUrlInRemote = imageUri.map { UploadImageResultEntity(url = it) }.toMutableList()
        println("${this.javaClass.simpleName}==${selectedImages.size}==3====   $selectedImages ")
        println("${this.javaClass.simpleName}==${selectedUrl.size}==4====   $selectedUrl ")
        println("${this.javaClass.simpleName}==${selectedPath.size}==5====   $selectedPath ")
        binding.flAddImage.isVisible = false
        imageListAdapter?.initAdapter(selectedImages.toList())
        binding.rwToUploadImages.adapter = imageListAdapter
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

    private fun getCategoryId(catName: String, subCatName: String?) =
        categoriesEntity.asSequence().filter { it.name == catName }.map { it }
            .filter { it.name == subCatName }.map { it.id }.first()

    private fun addIfNotContains(
        uri: UploadImageResultEntity,
        path: UploadImageResultEntity, imageUri: Uri
    ) {
        if (uri in selectedUrl) return
        if (containsModel(path)) return
        val itemIndex = selectedImages.indexOf(path)
        if (itemIndex < 0) return
        selectedPath.add(1, path)
        selectedUrl.add(1, uri)
        selectedImages[itemIndex] = UploadImageResultEntity(uri.url, path = imageUri)
        imageListAdapter?.imagesLoaded(selectedImages)
    }

    private fun containsModel(m: UploadImageResultEntity) =
        selectedPath.any {
            (it === m)
        }

    private fun deleteModelFromLists(index: Int) {
        val urlOfDeletedImage = selectedImages[index]
        println("${this.javaClass.simpleName}====1.00==${selectedImages.size}==   $selectedImages ")
        println("${this.javaClass.simpleName}====1.10====${selectedUrl.size}   $selectedUrl ")
        println("${this.javaClass.simpleName}====1.20====${selectedPath.size}   $selectedPath ")
        selectedImages.removeAt(index)
        selectedUrl.removeAt(index)
        selectedPath.removeAt(index)

        println("${this.javaClass.simpleName}====1.0====${selectedImages.size}   $selectedImages ")
        println("${this.javaClass.simpleName}====1.1====${selectedUrl.size}   $selectedUrl ")
        println("${this.javaClass.simpleName}====1.2====${selectedPath.size}   $selectedPath ")
        imageListAdapter?.initAdapter(selectedImages.toList())
        if (urlOfDeletedImage in selectedImages) return
        viewModel.deleteImageFromAd(DeletedImageUrlEntity(url = urlOfDeletedImage.url))
    }

    override fun toggleClicked(positionOfCell: Int) = with(binding) {
        when (positionOfCell) {
            0 -> cusDelivery.isVisible = categoriesEntity[0].delivery ?: false
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
        pickImages()
    }

    override fun selectMainImage(index: Int, uri: String?) {
        mainImageIndex = index
        imageListAdapter?.selectMainImage(index)
    }

    private fun prepareValuesForAd() = with(binding) {

        val editAds = EditAds(
            title = cusTitle.getText(),
            category = getCategoryId(cusCategory.getText(), cusSubCategory.getText()),
            adType = cusAdType.getText(),
            contractPrice = cusPriceIsNegotiable.isChecked(),
            currency = if (!cusPriceIsNegotiable.isChecked()){cusCurrency.getText()} else null,
            delivery = cusDelivery.isChecked(),
            description = cusDescription.getText(),
            images = prepareUrlForAd(),
            location = cusLocation.getItemId(),
            oMoneyPay = cusOMoneyAccept.isChecked(),
            price = if (!cusPriceIsNegotiable.isChecked()){cusPrice.getText()} else null,
            promotionType = null,
            telegramProfile = if (cusTelegram.isChecked()){cusTelegramNick.getText()} else null,
            whatsappNum = if (cusWhatsApp.isChecked()){cusWhatsAppNumber.getValue()} else null,
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


    private fun isButtonClickable(): Boolean {
        with(binding) {
            if (cusTitle.getText().isEmpty() ||
                cusCategory.getText().isEmpty() ||
                cusDescription.getText().isEmpty() ||
                cusAdType.getText().isEmpty() ||
                cusPriceIsNegotiable.isChecked().not() &&
                (cusCurrency.getText().isEmpty() ||
                        cusPrice.getText().isEmpty()) ||
                isAllImagesHasUrl()
            ) return false
        }
        return true
    }

    private fun isAllImagesHasUrl(): Boolean {
        if (selectedImages.size > 1) return false
        return selectedImages.filterNot { selectedImages.indexOf(it) != 0 }
            .all { it.url != null }
    }

    private fun createAd() {
        safeFlowGather {
            viewModel.editedAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        makeToast("Объявление создано")
                        deleteImagesFromRemote()
                        findNavController().navigate(R.id.mainFragment)
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

    private fun deleteImagesFromRemote() {
        println("${this.javaClass.simpleName}--${listImageUrlInRemote.size}....1000..$listImageUrlInRemote")
        //listImageUrlInRemote.retainAll(selectedImages.map { it.url })
        val tmp = listImageUrlInRemote.filter { it in selectedImages }
        println("${this.javaClass.simpleName}--${listImageUrlInRemote.size}....2000..$listImageUrlInRemote")
        println("${this.javaClass.simpleName}--${tmp.size}....3000..$tmp")
        //viewModel.deleteImageFromAd()
    }

    override fun onDestroy() {
        println("fOnDest------")
        super.onDestroy()
    }
}