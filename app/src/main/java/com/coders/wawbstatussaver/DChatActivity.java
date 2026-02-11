package com.coders.wawbstatussaver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coders.wawbstatussaver.util.AdController;
import com.coders.wawbstatussaver.util.SharedPrefs;
import com.coders.wawbstatussaver.util.Utils;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class DChatActivity extends AppCompatActivity {

    ImageView back;
    CountryCodePicker ccp;
    EditText edtPhoneNumber, msg_edt;
    LinearLayout wapp;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dchat);

        Utils.setLanguage(DChatActivity.this, SharedPrefs.getLang(DChatActivity.this));

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ccp = findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);
        ccp.registerPhoneNumberTextView(edtPhoneNumber);
        msg_edt = findViewById(R.id.msg_edt);

        wapp = findViewById(R.id.wapp);
        wapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect();
            }
        });

        container = findViewById(R.id.banner_container);
        
        // AdMob Integration
        AdController.loadBannerAd(DChatActivity.this, container);
    }

    void redirect() {
        if (TextUtils.isEmpty(edtPhoneNumber.getText().toString())) {
            Toast.makeText(DChatActivity.this, R.string.select_country, Toast.LENGTH_SHORT).show();
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "http://api.whatsapp.com/send?phone=" + ccp.getSelectedCountryCode() + edtPhoneNumber.getText().toString() + "&text=" + msg_edt.getText().toString();
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(DChatActivity.this, "Install WhatsApp First...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
