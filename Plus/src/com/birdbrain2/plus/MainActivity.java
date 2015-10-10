package com.birdbrain2.plus;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity {
	PagerAdapter pagerAdapter;
	ViewPager viewPager;
	private static final int NUM_PAGES = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Keep screen on for demo
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// Instantiate a ViewPager and a PagerAdapter.
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		/**
		 * Gets the current fragment
		 * 
		 * @return the current fragment
		 */
		@Override
		public Fragment getItem(int position) {
			if(position == 0) {
				return new FragmentHome();
			} else {
				return new FragmentHome();
			}
		}

		/**
		 * @return the number of pages this slider holds
		 */
		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "page1".toUpperCase(l);
			case 1:
				return "page2".toUpperCase(l);
			}
			return null;
		}
	}
}
