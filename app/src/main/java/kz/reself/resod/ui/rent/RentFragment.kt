package kz.reself.resod.ui.rent

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kz.reself.resod.APP_PREFERENCES
import kz.reself.resod.FilterActivity
import kz.reself.resod.USER_TOKEN_KEY
import kz.reself.resod.api.adapter.BuildingAdapter
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.model.BuildingDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentRentBinding
import kz.reself.resod.entity.BuildingCardEntity
import kz.reself.resod.ui.FilterViewModel
import kz.reself.resod.ui.favorites.FavoritesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RentFragment : Fragment(), BuildingAdapter.Listener {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private var rentBuildingList: MutableList<Building> = mutableListOf()
    private var _binding: FragmentRentBinding? = null
    private var currentPage = 0
    private var maxPage = -1
    private val filterViewModel: FilterViewModel by activityViewModels()
    private lateinit var favoriteViewModel: FavoritesViewModel
    private lateinit var appSharedPreferences: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rentViewModel =
            ViewModelProvider(this).get(RentViewModel::class.java)

        favoriteViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        appSharedPreferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        _binding = FragmentRentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fragmentRentFilterBtn.setOnClickListener {
            val intent = Intent(this@RentFragment.requireContext(), FilterActivity::class.java)
            startActivity(intent)
        }

        // textView
        val textView: TextView = binding.textRent
        rentViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
            textView.visibility = View.INVISIBLE
        }

        getBuilding(0, 10, 0, 1000000, false)

        filterViewModel.filterForm.observe(viewLifecycleOwner, {filterForm ->
            println("------ FILTER event")
            if (filterForm.status == "submit") {
                println("---- SUBMIT")
            }
        })

        return root
    }

    private fun getBuilding(pageNumber: Int, pageSize: Int, priceStart: Int, priceEnd: Int, isAdd: Boolean) {
        val options: HashMap<String, String> = HashMap()
        options["pageNumber"] = pageNumber.toString()
        options["pageSize"] = pageSize.toString()
        options["priceStart"] = priceStart.toString()
        options["priceEnd"] = priceEnd.toString()


        val responseBuilding = retrofit.getAllBuilding(options)

        responseBuilding.enqueue(object : Callback<BuildingDTO?> {
            override fun onResponse(call: Call<BuildingDTO?>, response: Response<BuildingDTO?>) {
                setBuildingList(response.body()!!, isAdd)
                Log.println(Log.INFO,"GET_BUILDING","BUILDING_LIST:" + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<BuildingDTO?>, t: Throwable) {
                Log.e("GET_BUILDING","ERROR:" + t.message)
            }
        })
    }

    private fun setBuildingList(body: BuildingDTO, isAdd: Boolean) {
        maxPage = body.total / body.size
        if (body.total % body.size != 0) {
            maxPage++
        }

        rentBuildingList.addAll(body.content)

        val recyclerView = binding.fragmentRentRvRentBuilding
        if (!isAdd) {
            recyclerView.adapter = BuildingAdapter(rentBuildingList, this, requireContext())
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1)) {
                        if (currentPage <= maxPage) {
                            currentPage++
                            getBuilding(currentPage, 10, 0, 1000000, true)
                        }
                    }
                }
            })
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(building: Building) {
        Toast.makeText(context, "Click CARD", Toast.LENGTH_SHORT).show()
    }

    override fun onLikeBtnAdd(building: Building) {
        Toast.makeText(context, "Click ADD F", Toast.LENGTH_SHORT).show()

        val token = appSharedPreferences.getString(USER_TOKEN_KEY, "")

        if (token.equals("")) {
            Log.w("ADD_CLICK", "WAS ADDED")
            favoriteViewModel.getUserByIdInDb(building.id).observe(viewLifecycleOwner, Observer { it ->
                if (it == null) {
                    favoriteViewModel.add(BuildingCardEntity.fromBuildingCard(building))
                }
            })
        } else {
            favoriteViewModel.add(building.id)
        }
    }

    override fun onLikeBtnRemove(building: Building) {

    }
}