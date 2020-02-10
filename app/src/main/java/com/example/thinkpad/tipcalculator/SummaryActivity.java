package com.example.thinkpad.tipcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class SummaryActivity extends AppCompatActivity implements OnClickListener {

    private String strCurrency;
    private int numSplit, numTipPercent, numTaxPercent;
    private double numBill, numTip, numTotal;

    private Toolbar toolbar;
    private LinearLayout llBillSplit, llTipSplit, llTaxSplit;
    private TextView tvTotalSplit;
    private TextView tvBillNum, tvTipNum, tvTaxNum, tvTotalNum;
    private TextView tvBillSplitNum, tvTipSplitNum, tvTaxSplitNum, tvTotalSplitNum;
    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        numBill = intent.getDoubleExtra("bill", 0);
        numSplit = intent.getIntExtra("split", 0);
        numTipPercent = intent.getIntExtra("tip", 0);
        numTaxPercent = intent.getIntExtra("tax", 0);
        strCurrency = intent.getStringExtra("currency");

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.tb_summary);
        tvBillNum = findViewById(R.id.tv_summary_billNum);
        tvTipNum = findViewById(R.id.tv_summary_tipNum);
        tvTaxNum = findViewById(R.id.tv_summary_taxNum);
        tvTotalNum = findViewById(R.id.tv_summary_totalNum);
        btnBack = findViewById(R.id.btn_summary_back);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btnBack.setOnClickListener(this);

        //calculate
        numTip = numBill * (0.01*numTipPercent);
        numTotal = (numBill + numTip)*(1 + 0.01*numTaxPercent);

        //set displayed values
        tvBillNum.setText(strCurrency + String.format(Locale.US,"%.2f", numBill));
        tvTipNum.setText(strCurrency + String.format(Locale.US,"%.2f", numTip));
        tvTaxNum.setText(String.valueOf(numTaxPercent) + "%");
        tvTotalNum.setText(strCurrency + String.format(Locale.US,"%.2f", numTotal));


        //show split part if number of people to pay is grater than 1
        if (numSplit > 1) {
            llBillSplit = findViewById(R.id.ll_summary_billSplit);
            llTipSplit = findViewById(R.id.ll_summary_tipSplit);
            llTaxSplit = findViewById(R.id.ll_summary_taxSplit);

            tvTotalSplit = findViewById(R.id.tv_summary_totalSplit);

            tvBillSplitNum = findViewById(R.id.tv_summary_billSplitNum);
            tvTipSplitNum = findViewById(R.id.tv_summary_tipSplitNum);
            tvTaxSplitNum = findViewById(R.id.tv_summary_taxSplitNum);
            tvTotalSplitNum = findViewById(R.id.tv_summary_totalSplitNum);

            //set views to VISIBLE
            llBillSplit.setVisibility(View.VISIBLE);
            llTipSplit.setVisibility(View.VISIBLE);
            llTaxSplit.setVisibility(View.VISIBLE);
            tvTotalSplit.setVisibility(View.VISIBLE);
            tvTotalSplitNum.setVisibility(View.VISIBLE);

            //set displayed values
            tvBillSplitNum.setText(strCurrency + String.format(Locale.US,"%.2f", numBill/numSplit));
            tvTipSplitNum.setText(strCurrency + String.format(Locale.US,"%.2f", numTip/numSplit));
            tvTaxSplitNum.setText(String.valueOf(numTaxPercent) + "%");
            tvTotalSplitNum.setText(strCurrency + String.format(Locale.US,"%.2f", numTotal/numSplit));
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == btnBack.getId()) {
            finish();
        }
    }
}
