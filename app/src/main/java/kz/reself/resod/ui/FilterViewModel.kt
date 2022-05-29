package kz.reself.resod.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.reself.resod.api.data.FilterForm

open class FilterViewModel : ViewModel() {
    private var _filterForm = MutableLiveData<FilterForm>(FilterForm("", "", "", "", "", "", ""))

    val filterForm: LiveData<FilterForm> = _filterForm

    fun change(newFilterForm: FilterForm) {
        _filterForm.value = newFilterForm
    }
}