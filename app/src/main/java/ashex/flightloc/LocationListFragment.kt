package ashex.flightloc

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.JsonReader
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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.xml.sax.helpers.LocatorImpl
import java.io.*
import java.lang.StringBuilder
import java.nio.Buffer

class LocationListFragment : Fragment(), View.OnClickListener {

    private var locations = arrayListOf<Location>()
    lateinit var addBtn: Button
    lateinit var rvLocations: RecyclerView
    lateinit var adapter: LocationsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_location_list, container, false)

        rvLocations = rootView.findViewById<View>(R.id.rvLocations) as RecyclerView
        adapter = LocationsAdapter(locations)
        rvLocations.adapter = adapter
        rvLocations.layoutManager = LinearLayoutManager(rootView.context)

        addBtn = rootView.findViewById(R.id.button)
        addBtn.setOnClickListener(this)

        val file = File(rootView.context.filesDir, "locations.json")

        if (!file.exists())
            Log.i("Result", "Need new file")
        file.createNewFile()

        val path = file.toString()

        val sb = StringBuilder()
        val reader = BufferedReader(FileReader(File(path)))
        var line: String? = ""

        do {
            line = reader.readLine()
            if (line == null)
                break
            //Log.i("Result", "Line: $line")
            sb.append(line)
        } while (true)
        reader.close()

        var stringJSON = sb.toString()

        val gson = GsonBuilder().setPrettyPrinting().create()

        var listLocation:List<Location> = listOf()

        if (stringJSON.isNotEmpty()) {
            listLocation = gson.fromJson(StringReader(stringJSON), Array<Location>::class.java).toList()
        }

        var alLocation = ArrayList(listLocation)

        var curSize = adapter.itemCount
        locations.addAll(alLocation.asReversed())
        adapter.notifyItemRangeChanged(curSize, alLocation.size)

        return rootView
    }

    override fun onClick(v: View) {
        newLocDialog(v)
    }

    private fun newLocDialog(v: View) {
        var dialog = Dialog(v.context)
        dialog.setContentView(R.layout.dialog_location)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setCancelable(true)

        var cancelBtn: ImageButton = dialog.findViewById(R.id.cancelBtn)
        var checkBtn: ImageButton = dialog.findViewById(R.id.checkBtn)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        checkBtn.setOnClickListener {
            val title: String = dialog.findViewById<EditText>(R.id.titleEdit).text.toString()
            val town: String = dialog.findViewById<EditText>(R.id.townEdit).text.toString()

            val file = File(v.context.filesDir, "locations.json")

            val path = file.toString()

            val sb = StringBuilder()
            val reader = BufferedReader(FileReader(File(path)))
            var line: String? = ""

            do {
                line = reader.readLine()
                if (line == null)
                    break
                //Log.i("Result", "Line: $line")
                sb.append(line)
            } while (true)
            reader.close()

            var stringJSON = sb.toString()
            //Log.i("Result", "String: $stringJSON, Len: ${stringJSON.length}")

            val gson = GsonBuilder().setPrettyPrinting().create()

            var listLocation:List<Location> = listOf()

            if (stringJSON.isNotEmpty()) {
                listLocation = gson.fromJson(StringReader(stringJSON), Array<Location>::class.java).toList()
            }

            var alLocation = ArrayList(listLocation)

            val newLoc = Location(title, town, false)
            alLocation.add(newLoc)
            val newJSON = gson.toJson(alLocation)

            //Log.i("Result", "Final: $newJSON")

            val writer = FileWriter(file)
            writer.write(newJSON)
            writer.flush()
            writer.close()

            locations.add(0, newLoc)
            adapter.notifyItemInserted(0)
            rvLocations.scrollToPosition(0)
            dialog.dismiss()
        }

        dialog.show()

    }

    companion object {
        fun newInstance(): LocationListFragment = LocationListFragment()
    }

}