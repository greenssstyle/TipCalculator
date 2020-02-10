package com.example.thinkpad.tipcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnFocusChangeListener, OnRatingBarChangeListener {

    private String strBillValue, strSplitValue, strTipPercentValue, strTaxPercentValue, strCurrency;

    private TextView tvBill;
    private EditText etBill, etSplit, etTipPercent;
    private RatingBar rbService;
    private Button btnCalculate, btnSettings, btnClear;
    //private ImageButton btnSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        tvBill = findViewById(R.id.tv_main_bill);
        etBill = findViewById(R.id.et_main_bill);
        etSplit = findViewById(R.id.et_main_split);
        etTipPercent = findViewById(R.id.et_main_tip);
        rbService = findViewById(R.id.rb_main_service);
        btnCalculate = findViewById(R.id.btn_main_calculate);
        //btnSettings = findViewById(R.id.btn_main_settings);
        btnClear = findViewById(R.id.btn_main_clear);

        etBill.setOnFocusChangeListener(this);
        etSplit.setOnFocusChangeListener(this);
        etTipPercent.setOnFocusChangeListener(this);
        rbService.setOnRatingBarChangeListener(this);
        btnCalculate.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        setDefaultValues();
    }


    public void setDefaultValues() {
        //get default values
        strTipPercentValue = ((TipApplication) this.getApplication()).getDefaultTipPercentage();
        strTaxPercentValue = ((TipApplication) this.getApplication()).getDefaultTaxPercentage();
        strCurrency = ((TipApplication) this.getApplication()).getDefaultCurrency();

        //set displayed default values
        tvBill.setText(getString(R.string.bill) + " (" + strCurrency + "):");
        etTipPercent.setText(strTipPercentValue);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (((TipApplication) this.getApplication()).isPreferenceChanged()) {
            setDefaultValues();
            ((TipApplication) this.getApplication()).setPreferenceChanged(false);
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int viewId = v.getId();

        if (viewId == etSplit.getId()) {
            if (hasFocus) {
                etSplit.getText().clear();
            }
        } else if (viewId == etTipPercent.getId()) {
            if (hasFocus) {
                if (rbService.getRating() > 0) {
                    rbService.setRating(0);
                }
            }
        }
    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int rbId = ratingBar.getId();

        if (rbId == rbService.getId()) {
            if (rbService.getRating() > 0) {
                strTipPercentValue = String.valueOf(10 + ((int)rating * 2));
                etTipPercent.setText(strTipPercentValue);

                tvBill.setText(getString(R.string.bill) + " (" + strCurrency + "):");
                etTipPercent.setText(strTipPercentValue);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == btnCalculate.getId()) {
            if (checkInput()) {
                Intent intent = new Intent(this, SummaryActivity.class);
                intent.putExtra("bill", Double.parseDouble(strBillValue));
                intent.putExtra("split", Integer.parseInt(strSplitValue));
                intent.putExtra("tip", Integer.parseInt(strTipPercentValue));
                intent.putExtra("tax", Integer.parseInt(strTaxPercentValue));
                intent.putExtra("currency", strCurrency);
                startActivity(intent);
            }
        } else if (viewId == btnSettings.getId()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (viewId == btnClear.getId()) {
            etBill.getText().clear();
            etSplit.getText().clear();
            etTipPercent.getText().clear();
            rbService.setRating(0);
        }
    }


    public boolean checkInput() {
        //check if bill amount is filled and its double value is greater than 0
        strBillValue = etBill.getText().toString();
        if(TextUtils.isEmpty(strBillValue)) {
            etBill.setError("Please enter a bill amount here.");
            return false;
        } else {
            if (Double.parseDouble(strBillValue) <= 0) {
                Toast.makeText(this, "The bill amount must be greater than 0.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //check if split is filled and its int value is not less than 1
        strSplitValue = etSplit.getText().toString();
        if(TextUtils.isEmpty(strSplitValue)) {
            etSplit.setError("Please enter the number of people to split here.");
            return false;
        } else {
            if (Integer.parseInt(strSplitValue) <= 0) {
                Toast.makeText(this, "The number of people to split cannot be less than 1.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //check if tip percentage is filled and its int value is not less than 0
        strTipPercentValue = etTipPercent.getText().toString();
        if(TextUtils.isEmpty(strTipPercentValue)) {
            etTipPercent.setError("Please enter the percentage of tip here.");
            return false;
        } else {
            if (Integer.parseInt(strTipPercentValue) < 0) {
                Toast.makeText(this, "The tip percentage cannot be less than 0.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}
