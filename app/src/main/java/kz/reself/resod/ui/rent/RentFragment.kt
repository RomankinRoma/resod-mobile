package kz.reself.resod.ui.rent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kz.reself.resod.databinding.FragmentRentBinding

class RentFragment : Fragment() {

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

        val textView: TextView = binding.textRent
        rentViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}