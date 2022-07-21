package com.example.youtubeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.youtubeapp.fragment.FragmentBtSheetUser;
import com.example.youtubeapp.fragment.FragmentChannel;
import com.example.youtubeapp.fragment.FragmentExplore;
import com.example.youtubeapp.fragment.FragmentHome;
import com.example.youtubeapp.fragment.FragmentLibrary;
import com.example.youtubeapp.fragment.FragmentNotify;
import com.example.youtubeapp.fragment.FragmentSubs;
import com.example.youtubeapp.fragment.FragmentValueSearch;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
public class MainActivity extends AppCompatActivity implements InterfaceDefaultValue {

    private ImageView ivEndNavHome, ivEndNavExplore,
            ivEndNavSubscriptions, ivEndNavNotification,
            ivEndNavLibrary, ivSearch, ivUser, ivDataTrans;
    public FragmentManager fragmentManager = getSupportFragmentManager();
    public FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        Intent getData = getIntent();
        String valueSearch = getData.getStringExtra(VALUE_SEARCH);
        String idChannel = getData.getStringExtra(VALUE_CHANNEL_ID);

        if (valueSearch != null){
//            Log.d("AHIHIHIHIHIHIIHIHI", valueSearch+"");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //ADD FRAGMENT
            FragmentValueSearch fragmentValueSearch = new FragmentValueSearch();
            Bundle bundle = new Bundle();
            bundle.putString(VALUE_SEARCH, valueSearch);
            fragmentValueSearch.setArguments(bundle);
            fragmentTransaction.add(R.id.cl_contains_search,
                    fragmentValueSearch, FRAGMENT_SEARCH);
            fragmentTransaction.addToBackStack(FRAGMENT_SEARCH);
            fragmentTransaction.commit();
        }
        else if (idChannel != null){
            Toast.makeText(this, idChannel+"", Toast.LENGTH_SHORT).show();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //ADD FRAGMENT
            FragmentChannel fragmentChannel = new FragmentChannel(idChannel);
            fragmentTransaction.add(R.id.cl_contains_search,
                    fragmentChannel, FRAGMENT_SEARCH);
            fragmentTransaction.addToBackStack(FRAGMENT_CHANNEL);
            fragmentTransaction.commit();
        }

        else{
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //ADD FRAGMENT
            FragmentHome fragmentHome = new FragmentHome();
            fragmentTransaction.add(R.id.cl_contains_fragment,
                    fragmentHome, FRAGMENT_HOME);
            fragmentTransaction.addToBackStack(FRAGMENT_HOME);
            fragmentTransaction.commit();
        }

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOnClickUser();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSearch = new Intent(MainActivity.this,
                        ActivitySearchVideo.class);
                startActivity(intentToSearch);
            }
        });

        ivDataTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void openOnClickUser(){
        FragmentBtSheetUser fragmentBtSheetUser = new FragmentBtSheetUser();
        fragmentBtSheetUser.show(getSupportFragmentManager(), fragmentBtSheetUser.getTag());
    }

    public void onClickHome(@NonNull View view) {
       fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.iv_end_bar_home:
                removeFragment();
                setDisplayEndNavOff();
                ivEndNavHome.setImageResource(R.drawable.ic_home_on);
//                REMOVE IF SEARCH DISPLAY
//                manageFragment(FRAGMENT_SEARCH);
//                Toast.makeText(this, getSupportFragmentManager().getBackStackEntryCount()+"", Toast.LENGTH_SHORT).show();
//                Fragment fragmentChannel = getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHANNEL);
//                if (fragmentChannel == null ){
//                    fragmentManager.popBackStack(FRAGMENT_HOME, 0);
//                }
//                else{
//                    fragmentTransaction.remove(fragmentChannel);
//                    FragmentHome fragmentHome = new FragmentHome();
//                    fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentHome);
//                }
//                if (fragmentManager.getBackStackEntryCount()>0){
//                    fragmentManager.popBackStack();
//                }

                FragmentHome fragmentHome = new FragmentHome();
                fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentHome, FRAGMENT_HOME);
                fragmentTransaction.addToBackStack(FRAGMENT_HOME);

                Toast.makeText(this, "Fragment Ex", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_end_bar_explore:
                removeFragment();
                setDisplayEndNavOff();
                ivEndNavExplore.setImageResource(R.drawable.ic_explore_on);
                FragmentExplore fragmentExplore = new FragmentExplore();
                fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentExplore, FRAGMENT_EXPLORE);
                fragmentTransaction.addToBackStack(FRAGMENT_EXPLORE);
                Toast.makeText(this, "Fragment Ex", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_end_bar_subscriptions:
                removeFragment();
                setDisplayEndNavOff();
                ivEndNavSubscriptions.setImageResource(R.drawable.ic_subscrip_on);
                FragmentSubs fragmentSubs = new FragmentSubs();
                fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentSubs, FRAGMENT_SUBSCRIPTION);
                fragmentTransaction.addToBackStack(FRAGMENT_SUBSCRIPTION);
                break;
            case R.id.iv_end_bar_notifications:
                removeFragment();
                setDisplayEndNavOff();
                ivEndNavNotification.setImageResource(R.drawable.ic_notifitcation_onn);
                FragmentNotify fragmentNotify = new FragmentNotify();
                fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentNotify, FRAGMENT_NOTIFICATION);
                fragmentTransaction.addToBackStack(FRAGMENT_NOTIFICATION);
                break;
            case R.id.iv_end_bar_library:
                removeFragment();
                setDisplayEndNavOff();
                ivEndNavLibrary.setImageResource(R.drawable.ic_library_on);
                FragmentLibrary fragmentLibrary = new FragmentLibrary();
                fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentLibrary, FRAGMENT_LIBRARY);
                fragmentTransaction.addToBackStack(FRAGMENT_LIBRARY);
                break;
        }
        fragmentTransaction.commit();
    }

    public void removeFragment(){
        Fragment fragmentChannel = getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHANNEL);
        Fragment fragmentValueSearch = getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH);
        if(fragmentChannel != null){
            getSupportFragmentManager().beginTransaction().remove(fragmentChannel).commit();
        }
        if (fragmentValueSearch != null){
            getSupportFragmentManager().beginTransaction().remove(fragmentValueSearch).commit();
        }
    }

//    public void manageFragment(String TAG_FRAGMENT){
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
//        if(fragment != null)
//            getSupportFragmentManager().beginTransaction().add(fragment, FRAGMENT_HOME).commit();
//    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount()>0){
            removeFragment();
            setDisplayEndNavOff();
            fragmentTransaction = fragmentManager.beginTransaction();
            ivEndNavHome.setImageResource(R.drawable.ic_home_on);
            FragmentHome fragmentHome = new FragmentHome();
            fragmentTransaction.replace(R.id.cl_contains_fragment, fragmentHome, FRAGMENT_HOME);
            fragmentTransaction.addToBackStack(FRAGMENT_HOME);
            Toast.makeText(this, "Fragment Ex", Toast.LENGTH_SHORT).show();
            fragmentTransaction.commit();
        }
        else{
            super.onBackPressed();
        }
    }

    public void setDisplayEndNavOff() {
        ivEndNavExplore.setImageResource(R.drawable.ic_explore_off);
        ivEndNavHome.setImageResource(R.drawable.ic_home_off);
        ivEndNavSubscriptions.setImageResource(R.drawable.ic_subscrip_off);
        ivEndNavLibrary.setImageResource(R.drawable.ic_library_off);
        ivEndNavNotification.setImageResource(R.drawable.ic_notification_off);
    }

    @SuppressLint("WrongViewCast")
    public void mapping() {
        ivDataTrans = findViewById(R.id.iv_top_bar_connect_tv);
        ivUser = findViewById(R.id.iv_top_bar_user);
        ivSearch = findViewById(R.id.iv_top_bar_search);
        ivEndNavExplore = findViewById(R.id.iv_end_bar_explore);
        ivEndNavHome = findViewById(R.id.iv_end_bar_home);
        ivEndNavSubscriptions = findViewById(R.id.iv_end_bar_subscriptions);
        ivEndNavLibrary = findViewById(R.id.iv_end_bar_library);
        ivEndNavNotification = findViewById(R.id.iv_end_bar_notifications);
    }

}