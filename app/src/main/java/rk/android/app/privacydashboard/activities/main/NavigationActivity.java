package rk.android.app.privacydashboard.activities.main;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.Calendar;

import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.activities.log.database.LogsRepository;
import rk.android.app.privacydashboard.databinding.ActivityMainBinding;
import rk.android.app.privacydashboard.databinding.ActivityNavigationBinding;
import rk.android.app.privacydashboard.manager.PreferenceManager;
import rk.android.app.privacydashboard.util.Permissions;
import rk.android.app.privacydashboard.util.Utils;

public class NavigationActivity extends AppCompatActivity {

    Intent serviceIntent;
    Context context;
    PreferenceManager preferenceManager;
    ActivityNavigationBinding binding;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = NavigationActivity.this;
        preferenceManager =  new PreferenceManager(getApplicationContext());
        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
        getWindow().setBackgroundDrawable(null);

        Permissions.checkAutoStartRequirement(context, getLayoutInflater(), preferenceManager);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);
}
    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){

                    return true;
                }
            };
}
