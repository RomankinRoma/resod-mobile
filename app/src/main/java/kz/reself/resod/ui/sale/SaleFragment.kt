package kz.reself.resod.ui.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.adapter.BuildingAdapter
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentSaleBinding

class SaleFragment : Fragment(), BuildingAdapter.Listener {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private val buildingsList: MutableList<Building> = mutableListOf()
    private var _binding: FragmentSaleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val saleViewModel =
            ViewModelProvider(this).get(SaleViewModel::class.java)

        _binding = FragmentSaleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSale

//        val recyclerView: RecyclerView = binding.fragmentSaleRvBuildings
//        recyclerView.adapter = buildingAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        saleViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
            textView.visibility = View.INVISIBLE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(building: Building) {
        Toast.makeText(context, "Click by object", Toast.LENGTH_SHORT).show()
    }
}