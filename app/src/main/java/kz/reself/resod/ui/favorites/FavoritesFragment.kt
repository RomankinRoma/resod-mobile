package kz.reself.resod.ui.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kz.reself.resod.*
import kz.reself.resod.api.adapter.BuildingAdapter
import kz.reself.resod.api.data.Building
import kz.reself.resod.databinding.FragmentFavoritesBinding
import kz.reself.resod.entity.BuildingCardEntity

class FavoritesFragment : Fragment(), BuildingAdapter.Listener {
    private val buildingsList: MutableList<Building> = mutableListOf()
    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var favoriteViewModel: FavoritesViewModel
    private lateinit var appSharedPreferences: SharedPreferences

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        appSharedPreferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        favoriteViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val token = appSharedPreferences.getString(USER_LOGIN_STATUS_KEY, "")

        val recyclerView: RecyclerView = binding.fragmentFavoritesRvBuildings
        val adapter = BuildingAdapter(buildingsList, this, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (!token.equals("")) {
            favoriteViewModel.listFavorites.observe(viewLifecycleOwner, Observer { list ->
                if(list.content.size != 0) {
                    Log.w("CHANGE VAL FAVORITE","MORE 0")
                    binding.favoriteListEmptyText.visibility = View.INVISIBLE
                    binding.favoriteListEmptySubText.visibility = View.INVISIBLE
                    binding.fragmentFavoritesListTitle.visibility = View.VISIBLE

                    var listBuilding = list.content.map { elem ->
                        elem.building
                    }
                    listBuilding = listBuilding.filter { x -> x != null }

                    Log.w("LIST BUILDING = ",Gson().toJson(listBuilding))

                    adapter.setList(listBuilding)
                } else {
                    Log.w("CHANGE VAL FAVORITE","0")

                    binding.favoriteListEmptyText.visibility = View.VISIBLE
                    binding.favoriteListEmptySubText.visibility = View.VISIBLE
                    binding.fragmentFavoritesListTitle.visibility = View.INVISIBLE
                }
            })
        } else {
            favoriteViewModel.readAllData.observe(viewLifecycleOwner, Observer { list ->
                if (list != null) {
                    Log.w("CHANGE VAL DB","NOT NULL")

                    binding.favoriteListEmptyText.visibility = View.INVISIBLE
                    binding.favoriteListEmptySubText.visibility = View.INVISIBLE
                    binding.fragmentFavoritesListTitle.visibility = View.VISIBLE

                    adapter.setList(list.map {
                            elem -> elem.toBuilding()
                    })
                } else {
                    Log.w("CHANGE VAL DB","NULL")

                    binding.favoriteListEmptyText.visibility = View.VISIBLE
                    binding.favoriteListEmptySubText.visibility = View.VISIBLE
                    binding.fragmentFavoritesListTitle.visibility = View.INVISIBLE
                }
            })
        }

        return root
    }

    private fun deleteAll() {
        Toast.makeText(requireContext(), "Click By Delete All", Toast.LENGTH_SHORT).show()

        favoriteViewModel.deleteAll()
        Toast.makeText(requireContext(), "FINISH DELETED ALL", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(building: Building) {
        Toast.makeText(context, "Click by object", Toast.LENGTH_SHORT).show()
    }
}