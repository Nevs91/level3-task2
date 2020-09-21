package com.example.madlevel3task2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.madlevel3task2.adapters.PortalsAdapter
import com.example.madlevel3task2.classes.Portal
import kotlinx.android.synthetic.main.fragment_portals.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalsFragment : Fragment() {

    private val portals = arrayListOf<Portal>();
    private val portalsAdapter = PortalsAdapter(portals) { portal: Portal ->
        partItemClicked(portal)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeAddPortalResult()
    }

    private fun initViews() {
        rvPortals.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rvPortals.adapter = portalsAdapter
    }

    /**
     * Handle click events from portal items inside the recyclerView
     */
    private fun partItemClicked(portal: Portal) {
        val context = requireContext()

        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        val builder = CustomTabsIntent.Builder()

        // Set toolbar color and/or setting custom actions before invoking build()
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent))

        // Once ready, call the build method to create a CustomTabsIntent
        val customTabsIntent = builder.build()

        // launch the desired Url
        customTabsIntent.launchUrl(context, Uri.parse(portal.url))
    }

    /**
     * Observe the [AddPortalFragment] to listen for new instances of the [Portal] data class and
     * add retrieved portals to the existing list
     */
    private fun observeAddPortalResult() {
        setFragmentResultListener(REQ_PORTAL_KEY) { _, bundle ->
            val portal = bundle.getParcelable<Portal>(BUNDLE_PORTAL_KEY)

            // Add the portal when the object is not null
            if (portal != null) {
                portals.add(portal)
                portalsAdapter.notifyDataSetChanged()
            } else {
                Log.e("PortalsFragment", "Request triggered, but empty portal object!")
            }
        }
    }
}