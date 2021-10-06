package com.example.waterpolo3000.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.waterpolo3000.GameFragment
import com.example.waterpolo3000.LedFragment
import com.example.waterpolo3000.ProtocolFragment

const val GAME_PAGE_INDEX = 0
const val PROTOCOL_PAGE_INDEX = 1
const val LED_BOARD_PAGE_INDEX = 2

class WaterpoloPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        GAME_PAGE_INDEX to { GameFragment() },
        PROTOCOL_PAGE_INDEX to { ProtocolFragment() },
        LED_BOARD_PAGE_INDEX to { LedFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
