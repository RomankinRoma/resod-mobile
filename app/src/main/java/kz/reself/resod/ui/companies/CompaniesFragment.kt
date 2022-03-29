package kz.reself.resod.ui.companies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.adapter.CompanyAdapter
import kz.reself.resod.api.data.Company
import kz.reself.resod.databinding.FragmentCompaniesBinding
import kz.reself.resod.ui.companies.CompaniesViewModel

class CompaniesFragment : Fragment(), CompanyAdapter.Listener {
    private val companies = generateCompanyList().toMutableList()
    private val companyAdapter = CompanyAdapter(companies, this)

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

        val recyclerView: RecyclerView = binding.fragmentCompaniesRvCompanies
        recyclerView.adapter = companyAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
            textView.visibility = View.INVISIBLE
        }
        return root
    }

    private fun onAddClick() {
        companies.add(generateNewCompany())
        companyAdapter.notifyDataSetChanged()
    }

    private fun generateNewCompany(): Company {
        return Company("Company New", "New com desc", "New sub desc", R.drawable.com11)
    }

    override fun onCompanyClick(company: Company) {
        Toast.makeText(context, company.companyName, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun generateCompanyList(): List<Company> {
    return listOf(
        Company(
            "Com 1",
            "Cpm desc 1",
            "Com sub desc 1",
                    R.drawable.com11
        ), Company(
            "Com 2",
            "Cpm desc 2",
            "Com sub desc 2",
            R.drawable.home1
        ), Company(
            "Com 3",
            "Cpm desc 3",
            "Com sub desc 3",
            R.drawable.home2
        ), Company(
            "Com 4",
            "Cpm desc 4",
            "Com sub desc 4",
            R.drawable.com11
        ), Company(
            "Com 5",
            "Cpm desc 5",
            "Com sub desc 5",
            R.drawable.home1
        ), Company(
            "Com 5",
            "Cpm desc 5",
            "Com sub desc 5",
            R.drawable.home2
        ), Company(
            "Com 6",
            "Cpm desc 6",
            "Com sub desc 6",
            R.drawable.com11
        )
    )
}