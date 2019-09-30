package com.example.login;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import com.google.android.material.snackbar.Snackbar;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecyclerViewAdapter;
import Constants.Constants;
import Repository.InfoRepository;
import Repository.InfoRepositoryImpl;

public class FontStyling extends AppCompatActivity {

    private TextView textView;
    private RadioButton boldRadio, normalRadio;
    private CheckBox isItalic;
    private RadioGroup radioGroup;
    private ConstraintLayout constraintLayout;
    private ColorPicker cp;
    private PendingIntent pendingIntent;
    private NotificationManagerCompat notificationManagerCompat;
    private Intent pIntent;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Switch layoutSwitch;
    private Button delButton;
    private Toolbar toolbar;
    private List<Model>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_styling);

        String text;

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            text = extras.getString(Constants.LOGIN);
            if (text == null|| text.equals(" ")||text.equals(""))
                text = getString(R.string.sampleText);
            textView = findViewById(R.id.textView);
            textView.setText(text);
        }

        isItalic = findViewById(R.id.italicCheck);
        radioGroup = findViewById(R.id.radioGroup);
        constraintLayout = findViewById(R.id.constraintFont);
        recyclerView = findViewById(R.id.recyclerView);

        layoutSwitch = findViewById(R.id.switchLayout);
        layoutSwitch.setOnClickListener(switchClickListener);

        boldRadio = findViewById(R.id.boldRadio);
        boldRadio.setOnClickListener(radioButtonClickListener);

        normalRadio = findViewById(R.id.normalRadio);
        normalRadio.setChecked(true);
        normalRadio.setOnClickListener(radioButtonClickListener);

        isItalic.setOnClickListener(checkBoxClickListener);

        delButton = findViewById(R.id.delete);
        delButton.setOnClickListener(delListener);

        notificationManagerCompat =NotificationManagerCompat.from(this);
        pIntent = new Intent(this,FontStyling.class);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this,0,pIntent,0);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list = InfoRepositoryImpl.getInstance().getDefaultItems();
        toolbar = findViewById(R.id.toolbar);
        adapter = new RecyclerViewAdapter(list,pendingIntent,notificationManagerCompat,toolbar, delButton);

        recyclerView.setAdapter(adapter);

        Snackbar snackbar = Snackbar
                .make(constraintLayout,extras.getString(Constants.RESULT),Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private final View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            int id = rb.getId();
            boolean isCheck = isItalic.isChecked();
            setType(id, isCheck);
        }
    };

    private final View.OnClickListener checkBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox ch = (CheckBox) v;
            boolean isCheck = ch.isChecked();
            int id = radioGroup.getCheckedRadioButtonId();
            setType(id, isCheck);
        }
    };

    private final View.OnClickListener switchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Switch s = (Switch)view;
            setLayout(s.isChecked());
        }
    };

    private final View.OnClickListener delListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<Model>delList = new ArrayList<>();
            for (Model m:list) {
                if(m.isSelected())
                    delList.add(m);
            }
            for (Model m:delList){
                list.remove(m);
            }
            toolbar.setVisibility(View.INVISIBLE);
            delButton.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(adapter);
        }
    };

    private void setLayout(boolean isCheck){
        if(isCheck){
            layoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(layoutManager);
        }
        else{
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void setType(int id, boolean isCheck) {
        switch (id) {
            case R.id.boldRadio:
                if (isCheck)
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                else
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
            case R.id.normalRadio:
                if (isCheck)
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                else
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
        }
    }

    public void onColorPick(View v){
        cp = new ColorPicker(FontStyling.this,255, 0, 0, 0);
        cp.show();
        cp.enableAutoClose();

        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                textView.setTextColor(color);
            }
        });
    }

}
