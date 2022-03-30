package kz.reself.resod.ui.specialists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kz.reself.resod.databinding.FragmentSpecialistsBinding

class SpecialistsFragment : Fragment() {
    private var _binding: FragmentSpecialistsBinding? = null;

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val specialistViewModel = ViewModelProvider(this).get(SpecialistsViewModel::class.java)

        _binding = FragmentSpecialistsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSpecialists

        specialistViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
//            textView.visibility = View.INVISIBLE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}