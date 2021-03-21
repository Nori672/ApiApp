package jp.techacademy.norihiro.nakano.apiapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){

    //タブの名前を格納したList
    val titleIds = listOf(R.string.tab_title_api, R.string.tab_title_favorite)

    //各ページの中身を格納したList
    val fragments= listOf(ApiFragment(), FavoriteFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}