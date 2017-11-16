package com.test.preference;

import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Checkable;
import android.widget.RadioButton;

public class MainActivity extends PreferenceActivity implements RadioButtonPreference.RadioButtonGroupState{

    PreferenceCategory mSchedulePlanPreferenceCategory;
    private Checkable mCurrentChecked;
    private String mCurrentEngine;
    private RadioButtonPreference radioOne;
    private RadioButtonPreference radioTwo;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_main);
        init();
    }

    private void init() {
        mSchedulePlanPreferenceCategory = (PreferenceCategory) findPreference("schedule_plan_preference");
        radioOne = new RadioButtonPreference(MainActivity.this, "Sunset to sunrise", this, MainActivity.this, true);
        radioTwo = new RadioButtonPreference(MainActivity.this, "Custom schedule", this, MainActivity.this, false);
        mSchedulePlanPreferenceCategory.addPreference(radioOne);
        mSchedulePlanPreferenceCategory.addPreference(radioTwo);
        Log.d("zly", "zly --> init.");
//        mRadioButton2.setChecked(true);
        //radioOne.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zly", "zly --> onResume");
    }

    @Override
    public String getCurrentKey() {
        return mCurrentEngine;
    }

    @Override
    public Checkable getCurrentChecked() {
        return mCurrentChecked;
    }

    @Override
    public void setCurrentKey(String key) {
        mCurrentEngine = key;
    }

    @Override
    public void setCurrentChecked(Checkable current) {
        mCurrentChecked = current;
    }

    @Override
    public void setRadioOne(RadioButton rb) {
        radioButtonOne = rb;
    }

    @Override
    public void setRadioTwo(RadioButton rb) {
        radioButtonTwo = rb;
    }

    @Override
    public void update() {
//        radioButtonOne.isChecked();
//        radioButtonTwo.;
        android.util.Log.d("zly", "zly -->radioButtonOne:" + radioButtonOne.isChecked());
        android.util.Log.d("zly", "zly -->radioButtonTwo:" + radioButtonTwo.isChecked());
    }
}
