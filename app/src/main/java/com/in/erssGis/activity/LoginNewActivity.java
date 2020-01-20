package com.in.erssGis.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.in.erssGis.R;
import com.in.erssGis.database.DataBaseHelper;
import com.in.erssGis.database.WebServiceHelper;
import com.in.erssGis.entity.UserDetails;
import com.in.erssGis.utility.CommonPref;
import com.in.erssGis.utility.GlobalVariables;
import com.in.erssGis.utility.Utiilties;

public class LoginNewActivity extends AppCompatActivity {
    TextView btn_signup;
    EditText et_mobile_num;
    private ProgressDialog dialog;
    String version= "";
    EditText txtmobilenum,txtpwd;
    UserDetails userDetails;


    private PopupWindow mPopupWindow;
    DataBaseHelper databaseHelper;
    boolean doubleBackToExitPressedOnce = false;
    FloatingActionButton fab;
    String umobile,passwordm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        dialog = new ProgressDialog(LoginNewActivity.this);
        databaseHelper=new DataBaseHelper(LoginNewActivity.this);
        //userDetails=new UserDetails(LoginNewActivity.this);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        txtmobilenum = (EditText) findViewById(R.id.txtmobile);
        txtpwd = (EditText) findViewById(R.id.txtpwd);
         btn_signup = (TextView) findViewById(R.id.btn_signup);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.isOffline && !Utiilties.isOnline(LoginNewActivity.this)) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(LoginNewActivity.this);
                    ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें या ऑफ़लाइन मोड के साथ जारी रखें।");
                    ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            GlobalVariables.isOffline = false;
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
                    ab.show();
                }else{
                    try {
                        setValue();
                        //if (validateData()) {
                            Boolean value = isNetworkConnected();
                            if(value.equals(true)){
                                new Getalldetails(umobile,passwordm).execute();
                            }else {
//                                CustomDialogClass cdd=new CustomDialogClass(LoginActivity.this,username);
//                                cdd.show();
                            }

                        //}
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }

            }
        });


//        btn_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LoginNewActivity.this,SignupActivity.class);
//               startActivity(i);
//            }
//        });

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void setValue(){
        umobile=txtmobilenum.getText().toString().trim();
        passwordm=txtpwd.getText().toString().trim();
        //imeicode=imei;

    }
    public void login() {

        umobile = txtmobilenum.getText().toString();
        passwordm = txtpwd.getText().toString();
        boolean cancelRegistration = false;
        String isValied = "yes";
        View focusView = null;
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("MOBILEDATA",umobile).commit();

        if (TextUtils.isEmpty(umobile)) {
            txtmobilenum.setError("कृपया मोबाइल नंबर डालें ");
            focusView = txtmobilenum;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(passwordm)) {
            txtpwd.setError("कृपया पासवर्ड डालें ");
            focusView = txtpwd;
            cancelRegistration = true;
        }

        if (cancelRegistration) {
            // error in login
            focusView.requestFocus();
        } else {
            //userDetails = new UserDetails();
            userDetails.setMobile(umobile);
            userDetails.setPassword(passwordm);
            new Getalldetails(umobile,passwordm).execute();

        }
    }
    private class Getalldetails extends AsyncTask<String, Void, UserDetails> {

        private final ProgressDialog dialog = new ProgressDialog(
                LoginNewActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                LoginNewActivity.this).create();
        String mob="";
        String Pass="";

        public Getalldetails(String mobile, String password) {
            this.mob=mobile;
            this.Pass=password;
        }

        protected void onPreExecute() {

            dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... params) {
            if (GlobalVariables.isOffline) {
                UserDetails userInfo = new UserDetails();
                userInfo.setAuthenticated(true);
                return userInfo;
            } else
                return WebServiceHelper.GetUserallDetails(umobile,passwordm);

            //return WebServiceHelper.GetUserallDetails(umobile,passwordm);
        }

        @Override
        protected void onPostExecute(UserDetails result) {
            super.onPostExecute(result);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            long c =0;

            if (result != null) {

                if (result.isAuthenticated() == true) {

                    try {
                        DataBaseHelper placeData = new DataBaseHelper(LoginNewActivity.this);
                        c = placeData.getalluserdetails(result);
                        if (c > 0) {


                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //  if (result.getIsRegistered().equalsIgnoreCase("Y")) {

                    DataBaseHelper placeData = new DataBaseHelper(LoginNewActivity.this);

                    if (GlobalVariables.isOffline == false) {
                        if (result.getIsRegistered().equalsIgnoreCase("Y")) {

                            try {
                                GlobalVariables.LoggedUser = result;
                                GlobalVariables.LoggedUser.setMobile(txtmobilenum.getText().toString().trim());
                                GlobalVariables.LoggedUser.setPassword( txtpwd.getText().toString());
                                CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", result.getMobile()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Name", result.getName()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_CODE", result.getDistrictCode()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_Name", result.getDistrictName()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_CODE", result.getBlockCode()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_NAME", result.getBlockName()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PANCHAYAT_CODE", result.getPanchayatCode()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PANCHAYAT_NAME", result.getPanchayatName()).commit();


                                Intent intent = new Intent(LoginNewActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } catch (Exception ex) {
                                Toast.makeText(LoginNewActivity.this, "लॉगिन विफल: Exception", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {

                        DataBaseHelper placeData1 = new DataBaseHelper(LoginNewActivity.this);
                        if (placeData.getUserCount() > 0) {

                            GlobalVariables.LoggedUser = placeData.getUserDetails(txtmobilenum.getText().toString().trim(), txtpwd.getText().toString());

                            if (GlobalVariables.LoggedUser != null) {

                                CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", result.getMobile()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Name", result.getName()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_CODE", result.getDistrictCode()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_Name", result.getDistrictName()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_CODE", result.getBlockCode()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_NAME", result.getBlockName()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PANCHAYAT_CODE", result.getPanchayatCode()).commit();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PANCHAYAT_NAME", result.getPanchayatName()).commit();

                                Intent iUserHome = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(iUserHome);
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), "आईडी और पासवर्ड मेल नहीं खाता!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "कृपया पहली बार लॉगिन के लिए इंटरनेट कनेक्शन चालू करें.", Toast.LENGTH_LONG)
                                    .show();
                            AlertDialog.Builder ab = new AlertDialog.Builder(LoginNewActivity.this);
                            ab.setTitle("ऑनलाइन लॉगिन");
                            ab.setMessage("कृपया पहली बार लॉगिन के लिए इंटरनेट कनेक्शन चालू करें.");
                            ab.setPositiveButton("[ ठीक है ]", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });

                            ab.setNegativeButton("[ इंटरनेट ऑन करें ]", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            //   ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                            ab.show();
                        }

                    }
                    // Toast.makeText(getApplicationContext(), "Global variable true case!", Toast.LENGTH_LONG).show();
                    //  }
                    //here


                }else if (result.isAuthenticated() == false) {

                    chk_msg("आपका यूजर आईडी गलत है ,कृपया पहले रजिस्टर करें | ");
                    // Toast.makeText(getApplicationContext(),"Invalid User",Toast.LENGTH_LONG).show();
//                      if(result.getIsRegistered().equalsIgnoreCase("N")) {
//                        Toast.makeText(getApplicationContext(),"Plz register ",Toast.LENGTH_LONG).show();
//                    }


                }
            }

            else{

                Toast.makeText(getApplicationContext(),"Result null",Toast.LENGTH_LONG).show();

            }
        }
    }

    public void chk_msg(String msg) {
        // final String wantToUpdate;
        AlertDialog.Builder ab = new AlertDialog.Builder(LoginNewActivity.this);
        ab.setCancelable(false);
        // ab.setIcon(R.drawable.bedicon);
        ab.setTitle("कम्प्लेन");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(LoginNewActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();

            }
        });

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }


    public  void BlinkButtonSignup(Button btn)
    {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2500);
        anim.setStartOffset(30);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
//        btn.startAnimation(anim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            //TextView tv = (TextView) findViewById(R.id.txtVersion);
            //tv.setText(getResources().getString(R.string.app_version) + version +"");

        } catch (PackageManager.NameNotFoundException e) {

        }

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
