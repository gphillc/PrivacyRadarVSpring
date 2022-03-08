package rk.android.app.privacydashboard.activities.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.activities.main.MainActivity;
import rk.android.app.privacydashboard.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity{

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter ;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim ;
    ConstraintLayout intro;
    DotsIndicator dotsIndicator;
    int lastPage = 0;

    ActivityIntroBinding binding;




    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intro = findViewById(R.id.intro);

        AnimationDrawable animationDrawable = (AnimationDrawable) intro.getBackground();
        animationDrawable.setEnterFadeDuration(0);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class );
            startActivity(mainActivity);
            finish();


        }




        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        tabIndicator = findViewById(R.id.tab_indicator);

        final List<ScreenItem> list = new ArrayList<>();
        list.add(new ScreenItem("Personal Data Statistics","",R.drawable.notification_cm));
        list.add(new ScreenItem("Real Time Permission Tracking","As an app uses YOUR private permissions we track and notify you with real time notification icons",R.drawable.notification_cm));
        list.add(new ScreenItem("sadawd", "", R.drawable.notification_cm));

        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,list);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        dotsIndicator = findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < list.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == list.size()-1) { // when we rech to the last screen
                    lastPage = 1;
                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();

                }



            }
        });
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == list.size()-1) {

                    loaddLastScreen();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get Started button click listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();



            }
        });

        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }


    }

    private void savePrefsData(){
        SharedPreferences pref = getApplication().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    private void loaddLastScreen() {

        btnGetStarted.setVisibility(View.VISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);



    }

}
