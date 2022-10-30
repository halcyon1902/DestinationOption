package com.example.desop.MainScreen;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.desop.HobbyFragment;
import com.example.desop.LoadingScreen.LoadingScreen2;
import com.example.desop.Login.activitySignIn;
import com.example.desop.Map.MapActivity;
import com.example.desop.R;
import com.example.desop.UI.AppInfoFragment;
import com.example.desop.UI.HistoryFragment;
import com.example.desop.UI.HomeFragment;
import com.example.desop.UI.NotificationFragment;
import com.example.desop.UI.QuestionFragment;
import com.example.desop.UI.SettingFragment;
import com.example.desop.UI.SupportFragment;
import com.example.desop.UI.UserFragment;
import com.example.desop.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    UserFragment userFragment = new UserFragment();
    SupportFragment supportFragment = new SupportFragment();
    SettingFragment settingFragment = new SettingFragment();
    HobbyFragment hobbyFragment = new HobbyFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    QuestionFragment questionFragment = new QuestionFragment();
    AppInfoFragment appInfoFragment = new AppInfoFragment();
    HomeFragment homeFragment = new HomeFragment();
    TextView username, username_gmail;
    ImageView profile_image;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //init
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.mainscreen);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.username);
        username_gmail = headerView.findViewById(R.id.username_gmail);
        profile_image = headerView.findViewById(R.id.profile_image);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        DisplayProfile();
        //thanh menu dưới
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setSelected(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.user:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, userFragment).commit();
                    return true;
                case R.id.map:
                    startActivity(new Intent(getApplicationContext(), MapActivity.class));
                    return true;
                case R.id.support:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, supportFragment).commit();
                    return true;
            }
            return true;
        });
        // nút fab
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, userFragment).commit();
                break;
            case R.id.hobby:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, hobbyFragment).commit();
                break;
            case R.id.history:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, historyFragment).commit();
                break;
            case R.id.notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, notificationFragment).commit();
                break;
            case R.id.Declaration:
                Uri uri = Uri.parse("https://tokhaiyte.vn/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.question:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, questionFragment).commit();
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, settingFragment).commit();
                break;
            case R.id.AppInfo:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, appInfoFragment).commit();
                break;
            case R.id.Exit:
                finish();
                startActivity(new Intent(getApplicationContext(), LoadingScreen2.class));
                break;
            case R.id.sign_out:
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                startActivity(new Intent(this, activitySignIn.class));
                this.finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(this);
        //tạo menu mở drawer trên toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        navigationView.setItemIconTintList(null);
    }

    private void DisplayProfile() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userId = auth.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.getKey().equals(userId)) {
                        user = child.getValue(User.class);
                        assert user != null;
                        username_gmail.setText(user.getEmail());
                        username.setText(user.getName());
//                        String image = "" + child.child("image").getValue();
                        if (user.getImage() == null) {
                            Drawable drawable = getResources().getDrawable(R.drawable.avatar);
                            profile_image.setImageDrawable(drawable);
                        } else {
                            Picasso.get().load(user.getImage()).into(profile_image);
                        }

//                        Glide.with(getApplicationContext()).load(image).into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
