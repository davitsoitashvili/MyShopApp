package com.example.myshopapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.example.myshopapp.App
import com.example.myshopapp.EMAIL
import com.example.myshopapp.R
import com.example.myshopapp.database.AppDatabase
import com.example.myshopapp.fragments.authentication.SignUpFragment
import com.example.myshopapp.network.ResponseLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_my_shop_dash_board.*
import kotlinx.android.synthetic.main.nav_header_my_shop_dash_board.view.*

class MyShopDashBoardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_shop_dash_board)
        manageDrawerLayout()
        getUserEmailAddress()
    }

    companion object {
        lateinit var emailAddress : String
        val db: AppDatabase by lazy {
            Room.databaseBuilder(
                App.getAppInstance().applicationContext,
                AppDatabase::class.java, "Products"
            ).build()
        }
    }

    private fun manageDrawerLayout() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_shoes, R.id.nav_bags, R.id.nav_watches
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_shop_dash_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserEmailAddress() {
        val headerView = nav_view.getHeaderView(0)
        emailAddress = intent.getStringExtra(EMAIL)!!
        headerView.userEmailAddress.text = emailAddress
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        openAuthenticationActivity()

    }

    private fun openAuthenticationActivity() {
        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
