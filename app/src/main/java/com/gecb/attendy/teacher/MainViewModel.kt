package com.gecb.attendy.teacher

import androidx.lifecycle.MutableLiveData

class MainViewModel {
    lateinit var userData :MutableLiveData<UserData>

    init{
        userData = MutableLiveData()
    }

    @JvmName("getUserData1")
    fun getUserData():MutableLiveData<UserData>{
        return userData
    }
}