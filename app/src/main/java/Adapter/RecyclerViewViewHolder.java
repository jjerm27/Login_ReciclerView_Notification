package Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

 class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

     View view;
     TextView textView;

     RecyclerViewViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        textView = itemView.findViewById(R.id.listTextView);
    }
}
