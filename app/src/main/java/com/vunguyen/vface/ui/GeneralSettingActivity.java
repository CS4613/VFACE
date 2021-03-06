/*
 * GeneralSettingActivity.java
 */
package com.vunguyen.vface.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vunguyen.vface.R;
import com.vunguyen.vface.helper.LocaleHelper;

import io.paperdb.Paper;

/**
 * This class implements some features to customize the app
 */
public class GeneralSettingActivity extends AppCompatActivity
{
    String account;
    AutoCompleteTextView languageMenu;
    String[] languagesList;
    TextView language_title;
    TextView tv_generalSetting;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_setting);
        account = getIntent().getStringExtra("ACCOUNT");

        initView();
        initData();
        initAction();
    }

    private void initAction()
    {
        displayLanguageMenu();
    }

    private void initData()
    {
        Paper.init(this);
        String language = Paper.book().read("language");
        if (language == null)
            Paper.book().write("language", "en");
        updateView(Paper.book().read("language"));
    }

    private void initView()
    {
        languageMenu = findViewById(R.id.filled_exposed_dropdown_language);
        language_title = findViewById(R.id.tv_language_title);
        tv_generalSetting = findViewById(R.id.tvTitle);
    }


    private void updateView(String language)
    {
        Context context = LocaleHelper.setLocale(this, language);
        Resources resources = context.getResources();
        language_title.setText(resources.getString(R.string.languages));
        tv_generalSetting.setText(resources.getString(R.string.general_settings));
    }

    private void displayLanguageMenu()
    {
        languagesList = getResources().getStringArray(R.array.languages);
        // initialize course menu
        ArrayAdapter<String> tvArrayAdapter = new ArrayAdapter<>(this,
                R.layout.dropdown_menu_popup_item, languagesList);
        languageMenu.setAdapter(tvArrayAdapter);

        languageMenu.setSelection(0);
        if (languagesList.length > 0)
        {
            languageMenu.setOnItemClickListener((parent, view, position, id) ->
            {
               if (position == 0)
               {
                   Paper.book().write("language", "en");
                   updateView(Paper.book().read("language"));
               }
               else if (position == 1)
               {
                   Paper.book().write("language", "vi");
                   updateView(Paper.book().read("language"));
               }
               else if (position == 2)
               {
                   Paper.book().write("language", "zh");
                   updateView(Paper.book().read("language"));
               }
            });
        }
        else
        {
            Log.i("EXECUTE", "No courses are available.");
        }
    }

    // go to another activity
    private void goToActivity(Intent intent)
    {
        intent.putExtra("ACCOUNT", account);
        startActivity(intent);
        finish();
    }

    // click the back button on navigation bar
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(GeneralSettingActivity.this, SettingsActivity.class);
        goToActivity(intent);
    }

    // click on back arrow button
    public void btnBackClick(View view)
    {
        onBackPressed();
    }
}
