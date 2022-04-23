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
            "Bazis",
            "BAZIS-А - флагман строительной",
            "Это жилые и административные здания, торговые и промышленные, здания спорта, культуры и образования, сельхозназначения, здания относящиеся к объектам инфраструктуры, а так же автомобильные и железные дороги, дамбы и плотины, автомобильные развязк",
                    R.drawable.company1
        ), Company(
            "RAMS Qazaqstan",
            "Строительная компания «RAMS Qazaqstan» Алматы. Компания была создана в августе 2004 года.",
            "В ноябре 2004 для торгового дома «АБДИ» находящегося по адресу Сейфуллина – Райымбека произведена входная группа.",
            R.drawable.company2
        ), Company(
            "Qazaq Stroy",
            "О КОМПАНИИ Qazaq Strоy – работает на строительном рынке с 2003 года.",
            "Qazaq Strоy – работает на строительном рынке с 2003 года.\n" +
                    "\n" +
                    "За это время компания зарекомендовала себя как ответственного застройщика предлагающего основной массе населения",
            R.drawable.company3
        ), Company(
            "K7 Group",
            "Строительный холдинг К7 GROUP — лидер рынка",
            "Мы предлагаем услуги по проектированию частных домов, коттеджей и коммерческих зданий с учетом всех ваших пожеланий,",
            R.drawable.company4
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