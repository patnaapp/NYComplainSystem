package com.in.erssGis.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.in.erssGis.R;
import com.in.erssGis.database.DataBaseHelper;
import com.in.erssGis.database.WebServiceHelper;
import com.in.erssGis.entity.FireInfo;
import com.in.erssGis.entity.PoiInfo;
import com.in.erssGis.utility.Utiilties;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    LinearLayout FireNewEntry, FireEdit,l1_logout,FireUpload ;
    DataBaseHelper localDBHelper;
    long count;
    TextView tv_user_name, tv_user_role,Tv_Pending1_Data,Tv_Pending_POI;
    ImageView menu_inflater;
    FireInfo fireInfo;
    ProgressDialog pd1;
    long numberOfPendingData = 0;
    long countpendingPOI;
    String version="",USERID="",Designation="",MobileNumber,Designition,Dist_Code,Thana_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        localDBHelper = new DataBaseHelper(HomeActivity.this);
        pd1= new ProgressDialog(HomeActivity.this);
        pd1 = new ProgressDialog(HomeActivity.this);
        pd1.setTitle("Data is Uploading Wait");
        pd1.setCancelable(false);
        try {
            localDBHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            localDBHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;



        }
        MobileNumber= PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("MobileNumber", "");
        Designition= PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("Designition", "");
        Dist_Code= PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("Dist_Code", "");
        Thana_Name= PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("Thana_Name", "");
        initialozation();

        FireNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(HomeActivity.this, SelectionPoiActivity.class);
//                //intent.putExtra("CallFor","NewEntry");
//                startActivity(intent);
//                finish();

            }
        });
        l1_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });

        FireEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, EditNewEntryPoi.class);
//                    startActivity(intent);
//                    finish();

            }
        });

        FireUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (Utiilties.isOnline(HomeActivity.this)) {

                    android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
                            HomeActivity.this);
                    ab.setTitle("Upload !");
                    ab.setMessage("Do you want to upload all pending to the server ?");
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // dialog.dismiss();
                            //finish();
                            if (localDBHelper.getPendingUploadCountPOI(MobileNumber) > 0) {
                                Log.d("uploadcount",""+localDBHelper.getPendingUploadCount());
                                List<PoiInfo> locDatas = localDBHelper.getAllEntryDetail(MobileNumber);
                                for (PoiInfo cn : locDatas) {
                                    //new UploadingNewPOIData(cn).execute();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No Records Found with Photo",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();

                } else

                {
                    Toast.makeText(HomeActivity.this, " No Internet connection ! \n Please check your internet connectivity.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        l1_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });

    }



    private void logout() {
        SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("MobileNumber", "" ).commit();
//        editor.putBoolean("MobileNumber", false);
//        editor.commit();

        Intent intent = new Intent(getBaseContext(), LoginNewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    public void initialozation(){
        FireNewEntry = (LinearLayout)findViewById(R.id.ll_NewEntry);
        FireEdit = (LinearLayout)findViewById(R.id.ll_Show_Edit);
        l1_logout = (LinearLayout)findViewById(R.id.l1_logout);
        FireUpload = (LinearLayout)findViewById(R.id.ll_Pending_Upload);
        //Tv_Pending1_Data = (TextView) findViewById(R.id.Tv_Pending1_Data);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_role = (TextView) findViewById(R.id.tv_user_role);
        Tv_Pending_POI = (TextView) findViewById(R.id.Tv_Pending_POI);

    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {

            return model.toUpperCase();
        } else {

            return manufacturer.toUpperCase() + " " + model;
        }
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public String getAppVersion(){
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//                TextView tv = (TextView)getActivity().findViewById(R.id.txtVersion_1);
//                tv.setText(getActivity().getString(R.string.app_version) + version + " ");
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        countpendingPOI = dataBaseHelper.getPendingUploadCountPOI(MobileNumber);
        //Tv_Pending1_Data.setText("" + count);
        Tv_Pending_POI.setText("" + countpendingPOI);
        USERID=PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("Name", "");
        tv_user_name.setText(USERID);
        Designation=PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("Designition", "");
        tv_user_role.setText(Designation);

    }

//    public class UploadingNewPOIData extends AsyncTask<String, Void, String> {
//        String result11;
//
//        PoiInfo ltsynInfo;
//
//        public UploadingNewPOIData(PoiInfo ltsynInfo) {
//            this.ltsynInfo = ltsynInfo;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            pd1.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String devicename=getDeviceName();
//            String app_version=getAppVersion();
//            boolean isTablet=isTablet(HomeActivity.this);
//            if(isTablet) {
//                devicename="Tablet::"+devicename;
//                Log.e("DEVICE_TYPE", "Tablet");
//            } else {
//                devicename="Mobile::"+devicename;
//                Log.e("DEVICE_TYPE", "Mobile");
//            }
//            result11 = WebServiceHelper.UploadNewPoI(ltsynInfo,devicename,app_version);
//            return result11;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            List<PoiInfo> records = localDBHelper.getAllEntryDetail(MobileNumber);
//            for (PoiInfo cn : records) {
//                if (result.toString().equals("1")) {
//                    Toast.makeText(getApplicationContext(), "Data is uploaded successfully !", Toast.LENGTH_SHORT).show();
//                    int PID = Integer.parseInt(ltsynInfo.getSlno());
//                    boolean isDel = localDBHelper.deleterowPOI(PID);
//                    if (isDel) {
//                        countpendingPOI = localDBHelper.getPendingUploadCountPOI(MobileNumber);
//                        Tv_Pending_POI.setText("" + countpendingPOI);
//                    } else {
//                        Log.e("message", "data is uploaded but not deleted !!");
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Uploading failed !\n\n" + result.toString(), Toast.LENGTH_LONG)
//                            .show();
//                }
//                if (pd1.isShowing()) {
//                    pd1.dismiss();
//                }
//            }
//        }
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something on back.
            // Display alert message when back button has been pressed
            backButtonHandler();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public void backButtonHandler() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Exit applications?");
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to exit the application?");
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.bulb_1);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }





}
