package com.android.example.cameraxbasic.remote_db

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RemoteApiInterface {


    @Multipart
    @POST("/upload/image")
    fun uploadImage(
        @Part("file") name: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    companion object {
        //API end points..
        const val PRODUCTION = "https://test3.acviss.co"

        const val SUCCESS = 200

        //        const val BASEURL = TESTING
        const val BASEURL = PRODUCTION

    }

}