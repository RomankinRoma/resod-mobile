package kz.reself.resod.ui.specialists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.adapter.SpecialistAdapter
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.databinding.FragmentSpecialistsBinding

class SpecialistsFragment : Fragment(), SpecialistAdapter.Listener {
    private val specialists = generateSpecialistList().toMutableList()
    private val specialistAdapter = SpecialistAdapter(specialists, this)

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

        val recyclerView: RecyclerView = binding.fragmentSpecialistRv
        recyclerView.adapter = specialistAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSpecialistClick(company: Specialist) {
        TODO("Not yet implemented")
    }
}

private fun generateSpecialistList(): List<Specialist> {
    return listOf(
        Specialist(
            "Yerbolat",
            "Pazyl",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.specialist_1
        ),
        Specialist(
            "Nurbolat",
            "Pazyl",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.specialist_2
        ),
        Specialist(
            "Zuko",
            "Fire",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.specialist_3
        ),
        Specialist(
            "Tom",
            "Luke",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.speclaist_3
        ),
        Specialist(
            "Luke",
            "Loki",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.specialist_1
        ),
        Specialist(
            "Munira",
            "Kosimova",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.specialist_2
        ),
        Specialist(
            "Yerulan",
            "Turganbek",
            "Проверенный специалист",
            "Казахстан, Алматы",
            R.drawable.specialist_3
        )
    )
}