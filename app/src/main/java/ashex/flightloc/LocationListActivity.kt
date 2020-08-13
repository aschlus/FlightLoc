package ashex.flightloc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LocationListActivity : AppCompatActivity() {
    lateinit var locations: ArrayList<Location>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        val rvLocations = findViewById<View>(R.id.rvLocations) as RecyclerView
        locations = Location.createLocationList(50)
        val adapter = LocationsAdapter(locations)
        rvLocations.adapter = adapter
        rvLocations.layoutManager = LinearLayoutManager(this)
    }
}