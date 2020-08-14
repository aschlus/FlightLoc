package ashex.flightloc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class LocationsAdapter (private val mLocations: List<Location>) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.location_title)
        val townTextView = itemView.findViewById<TextView>(R.id.location_town)
        val flownButton = itemView.findViewById<Button>(R.id.flown_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val locationView = inflater.inflate(R.layout.entry_location, parent, false)
        return ViewHolder(locationView)
    }

    override fun onBindViewHolder(viewHolder: LocationsAdapter.ViewHolder, position: Int) {
        val location: Location = mLocations.get(position)
        val titleTextView = viewHolder.titleTextView
        val townTextView = viewHolder.townTextView
        titleTextView.setText(location.title)
        townTextView.setText(location.town)
        val button = viewHolder.flownButton
        button.text = if (location.flown) "Flown" else "Unflown"
        button.isEnabled = false
    }

    override fun getItemCount(): Int {
        return mLocations.size
    }
}