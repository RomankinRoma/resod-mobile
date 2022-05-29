package kz.reself.resod.ui.proflie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kz.reself.resod.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        var acc = GoogleSignIn.getLastSignedInAccount(requireContext())

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val root: View = binding.root

        if (acc != null) {
            binding.fragmentProfileUserName.text = acc?.displayName
            binding.fragmentProfileUserEmail.text = binding.fragmentProfileUserEmail.text.toString() + acc?.email
            Glide.with(requireContext()).load(acc?.photoUrl).into(binding.fragmentProfileUserImg)
        }



        return root
    }
}