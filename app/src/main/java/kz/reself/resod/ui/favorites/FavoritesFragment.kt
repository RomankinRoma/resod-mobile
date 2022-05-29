package kz.reself.resod.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kz.reself.resod.api.adapter.BuildingAdapter
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.dao.BuildingCardDao
import kz.reself.resod.databinding.FragmentFavoritesBinding
import kz.reself.resod.db.AppDatabase
import kz.reself.resod.entity.BuildingCardEntity
import kz.reself.resod.repository.BuildingCardRepository

class FavoritesFragment : Fragment(), BuildingAdapter.Listener {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private val buildingsList: MutableList<Building> = mutableListOf()
    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var repositoryBuildingCard: BuildingCardRepository
    private lateinit var buildingCardDao: BuildingCardDao
    private lateinit var favoriteViewModel: FavoritesViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        favoriteViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSale

//        buildingCardDao = AppDatabase.getAppDatabase(requireContext()).getBuildingCardDao()
//        repositoryBuildingCard = BuildingCardRepository(buildingCardDao)

        binding.fragmentFavoritesAbbFavoriteBtn.setOnClickListener {
            addBuindingCard()
        }



        val recyclerView: RecyclerView = binding.fragmentSaleRvBuildings
        val adapter = BuildingAdapter(buildingsList, this, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        favoriteViewModel.readAllData.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {

                adapter.setList(list.map {
                    elem -> elem.toBuilding()
                })
            }
        })

//        saleViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//            textView.visibility = View.INVISIBLE
//        }

        return root
    }

    private fun addBuindingCard() {
        Toast.makeText(requireContext(), "Click By add favorite", Toast.LENGTH_SHORT).show()

        favoriteViewModel.add(
            BuildingCardEntity(
                0,
                1,
                "TEST BUILDING",
                1,
                1.0,
                1,
                "DESC",
                "https://sun9-north.userapi.com/sun9-82/s/v1/ig2/F0LFvqm6gktKrK5iBOcJt0c3VtxdH2_54vhd2Nmmau6PrCvLSIXbzhgPXmD3UNEiTEu5gLojfQ1OeruFw56wuoC6.jpg?size=1440x1440&quality=95&type=album",
                "ORG",
                System.currentTimeMillis()
            )
        )
        Toast.makeText(requireContext(), "ADD NEW", Toast.LENGTH_SHORT).show()
    }

    private fun getBuildingCards() {
        val recyclerView: RecyclerView = binding.fragmentSaleRvBuildings
        val adapter = BuildingAdapter(buildingsList, this, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        Toast.makeText(context, "GET LIST", Toast.LENGTH_SHORT).show()

        lifecycle.coroutineScope.launch {
            val list = repositoryBuildingCard.readAllData.value
            Toast.makeText(context, "SET 1 LIST", Toast.LENGTH_SHORT).show()

            if (list != null) {
                val listR = list.map { elem -> elem.toBuilding()}
                buildingsList.addAll(listR)
                adapter.setList(buildingsList)
                Toast.makeText(context, "SET LIST", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(building: Building) {
        Toast.makeText(context, "Click by object", Toast.LENGTH_SHORT).show()
    }
}