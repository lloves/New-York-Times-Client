package com.neverbendeasy.newyorktimes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.neverbendeasy.newyorktimes.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SettingsActivity extends AppCompatActivity {

    DatePicker dpDate;
    ToggleButton tbSort;
    CheckBox cbArts;
    CheckBox cbFashion;
    CheckBox cbSports;
    Button btnSave;

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
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
//        Toast.makeText(SettingsActivity.this, "DATE:" + year + month + day, Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbArts:
                if (checked) {
                    cbArts.setTag("Arts");
                }
                break;
            case R.id.cbFashion:
                if (checked) {
                    cbFashion.setTag("Fashion");
                }
                break;
            case R.id.cbSports:
                if (checked) {
                    cbSports.setTag("Sports");
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

        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api-key", "a14da2188514faff29d638a5dbb8c88b:13:74375067");
//        params.put("fq", cbArts.getTag().toString());
//        params.put("fq", cbFashion.getTag().toString());
//        params.put("fq", cbSports.getTag().toString());
        params.put("sort", tbSort.getText());
        params.put("page", 0);
        params.put("begin_date", stringDate);
        params.put("q", "weightlifting");

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
            }
        });
    }
}
