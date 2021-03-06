package com.joss.voodootvdb.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.joss.voodootvdb.DataStore;
import com.joss.voodootvdb.R;
import com.joss.voodootvdb.api.models.Settings.Settings;
import com.joss.voodootvdb.fragments.AboutFragment;
import com.joss.voodootvdb.fragments.FavoritesFragment;
import com.joss.voodootvdb.fragments.HomeFragment;
import com.joss.voodootvdb.fragments.NextFragment;
import com.joss.voodootvdb.fragments.SettingsFragment;
import com.joss.voodootvdb.fragments.TimelineFragment;
import com.joss.voodootvdb.interfaces.ToolbarListener;
import com.joss.voodootvdb.model.DrawerModel;
import com.joss.voodootvdb.views.DrawerUserHeader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseDrawerActivity implements ToolbarListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String CURRENT_FRAGMENT_ID = "current_fragment_id";

    int currentId;
    Fragment currentFrag;
    boolean pendingFragmentSwap = false;

    SearchView searchView;
    DrawerUserHeader userHeader;

    @Override
    View getDrawerHeader() {
        userHeader = new DrawerUserHeader(this);
        return userHeader;
    }

    @Override
    List<DrawerModel> getDrawerItems() {
        List<DrawerModel> items = new ArrayList<>();
        items.add(new DrawerModel(R.id.drawer_home, getString(R.string.drawer_home), R.drawable.selector_ic_home));
        items.add(new DrawerModel(R.id.drawer_favorites, getString(R.string.drawer_favorites), R.drawable.selector_ic_favorite));
        items.add(new DrawerModel(R.id.drawer_timeline, getString(R.string.drawer_timeline), R.drawable.selector_ic_timeline));
        items.add(new DrawerModel(R.id.drawer_next, getString(R.string.drawer_next), R.drawable.selector_ic_next));
        items.add(new DrawerModel(R.id.action_settings, getString(R.string.action_settings), R.drawable.selector_ic_settings));
        items.add(new DrawerModel(R.id.drawer_about, getString(R.string.drawer_about), R.drawable.selector_ic_about));
        items.add(new DrawerModel(R.id.drawer_contact, getString(R.string.drawer_contact), R.drawable.selector_ic_contact));
        return items;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            selectFragment(R.id.drawer_home);
            swapFragments();
        }else{
            currentId = savedInstanceState.getInt(CURRENT_FRAGMENT_ID);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Settings settings = DataStore.getUserSettings(this);
        if(settings != null)
            userHeader.setContent(settings);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DrawerModel drawerModel = ((DrawerModel) adapter.getItem(position - 1)); // Taking into account the Header Item
        if(drawerModel.id != currentId){
            selectFragment(drawerModel.id);
            swapFragments();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Search Menu Item
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem search = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(!searchView.isIconified()){
            searchView.setIconified(true);
        }else if(currentId != R.id.drawer_home){
            selectFragment(R.id.drawer_home);
            swapFragments();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(CURRENT_FRAGMENT_ID, currentId);
    }

    private void selectFragment(int drawerId) {
        Fragment fragment = new HomeFragment();
        int id = -1;

        switch (drawerId){
            case R.id.drawer_home:
                id = R.id.drawer_home;
                fragment = new HomeFragment();
                break;

            case R.id.drawer_favorites:
                id = R.id.drawer_favorites;
                fragment = new FavoritesFragment();
                break;

            case R.id.drawer_timeline:
                id = R.id.drawer_timeline;
                fragment = new TimelineFragment();
                break;

            case R.id.drawer_next:
                id = R.id.drawer_next;
                fragment = new NextFragment();
                break;

            case R.id.drawer_settings:
                id = R.id.drawer_settings;
                fragment = new SettingsFragment();
                break;

            case R.id.drawer_about:
                id = R.id.drawer_about;
                fragment = new AboutFragment();
                break;

            case R.id.drawer_contact:
                id = currentId;
                // TODO Open email thing
                Toast.makeText(this, "Open email thing", Toast.LENGTH_SHORT).show();
                break;
        }

        if(id != currentId){
            this.currentId = id;
            this.currentFrag = fragment;
            this.pendingFragmentSwap = true;
        }else{
            drawerListView.setItemChecked(adapter.getPositionAndSelect(currentId) + 1, true); // Adding 1 for the Header
        }

        closeDrawer();
    }

    private void swapFragments() {
        if(pendingFragmentSwap){
            this.pendingFragmentSwap = false;
            drawerListView.setItemChecked(adapter.getPositionAndSelect(currentId) + 1, true); // Adding 1 for the header
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_from_right, 0);
            ft.replace(contentView.getId(), currentFrag);
            ft.commit();
        }
    }

    @Override
    public void onSetToolbarTitles(String title, String subtitle) {
        setTitlesAndSave(title, subtitle);
    }
}
