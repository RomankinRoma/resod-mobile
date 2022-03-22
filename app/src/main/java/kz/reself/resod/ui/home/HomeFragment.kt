package kz.reself.resod.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kz.reself.resod.api.model.AdData
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val button: Button = binding.button
        button.setOnClickListener(View.OnClickListener { view -> getAdData() })
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        getAdData()
        return root
    }
    private fun getAdData() {

        val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

        var response  = retrofit.getSortedData()

        response.enqueue(object : Callback<Map<String, List<AdData>>?> {
            override fun onResponse(
                call: Call<Map<String, List<AdData>>?>,
                response: Response<Map<String, List<AdData>>?>
            ) {
                val body = response.body();
                Log.d("SORTED","ALL SORTED:"+Gson().toJson(body))

            }

            override fun onFailure(call: Call<Map<String, List<AdData>>?>, t: Throwable) {
                Log.d("SORTED","ERROR:"+t.message)

            }
        })
        val response2  = retrofit.getAll()

        response2.enqueue(object : Callback<List<AdData>?> {
            override fun onResponse(call: Call<List<AdData>?>, response: Response<List<AdData>?>) {
                val body = response.body()
                Log.println(Log.INFO,"ALL","ALL:"+Gson().toJson(body))
            }

            override fun onFailure(call: Call<List<AdData>?>, t: Throwable) {
                Log.e("ALL","ERROR:"+t.message)
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
