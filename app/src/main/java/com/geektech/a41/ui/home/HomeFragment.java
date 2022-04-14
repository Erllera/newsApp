package com.geektech.a41.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.a41.NewsModel;
import com.geektech.a41.OnItemClickListener;
import com.geektech.a41.R;
import com.geektech.a41.databinding.FragmentHomeBinding;
import com.geektech.a41.ui.App;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private NewsAdapter adapter;
    private FragmentHomeBinding binding;
    private List<NewsModel> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new NewsAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                list = App.getAppDataBase().articleDao().listSearch(s);
                adapter.addItems(list);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        openFragment();
        binding.recyclerView.setAdapter(adapter);
        list = App.appDataBase.articleDao().getAllSorted();
        adapter.addItems(list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NewsModel model = adapter.getItem(position);
                Toast.makeText(requireActivity(), model.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                alert.setTitle("Удаление объекта");
                alert.setMessage("Удалить выбранный объект?");
                alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        NewsModel model = adapter.getItem(position);
                        App.getAppDataBase().articleDao().delete(model);
                        list = App.getAppDataBase().articleDao().getAllSorted();
                        adapter.addItems(list);
                    }
                });
                alert.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.create().show();
            }
        });
    }


    private void openFragment() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.newsFragment);
            }
        });
/*        getParentFragmentManager().setFragmentResultListener("rk_key", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NewsModel model = (NewsModel) result.getSerializable("key_news");
                Log.e("HomeFragment", "result = " + model.getText() + "date = " + model.getDate());
                adapter.addItems(model);
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}