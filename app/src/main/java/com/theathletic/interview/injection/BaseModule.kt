package com.theathletic.interview.injection

import com.theathletic.interview.articles.data.ArticleRepository
import com.theathletic.interview.articles.data.remote.ArticleApi
import com.theathletic.interview.articles.ui.ArticlesPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val baseModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://mobile-interview-backend.theathletic.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(ArticleApi::class.java) }

    single { ArticleRepository(get()) }

    viewModel { ArticlesPresenter(get()) }

}