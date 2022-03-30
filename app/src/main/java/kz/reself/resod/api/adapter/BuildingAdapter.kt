package kz.reself.resod.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.reself.resod.R
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.adapter.BuildingAdapter.BuildingViewHolder

class BuildingAdapter (
    private val buildings: List<Building>
) : RecyclerView.Adapter<BuildingViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BuildingViewHolder {
        var view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_building, viewGroup, false)
        return BuildingViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BuildingViewHolder, position: Int) {
        val building = buildings[position]
        viewHolder.bind(building)
        viewHolder.itemView.tag = building
    }

    override fun getItemCount(): Int {
        return buildings.size
    }

    class BuildingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeAndCountry: TextView = itemView.findViewById(R.id.fragment_building__placeCountry)
        private val price: TextView = itemView.findViewById(R.id.fragment_building__buildingPrice)
        private val areaAndNumber: TextView = itemView.findViewById(R.id.fragment_building__buildingAreaNumberOfRooms)
        private val companyName: TextView = itemView.findViewById(R.id.fragment_building__buildingCompanyName)
        private val description: TextView = itemView.findViewById(R.id.fragment_building__buildingDescription)
        private val imgUrl: ImageView = itemView.findViewById(R.id.fragment_building__buildingImg)

        fun bind(building: Building) {
            placeAndCountry.text = building.address
            price.text = building.price.toString()
            areaAndNumber.text = building.area.toString()
            companyName.text = building.companyName
            description.text = building.description
            imgUrl.setImageResource(building.imgUrl)
        }
    }
}