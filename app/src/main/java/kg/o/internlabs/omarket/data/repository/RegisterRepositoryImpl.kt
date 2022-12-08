package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RegisterRepository, BaseRepository() {

    private val mapper = MapperForModels()

    override fun checkOtp(reg: RegisterEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntity(
            apiService.checkOtp(
                mapper.mapEntityToDbModel(
                    reg
                )
            )
        )
    }

    override fun loginUser(reg: RegisterEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntity(
            apiService.loginUser(
                mapper.mapEntityToDbModel(
                    reg
                )
            )
        )
    }

    override fun refreshToken(reg: RegisterEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntity(
            apiService.refreshToken(
                mapper.mapEntityToDbModel(
                    reg
                )
            )
        )
    }

    override fun registerUser(reg: RegisterEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntity(
            apiService.registerUser(
                mapper.mapEntityToDbModel(
                    reg
                )
            )
        )
    }
}