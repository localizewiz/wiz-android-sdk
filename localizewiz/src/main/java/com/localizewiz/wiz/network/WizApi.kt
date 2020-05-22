package com.localizewiz.wiz.network

import com.localizewiz.wiz.models.Project
import com.localizewiz.wiz.models.responses.LanguageEnvelope
import com.localizewiz.wiz.models.responses.LocalizedStringEnvelope
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WizApi {

    @GET("projects/{projectId}")
    fun  getProject(@Path("projectId") projectId: String): Call<Project>

    @GET("projects/{projectId}/details")
    fun  getProjectDetails(@Path("projectId") projectId: String): Call<Project>

    @GET("projects/{projectId}/strings")
    fun  getProjectStrings(@Path("projectId") projectId: String): Call<LocalizedStringEnvelope>

    @GET("projects/{projectId}/languages")
    fun  getProjectLanguages(@Path("projectId") projectId: String): Call<LanguageEnvelope>

    @GET("projects/{projectId}/strings/translations/{languageCode}")
    fun  getStringTranslations(@Path("projectId") projectId: String, @Path("languageCode") languageCode: String): Call<LocalizedStringEnvelope>

    @GET("/languages")
    fun getLanguages(): Call<LanguageEnvelope>

    @POST("/projects/{projectId}/files")
    fun uploadProjectFile(projectId: String, fileName: String, fileData: MultipartBody.Part): Call<Void>

    @GET("/")
    fun getRoot(): Call<String>
}