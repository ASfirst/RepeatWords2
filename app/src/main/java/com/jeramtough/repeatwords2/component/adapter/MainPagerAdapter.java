package com.jeramtough.repeatwords2.component.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.jeramtough.repeatwords2.controller.fragment.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 23:50.
 */
public class MainPagerAdapter extends FragmentPagerAdapter
{
    private DictionaryFragment dictionaryFragment;
    private SettingFragment settingFragment;
    private ListFragment listFragment;
    private LearningFragment learningFragment;
    
    private List<Fragment> fragments;
    
    public MainPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragments = new ArrayList<>();
        
        dictionaryFragment = new DictionaryFragment();
        settingFragment = new SettingFragment();
        listFragment = new ListFragment();
        learningFragment = new LearningFragment();
        
        fragments.add(learningFragment);
        fragments.add(settingFragment);
        fragments.add(listFragment);
        fragments.add(dictionaryFragment);
    }
    
    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }
    
    @Override
    public int getCount()
    {
        return fragments.size();
    }
	
	/*@Override
	public int getItemPosition(@NonNull Object object)
	{
		return POSITION_NONE;
	}*/
}
