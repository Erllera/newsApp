package com.geektech.a41.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.a41.Prefs;
import com.geektech.a41.R;
import com.geektech.a41.databinding.FragmentBoardBinding;

public class BoardFragment extends Fragment {
    FragmentBoardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BoardAdapter boardAdapter = new BoardAdapter();
        binding.viewPager.setAdapter(boardAdapter);
        binding.wordDots.setViewPager2(binding.viewPager);
        skipClick();
    }

    private void skipClick() {
        binding.textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}