package kz.reself.resod.ui.specialists

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kz.reself.resod.SpecialistDetailsActivity
import kz.reself.resod.api.adapter.SpecialistAdapter
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.api.model.SpecialistDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentSpecialistsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecialistsFragment : Fragment(), SpecialistAdapter.Listener {
    private val specialists: MutableList<Specialist> = mutableListOf()
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

    private var _binding: FragmentSpecialistsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

//        val specialistViewModel = ViewModelProvider(this).get(SpecialistsViewModel::class.java)

        _binding = FragmentSpecialistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getSpecialists()

        return root
    }

    private fun getSpecialists() {
        val responseCompany = retrofit.getAllSpecialists()

        responseCompany.enqueue(object : Callback<SpecialistDTO?> {
            override fun onResponse(call: Call<SpecialistDTO?>, response: Response<SpecialistDTO?>) {
                showCompany(response.body()!!)
                Log.println(Log.INFO,"GET_SPECIALISTS","SPECIALISTS_LIST_JSON:" + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<SpecialistDTO?>, t: Throwable) {
                Log.e("GET_SPECIALISTS","ERROR:" + t.message)
            }
        })
    }

    private fun showCompany(body: SpecialistDTO) {
        specialists.addAll(body.content)
        val recyclerView: RecyclerView = binding.fragmentSpecialistRv
        recyclerView.adapter = SpecialistAdapter(specialists, this, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // when click by specialist
    override fun onSpecialistClick(specialist: Specialist) {
        val intent = Intent(this@SpecialistsFragment.requireContext(), SpecialistDetailsActivity::class.java).also {
            it.putExtra("id", specialist.id.toString())
            startActivity(it)
        }
    }
}