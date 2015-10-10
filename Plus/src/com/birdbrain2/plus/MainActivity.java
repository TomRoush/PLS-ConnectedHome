package com.birdbrain2.plus;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	PagerAdapter pagerAdapter;
	ViewPager viewPager;
	private static final int NUM_PAGES = 2;
	FragmentHome one;
	FragmentHome two;
	
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
	
	public void refresh(View v) {
		Toast toast = Toast.makeText(getBaseContext(), "Updating data...", Toast.LENGTH_SHORT);
		toast.show();
		((FragmentHome) ((ScreenSlidePagerAdapter) pagerAdapter).getItem(viewPager.getCurrentItem())).refresh();
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
				if(one == null) {
					one = new FragmentHome();
					Bundle args = new Bundle();
					args.putBoolean("warning", true);
					one.setArguments(args);
				}
				return one;
			} else {
				if(two == null) {
					two = new FragmentHome();
					Bundle args = new Bundle();
					args.putBoolean("warning", false);
					two.setArguments(args);
				}
				return two;
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
				return "warnings".toUpperCase(l);
			case 1:
				return "all sensors".toUpperCase(l);
			}
			return null;
		}
	}
}
