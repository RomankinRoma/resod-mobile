package kz.reself.resod.ui.specialists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpecialistsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Specialists Fragment"
    }

    val text: LiveData<String> = _text
}