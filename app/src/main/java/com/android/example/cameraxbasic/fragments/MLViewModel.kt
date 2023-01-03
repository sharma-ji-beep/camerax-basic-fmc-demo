package com.android.example.cameraxbasic.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.cameraxbasic.model_class.MLResponse
import com.android.example.cameraxbasic.repositories.MLModelListener
import com.android.example.cameraxbasic.repositories.MLModelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


/***
 *need to do proper error handling
 */

class MLViewModel(private val mlModelListener: MLModelListener) : ViewModel() {
    val TAG = MLViewModel::class.java.simpleName
    private  val mlModelRepository: MLModelRepository=MLModelRepository()


    fun getMLResponse(
        file: File
    ) {

        val requestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.Companion.createFormData("file", file.name, requestBody)
        Log.d(TAG, "file ${file.name}: ${requestBody.contentType()}  : $body")
        val name: RequestBody = "file".toRequestBody("text/plain".toMediaTypeOrNull())


        viewModelScope.launch {
            val response = mlModelRepository.uploadImage(name,body)
            response.enqueue( object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val json= Gson().toJson(response.body())
                        val data=  Gson().fromJson(json, MLResponse::class.java)
                        mlModelListener.onSuccess(data)
                        Log.d(TAG, "json:: tag:${data}")
                    } else {
                        response.errorBody(); // do something with that
                        Log.d(TAG, "errorBody:: tag:${response.errorBody()}")
                        mlModelListener.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d(TAG, "onFailure:: tag:${t.printStackTrace()}")

                    t.message?.let { mlModelListener.onFailure(it) }
                }

            })

        }
    }

}