package kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter

import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity

interface AdClicked {
    fun adClicked(ad: MyAdsResultsEntity)
}