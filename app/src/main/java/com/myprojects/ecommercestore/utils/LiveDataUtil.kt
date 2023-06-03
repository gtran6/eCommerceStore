package com.myprojects.ecommercestore.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T1, T2, T3, R> combineLatest(
    source1: LiveData<T1>,
    source2: LiveData<T2>,
    source3: LiveData<T3>,
    combineFunction: (T1, T2, T3) -> R
): LiveData<R> {
    val resultLiveData = MediatorLiveData<R>()

    val mergeFunction: () -> Unit = {
        val value1 = source1.value
        val value2 = source2.value
        val value3 = source3.value
        if (value1 != null && value2 != null && value3 != null) {
            resultLiveData.value = combineFunction(value1, value2, value3)
        }
    }

    resultLiveData.addSource(source1) { mergeFunction() }
    resultLiveData.addSource(source2) { mergeFunction() }
    resultLiveData.addSource(source3) { mergeFunction() }

    return resultLiveData
}