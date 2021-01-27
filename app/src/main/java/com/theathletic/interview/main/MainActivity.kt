package com.theathletic.interview.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theathletic.interview.R
import com.theathletic.interview.articles.ui.ArticlesFragment
import com.theathletic.interview.leagues.ui.LeaguesFragment
import kotlinx.android.synthetic.main.activity_main.nav_view

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(bottomNavigationItemListener)
        nav_view.setOnNavigationItemReselectedListener(bottomNavigationItemListener)
        updatePrimaryNavigation(BottomTabItem.ARTICLES)
    }

    private val bottomNavigationItemListener = object :
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val tabItem = getBottomTabItem(item)
            updatePrimaryNavigation(tabItem)
            return true
        }

        override fun onNavigationItemReselected(item: MenuItem) {
            // TODO (scroll to the top of a list if possible)
        }
    }

    private fun updatePrimaryNavigation(tabItem: BottomTabItem) {
        supportActionBar?.title = getString(tabItem.menuTitle)
        val fragment = createFragmentForTabItem(tabItem)

        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun createFragmentForTabItem(tabItem: BottomTabItem): Fragment {
        return when(tabItem) {
            BottomTabItem.ARTICLES -> ArticlesFragment()
            BottomTabItem.LEAGUES -> LeaguesFragment()
        }
    }

    private fun getBottomTabItem(menuItem: MenuItem): BottomTabItem {
        return BottomTabItem.values().firstOrNull {
            it.menuItemId == menuItem.itemId
        } ?: BottomTabItem.ARTICLES
    }

    enum class BottomTabItem(
        @IdRes val menuItemId: Int,
        @StringRes val menuTitle: Int
    ) {
        ARTICLES(R.id.navigation_articles, R.string.title_articles),
        LEAGUES(R.id.navigation_leagues, R.string.title_leagues)
    }

}