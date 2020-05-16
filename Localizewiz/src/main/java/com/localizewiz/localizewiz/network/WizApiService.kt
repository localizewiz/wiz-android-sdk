package com.localizewiz.localizewiz.network

import com.localizewiz.localizewiz.Config
import com.localizewiz.localizewiz.models.Language
import com.localizewiz.localizewiz.models.LocalizedString
import com.localizewiz.localizewiz.models.Project
import com.localizewiz.localizewiz.models.responses.LanguageEnvelope
import com.localizewiz.localizewiz.models.responses.LocalizedStringEnvelope
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object  WizApiBuilder {

    private var config: Config? = null
    private val clientBuilder = OkHttpClient().newBuilder()
    private val authHeaderInterceptor = Interceptor { chain ->
        val builder = chain.request().newBuilder()
        config?.apiKey?.let {
            builder.addHeader("x-api-key", it)
        }
        chain.proceed(builder.build())
    }
    private val loggingInterceptor = HttpLoggingInterceptor()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(clientBuilder
            .addInterceptor(authHeaderInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dev.api.localizewiz.com")
        .build()

    fun<T> buildService(service: Class<T>, config: Config): T {
        this.config = config
        return retrofit.create(service)
    }

}
class WizApiService (var config: Config) {
    private var wizApi: WizApi = WizApiBuilder.buildService(WizApi::class.java, config)

    fun getProject(projectId: String): Call<Project> {
        return wizApi.getProject(projectId)
    }

    fun getProjectDetails(projectId: String): Call<Project> {
        return wizApi.getProjectDetails(projectId = projectId)
    }

    fun getProjectStrings(projectId: String): Call<LocalizedStringEnvelope> {
        return wizApi.getProjectStrings(projectId)
    }

    fun getProjectLanguages(projectId: String): Call<LanguageEnvelope> {
        return wizApi.getProjectLanguages(projectId)
    }

    fun getStringTranslations(projectId: String, languageCode: String): Call<LocalizedStringEnvelope> {
        return wizApi.getStringTranslations(projectId, languageCode)
    }

    fun getLanguages(): Call<LanguageEnvelope> {
        return wizApi.getLanguages()
    }

    fun uploadProjectFile(projectId: String, fileName: String, file: File): Call<Void>  {
        var requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), file)
        val multipart: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        return wizApi.uploadProjectFile(projectId, fileName = file.name, fileData = multipart)
    }
}
