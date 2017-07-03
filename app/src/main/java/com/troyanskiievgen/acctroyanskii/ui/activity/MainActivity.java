package com.troyanskiievgen.acctroyanskii.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.troyanskiievgen.acctroyanskii.presenter.MainActivityPresenter;
import com.troyanskiievgen.acctroyanskii.service.AccelerometerService;
import com.troyanskiievgen.acctroyanskii.ui.adapter.ViewPagerAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.troyanskiievgen.acctroyanskii.R;
import com.troyanskiievgen.acctroyanskii.ui.fragment.AccelerometerDataListFragment;
import com.troyanskiievgen.acctroyanskii.ui.fragment.AccelerometerDataGraphFragment;
import com.troyanskiievgen.acctroyanskii.view.MainActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static com.troyanskiievgen.acctroyanskii.ui.activity.LoginActivity.USER_ID_KEY;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @Nullable
    @BindView(R.id.main_tab_layout) TabLayout tabLayout;
    @Nullable
    @BindView(R.id.main_viewpager) ViewPager viewPager;
    @BindView(R.id.spinner_interval) Spinner intervalPicker;
    @BindView(R.id.edit_text_session_duration) EditText etSessionDuration;
    @BindView(R.id.edit_text_start_delay) EditText etStartDelay;
    @BindView(R.id.simple_progress_layer) LinearLayout progressLayer;

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    private String currenUserId;
    private ArrayAdapter<Integer> intervalAdapter;
    private ViewPagerAdapter fragmentAdapter;

    private AccelerometerDataListFragment dataListFragment;
    private AccelerometerDataGraphFragment dataGraphFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        currenUserId = getIntent().getStringExtra(USER_ID_KEY);
    }

    @Override
    public void setupMainActivity() {
        if (isPortraitOrientation()) {
            setupForPortrait();
        } else {
            setupForLandscape();
        }
    }

    private void setupForPortrait() {
        fragmentAdapter = new ViewPagerAdapter(getFragmentManager());
        fragmentAdapter.addFragment(AccelerometerDataListFragment.newInstance(), "Accelerometer Data List");
        fragmentAdapter.addFragment(AccelerometerDataGraphFragment.newInstance(), "Accelerometer Data Schedule");
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainActivityPresenter.setSelectedFragmentPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupForLandscape() {
        initFragments();
        addFragment(R.id.main_list_fragment_container, dataListFragment);
        addFragment(R.id.main_schedule_fragment_container, dataGraphFragment);
    }

    private void initFragments() {
        if (dataListFragment == null) {
            dataListFragment = AccelerometerDataListFragment.newInstance();
        }
        if (dataGraphFragment == null) {
            dataGraphFragment = AccelerometerDataGraphFragment.newInstance();
        }
    }

    private void addFragment(int fragmetLayoutId, Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(fragmetLayoutId, fragment);
        ft.commit();
    }

    @Override
    public void setupIntervalsPicker(Integer [] intervalsArray) {
        intervalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, intervalsArray);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalPicker.setAdapter(intervalAdapter);
        intervalPicker.setPrompt(getString(R.string.main_intervals));
        intervalPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mainActivityPresenter.updateIntervalSelected(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void startAccelerometerService() {
        Intent intent = new Intent(this, AccelerometerService.class);
        intent.putExtra(AccelerometerService.WORk_DURATION, getSessionDuration());
        intent.putExtra(AccelerometerService.START_DATE, getStartTime());
        intent.putExtra(AccelerometerService.INTERVAL, mainActivityPresenter.getIntervalSelected());
        intent.putExtra(USER_ID_KEY, currenUserId);
        startService(intent);
    }

    private long getSessionDuration() {
        long duration = 0;
        if(!etSessionDuration.getText().toString().isEmpty()) {
            duration = Long.valueOf(etSessionDuration.getText().toString()) * 1000;
        }
        return duration;
    }

    private long getStartTime() {
        long delay = 0;
        if(!etStartDelay.getText().toString().isEmpty()) {
            delay = Long.valueOf(etStartDelay.getText().toString()) * 1000;
        }
        return delay;
    }

    @Override
    public void setIntervalSelected(int position) {
        intervalPicker.setSelection(position);
    }

    @Override
    public void setSelectedFragmentPager(int position) {
        if(isPortraitOrientation()) {
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void setupButtons(boolean isStartEnabled) {
        findViewById(R.id.main_stop_btn).setEnabled(!isStartEnabled);
        findViewById(R.id.main_start_btn).setEnabled(isStartEnabled);
    }

    @Override
    public void showProgressBar() {
        progressLayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressLayer.setVisibility(View.GONE);
    }

    @OnClick(R.id.main_start_btn)
    public void onStartClick() {
        startAccelerometerService();
        mainActivityPresenter.setupButtonsAvailability(false);
    }

    @OnClick(R.id.main_stop_btn)
    public void onStopClick(){
        stopService(new Intent(this, AccelerometerService.class));
        mainActivityPresenter.setupButtonsAvailability(true);
    }

    private boolean isPortraitOrientation() {
        return getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT;
    }
}
