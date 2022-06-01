package kz.reself.resod

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import kz.reself.resod.databinding.ActivityMainBinding

const val APP_PREFERENCES = "APP_PREFERENCES"

const val USER_ID_KEY = "USER_ID_KEY"
const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
const val USER_TOKEN_KEY = "USER_TOKEN_KEY"
const val USER_LOGIN_TYPE_KEY = "USER_LOGIN_TYPE_KEY"
const val USER_LOGIN_STATUS_KEY = "USER_LOGIN_STATUS_KEY"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var appSharedPreferences: SharedPreferences

    private val appSharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
        Log.w("LISTENER PREFEREN NICO", "KEY = " + key)

        if (key == USER_LOGIN_STATUS_KEY) {
            val value = preferences.getString(USER_LOGIN_STATUS_KEY, "")

            if (value.equals("ok")) {
                binding.navView.getMenu().findItem(R.id.nav_login)?.isVisible = false
                binding.navView.getMenu().findItem(R.id.nav_profile)?.isVisible = true
            } else {
                binding.navView.getMenu().findItem(R.id.nav_login)?.isVisible = true
                binding.navView.getMenu().findItem(R.id.nav_profile)?.isVisible = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appSharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_sale,
                R.id.nav_rent,
                R.id.nav_companies,
                R.id.nav_specialists,
                R.id.nav_login,
                R.id.nav_profile,
                R.id.nav_chat
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        defineLoginVisible()
        appSharedPreferences.registerOnSharedPreferenceChangeListener(appSharedPreferencesListener)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        appSharedPreferences.unregisterOnSharedPreferenceChangeListener(appSharedPreferencesListener)
    }

    private fun defineLoginVisible() {
        val status = appSharedPreferences.getString(USER_LOGIN_STATUS_KEY, "")

        if (status.equals("ok")) {
            binding.navView.getMenu().findItem(R.id.nav_login)?.isVisible = false
            binding.navView.getMenu().findItem(R.id.nav_profile)?.isVisible = true
        } else {
            binding.navView.getMenu().findItem(R.id.nav_login)?.isVisible = true
            binding.navView.getMenu().findItem(R.id.nav_profile)?.isVisible = false
        }
    }
}