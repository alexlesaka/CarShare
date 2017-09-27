package com.alexlesaka.carshare.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alexlesaka.carshare.activities.Group.GroupTabChatFragment;
import com.alexlesaka.carshare.activities.Group.GroupTabEventsFragment;
import com.alexlesaka.carshare.activities.Group.GroupTabMembersFragment;
import com.alexlesaka.carshare.activities.Group.GroupTabRankingFragment;

/**
 * Created by aabuin on 18/09/2017.
 */

public class GroupTabAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;
    

    public GroupTabAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GroupTabRankingFragment tabRanking = new GroupTabRankingFragment();
                return tabRanking;
            case 1:
                GroupTabEventsFragment tabEvents = new GroupTabEventsFragment();
                return tabEvents;
                
            case 2:
                GroupTabMembersFragment tabMembers = new GroupTabMembersFragment();
                return tabMembers;
            case 3:
                GroupTabChatFragment tabChat = new GroupTabChatFragment();
                return tabChat;
                
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return mNumOfTabs;
    }

}
