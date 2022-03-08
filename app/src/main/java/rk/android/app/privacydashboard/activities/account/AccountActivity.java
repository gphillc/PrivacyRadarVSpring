package rk.android.app.privacydashboard.activities.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.databinding.ActivityAccountBinding;
import rk.android.app.privacydashboard.manager.PreferenceManager;

public class AccountActivity extends AppCompatActivity {

    Context context;

    private FirebaseAuth mAuth;

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

        if(mAuth.getCurrentUser()  != null) {
            binding.accountLogin.setVisibility(View.GONE);
        }
        else{
            binding.accountSignOut.setVisibility(View.GONE);
        }


    }

    private void initOnClickListeners(){
        binding.accountLogin.setOnClickListener (view -> startActivity(new Intent(context, LoginActivity.class),bundle));
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