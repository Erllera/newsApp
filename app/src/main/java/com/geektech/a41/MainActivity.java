package com.geektech.a41;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geektech.a41.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.profileFragment, R.id.profileFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            navController.navigate(R.id.loginFragment);
        }
        Prefs prefs = new Prefs(this);

        if (!prefs.isShown()){
            navController.navigate(R.id.boardFragment);
        }




        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                ArrayList<Integer> fragments = new ArrayList<>();
                fragments.add(R.id.navigation_home);
                fragments.add(R.id.navigation_dashboard);
                fragments.add(R.id.navigation_notifications);
                fragments.add(R.id.profileFragment);

                if (fragments.contains(navDestination.getId())) {

                    binding.navView.setVisibility(View.VISIBLE);
                } else {
                    binding.navView.setVisibility(View.GONE);
                }

                if (navDestination.getId() == R.id.boardFragment) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}