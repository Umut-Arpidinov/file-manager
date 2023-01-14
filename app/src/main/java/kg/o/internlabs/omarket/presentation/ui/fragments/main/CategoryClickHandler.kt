package kg.o.internlabs.omarket.presentation.ui.fragments.main

import kg.o.internlabs.omarket.domain.entity.ResultEntity

interface CategoryClickHandler {
    fun clickedCategory(item: ResultEntity?)

}