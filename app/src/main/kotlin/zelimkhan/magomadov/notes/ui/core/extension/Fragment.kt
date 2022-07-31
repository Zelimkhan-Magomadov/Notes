package zelimkhan.magomadov.notes.ui.core.extension

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import zelimkhan.magomadov.notes.ui.core.navigation.NavigationDrawer

fun Fragment.connectToolbarToNavigationController(toolbar: Toolbar) {
    activity?.lifecycleScope?.launchWhenCreated {
        (requireActivity() as NavigationDrawer).connectWithToolbar(toolbar)
    }
}