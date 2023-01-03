package com.android.example.cameraxbasic.repositories

import com.android.example.cameraxbasic.model_class.MLResponse

interface MLModelListener {
    fun onSuccess(authData: MLResponse?)

    fun onFailure(msg:String)

    fun onError(msg: String)

    fun onNoConnection(msg: String)
}