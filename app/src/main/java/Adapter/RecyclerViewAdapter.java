package Adapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.Model;
import com.example.login.R;

import java.util.List;

import Constants.Constants;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

    private List<Model> mModelList;
    private PendingIntent pendingIntent;
    private NotificationManagerCompat notificationManagerCompat;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Button delButton;

    public RecyclerViewAdapter(List<Model> mModelList, PendingIntent pendingIntent, NotificationManagerCompat managerCompat, Toolbar toolbar, Button delButton) {
        this.mModelList = mModelList;
        this.pendingIntent = pendingIntent;
        this.notificationManagerCompat = managerCompat;
        this.toolbar = toolbar;
        this.delButton = delButton;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewViewHolder holder, final int position) {
        final Model model = mModelList.get(position);
        holder.textView.setText(model.getText());
        holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if (position % 2 != 0 && position != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.alertTitle)
                            .setIcon(R.drawable.round_sentiment_satisfied_black_24dp)
                            .setMessage(mModelList.get(position).getText())
                            .setCancelable(false)
                            .setPositiveButton(R.string.alertOk, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    createNotificationChannel(context);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.baseline_done_black_18dp)
                            .setContentTitle(context.getString(R.string.notificationInfo))
                            .setContentText(mModelList.get(position).getText())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    notificationManagerCompat.notify(Constants.NOTIFICATION_ID_START++, builder.build());
                }
            }
        });

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                toolbar.setVisibility(View.VISIBLE);
                delButton.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }
}
