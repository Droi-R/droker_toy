package com.bvc.ordering.di

import com.bvc.data.remote.api.GithubApi
import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.api.SplashApi
import com.bvc.domain.log
import com.bvc.domain.utils.Constant
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .client(provideHttpClient())
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideSplashApiService(retrofit: Retrofit): SplashApi = retrofit.create(SplashApi::class.java)

    @Provides
    @Singleton
    fun provideMainApiService(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logger =
            HttpLoggingInterceptor.Logger { message ->
                if (!message.startsWith("{") && !message.startsWith("[")) {
                    log.d(message)
                    return@Logger
                }
                try {
                    val prettyJson =
                        GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(JsonParser.parseString(message))
                    log.w("prettyJson: $prettyJson")
                } catch (e: JsonSyntaxException) {
                    log.d(message)
                }
            }

        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
