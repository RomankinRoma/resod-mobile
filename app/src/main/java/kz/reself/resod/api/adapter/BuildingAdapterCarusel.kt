package kz.reself.resod.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.reself.resod.R
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.adapter.BuildingAdapterCarusel.BuildingViewHolder


class BuildingAdapterCarusel(
    private var list: List<Building>,
    private val onClickListener: Listener,
    private val context: Context
) : RecyclerView.Adapter<BuildingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.building_card, parent, false)
        return BuildingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        val building = list[position]
        holder.bind(building, onClickListener, context)
        holder.itemView.tag = building
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BuildingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.card_person__title)
        private val textDesc: TextView = itemView.findViewById(R.id.card_person__description)
        private val img: ImageView = itemView.findViewById(R.id.card_person__bannerIv)

        fun bind(building: Building, onClickListener: Listener, context: Context) {
            textTitle.text = building.name
            textDesc.text = building.description

            itemView.setOnClickListener {
                onClickListener.onBuildingCarusel(building)
            }

            if (building.images.size > 0 && building.images[0].storageUrl != null) {
                Glide.with(context).load(building.images[0].storageUrl).into(img)
            }
        }
    }

    interface Listener {
        fun onBuildingCarusel(building: Building)
    }

    fun setList(newlist: List<Building>) {
        list = newlist
        notifyDataSetChanged()
    }
}