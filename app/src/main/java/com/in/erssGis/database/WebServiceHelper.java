package com.in.erssGis.database;

import android.util.Base64;
import android.util.Log;

import com.in.erssGis.entity.ComplainStatusEntity;
import com.in.erssGis.entity.PoiInfo;
import com.in.erssGis.entity.UploadComplainEntity;
import com.in.erssGis.entity.UserDetails;
import com.in.erssGis.entity.Versioninfo;
import com.in.erssGis.entity.YOJANA;
import com.in.erssGis.entity.studentList;
import com.in.erssGis.entity.ward;
import com.in.erssGis.utility.Utiilties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by NICSI on 3/15/2018.
 */

public class WebServiceHelper {


    public static final String SERVICENAMESPACE = "http://nicapp.bih.nic.in/";
    public static final String SERVICEURL = "http://nicapp.bih.nic.in/MMNischayaYojnaWebService.asmx";
    public static final String APPVERSION_METHOD = "getAppLatestComplain";
    private static final String AUTHENTICATE_LIST_METHOD = "AuthenticatePublic";
    public static final String REGISTRATION_METHOD = "RegisterUserMobile";
    public static final String REGISTRATIONOTP = "VerifyComplainuser";
    public static final String WARD_LIST_METHOD="getWardLst";
    public static final String YOJANA_LIST_METHOD="getSchemeLst";
    public static final String BASIC_DETAILS_UPLOAD="InsertComplainUsersDt";

    private static final String GETCOMPLAINdETAILS="getFeedBackComplain";
    private static final String GETCOMPLAINALLSTATUS="getComplainDetails";
    public static Versioninfo CheckVersion(String imei, String version) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, APPVERSION_METHOD);

        request.addProperty("IMEI", "0000000000");
        request.addProperty("Ver", version);
        Versioninfo versioninfo;
        SoapObject res1;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, Versioninfo.Versioninfo_CLASS.getSimpleName(), Versioninfo.Versioninfo_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + APPVERSION_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            // Object property = res1.getProperty(0);
            SoapObject final_object = (SoapObject) res1.getProperty(0);
            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;

    }
    public static UserDetails GetUserallDetails(String mobile, String password) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_LIST_METHOD);

        request.addProperty("UserID", mobile);
        request.addProperty("Password", password);
        UserDetails userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,UserDetails.USER_CLASS.getSimpleName(), UserDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_LIST_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new UserDetails(res1);

        } catch (Exception e) {

            return null;
        }
        return userDetails;

    }


    public static String Registration(String mob) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,REGISTRATION_METHOD);


            request.addProperty("_Mobile",mob);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UserDetails.USER_CLASS.getSimpleName(),
                    UserDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTRATION_METHOD,
                    envelope);

            Object result = envelope.getResponse();

            if (result != null) {
                // Log.d("", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String registration_otp(UserDetails user) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,REGISTRATIONOTP);
            request.addProperty("_otp",user.getOtp());
            request.addProperty("_Name",user.getName());
            request.addProperty("_Mobile",user.getMobile());
            request.addProperty("_FatherName",user.getFatherName());
            request.addProperty("_Address",user.getAddress());
            request.addProperty("_Pwd",user.getPassword());
            request.addProperty("_DistCode",user.getDistrictCode());
            request.addProperty("_panchayatCode",user.getPanchayatCode());
            request.addProperty("_BlockCode",user.getBlockCode());
            request.addProperty("_EntryDate",user.getEntrydate());
            request.addProperty("_DOB",user.getEntrydate());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UserDetails.USER_CLASS.getSimpleName(),
                    UserDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTRATIONOTP,
                    envelope);

            Object result = envelope.getResponse();

            if (result != null) {
                // Log.d("", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<ward> loadWardList(String Panchayat_Code) {

        String PACS_VM_LIST_METHOD=null;


        SoapObject request = new SoapObject(SERVICENAMESPACE,
                WARD_LIST_METHOD);

        request.addProperty("PanchayatCode", Panchayat_Code);


        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE,ward.ward_CLASS.getSimpleName(), ward.ward_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + WARD_LIST_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            Log.e("Exception1: ",e.getLocalizedMessage());
            Log.e("Exception2: ",e.getMessage());
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<ward> pvmArrayList = new ArrayList<ward>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ward vill = new ward(final_object,Panchayat_Code);
                    pvmArrayList.add(vill);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static ArrayList<YOJANA> loadYojanaList(String pancode) {


        SoapObject request = new SoapObject(SERVICENAMESPACE,	YOJANA_LIST_METHOD);

        request.addProperty("PanchayatCode",pancode);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, ward.ward_CLASS.getSimpleName(), ward.ward_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + YOJANA_LIST_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            Log.e("Exception1: ",e.getLocalizedMessage());
            Log.e("Exception2: ",e.getMessage());
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<YOJANA> pvmArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    YOJANA vill = new YOJANA(final_object);
                    pvmArrayList.add(vill);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static String UploadBasicDetails(UploadComplainEntity data) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    BASIC_DETAILS_UPLOAD);
            // request.addProperty("location",data.get_slno());
            request.addProperty("_mobile",data.getMobileNo());
            request.addProperty("_DistCode",data.getDistrictCode());
            request.addProperty("_BlockCode",data.getBlockCode());
            request.addProperty("_panchayatCode", data.getPanchayatCode());
            request.addProperty("_nishchayaTypecode", data.getNischaeyTypeCode());
            request.addProperty("_WardCode", data.getWardCode());

            request.addProperty("_yojnatypecode",data.getYojnaTypeCode());
            request.addProperty("_ComplainRemark",data.getComplainRemarks());
            request.addProperty("_FeedbackPhoto1",data.getPhoto1());
            request.addProperty("_FeedbackPhoto2", data.getPhoto2());
            request.addProperty("_FeedbackPhoto3", data.getPhoto3());
            request.addProperty("_FeedbackPhoto4", data.getPhoto4());

            request.addProperty("_Latlong", data.getLatitude());
            request.addProperty("_Longitude", data.getLongitude());
            request.addProperty("_EntryDate",(Utiilties.getCurrentDate()));


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UploadComplainEntity.Basicdetail.getSimpleName(), UploadComplainEntity.Basicdetail);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + BASIC_DETAILS_UPLOAD, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<studentList> GetTamilaDetails(String complainid) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GETCOMPLAINdETAILS);

        request.addProperty("_ComplianId", complainid);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    studentList.getdata.getSimpleName(), studentList.getdata);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GETCOMPLAINdETAILS, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<studentList> pvmArrayList = new ArrayList<studentList>();
        if(TotalProperty>0) {


            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        studentList state = new studentList(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }



    public static ArrayList<ComplainStatusEntity> ComplainDetails(String mobilenumber) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GETCOMPLAINALLSTATUS);

        request.addProperty("_UserId", mobilenumber);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    studentList.getdata.getSimpleName(), studentList.getdata);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GETCOMPLAINALLSTATUS, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<ComplainStatusEntity> pvmArrayList = new ArrayList<ComplainStatusEntity>();
        if(TotalProperty>0) {
            for (int ii = 0; ii < TotalProperty; ii++) {
                if (res1.getProperty(ii) != null) {
                    Object property = res1.getProperty(ii);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        ComplainStatusEntity state = new ComplainStatusEntity(final_object);
                        pvmArrayList.add(state);
                    }
                } else
                    return pvmArrayList;
            }
        }


        return pvmArrayList;
    }


}
