package zelimkhan.magomadov.notes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import zelimkhan.magomadov.notes.R
import zelimkhan.magomadov.notes.databinding.ActivityMainBinding
import zelimkhan.magomadov.notes.ui.core.navigation.NavigationDrawer

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationDrawer {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val topLevelDestinations =
        setOf(R.id.notesDestination, R.id.archiveDestination, R.id.trashDestination)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavConfig()
        setupNavDrawer()
    }

    private fun setupNavConfig() {
        navController = findNavController(R.id.hostFragment)
        binding.navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(topLevelDestinations, binding.drawerLayout)
    }

    private fun setupNavDrawer() {
        disableSwipeInNestedDestinations()
    }

    private fun disableSwipeInNestedDestinations() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevel = topLevelDestinations.contains(destination.id)
            val lockMode = if (isTopLevel) DrawerLayout.LOCK_MODE_UNLOCKED
            else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            binding.drawerLayout.setDrawerLockMode(lockMode)
        }
    }

    override fun connectWithToolbar(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) binding.drawerLayout.close()
        else super.onBackPressed()
    }
}