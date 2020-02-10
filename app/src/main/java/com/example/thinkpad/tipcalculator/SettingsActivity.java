package com.example.thinkpad.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements OnClickListener, OnCheckedChangeListener {

    private String strDefaultTipPercentage, strDefaultTaxPercentage, strDefaultCurrency;

    private Toolbar toolbar;
    private EditText etDefaultTipPercentage, etDefaultTaxPercentage;
    private RadioGroup rgroupCurrency;
    private RadioButton rbtnDollar, rbtnEuro, rbtnPound;
    private Button btnSave;
    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.tb_settings);
        etDefaultTipPercentage = findViewById(R.id.et_settings_tipPercentage);
        etDefaultTaxPercentage = findViewById(R.id.et_settings_taxPercentage);
        rgroupCurrency = findViewById(R.id.rgroup_settings_currency);
        rbtnDollar = findViewById(R.id.rbtn_settings_dollar);
        rbtnEuro = findViewById(R.id.rbtn_settings_euro);
        rbtnPound = findViewById(R.id.rbtn_settings_pound);
        btnSave = findViewById(R.id.btn_settings_save);
        btnBack = findViewById(R.id.btn_settings_back);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rgroupCurrency.setOnCheckedChangeListener(this);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //get default values
        strDefaultTipPercentage = ((TipApplication) this.getApplication()).getDefaultTipPercentage();
        strDefaultTaxPercentage = ((TipApplication) this.getApplication()).getDefaultTaxPercentage();
        strDefaultCurrency = ((TipApplication) this.getApplication()).getDefaultCurrency();

        //set displayed default values
        etDefaultTipPercentage.setText(strDefaultTipPercentage);
        etDefaultTaxPercentage.setText(strDefaultTaxPercentage);
        if (strDefaultCurrency.contentEquals(rbtnDollar.getText())) {
            rbtnDollar.setChecked(true);
        } else if (strDefaultCurrency.contentEquals(rbtnEuro.getText())) {
            rbtnEuro.setChecked(true);
        } if (strDefaultCurrency.contentEquals(rbtnPound.getText())) {
            rbtnPound.setChecked(true);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == rgroupCurrency.getId()) {
            if (checkedId == rbtnDollar.getId()) {
                strDefaultCurrency = (String) rbtnDollar.getText();
            } else if (checkedId == rbtnEuro.getId()) {
                strDefaultCurrency = (String) rbtnEuro.getText();
            } else {
                strDefaultCurrency = (String) rbtnPound.getText();
            }
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == btnBack.getId()) {
            finish();
        } else if (viewId == btnSave.getId()) {
            boolean changed = false;

            //set new default tip percentage
            strDefaultTipPercentage = etDefaultTipPercentage.getText().toString();
            if( !(TextUtils.isEmpty(strDefaultTipPercentage)) &&
                    !(strDefaultTipPercentage.equals(((TipApplication) this.getApplication()).getDefaultTipPercentage())) ) {
                ((TipApplication) this.getApplication()).setDefaultTipPercentage(strDefaultTipPercentage);
                changed = true;
            }

            //set new default tax percentage
            strDefaultTaxPercentage = etDefaultTaxPercentage.getText().toString();
            if( !(TextUtils.isEmpty(strDefaultTaxPercentage)) &&
                    !(strDefaultTaxPercentage.equals(((TipApplication) this.getApplication()).getDefaultTaxPercentage())) ) {
                ((TipApplication) this.getApplication()).setDefaultTaxPercentage(strDefaultTaxPercentage);
                changed = true;
            }

            //set new default currency
            if( !(strDefaultCurrency.equals(((TipApplication) this.getApplication()).getDefaultCurrency())) ) {
                ((TipApplication) this.getApplication()).setDefaultCurrency(strDefaultCurrency);
                changed = true;
            }

            //give a feedback if any value is changed
            if (changed) {
                Toast.makeText(this, "New default values are set successfully!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
