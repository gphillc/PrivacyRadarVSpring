package rk.android.app.privacydashboard.activities.main;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rk.android.app.privacydashboard.R;
import rk.android.app.privacydashboard.activities.account.AccountActivity;
import rk.android.app.privacydashboard.activities.log.database.LogsRepository;
import rk.android.app.privacydashboard.constant.Constants;
import rk.android.app.privacydashboard.databinding.ActivityMainBinding;
import rk.android.app.privacydashboard.manager.PreferenceManager;
import rk.android.app.privacydashboard.service.PrivacyService;
import rk.android.app.privacydashboard.util.Dialogs;
import rk.android.app.privacydashboard.util.Permissions;
import rk.android.app.privacydashboard.util.PieCharts;
import rk.android.app.privacydashboard.util.Utils;

public class MainActivity extends AppCompatActivity {

    Intent serviceIntent;
    Context context;
    PreferenceManager preferenceManager;
    ActivityMainBinding binding;

    LogsRepository logsRepository;

    TextView text_view_date;
    CalendarView expandableCalendarView;

    String date = "Jan 01, 2022";
    String dateWeek = "01/01/2021-01/07/2021";

    Bundle bundle;
    Intent incomingIntent = getIntent();
    int score = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = MainActivity.this;
        preferenceManager = new PreferenceManager(getApplicationContext());
        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
        getWindow().setBackgroundDrawable(null);
        logsRepository = new LogsRepository(getApplication());


        setupToolbar();
        initUI();
        initDate();
        initOnClickListener();
        //double Sum_Score(int l, int m, int c);
        initValues();
        initPieChart();


        Permissions.checkAutoStartRequirement(context, getLayoutInflater(), preferenceManager);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);



    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_user));
        toolbar.setNavigationIcon(R.drawable.icon_user);
        toolbar.setNavigationOnClickListener(view -> startActivity(new Intent(context, AccountActivity.class), bundle));

        binding.scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (binding.scrollView.canScrollVertically(Constants.SCROLL_DIRECTION_UP)) {
                toolbar.setElevation(Constants.TOOLBAR_SCROLL_ELEVATION);
            } else {
                toolbar.setElevation(Constants.TOOLBAR_DEFAULT_ELEVATION);
            }
        });
    }

    private void initUI() {

        Dialogs.showWhatsNewDialog(context, getLayoutInflater(), preferenceManager, false);

    }


    private void initPieChart() {
        binding.pieChart.setDrawCenterText(true);
        binding.pieChart.setCenterText(String.valueOf(score));
        binding.pieChart.setCenterTextColor(Utils.getAttrColor(context, R.attr.colorIcon));
        binding.pieChart.setCenterTextTypeface(ResourcesCompat.getFont(this, R.font.medium));
        binding.pieChart.setCenterTextSize(50f);
        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setHoleColor(getColor(R.color.tan));
        binding.pieChart.setTransparentCircleAlpha(0);
        binding.pieChart.setHoleRadius(90f);
        binding.pieChart.setRotationAngle(0);
        binding.pieChart.setRotationEnabled(false);
        binding.pieChart.setHighlightPerTapEnabled(false);
        //chart.setOnChartValueSelectedListener(this);
        binding.pieChart.setEntryLabelColor(getColor(R.color.lightBackground));
        binding.pieChart.setEntryLabelTypeface(ResourcesCompat.getFont(this, R.font.medium));
        binding.pieChart.setEntryLabelTextSize(15f);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setEnabled(true);
        binding.pieChart.highlightValues(null);
        binding.pieChart.setExtraTopOffset(8f);
        binding.pieChart.setExtraBottomOffset(8f);
        binding.pieChart.setDrawRoundedSlices(true);

    }

    private String getString(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }


    private void initOnClickListener() {

        binding.buttonAccessSetting.setOnClickListener(view -> {
            startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"), bundle);
            Toast.makeText(context, getString(R.string.settings_accessibility_on), Toast.LENGTH_SHORT).show();
        });

        binding.buttonLocationSetting.setOnClickListener(view -> askPermission());

        binding.permissionLocation.setOnClickListener(view -> Utils.openHistoryActivity(context, Constants.PERMISSION_LOCATION));

        binding.permissionCamera.setOnClickListener(view -> Utils.openHistoryActivity(context, Constants.PERMISSION_CAMERA));

        binding.permissionMicrophone.setOnClickListener(view -> Utils.openHistoryActivity(context, Constants.PERMISSION_MICROPHONE));

        binding.pieChart.setOnClickListener(view -> startActivity(new Intent(context, CalendarActivity.class), bundle));

        binding.textViewDate.setOnClickListener(view -> startActivity(new Intent(context, CalendarActivity.class), bundle));

        binding.dataDetective.setOnClickListener(view -> startActivity(new Intent(context, DataDetectiveActivity.class), bundle));

    }

    private void initDate() {
        if(expandableCalendarView == null){
            date = Utils.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis());
        }
        else{
            date = incomingIntent.getStringExtra("date");
        }
    }
    //scoring algorithm
    int Sum_Score(int score, int l, int m, int c)
    {
        //double score =100;
        // int l=5, m=4, c=2;
        int l_counter_mod=0, l_counter_reg=0, m_counter_mod=0, m_counter_reg=0, c_counter_mod=0, c_counter_reg=0;

//location algorithm
        for (int j=0; j<=l; j++){
            if(l%3==0&&l!=0){
                l_counter_mod++;
            }
            else if(l%3!=0&&l!=0)
                l_counter_reg++;
        }
//microphone algorithm
        for (int j=0; j<=m; j++){
            if(m%3==0&&m!=0){
                m_counter_mod++;
            }
            else if(m%3!=0&&m!=0)
                m_counter_reg++;
        }
//camera algorithm
        for (int j=0; j<=c; j++){
            if(c%3==0&&c!=0){
                c_counter_mod++;
            }
            else if(c%3!=0&&c!=0)
                c_counter_reg++;
        }
//sum up score
        score = score-4*l_counter_mod-2*l_counter_reg-3*m_counter_mod-m_counter_reg-2*c_counter_mod-c_counter_reg;
        return score;
    }

    private void initValues() {
        score = 100;

//location worth 1.5 points unless multiple of 3, then double
        //mic worth 1 unless multiple of 3 then, double
        //cam worth 0.5 unless multiple of 3 then, double
        int c = logsRepository.getLogsCount(Constants.PERMISSION_CAMERA, date);
        int l = logsRepository.getLogsCount(Constants.PERMISSION_LOCATION, date);
        int m = logsRepository.getLogsCount(Constants.PERMISSION_MICROPHONE, date);

        score = (int) Sum_Score(score,l,m,c);


        List<Integer> logs = new ArrayList<>();
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        logs.add(logsRepository.getLogsCount(Constants.PERMISSION_LOCATION, date));
        logs.add(logsRepository.getLogsCount(Constants.PERMISSION_CAMERA, date));
        logs.add(logsRepository.getLogsCount(Constants.PERMISSION_MICROPHONE, date));

        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i) != 0) {
                entries.add(new PieEntry(logs.get(i), Permissions.getString(context, i)));
                colors.add(Utils.getAttrColor(context, Permissions.getColor(i)));
            }
        }

        if (entries.size() == 0) {
            entries.add(new PieEntry(1, " "));
            colors.add(Utils.getAttrColor(context, R.attr.colorCardBackground));
        }

        binding.permissionLocation.setPermissionUsage(Permissions.getPermissionUsageInfo(context, logs.get(Constants.POSITION_LOCATION)));
        binding.permissionCamera.setPermissionUsage(Permissions.getPermissionUsageInfo(context, logs.get(Constants.POSITION_CAMERA)));
        binding.permissionMicrophone.setPermissionUsage(Permissions.getPermissionUsageInfo(context, logs.get(Constants.POSITION_MICROPHONE)));

        binding.pieChart.setData(PieCharts.getData(context, entries, colors));
        binding.pieChart.invalidate();

    }

    private void askPermission() {
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    private void checkForAccessibilityAndStart() {
        if (!Permissions.accessibilityPermission(getApplicationContext(), PrivacyService.class)) {
            serviceIntent = new Intent(MainActivity.this, PrivacyService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            initValues();
            initPieChart();

            Toast.makeText(context, getString(R.string.menu_refresh_info), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initValues();

        binding.viewSettings.setVisibility(View.GONE);

        if (Permissions.accessibilityPermission(context, PrivacyService.class)) {
            binding.lyAccessibility.setVisibility(View.GONE);
        } else {
            binding.lyAccessibility.setVisibility(View.VISIBLE);
            binding.viewSettings.setVisibility(View.VISIBLE);
        }

        if (Permissions.checkLocation(context)) {
            binding.lyLocation.setVisibility(View.GONE);
        } else {
            binding.lyLocation.setVisibility(View.VISIBLE);
            binding.viewSettings.setVisibility(View.VISIBLE);
        }

        checkForAccessibilityAndStart();

    }

}


