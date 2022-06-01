package kz.reself.resod

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.ActivitySpecialistDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecialistDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpecialistDetailsBinding
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpecialistDetailsBinding.inflate(layoutInflater)

        getSpecialistById()

        setContentView(binding.root)
    }

    private fun getSpecialistById() {
        val idSpe = intent.getStringExtra("id")
        Log.println(Log.INFO,"SPECIALIST_ID","SPE_ID:" + idSpe)

        val responseCompany = retrofit.getSpecialistById(idSpe!!)

        responseCompany.enqueue(object : Callback<Specialist?> {
            override fun onResponse(call: Call<Specialist?>, response: Response<Specialist?>) {
                setSpecialist(response.body()!!)
                Log.println(Log.INFO,"GET_SPECIALIST_BY_ID","SPECIALIST_BY_ID_JSON:" + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<Specialist?>, t: Throwable) {
                Log.e("GET_SPECIALIST_BY_ID","ERROR:" + t.message)
            }
        })
    }

    private fun setSpecialist(specialist: Specialist) {
        Log.println(Log.INFO,"GET_SPECIALIST_IMG_URL","GET_SPECIALIST_IMG_URL: " + specialist.storageUrl)

        Glide.with(this).load(specialist.storageUrl).into(binding.activitySpecialistDetailsSpeImg)

        binding.activitySpecialistDetailsSpeFullName.text = specialist.firstName + " " + specialist.lastName
        binding.activitySpecialistDetailsCompanyName.text = getString(R.string.specialist_from_company, specialist.organization.name)
        binding.activitySpecialistDetailsPhoneNumber.text = specialist.phoneNumber
        binding.activitySpecialistDetailsWhatsappNumber.text = specialist.whatsappPhoneNumber
        binding.activitySpecialistDetailsEmailAddress.text = specialist.email
        binding.activitySpecialistDetailsApartmentCount.text = getString(R.string.apartments_with_count, specialist.apartmentCount)
        binding.activitySpecialistDetailsHouseCount.text = getString(R.string.house_with_count, specialist.houseCount)
        binding.activitySpecialistDetailsCommerceCount.text = getString(R.string.commerce_with_count, specialist.commerceCount)
        binding.activitySpecialistDetailsPlaceJobName.text = getString(R.string.place_of_work, specialist.organization.name)
        binding.activitySpecialistDetailsDescription.text = specialist.description
    }
}