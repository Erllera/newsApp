package com.geektech.a41.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.geektech.a41.Prefs;
import com.geektech.a41.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"Первый тайтл приложения", "Второй тайтл приложения", "Третий тайтл приложения"};
    private String[] descriptions = new String[]{"Первое описание приложения", "Второе описание приложения", "Третье описание приложения"};
    private int[] images = new int[]{R.drawable.pager_one, R.drawable.pager_two, R.drawable.pager_three};
    private int[] lottieAnim = new int[]{R.raw.welcome, R.raw.search, R.raw.delivery};


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText, description;
        private ImageView pagerImage;
        private LottieAnimationView lottie;
        private Button btnStart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lottie = itemView.findViewById(R.id.lottie);
            btnStart = itemView.findViewById(R.id.startBtn);
            titleText = itemView.findViewById(R.id.titleText);
            description = itemView.findViewById(R.id.boardDesc);
            pagerImage = itemView.findViewById(R.id.pagerImage);

        }

        public void bind(int position) {
            lottie.setAnimation(lottieAnim[position]);
            titleText.setText(titles[position]);
            pagerImage.setImageResource(images[position]);
            description.setText(descriptions[position]);
            if (position == titles.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Prefs prefs = new Prefs(view.getContext());
                        prefs.saveState();
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }
    }
}
