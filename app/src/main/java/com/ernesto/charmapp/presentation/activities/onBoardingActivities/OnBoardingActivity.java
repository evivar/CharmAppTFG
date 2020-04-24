package com.ernesto.charmapp.presentation.activities.onBoardingActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.sharedPreferences.SharedPreferencesManager;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientMainActivity;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingCalendarFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingChartsFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingDiaryFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingEditFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingHeadacheFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingIndexFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingProfileFragment;
import com.ernesto.charmapp.presentation.fragments.onBoardingFragments.OnBoardingWelcomeFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;


public class OnBoardingActivity extends FragmentActivity {

    private ViewPager pager;

    private SmartTabLayout indicator;

    private Button skipBtn;

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        pager = findViewById(R.id.viewPagerOnBoarding);
        indicator = findViewById(R.id.indicator);
        skipBtn = findViewById(R.id.skipBtnOnBoarding);
        nextBtn = findViewById(R.id.nextBtnOnBoarding);

        pager.setAdapter(setUpAdapter());
        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 7) {
                    skipBtn.setVisibility(View.GONE);
                    nextBtn.setText("Finalizar");
                } else {
                    skipBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("Siguiente");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishOnBoarding();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager.getCurrentItem() == 7) {
                    finishOnBoarding();
                } else {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }
            }
        });
    }

    private void finishOnBoarding() {
        SharedPreferencesManager.getInstance(this).completeOnBoarding(true);
        Intent intent = new Intent(this, PatientMainActivity.class);
        startActivity(intent);
        finish();
    }

    private FragmentStatePagerAdapter setUpAdapter() {
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new OnBoardingWelcomeFragment();
                    case 1:
                        return new OnBoardingIndexFragment();
                    case 2:
                        return new OnBoardingDiaryFragment();
                    case 3:
                        return new OnBoardingHeadacheFragment();
                    case 4:
                        return new OnBoardingCalendarFragment();
                    case 5:
                        return new OnBoardingEditFragment();
                    case 6:
                        return new OnBoardingChartsFragment();
                    case 7:
                        return new OnBoardingProfileFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 8;
            }
        };
        return adapter;
    }
}
