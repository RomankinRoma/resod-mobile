package kz.reself.resod.api.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.data.Company
import kz.reself.resod.api.adapter.CompanyAdapter.CompanyViewHolder

class CompanyAdapter(
    private val companies: List<Company>,
    private val onCompanyClickListener: Listener,
) : RecyclerView.Adapter<CompanyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CompanyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_company, viewGroup, false)
        view.setOnClickListener { v: View -> onCompanyClickListener.onCompanyClick(v.tag as Company) }
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CompanyViewHolder, position: Int) {
        val company = companies[position]
        viewHolder.bind(company)
        viewHolder.itemView.tag = company
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val companyNameTextView: TextView = itemView.findViewById(R.id.fragment_company__companyName)
        private val companyDescriptionTextView: TextView = itemView.findViewById(R.id.fragment_company__companyDescription)
        private val companySubDescriptionTextView: TextView = itemView.findViewById(R.id.fragment_company__companySubDescription)
        private val companyImageView: ImageView = itemView.findViewById(R.id.fragment_company__companyImg)

        fun bind(company: Company) {
            companyNameTextView.text = company.name
            companyDescriptionTextView.text = company.description
            companySubDescriptionTextView.text = Html.fromHtml(company.body)
            companyImageView.setImageResource(company.imgUrl)
        }

    }

    interface Listener {
        fun onCompanyClick(company: Company)
    }
}