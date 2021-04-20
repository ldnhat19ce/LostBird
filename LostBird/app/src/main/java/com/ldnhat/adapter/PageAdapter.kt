package com.ldnhat.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ldnhat.lostbird.R

class PageAdapter : FragmentStatePagerAdapter {

    private var fragments:MutableList<Fragment>

    constructor(fm: FragmentManager, behavior: Int, fragments: MutableList<Fragment>) : super(fm, behavior) {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}