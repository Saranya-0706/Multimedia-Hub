package com.example.woc_multimediahub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.woc_multimediahub.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolBar)
        val toggle = ActionBarDrawerToggle(this, binding.DrawerLayout,R.string.nav_open,R.string.nav_close )
        binding.DrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNav.background=null
        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Images -> replaceFragment(ImageFragment())
                R.id.Videos -> replaceFragment(VideoFragment())
                R.id.Music -> replaceFragment(MusicFragment())
                R.id.Pdfs -> replaceFragment(PdfFragment())
            }
            true
        }
        fragmentManager= supportFragmentManager
        replaceFragment(ImageFragment())

        binding.fab.setOnClickListener {
        Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show()}

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.nav_Storage -> replaceFragment(StorageFragment())
            R.id.nav_share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            R.id.nav_about -> Toast.makeText(this, "File Manager App", Toast.LENGTH_SHORT).show()
        }
        binding.DrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.DrawerLayout.isDrawerOpen(GravityCompat.START))
            binding.DrawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }





}