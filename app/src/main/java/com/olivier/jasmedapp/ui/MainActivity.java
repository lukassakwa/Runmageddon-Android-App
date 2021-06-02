package com.olivier.jasmedapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.MainActivityContract;
import com.olivier.jasmedapp.model.Event;
import com.olivier.jasmedapp.presenter.MainActivityPresenter;
import com.olivier.jasmedapp.ui.fragments.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityPresenter mMainActivityPresenter;

    private Intent mLoginActivityIntent;

    private DrawerLayout drawerLayout;
    private NavigationView mNavigationView;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init drawerlayout in appbar
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle
                = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mLoginActivityIntent = new Intent(this, LoginActivity.class);
        //init presenter
        mMainActivityPresenter = new MainActivityPresenter();
        mMainActivityPresenter.attach(this);

        //init fragment
        mFragmentManager = getSupportFragmentManager();

        //get user events from home and event fragment
        getSupportFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle bundle) {
                ArrayList<Event> result = (ArrayList<Event>) bundle.getSerializable("userEvents");
                mMainActivityPresenter.setUserEvents(result);
            }
        });

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Bundle bundle = new Bundle();

                switch (id){

                    case R.id.home:
                        //todo:: if empty
                        //todo:: to MVP
                        //todo:: work on that

                        if(mMainActivityPresenter.getUserEvents().isEmpty()){
                            mFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container_view, EmptyHomeFragment.class, null)
                                    .commit();
                        }else{

                            HomeFragment homeFragment = new HomeFragment();
                            bundle.putSerializable("userEvents", mMainActivityPresenter.getUserEvents());
                            homeFragment.setArguments(bundle);

                            mFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container_view, homeFragment, null)
                                    .commit();

                        }

                        break;
                    case R.id.events:
                        //todo:: to MVP
                        //todo:: work on that
                        EventsFragment eventsFragment = new EventsFragment();
                        bundle.putSerializable("userEvents", mMainActivityPresenter.getUserEvents());
                        eventsFragment.setArguments(bundle);

                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, eventsFragment, null)
                                .commit();

                        //todo:: event fragment
                        break;
                    case R.id.gallery:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, GalleryFragment.class, null)
                                .commit();
                        break;
                    case R.id.aboutUs:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, AboutUsFragment.class, null)
                                .commit();
                        break;
                    case R.id.contact:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, ContactFragment.class, null)
                                .commit();
                        break;
                    case R.id.regulamin:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, RegulaminFragment.class, null)
                                .commit();
                        break;
                    case R.id.profile:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, UserProfileFragment.class, null)
                                .commit();

                        break;
                    case R.id.userSettings:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, UserSettingsFragment.class, null)
                                .commit();
                        break;
                    case R.id.signOut:
                        mMainActivityPresenter.signOut();
                        mLoginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mLoginActivityIntent);
                        finish();
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.drawable, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


    @Override
    protected void onPause() {
        super.onPause();

        mMainActivityPresenter.setUserEventsDatabase();
    }

    @Override
    public void setMainFragment(ArrayList<Event> events) {

        if(events.isEmpty()){
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, EmptyHomeFragment.class, null)
                    .commit();
        }else{
            Bundle bundle = new Bundle();
            HomeFragment homeFragment = new HomeFragment();
            bundle.putSerializable("userEvents", events);
            homeFragment.setArguments(bundle);

            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, homeFragment, null)
                    .commit();
        }
    }
}