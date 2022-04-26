package kz.reself.resod.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.api.adapter.SpecialistAdapter.SpecialistViewHolder

class SpecialistAdapter(
    private val specialists: List<Specialist>,
    private val onSpecialistClickListener: Listener
) : RecyclerView.Adapter<SpecialistViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SpecialistViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_specialist, viewGroup, false)
        view.setOnClickListener { v: View -> onSpecialistClickListener.onSpecialistClick(v.tag as Specialist) }
        return SpecialistViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SpecialistViewHolder, position: Int) {
        val specialist = specialists[position]
        viewHolder.bind(specialist)
        viewHolder.itemView.tag = specialist
    }

    override fun getItemCount(): Int {
        return specialists.size
    }

    class SpecialistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val specialistFullName: TextView = itemView.findViewById(R.id.card_specialist__fullName)
        private val specialistSpeciality: TextView = itemView.findViewById(R.id.card_specialist__speciality)
        private val specialistAddress: TextView = itemView.findViewById(R.id.card_specialist__address)
        private val specialistImageView: ImageView = itemView.findViewById(R.id.card_specialist__specialistImg)

        fun bind(specialist: Specialist) {
            specialistFullName.text = specialist.firstName + " " + specialist.lastName
            specialistSpeciality.text = specialist.status
            specialistAddress.text = specialist.address
            specialistImageView.setImageResource(specialist.imgUrl)
        }
    }

    interface Listener {
        fun onSpecialistClick(specialist: Specialist)
    }
}