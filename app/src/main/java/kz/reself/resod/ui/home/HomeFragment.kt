package kz.reself.resod.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kz.reself.resod.api.model.AdData
import kz.reself.resod.api.model.BuildingDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        val button: Button = binding.button
//        button.setOnClickListener(View.OnClickListener { view -> getAdData() })
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        getAdData()
        return root
    }
    private fun getAdData() {

        val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

        var response  = retrofit.getSortedData()

        response.enqueue(object : Callback<Map<String, List<AdData>>?> {
            override fun onResponse(
                call: Call<Map<String, List<AdData>>?>,
                response: Response<Map<String, List<AdData>>?>
            ) {
                val body = response.body();
                Log.d("SORTED","ALL SORTED:"+Gson().toJson(body))

            }

            override fun onFailure(call: Call<Map<String, List<AdData>>?>, t: Throwable) {
                Log.d("SORTED","ERROR:"+t.message)

            }
        })
        val options: HashMap<String, String> = HashMap()
        options["pageNumber"] = "0"
        options["pageSize"] = "10"
        options["priceStart"] = "0"
        options["priceEnd"] = "1000000"

        val response2  = retrofit.getAllBuilding(options)

        response2.enqueue(object : Callback<BuildingDTO?> {
            override fun onResponse(call: Call<BuildingDTO?>, response: Response<BuildingDTO?>) {
                val body = response.body()
                Log.println(Log.INFO,"ALL","ALL:"+Gson().toJson(body))
            }

            override fun onFailure(call: Call<BuildingDTO?>, t: Throwable) {
                Log.e("ALL","ERROR:"+t.message)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
