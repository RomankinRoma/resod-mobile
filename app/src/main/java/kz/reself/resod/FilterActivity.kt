package kz.reself.resod

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kz.reself.resod.api.data.FilterForm
import kz.reself.resod.databinding.ActivityFilterBinding
import kz.reself.resod.ui.FilterViewModel
import kz.reself.resod.ui.rent.RentFragment

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    private lateinit var filterViewModel: FilterViewModel

    override fun onResume() {
        super.onResume()

        val filterTypeBuyVals = resources.getStringArray(R.array.filter_type_buy_values)
        val adapterTypeBuy = ArrayAdapter(this, R.layout.dropdown_item, filterTypeBuyVals)
        binding.activityFilterAutoCompleteBuyType.setAdapter(adapterTypeBuy)
    }

//    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFilterBinding.inflate(layoutInflater)

        binding.activityFilterDoFilterBtn.setOnClickListener {
            clickByDoFilterBtn()
        }
        binding.activityFilterClearBtn.setOnClickListener {
            clickByClearBtn()
        }

        println("--- open filter")
//        println("--- filter country val: " + filterViewModel.filterForm.value?.status)

        printFilterForm()

        binding.activityFilterInputCountry.setText(filterViewModel.filterForm.value?.country)

//        filterViewModel.filterFormData.observe(this, {
//            binding.activityFilterAutoCompleteBuyType.setSelection(getBuyTypeSelectionIndex(it.buyType))
//            binding.activityFilterAutoCompleteBuildingType.setText(it.buildingTpe)
//            binding.activityFilterInputCountry.setText(it.country)
//            binding.activityFilterInputCostStart.setText(it.startCost)
//            binding.activityFilterInputCostEnd.setText(it.endCost)
//
//            if (it.numberRoom != null && it.numberRoom != "") {
//                println("---- set chip value")
//                binding.activityFilterChipGroupNumberRoom.check(getChipId(it.numberRoom.toInt()))
//            }
//        })

        setContentView(binding.root)
    //        val rangeSlider = binding.activityFilterRangeSlider

    //        rangeSlider.setLabelFormatter { value: Float ->
    //            val format = NumberFormat.getCurrencyInstance()
    //            format.maximumFractionDigits = 0
    //            format.currency = Currency.getInstance("USD")
    //            format.format(value.toDouble())
    //        }
    }

    private fun clickByDoFilterBtn() {
        val buyType = binding.activityFilterAutoCompleteBuyType.text.toString()
        val buildingType = binding.activityFilterAutoCompleteBuildingType.text.toString()
        val country = binding.activityFilterInputCountry.text.toString()
        val startCost = binding.activityFilterInputCostStart.text.toString()
        val endCost = binding.activityFilterInputCostEnd.text.toString()

        val selectedChip = defineChipValue(binding.activityFilterChipGroupNumberRoom.checkedChipId)
        println("----- chip ID: " + selectedChip)
        println("----- Country: " + country)

        val filterForm = FilterForm(buyType, buildingType, country, startCost, endCost, selectedChip, "submit")

        filterViewModel.change(filterForm)

    }

    private fun clickByClearBtn() {
        val filterForm = FilterForm("","", "", "", "", "", "clear")

        filterViewModel.change(filterForm)
    }

    private fun defineChipValue(checkedChipId: Int): String {
        if (checkedChipId == R.id.activity_filter__oneRoomChip) {
            return "1"
        }
        if (checkedChipId == R.id.activity_filter__twoRoomChip) {
            return "2"
        }
        if (checkedChipId == R.id.activity_filter__thirdRoomChip) {
            return "3"
        }
        if (checkedChipId == R.id.activity_filter__moreFourRoomChip) {
            return "4"
        }
        return ""
    }

    private fun getBuyTypeSelectionIndex(buyTypeVal: String): Int {
        if (buyTypeVal.equals("Buy")) {
            return 1
        }
        if (buyTypeVal.equals("Rent")){
            return 2
        }
        return 1
    }

    private fun getChipId(chipId: Int): Int {
        if (chipId == 1) {
            return R.id.activity_filter__oneRoomChip
        }
        if (chipId == 2) {
            return R.id.activity_filter__twoRoomChip
        }
        if (chipId == 3) {
            return R.id.activity_filter__thirdRoomChip
        }
        if (chipId == 4) {
            return R.id.activity_filter__moreFourRoomChip
        }
        return -1
    }

    private fun printFilterForm() {
        println(" ----- SHOW filter VALUES -----------")
        println(binding.activityFilterAutoCompleteBuyType.text)
        println(binding.activityFilterAutoCompleteBuildingType.text)
        println(binding.activityFilterInputCountry.text)
        println(binding.activityFilterInputCostStart.text)
        println(binding.activityFilterInputCostEnd.text)
        println(binding.activityFilterChipGroupNumberRoom.checkedChipId)
    }
}