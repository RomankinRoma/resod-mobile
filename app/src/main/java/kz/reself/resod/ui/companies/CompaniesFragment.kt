package kz.reself.resod.ui.companies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kz.reself.resod.R
import kz.reself.resod.api.adapter.CompanyAdapter
import kz.reself.resod.api.data.Company
import kz.reself.resod.api.model.AdData
import kz.reself.resod.api.model.CompanyDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentCompaniesBinding
import kz.reself.resod.ui.companies.CompaniesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompaniesFragment : Fragment(), CompanyAdapter.Listener {
//    private val companies = generateCompanyList().toMutableList()
//    private val companyAdapter = CompanyAdapter(companies, this)

    private var _binding: FragmentCompaniesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(CompaniesViewModel::class.java)

        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCompanies

//        val recyclerView: RecyclerView = binding.fragmentCompaniesRvCompanies
//        recyclerView.adapter = companyAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        getCompanies()

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
            textView.visibility = View.INVISIBLE
        }
        return root
    }

//    private fun onAddClick() {
//        companies.add(generateNewCompany())
//        companyAdapter.notifyDataSetChanged()
//    }

//    private fun generateNewCompany(): Company {
//        return Company("Company New", "New com desc", "New sub desc", R.drawable.com11)
//    }

    override fun onCompanyClick(company: Company) {
        Toast.makeText(context, "Click by company", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCompanies() {
        val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

        val responseCompany = retrofit.getAllCompany()

        responseCompany.enqueue(object : Callback<CompanyDTO?> {
            override fun onResponse(call: Call<CompanyDTO?>, response: Response<CompanyDTO?>) {
                showCompany(response.body()!!)
                Log.println(Log.INFO,"ALL","ALL:"+ Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<CompanyDTO?>, t: Throwable) {
                Log.e("ALL","ERROR:"+t.message)
            }
        })
    }

    private fun showCompany(body: CompanyDTO) {
        val recyclerView: RecyclerView = binding.fragmentCompaniesRvCompanies
        recyclerView.adapter = CompanyAdapter(body.content, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}

//private fun generateCompanyList(): List<Company> {
//    return listOf(
//        Company(
//            "Bazis",
//            "BAZIS-?? - ?????????????? ????????????????????????",
//            "?????? ?????????? ?? ???????????????????????????????? ????????????, ???????????????? ?? ????????????????????????, ???????????? ????????????, ???????????????? ?? ??????????????????????, ??????????????????????????????????, ???????????? ?????????????????????? ?? ???????????????? ????????????????????????????, ?? ?????? ???? ?????????????????????????? ?? ???????????????? ????????????, ?????????? ?? ??????????????, ?????????????????????????? ??????????????",
//                    R.drawable.company1
//        ), Company(
//            "RAMS Qazaqstan",
//            "???????????????????????? ???????????????? ??RAMS Qazaqstan?? ????????????. ???????????????? ???????? ?????????????? ?? ?????????????? 2004 ????????.",
//            "?? ???????????? 2004 ?????? ?????????????????? ???????? ???????????? ???????????????????????? ???? ???????????? ???????????????????? ??? ?????????????????? ?????????????????????? ?????????????? ????????????.",
//            R.drawable.company2
//        ), Company(
//            "Qazaq Stroy",
//            "?? ???????????????? Qazaq Str??y ??? ???????????????? ???? ???????????????????????? ?????????? ?? 2003 ????????.",
//            "Qazaq Str??y ??? ???????????????? ???? ???????????????????????? ?????????? ?? 2003 ????????.\n" +
//                    "\n" +
//                    "???? ?????? ?????????? ???????????????? ?????????????????????????????? ???????? ?????? ???????????????????????????? ?????????????????????? ?????????????????????????? ???????????????? ?????????? ??????????????????",
//            R.drawable.company3
//        ), Company(
//            "K7 Group",
//            "???????????????????????? ?????????????? ??7 GROUP ??? ?????????? ??????????",
//            "???? ???????????????????? ???????????? ???? ???????????????????????????? ?????????????? ??????????, ?????????????????? ?? ???????????????????????? ???????????? ?? ???????????? ???????? ?????????? ??????????????????,",
//            R.drawable.company4
//        ), Company(
//            "Com 5",
//            "Cpm desc 5",
//            "Com sub desc 5",
//            R.drawable.home1
//        ), Company(
//            "Com 5",
//            "Cpm desc 5",
//            "Com sub desc 5",
//            R.drawable.home2
//        ), Company(
//            "Com 6",
//            "Cpm desc 6",
//            "Com sub desc 6",
//            R.drawable.com11
//        )
//    )
//}