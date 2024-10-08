package com.singhdevs.cryptocurrencyapp.domain.use_case.get_coin

import com.singhdevs.cryptocurrencyapp.common.Resource
import com.singhdevs.cryptocurrencyapp.data.remote.dto.toCoin
import com.singhdevs.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.singhdevs.cryptocurrencyapp.domain.model.Coin
import com.singhdevs.cryptocurrencyapp.domain.model.CoinDetail
import com.singhdevs.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private var repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow{
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch(e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}