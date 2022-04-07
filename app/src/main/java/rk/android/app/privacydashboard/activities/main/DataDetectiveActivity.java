package rk.android.app.privacydashboard.activities.main;

import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.databinding.ActivityDataDetectiveBinding;
import rk.android.app.privacydashboard.manager.PreferenceManager;


public class DataDetectiveActivity extends AppCompatActivity {
    Context context;

    PreferenceManager preferenceManager;
    ActivityDataDetectiveBinding binding;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataDetectiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        context = DataDetectiveActivity.this;
        preferenceManager = new PreferenceManager(getApplicationContext());
        getWindow().setBackgroundDrawable(null);
        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();


        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.data_detective));
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
    }
}