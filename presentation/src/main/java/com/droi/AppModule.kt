//package com.droi
//
////import com.droi.retrofit.ApiInterfaceImpl
//import com.droi.data.api.NetRetrofit
//import com.droi.data.api.RetrofitService
//import com.droi.data.datasource.YoRemoteDataSource
//import com.droi.data.datasource.YoRemoteDataSourceImpl
//import com.droi.data.repository.YoRepositoryImpl
//import com.droi.data.util.Logger
//import com.droi.domain.repository.YoRepository
//import com.droi.domain.usecase.GetUserUseCase
//import com.droi.view.FirstFragment
//import com.droi.viewmodel.MainViewModel
//import kotlinx.coroutines.CoroutineScope
//import org.koin.dsl.module
//
//val appModule = module {
//
//    factory{ CoroutineScope(get()) }
//    // single instance of HelloRepository
//    /* single : 싱글톤 빈 정의를 제공. 즉 1번만 객체를 생성한다 */
////    single() { Logger() }
//    single<RetrofitService>() { NetRetrofit(get()).getRetrofitService() }
//    single<YoRemoteDataSource>() { YoRemoteDataSourceImpl(get()) }
//    single<YoRepository>() { YoRepositoryImpl(get()) }
//    single<GetUserUseCase>() { GetUserUseCase(get()) }
//
//    // Simple Presenter Factory
//    /* factory : 호출될 때마다 객체 생성 */
////    factory { ApiViewModel(get()) }
////  factory {MainViewModel(get(),get())}
//    single {MainViewModel(get(),get())}
//    factory { FirstFragment() }
//}