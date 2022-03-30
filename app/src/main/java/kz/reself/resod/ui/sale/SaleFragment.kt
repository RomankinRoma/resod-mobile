package kz.reself.resod.ui.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.adapter.BuildingAdapter
import kz.reself.resod.api.data.Building
import kz.reself.resod.databinding.FragmentSaleBinding

class SaleFragment : Fragment() {
    private val buildings = generateBuildingsList().toMutableList()
    private val buildingAdapter = BuildingAdapter(buildings)
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

        val recyclerView: RecyclerView = binding.fragmentSaleRvBuildings
        recyclerView.adapter = buildingAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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
}

private fun generateBuildingsList(): List<Building> {
    return listOf(
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b1
        ),
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b2
        ),
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b3
        ),
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b4
        ),
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b5
        ),
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b1
        ),
        Building(
            "Bi Group",
            50000,
            500.0,
            2,
            "Апартаменты на острове Пхукет, Таиланд",
            "Расположен в экологически чистом районе! Тупиковая улица, всегда тихо, посторонних нет. Очень удобное расположение, есть 3 вызда в город, 7 минут езды...",
            R.drawable.b2
        ),
    )
}