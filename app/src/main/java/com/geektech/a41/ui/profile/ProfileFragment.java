package com.geektech.a41.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.a41.Prefs;
import com.geektech.a41.R;
import com.geektech.a41.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    NavController navController;
    private Menu activityMenu;
    private MenuItem curMenuItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProfilePhotos();
        setText();
        setHasOptionsMenu(true);
        binding.centerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoAnimation();
            }
        });

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
        activityMenu=menu;
        curMenuItem = menu.findItem(R.id.exit);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            curMenuItem.setVisible(true);
            curMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigateUp();
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            activityMenu= menu;
            curMenuItem = activityMenu.findItem(R.id.exit);
            curMenuItem.setVisible(false);
            super.onPrepareOptionsMenu(menu);
        }
    }

    private void photoAnimation() {

        TransitionManager.beginDelayedTransition(binding.container);
        ViewGroup.LayoutParams params = binding.centerImage.getLayoutParams();
        params.width = 1080;
        params.height = 1000;
        binding.centerImage.setLayoutParams(params);
    }

    private void setText() {
        Prefs prefs = new Prefs(requireActivity());
        String name = prefs.getString();
        binding.etName.setText(name);
    }

    private void setProfilePhotos() {
        ActivityResultLauncher<String> launcherBackground = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.backImage.setImageURI(result);
                Prefs prefs = new Prefs(requireActivity());
                prefs.saveImage(result);
            }
        });


        ActivityResultLauncher<String> launcherCenter = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.centerImage.setImageURI(result);
            }
        });

        binding.backImgBtn.setOnClickListener(view1 -> launcherBackground.launch("image/*"));
        binding.centerImageButton.setOnClickListener(view1 -> launcherCenter.launch("image/*"));
    }

    @Override
    public void onStart() {
        super.onStart();
        Prefs prefs = new Prefs(requireActivity());
        if (prefs.getString() != null) {
            binding.etName.setText(prefs.getString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Prefs prefs = new Prefs(requireActivity());
        prefs.saveString(binding.etName.getText().toString());
    }

    private void close() {

        navController.navigateUp();
    }
}