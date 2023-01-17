package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers

import kg.o.internlabs.omarket.domain.entity.SubCategoriesEntity

interface SubCategoryClickHandler {
    fun subClickHandler(item: SubCategoriesEntity?)
}