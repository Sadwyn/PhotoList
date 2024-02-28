package com.sadwyn.photolist.di

import com.sadwyn.photolist.data.PhotoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", AUTHORIZATION_TOKEN)
                .build()
            chain.proceed(newRequest)
        }).build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): PhotoApi {
        return retrofit.create(PhotoApi::class.java)
    }

    private const val AUTHORIZATION_TOKEN =
        "Oeul4NRgbEx2IPbTTxAapqSnWMSvNhy4kjuSKGk9eKQxcn3At8LhSVGc"
    private const val BASE_URL = "https://api.pexels.com/"
}
