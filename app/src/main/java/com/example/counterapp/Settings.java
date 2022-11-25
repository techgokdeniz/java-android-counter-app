package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    private CheckBox Titresim,Bildirim,Limitler;
    private EditText UstLimitBox,AltLimitBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Titresim = (CheckBox) findViewById(R.id.TitresimAc);
        Bildirim = (CheckBox) findViewById(R.id.BildirimAc);
        Limitler = (CheckBox) findViewById(R.id.LimitAc);

        UstLimitBox = (EditText) findViewById(R.id.UstLimitBox);
        AltLimitBox = (EditText) findViewById(R.id.AltLimitBox);

        Titresim.setChecked(SharedPrefs.getInstance(this).read("titresim",false));
        Bildirim.setChecked(SharedPrefs.getInstance(this).read("bildirim",false));
        Limitler.setChecked(SharedPrefs.getInstance(this).read("limitler",false));

        Titresim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPrefs.getInstance(Settings.this).write("titresim",true);
                }
                else{
                    SharedPrefs.getInstance(Settings.this).write("titresim",false);
                }
            }
        });

        Limitler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPrefs.getInstance(Settings.this).write("limitler",true);
                }
                else{
                    SharedPrefs.getInstance(Settings.this).write("limitler",false);
                }
            }
        });

        Bildirim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPrefs.getInstance(Settings.this).write("bildirim",true);
                }
                else{
                    SharedPrefs.getInstance(Settings.this).write("bildirim",false);
                }
            }
        });

        UstLimitBox.setText(String.valueOf(SharedPrefs.getInstance(this).read("ustlimit","100")));
        AltLimitBox.setText(String.valueOf(SharedPrefs.getInstance(this).read("altlimit","0")));

        UstLimitBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPrefs.getInstance(Settings.this).write("ustlimit",UstLimitBox.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AltLimitBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPrefs.getInstance(Settings.this).write("altlimit",AltLimitBox.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}