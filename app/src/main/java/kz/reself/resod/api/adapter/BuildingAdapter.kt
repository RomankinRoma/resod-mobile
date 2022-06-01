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
        private val favoriteBtnAdd: TextView = itemView.findViewById(R.id.fragment_building__likeBtnAdd)
        private val favoriteBtnRemove: TextView = itemView.findViewById(R.id.fragment_building__likeBtnRemove)

        fun bind(building: Building, onFavoriteClickListener: Listener, context: Context) {
            placeAndCountry.text = building.name
            price.text = building.price.toString()
            areaAndNumber.text = building.area.toString()
            companyName.text = building.organization.name
            description.text = building.description
            favoriteBtnRemove.visibility = View.GONE

            itemView.setOnClickListener {
                onFavoriteClickListener.onFavoriteClick(building)
            }

            if (building.images.size > 0 && building.images[0].storageUrl != null) {
                Glide.with(context).load(building.images[0].storageUrl).into(imgUrl)
            }

            favoriteBtnAdd.setOnClickListener {
                favoriteBtnAdd.visibility = View.GONE
                favoriteBtnRemove.visibility = View.VISIBLE
                onFavoriteClickListener.onLikeBtnAdd(building)
            }

            favoriteBtnRemove.setOnClickListener {
                favoriteBtnRemove.visibility = View.GONE
                favoriteBtnAdd.visibility = View.VISIBLE
                onFavoriteClickListener.onLikeBtnRemove(building)
            }

            if (building.isAddFavorites == true) {
                favoriteBtnRemove.visibility = View.VISIBLE
                favoriteBtnAdd.visibility = View.GONE
            } else {
                favoriteBtnRemove.visibility = View.GONE
                favoriteBtnAdd.visibility = View.VISIBLE
            }
        }
    }

    interface Listener {
        fun onFavoriteClick(building: Building)
        fun onLikeBtnAdd(building: Building)
        fun onLikeBtnRemove(building: Building)
    }

    fun setList(list: List<Building>) {
        buildings = list
        notifyDataSetChanged()
    }
}