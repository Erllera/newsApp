package com.geektech.a41.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.a41.NewsModel;
import com.geektech.a41.R;
import com.geektech.a41.databinding.FragmentNewsBinding;
import com.geektech.a41.ui.App;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.btnNews.setVisibility(View.GONE);
            }
        });

    }

    private void save() {
        String text = binding.editText.getText().toString();
        NewsModel model = new NewsModel();
        model.setText(text);
        model.setDate(System.currentTimeMillis());
        App.appDataBase.articleDao().insert(model);
        saveToFireStore(model);

    }

    private void saveToFireStore(NewsModel model) {
        FirebaseFirestore.getInstance().collection("news").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

            if (task.isSuccessful()){
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                close();
            }
            else {
                Toast.makeText(requireContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            }
        });
    }


    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}