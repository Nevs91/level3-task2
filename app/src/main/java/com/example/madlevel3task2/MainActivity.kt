package com.example.madlevel3task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.madlevel3task2.adapters.PortalsAdapter
import com.example.madlevel3task2.classes.Portal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_portals.*

class MainActivity : AppCompatActivity() {

    private val portals = arrayListOf<Portal>()
    private val portalsAdapter = PortalsAdapter(portals)

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController = findNavController(R.id.nav_host_fragment)

        // Navigate to the add portal fragment when clicking the button
        fab.setOnClickListener {
            navController.navigate(
                    R.id.action_portalsFragment_to_addPortalFragment
            )
        }

        initViews()
        fabToggler()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        // Initialize the recycler view with a staggeredGridlayout manager and adapter
        rvPortals.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        rvPortals.adapter = portalsAdapter
    }

    /**
     * Toggle the fragment action button based on the current destination
     */
    private fun fabToggler() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.addPortalFragment)) {
                fab.hide()
            } else {
                fab.show()
            }
        }
    }
}