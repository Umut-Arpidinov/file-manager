package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.FAQEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getFaq(token: String): Flow<ApiState<FAQEntity>>

}