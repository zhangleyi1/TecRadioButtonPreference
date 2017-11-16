package com.test.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.RadioButton;

//import com.android.settings.SettingsActivity;


public class RadioButtonPreference extends Preference {

    private final RadioButtonGroupState mSharedState;
    private final MainActivity mSettingsActivity;
    private volatile boolean mPreventRadioButtonCallbacks;
    private String mName;
    private RadioButton mRadioButton;
    private boolean mChecked;
    private static int beginInit = 0;

    public RadioButtonPreference(Context context, String name, RadioButtonGroupState state, MainActivity prefActivity, boolean checked) {
        super(context);

        setLayoutResource(R.layout.preference_radio_button);
        mName = name;
        mSharedState = state;
        mSettingsActivity = prefActivity;
        mPreventRadioButtonCallbacks = false;
        mChecked = checked;
        setKey(name);
    }

    private final CompoundButton.OnCheckedChangeListener mRadioChangeListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onRadioButtonClicked(buttonView, isChecked);
                    Log.d("zly", "zly --> onCheckedChanged isChecked" + isChecked + " beginInit:" + beginInit);
                    if (2 == beginInit) {
                        mSharedState.update();
                    }
                }
            };

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        if (null == mSharedState) {
            throw new IllegalStateException("Call to getView() before a call to" +
                    "setSharedState()");
        }

        final RadioButton rb = (RadioButton) view.findViewById(R.id.night_mode_radio_button);
        rb.setOnCheckedChangeListener(mRadioChangeListener);
        rb.setText(mName);
        Log.d("zly", "zly --> onBindView getCurrentKey:" + mSharedState.getCurrentKey() + " getKey:" + getKey());
//        boolean isChecked = getKey().equals(mSharedState.getCurrentKey());
        boolean isChecked = getKey().equals("Sunset to sunrise");

        if (isChecked) {
            mSharedState.setCurrentChecked(rb);
        }

        if (getKey().equals("Sunset to sunrise")) {
            mSharedState.setRadioOne(rb);
            beginInit = 1;
        } else {
            beginInit = 2;
            mSharedState.setRadioTwo(rb);
        }

        mPreventRadioButtonCallbacks = true;
        rb.setChecked(mChecked);
        mPreventRadioButtonCallbacks = false;
        mRadioButton = rb;

        //syncSummaryView(view);
    }

    private void onRadioButtonClicked(final CompoundButton buttonView,
                                      boolean isChecked) {
        if (mPreventRadioButtonCallbacks ||
                (mSharedState.getCurrentChecked() == buttonView)) {
            return;
        }

        makeCurrentEngine(buttonView);
/*
        if (isChecked) {
            // Should we alert user? if that's true, delay making engine current one.
            if (shouldDisplayDataAlert()) {
                displayDataAlert(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        makeCurrentEngine(buttonView);
                    }
                },new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Undo the click.
                        buttonView.setChecked(false);
                    }
                });
            } else {
                // Privileged engine, set it current
                makeCurrentEngine(buttonView);
            }
        } else {
//            mSettingsIcon.setEnabled(false);
        }*/
    }

    private void makeCurrentEngine(Checkable current) {
        if (mSharedState.getCurrentChecked() != null) {
            mSharedState.getCurrentChecked().setChecked(false);
        }
        mSharedState.setCurrentChecked(current);
        mSharedState.setCurrentKey(getKey());
        callChangeListener(mSharedState.getCurrentKey());
    }

    public interface RadioButtonGroupState {
        String getCurrentKey();
        Checkable getCurrentChecked();

        void setCurrentKey(String key);
        void setCurrentChecked(Checkable current);
        void setRadioOne(RadioButton rb);
        void setRadioTwo(RadioButton rb);
        void update();
    }
}