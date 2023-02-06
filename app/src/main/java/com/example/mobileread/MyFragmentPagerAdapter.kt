package com.example.mobileread

import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.contracts.contract

class MyFragmentPagerAdapter:FragmentStateAdapter
{
   var fragments:MutableList<Fragment> = arrayListOf()
   constructor(fragmentActivity: FragmentActivity):super(fragmentActivity)
   constructor(fragment: Fragment):super(fragment)
   constructor(fragmentManager: FragmentManager,lifecycle: Lifecycle):super(fragmentManager,lifecycle)

   override fun getItemCount()  = fragments.size


   override fun createFragment(position: Int)=fragments[position]


}