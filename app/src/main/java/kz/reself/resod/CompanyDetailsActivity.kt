package kz.reself.resod

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kz.reself.resod.api.adapter.BuildingAdapterCarusel
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.data.Company
import kz.reself.resod.api.data.Organization
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.api.model.AdData
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.ActivityCompanyDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyDetailsActivity :  AppCompatActivity(), BuildingAdapterCarusel.Listener {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private lateinit var binding: ActivityCompanyDetailsBinding
    private lateinit var buildingAdapterCarusel: BuildingAdapterCarusel
    private var listBuilding: MutableList<Building> = mutableListOf()

    private var storedBuildingMap: Map<String, List<Building>> = HashMap()
    private val apaList: MutableList<Building> = mutableListOf()
    private val houseList: MutableList<Building> = mutableListOf()
    private val comList: MutableList<Building> = mutableListOf()
    private val vilList: MutableList<Building> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompanyDetailsBinding.inflate(layoutInflater)

        listBuilding.add(Building(1, "Name 1", 1, 1.0, 1, "desc", listOf(), Organization("ddd"), false))
        listBuilding.add(Building(1, "Name 1", 1, 1.0, 1, "desc", listOf(), Organization("ddd"), false))
        listBuilding.add(Building(1, "Name 1", 1, 1.0, 1, "desc", listOf(), Organization("ddd"), false))
        listBuilding.add(Building(1, "Name 1", 1, 1.0, 1, "desc", listOf(), Organization("ddd"), false))

        val rView = binding.activityCompanyDetailsRView

        val idSpe = intent.getStringExtra("id")
        getCompanyInfos(idSpe!!.toLong())
        getStoredBuilding(idSpe!!.toLong())

        buildingAdapterCarusel = BuildingAdapterCarusel(listBuilding, this, this)
        rView.adapter = buildingAdapterCarusel
        rView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        setContentView(binding.root)
    }

    private fun getCompanyInfos(id: Long) {
        val respone = retrofit.getCompanyById(id)

        respone.enqueue(object : Callback<Company?> {
            override fun onResponse(call: Call<Company?>, response: Response<Company?>) {
                setCompany(response.body()!!)
            }

            override fun onFailure(call: Call<Company?>, t: Throwable) {

            }
        })
    }

    private fun getStoredBuilding(id: Long) {
        val response  = retrofit.getSortedDataByCompanyId(id)

        response.enqueue(object : Callback<Map<String, List<Building>>?> {
            override fun onResponse(
                call: Call<Map<String, List<Building>>?>,
                response: Response<Map<String, List<Building>>?>
            ) {
                val body = response.body()
                storedBuildingMap = body!!
                buildingAdapterCarusel.setList(storedBuildingMap.get("Apartment")!!)
                Log.d("SORTED","ALL SORTED:"+Gson().toJson(body))

            }

            override fun onFailure(call: Call<Map<String, List<Building>>?>, t: Throwable) {
                Log.d("SORTED","ERROR:"+t.message)

            }
        })
    }


    private fun setCompany(com: Company) {
        binding.activityCompanyDetailsCompanyName.text = com.name
        binding.activityCompanyDetailsCompanyEmail.text = com.email
        binding.activityCompanyDetailsCompanyPhone.text = com.phone
        binding.activityCompanyDetailsCompanyService.text = Html.fromHtml(com.body)
        binding.activityCompanyDetailsCompanyDescription.text = com.description

//        Glide.with(this).load(com.imgUrlStr).into(binding.activityCompanyDetailsCompanyImg)

    }

    override fun onBuildingCarusel(building: Building) {
        TODO("Not yet implemented")
    }
}