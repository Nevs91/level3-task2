package com.example.madlevel3task2

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.madlevel3task2.classes.Portal
import kotlinx.android.synthetic.main.fragment_add_portal.*

const val REQ_PORTAL_KEY = "req_portal"
const val BUNDLE_PORTAL_KEY = "bundle_portal"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddPortalFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_portal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPortal.setOnClickListener {
            onAddPortal()
        }
    }

    /**
     * Store the game rating and navigate to the summary fragment.
     */
    private fun onAddPortal() {
        val title = etTitle.text.toString()
        val url = etUrl.text.toString()

        // Check if a title and url is filled in
        if (title.isNotEmpty() && url.isNotEmpty()) {

            // Check if the url matches parts of "RFC 3987"
            if (Patterns.WEB_URL.matcher(url).matches()) {

                // Create a new bundle with a new parcelable portal instance
                val bundle = Bundle()
                bundle.putParcelable(BUNDLE_PORTAL_KEY, Portal(title, url))

                // Set the data as fragmentResult
                setFragmentResult(REQ_PORTAL_KEY, bundle)

                // "pop" the backstack, this means we destroy this fragment and go back to the PortalsFragment
                findNavController().popBackStack()
            } else {
                Toast.makeText(activity, R.string.invalid_url, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, R.string.invalid_portal, Toast.LENGTH_SHORT).show()
        }
    }
}