package kz.reself.resod.ui.rent

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.reself.resod.R
import kz.reself.resod.api.adapter.BuildingAdapter
import kz.reself.resod.api.data.Building
import kz.reself.resod.databinding.FragmentRentBinding

class RentFragment : Fragment() {
    private var rentBuilding = generateBuildingsList().toMutableList()
    private var buildingAdapter = BuildingAdapter(rentBuilding)
    private var _binding: FragmentRentBinding? = null

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

        _binding = FragmentRentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Recycler View
        val recyclerView = binding.fragmentRentRvRentBuilding
        recyclerView.adapter = buildingAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // textView
        val textView: TextView = binding.textRent
        rentViewModel.text.observe(viewLifecycleOwner) {
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