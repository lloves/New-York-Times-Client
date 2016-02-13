package com.neverbendeasy.newyorktimes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ToggleButton;

import com.neverbendeasy.newyorktimes.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    DatePicker dpDate;
    ToggleButton tbSort;
    CheckBox cbArts;
    CheckBox cbFashion;
    CheckBox cbSports;
    Button btnSave;
    HashSet<String> newsDeskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews() {
        dpDate = (DatePicker) findViewById(R.id.dpDate);
        tbSort = (ToggleButton) findViewById(R.id.tbSort);
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbFashion = (CheckBox) findViewById(R.id.cbFashion);
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        btnSave = (Button) findViewById(R.id.btnSave);
        newsDeskList = new HashSet<>();
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
//        Toast.makeText(SettingsActivity.this, "DATE:" + year + month + day, Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbArts:
                if (checked)
                    newsDeskList.add("Arts");
                else
                    if (newsDeskList.contains("Arts")) {
                        newsDeskList.remove("Arts");
                    }

                break;
            case R.id.cbFashion:
                if (checked)
                    newsDeskList.add("Fashion");
                else
                    if (newsDeskList.contains("Fashion")) {
                        newsDeskList.remove("Fashion");
                    }
                break;
            case R.id.cbSports:
                if (checked)
                    newsDeskList.add("Sports");
                else
                    if (newsDeskList.contains("Sports")) {
                        newsDeskList.remove("Sports");
                    }
                break;
        }
    }

    public void onSave(View view) {
        onDateSet(dpDate, dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth());

        Calendar calendar = Calendar.getInstance();
        calendar.set(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth());

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String stringDate = format.format(calendar.getTime());

        String newsDeskString = TextUtils.join(" ", newsDeskList);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("date", stringDate);
        edit.putString("sortOrder", tbSort.getText().toString());
        edit.putString("newsDesk", "news_desk:(" + newsDeskString + ")");
        edit.commit();

        Intent data = new Intent();
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        finish();
    }

}
