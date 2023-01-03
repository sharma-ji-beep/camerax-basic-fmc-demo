package com.android.example.cameraxbasic.repositories

import com.android.example.cameraxbasic.remote_db.RemoteApiClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

class MLModelRepository {

     fun uploadImage(
         name: RequestBody,
         file: MultipartBody.Part
     ): Call<ResponseBody> {
        return RemoteApiClient.getClient().uploadImage(name,file = file)

    }


}
