package rk.android.app.privacydashboard.activities.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.constant.Constants;
import rk.android.app.privacydashboard.manager.PreferenceManager;
import rk.android.app.privacydashboard.databinding.ActivityCalendarBinding;


public class CalendarActivity extends AppCompatActivity{
    Context context;

    PreferenceManager preferenceManager;
    ActivityCalendarBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = CalendarActivity.this;
        preferenceManager =  new PreferenceManager(getApplicationContext());
        getWindow().setBackgroundDrawable(null);


        setupToolbar();
        Bundle bundle;


    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.calender_title));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_back);
        toolbar.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });

    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }

}

