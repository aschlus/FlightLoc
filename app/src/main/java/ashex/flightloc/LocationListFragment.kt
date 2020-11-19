package ashex.flightloc

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LocationListFragment : Fragment(), View.OnClickListener {

    private var locations = arrayListOf<Location>()
    lateinit var addBtn: Button
    lateinit var rvLocations: RecyclerView
    lateinit var adapter: LocationsAdapter
    private var db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_location_list, container, false)

        rvLocations = rootView.findViewById<View>(R.id.rvLocations) as RecyclerView
        adapter = LocationsAdapter(locations)
        rvLocations.adapter = adapter
        rvLocations.layoutManager = LinearLayoutManager(rootView.context)

        addBtn = rootView.findViewById(R.id.button)
        addBtn.setOnClickListener(this)

        loadData()

        return rootView

    }

    override fun onClick(v: View) {
        newLocDialog(v)
    }

    private fun newLocDialog(v: View) {
        val dialog = Dialog(v.context)
        dialog.setContentView(R.layout.dialog_location)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setCancelable(true)

        val cancelBtn: ImageButton = dialog.findViewById(R.id.cancelBtn)
        val checkBtn: ImageButton = dialog.findViewById(R.id.checkBtn)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        checkBtn.setOnClickListener {
            val title: String = dialog.findViewById<EditText>(R.id.titleEdit).text.toString()
            val town: String = dialog.findViewById<EditText>(R.id.townEdit).text.toString()

            val newLocFire = hashMapOf(
                "added" to System.currentTimeMillis(),
                "title" to title,
                "town" to town
            )

            db.collection("locations")
                .add(newLocFire)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                    locations.add(0, Location(title, town, false))
                    adapter.notifyItemInserted(0)
                    rvLocations.scrollToPosition(0)
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error adding document", e)
                }

            dialog.dismiss()
        }

        dialog.show()

    }

    private fun loadData(){
        val test: MutableList<Location> = arrayListOf()

        db.collection("locations").orderBy("added", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Firebase", "${document.id} => ${document.data}")
                    test.add(0, Location(document.get("title") as String,
                        document.get("town") as String, false))
                }
                Log.d("Firebase", "AL => $test")
                locations.addAll(test.asReversed())
                adapter.notifyItemRangeChanged(adapter.itemCount, test.size)

            }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "Error getting documents: ", exception)
            }
    }

    companion object {
        fun newInstance(): LocationListFragment = LocationListFragment()
    }

}