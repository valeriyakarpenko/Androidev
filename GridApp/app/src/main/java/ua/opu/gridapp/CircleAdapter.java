package ua.opu.gridapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.CircleHolder> {

    private final LayoutInflater inflater;
    private List<Circle> list;
    private View.OnClickListener listener;


    public CircleAdapter(Context context, List<Circle> list, View.OnClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CircleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.digit_item, viewGroup, false);
        v.setOnClickListener(listener);
        return new CircleHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CircleHolder holder, int i) {
        holder.image.setImageTintList(ColorStateList.valueOf(list.get(i).getColor()));
        holder.text.setText(Integer.toString(list.get(i).getDigit()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CircleHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public CircleHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.color_view);
            text = itemView.findViewById(R.id.digit_text);
        }
    }
}
