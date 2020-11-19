package ashex.flightloc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    lateinit var signoutBtn : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        signoutBtn = rootView.findViewById(R.id.signoutBtn)
        signoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        return rootView
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

}