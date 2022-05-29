package kz.reself.resod.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.reself.resod.R
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.adapter.BuildingAdapter.BuildingViewHolder

class BuildingAdapter (
    private var buildings: List<Building>,
    private val onFavoriteClickListener: Listener,
    private val context: Context
    ) : RecyclerView.Adapter<BuildingViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BuildingViewHolder {
        var view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_building, viewGroup, false)
        return BuildingViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BuildingViewHolder, position: Int) {
        val building = buildings[position]
        viewHolder.bind(building, onFavoriteClickListener, context)
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
        private val favoriteBtn: ImageButton = itemView.findViewById(R.id.fragment_building__likeBtn)

        fun bind(building: Building, onFavoriteClickListener: Listener, context: Context) {
            placeAndCountry.text = building.name
            price.text = building.price.toString()
            areaAndNumber.text = building.area.toString()
            companyName.text = building.organization.name
            description.text = building.description

            if (building.images.size > 0 && building.images[0].storageUrl != null) {
                Glide.with(context).load(building.images[0].storageUrl).into(imgUrl)
            }
//            imgUrl.setImageResource(building.imgUrl)

            favoriteBtn.setOnClickListener {
                onFavoriteClickListener.onFavoriteClick(building)
            }
        }
    }

    interface Listener {
        fun onFavoriteClick(building: Building)
    }

    fun setList(list: List<Building>) {
        buildings = list
        notifyDataSetChanged()
    }
}