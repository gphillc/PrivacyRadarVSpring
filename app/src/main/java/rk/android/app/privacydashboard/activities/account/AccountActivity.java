package rk.android.app.privacydashboard.activities.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.activities.settings.SettingsActivity;
import rk.android.app.privacydashboard.activities.settings.excluded.ExcludeActivity;
import rk.android.app.privacydashboard.databinding.ActivityAccountBinding;
import rk.android.app.privacydashboard.manager.PreferenceManager;
import rk.android.app.privacydashboard.service.PrivacyService;
import rk.android.app.privacydashboard.util.Dialogs;
import rk.android.app.privacydashboard.util.Permissions;
import rk.android.app.privacydashboard.util.Utils;

public class AccountActivity extends AppCompatActivity {

    Context context;

    private FirebaseAuth mAuth;

    boolean switchPressed = false;

    PreferenceManager preferenceManager;
    ActivityAccountBinding binding;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();


        context = AccountActivity.this;
        preferenceManager =  new PreferenceManager(getApplicationContext());
        getWindow().setBackgroundDrawable(null);
        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();


        setupToolbar();
        initOnClickListeners();
        initValues();

        if(mAuth.getCurrentUser()  != null) {
            binding.accountLogin.setVisibility(View.GONE);
        }
        else{
            binding.accountSignOut.setVisibility(View.GONE);
        }


    }

    private void initValues(){
        binding.settingsAccessibility.setSwitchState(Permissions.accessibilityPermission(context, PrivacyService.class));
        binding.settingsLocation.setSwitchState(Permissions.checkLocation(context));
    }

    private void initOnClickListeners(){
        binding.accountLogin.setOnClickListener (view -> startActivity(new Intent(context, LoginActivity.class),bundle));
        binding.settingsDeleteLogs.setOnClickListener(view -> Dialogs.deleteLogs(getApplication(),context, getLayoutInflater(),
                getString(R.string.delete_logs_title2),getString(R.string.delete_logs_info2),null));
        binding.settingsExcluded.setOnClickListener(view -> {
            Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            Intent i = new Intent(context, ExcludeActivity.class);
            startActivity(i, bundle);
        });

        binding.settingsAccessibility.setOnSwitchListener((compoundButton, b) -> {
            if (!switchPressed) {
                if (Permissions.accessibilityPermission(context, PrivacyService.class)) {
                    Toast.makeText(context, getString(R.string.settings_accessibility_off), Toast.LENGTH_SHORT).cancel();
                } else {
                    Toast.makeText(context, getString(R.string.settings_accessibility_on), Toast.LENGTH_SHORT).cancel();
                }
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
                switchPressed = true;
            }
        });
        binding.settingsAccessibility.setOnClickListener(view -> binding.settingsAccessibility.performSwitchClick());

        binding.settingsLocation.setOnSwitchListener((compoundButton, b) -> {
            if (!switchPressed) {
                askPermission();
            }
        });
        binding.settingsLocation.setOnClickListener(view -> binding.settingsLocation.performSwitchClick());
        binding.settingsTheme.setOnClickListener(view -> Dialogs.showThemeDialog(context,getLayoutInflater(),preferenceManager, SettingsActivity.class));
        binding.settingsLimitation.setOnClickListener(view -> Dialogs.showHelpDialog(context, getLayoutInflater()));
    }

    private void askPermission() {
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }else {
            Toast.makeText(context, getString(R.string.settings_location_off), Toast.LENGTH_SHORT).cancel();
            switchPressed = true;
        }
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.settings_title));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_back);
        toolbar.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED,null);
            finish();
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }
}