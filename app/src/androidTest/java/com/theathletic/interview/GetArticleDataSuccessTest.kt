package com.theathletic.interview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.theathletic.interview.articles.data.ArticleRepository
import com.theathletic.interview.articles.data.remote.ArticleApi
import com.theathletic.interview.articles.ui.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.TestCase.assertNotNull
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class GetArticleDataSuccessTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var articlesPresenter: ArticlesPresenter? = null

    private lateinit var mockWebServer: MockWebServer

    lateinit var jsonRepository: JsonRepository
    lateinit var placeholderApi: ArticleApi
    lateinit var articleRepository: ArticleRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        placeholderApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ArticleApi::class.java)
        jsonRepository = JsonRepository(placeholderApi)

        articleRepository = ArticleRepository(placeholderApi)
        articlesPresenter = ArticlesPresenter(articleRepository)
    }

    /**
     * Fetch valid data from mock web server and proxy api and check state
     */
    @Test
    fun FetchSuccessDataAndCheckState() { //`fetch data and check state`
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(FileReader.readStringFromFile(R.raw.article_list_success_response))
        mockWebServer.enqueue(response)

        //Act
        runBlocking {
            val mockResult = jsonRepository.getArticleList()
            articlesPresenter?.updateState {
                ArticlesState(true, mockResult)
            }

            articlesPresenter?.state?.let {
                assertNotNull(it)
                assertNotNull(it.articles)
                assertEquals(it.articles.size, mockResult.size)
            }
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}