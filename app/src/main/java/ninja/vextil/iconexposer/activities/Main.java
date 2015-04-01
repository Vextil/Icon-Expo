package ninja.vextil.iconexposer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.vextil.iconexposer.R;
import ninja.vextil.iconexposer.adapters.ChangelogAdapter;
import ninja.vextil.iconexposer.fragments.Apply;
import ninja.vextil.iconexposer.fragments.Home;
import ninja.vextil.iconexposer.fragments.Previews;
import ninja.vextil.iconexposer.fragments.Request;
import ninja.vextil.iconexposer.fragments.Wallpapers;
import ninja.vextil.iconexposer.utilities.DrawerBuilder;
import ninja.vextil.iconexposer.utilities.DrawerItemActionInterface;
import ninja.vextil.iconexposer.utilities.Preferences;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class Main extends ActionBarActivity {

    private Drawer.Result drawerResult;
    public String thaApp, thaHome, thaPreviews, thaApply, thaWalls, thaRequest, thaCredits;
    public String version, drawerVersion;
    private Preferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = new Preferences(Main.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thaApp = getResources().getString(R.string.app_name);
        thaHome = getResources().getString(R.string.section_one);
        thaPreviews = getResources().getString(R.string.section_two);
        thaApply = getResources().getString(R.string.section_three);
        thaWalls = getResources().getString(R.string.section_four);
        thaRequest = getResources().getString(R.string.section_five);
        thaCredits = getResources().getString(R.string.section_seven);

        drawerVersion = "v " + getResources().getString(R.string.current_version);

        AccountHeader.Result drawerHeader = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSelectionFirstLine(getResources().getString(R.string.app_long_name))
                .withSelectionSecondLine(drawerVersion)
                .withSavedInstance(savedInstanceState)
                .build();

        DrawerBuilder drawer = new DrawerBuilder();
        drawer.settings()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(drawerHeader)
                .withHeaderDivider(false)
                .withSavedInstance(savedInstanceState);

        // Home
        drawer.item(new PrimaryDrawerItem()
            .withName(thaHome)
            .withIcon(GoogleMaterial.Icon.gmd_home)
        ).opensFragment(Home.class);

        // Icons
        drawer.item(new PrimaryDrawerItem()
            .withName(thaPreviews)
            .withIcon(GoogleMaterial.Icon.gmd_palette)
        ).opensFragment(Previews.class);

        // Apply
        drawer.item(new PrimaryDrawerItem()
            .withName(thaApply)
            .withIcon(GoogleMaterial.Icon.gmd_loyalty)
        ).opensFragment(Apply.class);

        // Wallpapers
        drawer.item(new PrimaryDrawerItem()
            .withName(thaWalls)
            .withIcon(GoogleMaterial.Icon.gmd_landscape)
        ).opensFragment(Wallpapers.class);

        // Request Icon
        drawer.item(new PrimaryDrawerItem()
            .withName(thaRequest)
            .withIcon(GoogleMaterial.Icon.gmd_forum)
        ).opensFragment(Request.class);

        // Divider
        drawer.divider();

        // About
        drawer.item(new SecondaryDrawerItem()
            .withName(thaCredits)
        ).doesAction(new DrawerItemActionInterface()
        {
            @Override
            public void action(DrawerBuilder builder)
            {
                builder.openFragmentInstantly(new Libs.Builder()
                        .withFields(R.string.class.getFields())
                        .fragment()
                );
            }
        });

        drawerResult = drawer.build();

        if (savedInstanceState == null) {
            drawerResult.setSelection(0);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState = drawerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed()
    {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out this awesome icon pack by " + getResources().getString(R.string.iconpack_designer) + ".    Download Here: " + getResources().getString(R.string.play_store_link);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, (getResources().getString(R.string.share_title))));
                break;

            case R.id.sendemail:
                StringBuilder emailBuilder = new StringBuilder();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getResources().getString(R.string.email_id)));
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));

                emailBuilder.append("\n \n \nOS Version: " + System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")");
                emailBuilder.append("\nOS API Level: " + Build.VERSION.SDK_INT);
                emailBuilder.append("\nDevice: " + Build.DEVICE);
                emailBuilder.append("\nManufacturer: " + Build.MANUFACTURER);
                emailBuilder.append("\nModel (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")");
                PackageInfo appInfo = null;
                try {
                    appInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                emailBuilder.append("\nApp Version Name: " + appInfo.versionName);
                emailBuilder.append("\nApp Version Code: " + appInfo.versionCode);

                intent.putExtra(Intent.EXTRA_TEXT, emailBuilder.toString());
                startActivity(Intent.createChooser(intent, (getResources().getString(R.string.send_title))));
                break;

            case R.id.changelog:
                changelog();
                break;
        }
        return true;
    }

    private void changelog()
    {
        new MaterialDialog.Builder(this)
                .title(R.string.changelog_dialog_title)
                .adapter(new ChangelogAdapter(this, R.array.fullchangelog), null)
                .positiveText(R.string.nice)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        mPrefs.setNotFirstrun();
                    }
                })
                .show();
    }

    private void showChangelogDialog()
    {
        String launchinfo = getSharedPreferences("PrefsFile", MODE_PRIVATE).getString("version", "0");
        if (!launchinfo.equals(getResources().getString(R.string.current_version))) {
            changelog();
        }
        storeSharedPrefs();
    }

    protected void storeSharedPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("PrefsFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("version", getResources().getString(R.string.current_version));
        editor.apply();
    }

}