package com.example.youtubeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
import com.example.youtubeapp.interfacee.InterfaceSenData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class MainActivity extends AppCompatActivity implements
        YouTubePlayer.OnInitializedListener,InterfaceDefaultValue {

    private ImageView ivEndNavHome, ivEndNavExplore,
            ivEndNavSubscriptions, ivEndNavNotification,
            ivEndNavLibrary, ivSearch, ivUser, ivDataTrans;

    private YouTubePlayer ypPlayItemClick;
    public String id = "";

    public FragmentManager fragmentManager = getSupportFragmentManager();
    public static FragmentTransaction fragmentTransaction;

    public FrameLayout clChannelSearch;
    private YouTubePlayerFragment youTubePlayerFragment;
    public static BottomSheetBehavior btSheetPlay;

    public static ConstraintLayout clPlay;
    public InterfaceSenData interfaceSenData;

    private boolean isHomeDisplay = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        Intent intent = new Intent(this, StartApp.class);
        startActivity(intent)*/;

        mapping();

        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.fm_contains_player_views);

        youTubePlayerFragment.initialize(API_KEY, this);

        Intent getData = getIntent();
        String valueSearch = getData.getStringExtra(VALUE_SEARCH);
        String idChannel = getData.getStringExtra(VALUE_CHANNEL_ID);

        FragmentTransaction fragmentTransaction;

        if (valueSearch != null) {
//            Log.d("AHIHIHIHIHIHIIHIHI", valueSearch+"");
            fragmentTransaction = fragmentManager.beginTransaction();
            //ADD FRAGMENT
            FragmentValueSearch fragmentValueSearch = new FragmentValueSearch();
            Bundle bundle = new Bundle();
            bundle.putString(VALUE_SEARCH, valueSearch);
            fragmentValueSearch.setArguments(bundle);
            fragmentTransaction.add(R.id.cl_contains_search,
                    fragmentValueSearch, FRAGMENT_SEARCH);
            fragmentTransaction.addToBackStack(FRAGMENT_SEARCH);

            fragmentTransaction.commit();
        } else if (idChannel != null) {
            Toast.makeText(this, idChannel + "", Toast.LENGTH_SHORT).show();
            fragmentTransaction = fragmentManager.beginTransaction();
            //ADD FRAGMENT
            FragmentChannel fragmentChannel = new FragmentChannel(idChannel, this);
            fragmentTransaction.add(R.id.cl_contains_search,
                    fragmentChannel, FRAGMENT_CHANNEL);
            fragmentTransaction.addToBackStack(FRAGMENT_CHANNEL);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction = fragmentManager.beginTransaction();
            //ADD FRAGMENT
            FragmentHome fragmentHome = new FragmentHome();
            fragmentTransaction.replace(R.id.cl_contains_fragment,
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

    public void playVideo(String id){
        this.id = id;
        btSheetPlay  = BottomSheetBehavior.from(clPlay);
        if (btSheetPlay.getState() != BottomSheetBehavior.STATE_EXPANDED){
            btSheetPlay.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        else{
            btSheetPlay.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

    }

    private void openOnClickUser() {
        FragmentBtSheetUser fragmentBtSheetUser = new FragmentBtSheetUser();
        fragmentBtSheetUser.show(getSupportFragmentManager(), fragmentBtSheetUser.getTag());
    }

    public void onClickHome(@NonNull View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.iv_end_bar_home:
                setDisplayEndNavOff();
                ivEndNavHome.setImageResource(R.drawable.ic_home_on);
                removeNav();

                Fragment fragmentSearch = getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH);
                Fragment fragmentChannel = getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHANNEL);
                if (fragmentChannel != null){
                    Log.d("fragmentChannel != null", fragmentChannel+"");
                    if(clChannelSearch.getVisibility() == View.GONE){
                        Log.d("fragment channel "," clChannelSearch.getVisibility() == View.VISIBLE");
                        clChannelSearch.setVisibility(View.VISIBLE);
                    }
                    else{
                        Log.d("REMOVE: ", "ragmentManager.beginTransaction().remove(fragmentChannel);");
                        getSupportFragmentManager().beginTransaction().remove(fragmentChannel).commit();
                        Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME);
                        if (fragmentHome == null){
                            fragmentTransaction = fragmentManager.beginTransaction();
                            //ADD FRAGMENT
                            fragmentHome = new FragmentHome();
                            fragmentTransaction.replace(R.id.cl_contains_fragment,
                                    fragmentHome, FRAGMENT_HOME);
                            fragmentTransaction.addToBackStack(FRAGMENT_HOME);
                            Log.d("CREATE: ", "FRAGMENT HOME");
                        }
                        else{
                            fragmentManager.popBackStack(FRAGMENT_HOME, 0);
                            Log.d("POP BACK: ", "0");
                        }
                    }
                }

                else if(fragmentSearch != null){
                    Log.d("fragmentChannel != null", fragmentSearch+"");
                    if (!isHomeDisplay){
                        Log.d("!isHomeDisplay", fragmentSearch+"");
                        fragmentManager.beginTransaction().remove(fragmentSearch).commit();
                        clChannelSearch.setVisibility(View.GONE);
                        Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME);
                        if (fragmentHome != null){
                            fragmentManager.popBackStack(FRAGMENT_HOME, 0);
                        }
                        else{
                            fragmentTransaction = fragmentManager.beginTransaction();
                            //ADD FRAGMENT
                            fragmentHome = new FragmentHome();
                            fragmentTransaction.replace(R.id.cl_contains_fragment,
                                    fragmentHome, FRAGMENT_HOME);
                            fragmentTransaction.addToBackStack(FRAGMENT_HOME);
                            Log.d("ADD BACK: ", "FRAGMENT HOME");
                        }
                    }
                    else if(clChannelSearch.getVisibility() == View.GONE){
                        Log.d("fragment channel "," clChannelSearch.getVisibility() == View.GONE");
                        clChannelSearch.setVisibility(View.VISIBLE);
                        isHomeDisplay = false;
                    }
                    else{
                        Log.d("REMOVE: ", "ragmentManager.beginTransaction().remove(fragmentChannel);");
                        getSupportFragmentManager().beginTransaction().remove(fragmentSearch).commit();
                        Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME);
                        if (fragmentHome == null){
                            fragmentTransaction = fragmentManager.beginTransaction();
                            //ADD FRAGMENT
                            fragmentHome = new FragmentHome();
                            fragmentTransaction.replace(R.id.cl_contains_fragment,
                                    fragmentHome, FRAGMENT_HOME);
                            fragmentTransaction.addToBackStack(FRAGMENT_HOME);
                        }
                        fragmentManager.popBackStack(FRAGMENT_HOME, 0);
                    }
                }
                else{
                    Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME);
                    if (fragmentHome == null){
                        fragmentTransaction = fragmentManager.beginTransaction();
                        //ADD FRAGMENT
                        fragmentHome = new FragmentHome();
                        fragmentTransaction.replace(R.id.cl_contains_fragment,
                                fragmentHome, FRAGMENT_HOME);
                        fragmentTransaction.addToBackStack(FRAGMENT_HOME);
                        Log.d("ADD BACK: ", "FRAGMENT HOME");
                    }
                    fragmentManager.popBackStack(FRAGMENT_HOME, 0);
                    Log.d("POP BACK: ", "1");
                }

                break;

            case R.id.iv_end_bar_explore:
                removeNav();
                temporaryFragment();
                setDisplayEndNavOff();
                ivEndNavExplore.setImageResource(R.drawable.ic_explore_on);
                FragmentExplore fragmentExplore = new FragmentExplore();
                fragmentTransaction.add(R.id.cl_contains_search, fragmentExplore, FRAGMENT_EXPLORE);
                fragmentTransaction.addToBackStack(FRAGMENT_EXPLORE);
                Toast.makeText(this, "Fragment Ex", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_end_bar_subscriptions:
                removeNav();
                temporaryFragment();
                setDisplayEndNavOff();
                ivEndNavSubscriptions.setImageResource(R.drawable.ic_subscrip_on);
                FragmentSubs fragmentSubs = new FragmentSubs();
                fragmentTransaction.add(R.id.cl_contains_fragment, fragmentSubs, FRAGMENT_SUBSCRIPTION);
                fragmentTransaction.addToBackStack(FRAGMENT_SUBSCRIPTION);
                break;

            case R.id.iv_end_bar_notifications:
                removeNav();
                temporaryFragment();
                setDisplayEndNavOff();
                ivEndNavNotification.setImageResource(R.drawable.ic_notifitcation_onn);
                FragmentNotify fragmentNotify = new FragmentNotify();
                fragmentTransaction.add(R.id.cl_contains_fragment, fragmentNotify, FRAGMENT_NOTIFICATION);
                fragmentTransaction.addToBackStack(FRAGMENT_NOTIFICATION);
                break;

            case R.id.iv_end_bar_library:
                removeNav();
                temporaryFragment();
                setDisplayEndNavOff();
                ivEndNavLibrary.setImageResource(R.drawable.ic_library_on);

                FragmentLibrary fragmentLibrary = (FragmentLibrary) fragmentManager.findFragmentByTag(FRAGMENT_LIBRARY);

                if (fragmentLibrary == null){
                    fragmentLibrary = new FragmentLibrary();
                    fragmentTransaction.add(R.id.cl_contains_fragment, fragmentLibrary, FRAGMENT_LIBRARY);
                    fragmentTransaction.addToBackStack(FRAGMENT_LIBRARY);
                    Log.d(FRAGMENT_LIBRARY, "NULL");
                }

                else{
                    fragmentManager.popBackStack(FRAGMENT_LIBRARY, 0);
                    Log.d(FRAGMENT_LIBRARY, "NOT NULL");
                }
                break;
        }
        fragmentTransaction.commit();
    }

    public void temporaryFragment(){
        Fragment fragmentChannel = fragmentManager.findFragmentByTag(FRAGMENT_CHANNEL);
        Fragment fragmentSearch = fragmentManager.findFragmentByTag(FRAGMENT_SEARCH);
        if (fragmentChannel != null || fragmentSearch != null){
            if (clChannelSearch.getVisibility() == View.VISIBLE){
                clChannelSearch.setVisibility(View.GONE);
            }
        }
    }

    public void removeNav(){
        FragmentSubs fragmentSubs = (FragmentSubs) fragmentManager.findFragmentByTag(FRAGMENT_SUBSCRIPTION);
        FragmentLibrary fragmentLibrary = (FragmentLibrary) fragmentManager.findFragmentByTag(FRAGMENT_LIBRARY);
        FragmentNotify fragmentNotify = (FragmentNotify) fragmentManager.findFragmentByTag(FRAGMENT_NOTIFICATION);

        if (fragmentSubs != null){
            getSupportFragmentManager().beginTransaction().remove(fragmentSubs).commit();
        }

        if (fragmentLibrary != null){
            getSupportFragmentManager().beginTransaction().remove(fragmentLibrary).commit();
        }

        if (fragmentNotify != null){
            getSupportFragmentManager().beginTransaction().remove(fragmentNotify).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME);
        Fragment fragmentSearch = getSupportFragmentManager().findFragmentByTag(FRAGMENT_SEARCH);
        Log.d("", fragmentHome+"");

        if (fragmentSearch != null){
            finish();
        }

        else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            setDisplayEndNavOff();
            ivEndNavHome.setImageResource(R.drawable.ic_home_on);
        }

        Log.d("", fragmentHome+"");
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
        clPlay = findViewById(R.id.cl_contains_play_video);
        clChannelSearch = findViewById(R.id.cl_contains_search);
        ivDataTrans = findViewById(R.id.iv_top_bar_connect_tv);
        ivUser = findViewById(R.id.iv_top_bar_user);
        ivSearch = findViewById(R.id.iv_top_bar_search);
        ivEndNavExplore = findViewById(R.id.iv_end_bar_explore);
        ivEndNavHome = findViewById(R.id.iv_end_bar_home);
        ivEndNavSubscriptions = findViewById(R.id.iv_end_bar_subscriptions);
        ivEndNavLibrary = findViewById(R.id.iv_end_bar_library);
        ivEndNavNotification = findViewById(R.id.iv_end_bar_notifications);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            ypPlayItemClick = youTubePlayer;
            ypPlayItemClick.loadVideo(id);
            Log.d("ID VIDEO: ", id);
        }
        Log.d("ID VIDEO: ", id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("FALSE PLAY YOUTUBE: ", youTubeInitializationResult+"");
    }

}