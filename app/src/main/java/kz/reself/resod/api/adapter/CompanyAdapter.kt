package kz.reself.resod.api.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kz.reself.resod.R
import kz.reself.resod.api.data.Company
import kz.reself.resod.api.adapter.CompanyAdapter.CompanyViewHolder
import kz.reself.resod.api.data.CompanyImg
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyAdapter(
    private val companies: List<Company>,
    private val onCompanyClickListener: Listener,
    private val context: Context
) : RecyclerView.Adapter<CompanyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CompanyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_company, viewGroup, false)
        view.setOnClickListener { v: View -> onCompanyClickListener.onCompanyClick(v.tag as Company) }
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CompanyViewHolder, position: Int) {
        val company = companies[position]
        viewHolder.bind(company, context)
        viewHolder.itemView.tag = company
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

        private val companyNameTextView: TextView = itemView.findViewById(R.id.fragment_company__companyName)
        private val companyDescriptionTextView: TextView = itemView.findViewById(R.id.fragment_company__companyDescription)
        private val companySubDescriptionTextView: TextView = itemView.findViewById(R.id.fragment_company__companySubDescription)
        private val companyImageView: ImageView = itemView.findViewById(R.id.fragment_company__companyImg)

        fun bind(company: Company, context: Context) {
            companyNameTextView.text = company.name
            companyDescriptionTextView.text = company.description

            if (company.body == null) {
                companySubDescriptionTextView.text = ""
            } else {
                companySubDescriptionTextView.text = Html.fromHtml(company.body)
            }

            getCompanyImgByCompanyId(company.id, context)
        }

        private fun getCompanyImgByCompanyId(companyId: Long, context: Context) {
            val responseCompanyImg = retrofit.getCompanyImg(companyId)

            responseCompanyImg.enqueue(object : Callback<List<CompanyImg>?> {
                override fun onResponse(call: Call<List<CompanyImg>?>, response: Response<List<CompanyImg>?>) {
                    Glide.with(context).load(response.body()!![0].storageUrl).into(companyImageView)
                    Log.println(Log.INFO,"GET_COMPANY_IMG","COMPANY_IMG_LIST:" + Gson().toJson(response.body()))
                }

                override fun onFailure(call: Call<List<CompanyImg>?>, t: Throwable) {
                    Log.e("GET_COMPANY_IMG","ERROR:" + t.message)
                }
            })
        }

    }

    interface Listener {
        fun onCompanyClick(company: Company)
    }
}