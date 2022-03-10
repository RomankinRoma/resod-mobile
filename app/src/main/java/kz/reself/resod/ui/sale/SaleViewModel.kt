package kz.reself.resod.ui.sale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SaleViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sale Fragment"
    }
    val text: LiveData<String> = _text
}