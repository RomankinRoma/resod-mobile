package kz.reself.resod.ui.companies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kz.reself.resod.api.adapter.CompanyAdapter
import kz.reself.resod.api.data.Company
import kz.reself.resod.api.model.CompanyDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentCompaniesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompaniesFragment : Fragment(), CompanyAdapter.Listener {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private val companiesList: MutableList<Company> = mutableListOf()
    private var _binding: FragmentCompaniesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val homeViewModel =
            ViewModelProvider(this).get(CompaniesViewModel::class.java)

        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getCompanies()

        return root
    }

//    private fun onAddClick() {
//        companies.add(generateNewCompany())
//        companyAdapter.notifyDataSetChanged()
//    }

    override fun onCompanyClick(company: Company) {
        Toast.makeText(context, "Click by company", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCompanies() {
        val responseCompany = retrofit.getAllCompany()

        responseCompany.enqueue(object : Callback<CompanyDTO?> {
            override fun onResponse(call: Call<CompanyDTO?>, response: Response<CompanyDTO?>) {
                showCompany(response.body()!!)
                Log.println(Log.INFO,"GET_COMPANIES","COMPANIES_LIST:" + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<CompanyDTO?>, t: Throwable) {
                Log.e("GET_COMPANIES","ERROR:" + t.message)
            }
        })
    }

    private fun showCompany(body: CompanyDTO) {
        companiesList.addAll(body.content)

        val recyclerView: RecyclerView = binding.fragmentCompaniesRvCompanies
        recyclerView.adapter = CompanyAdapter(companiesList, this, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}