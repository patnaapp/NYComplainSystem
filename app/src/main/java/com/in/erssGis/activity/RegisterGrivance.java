package com.in.erssGis.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.bih.nic.in.erssGis.R;
import com.in.erssGis.utility.CommonPref;

public class RegisterGrivance extends AppCompatActivity {
    TextView tv_district,tv_block,tv_panchayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_grivance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_district=(TextView)findViewById(R.id.tv_district);
        tv_block=(TextView)findViewById(R.id.tv_block);
        tv_panchayat=(TextView)findViewById(R.id.tv_panchayat);

        tv_district.setText(CommonPref.getUserDetails(RegisterGrivance.this).getDistrictName());
        tv_block.setText(CommonPref.getUserDetails(RegisterGrivance.this).getBlockName());
        tv_panchayat.setText(CommonPref.getUserDetails(RegisterGrivance.this).getPanchayatName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
