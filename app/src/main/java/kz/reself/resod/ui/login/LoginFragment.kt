package kz.reself.resod.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.reself.resod.RegistrationActivity
import kz.reself.resod.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentLoginBinding.inflate(inflater, container,false)

        val root: View = binding.root

        binding.fragmentLoginRegistrationBtn.setOnClickListener {
            val intent = Intent(this@LoginFragment.requireContext(), RegistrationActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}