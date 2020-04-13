package com.sebastian.osorios.udea.atlas.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sebastian.osorios.udea.atlas.Fragments.CharacteristFragment
import com.sebastian.osorios.udea.atlas.Fragments.GeolocationFragment
import com.sebastian.osorios.udea.atlas.Fragments.OthersFragment
import com.sebastian.osorios.udea.atlas.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                val characteristFragment: CharacteristFragment = CharacteristFragment()
                return characteristFragment
            }
            1 -> {
                val othersFragment = OthersFragment()
                return othersFragment
            }
            else -> {
                val geolocationFragment = GeolocationFragment()
                return geolocationFragment
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}