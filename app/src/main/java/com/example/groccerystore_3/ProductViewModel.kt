package com.example.groccerystore_3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    val products: MutableList<Product> = mutableListOf()
    val productsData: MutableLiveData<Product> by lazy { MutableLiveData<Product>() }

}