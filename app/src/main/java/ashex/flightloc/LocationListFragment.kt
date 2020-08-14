package ashex.flightloc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LocationListFragment : Fragment() {

    lateinit var locations: ArrayList<Location>
    var flag = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_location_list, container, false)

        val rvLocations = rootView.findViewById<View>(R.id.rvLocations) as RecyclerView
        locations = Location.createLocationList(50)
        val adapter = LocationsAdapter(locations)
        rvLocations.adapter = adapter
        rvLocations.layoutManager = LinearLayoutManager(rootView.context)

        return rootView
    }

    companion object {
        fun newInstance(): LocationListFragment = LocationListFragment()
    }

}