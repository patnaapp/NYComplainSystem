package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

/**
 * Created by NICSI on 3/15/2018.
 */

public class UserDetails implements KvmSerializable {

    public static Class<UserDetails> USER_CLASS = UserDetails.class;
    private boolean isAuthenticated = false;
    private String Name = "";
    private String DistrictCode = "";
    private String DistrictName = "";
    private String BlockCode = "";
    private String BlockName = "";
    private String PanchayatCode = "";
    private String PanchayatName = "";
    private String FatherName = "";
    private String Address = "";
    private String entry_date = "";
    private String mobile = "";
    private String isRegistered="";
    private String Password = "";
    private String otp = "";
    private String Entrydate = "";
    private String dob = "";

    public UserDetails() {
    }

    @SuppressWarnings("deprecation")
    public UserDetails(SoapObject obj) {
        this.setAuthenticated(Boolean.parseBoolean(obj.getProperty("isAuthenticated").toString()));
        this.setName(obj.getProperty("Name").toString());
        this.setFatherName(obj.getProperty("FatherName").toString());
        this.setMobile(obj.getProperty("MobileNo").toString());
        this.setAddress(obj.getProperty("Address").toString());
        this.setDistrictCode(obj.getProperty("DistrictCode").toString());
        this.setDistrictName(obj.getProperty("DistrictName").toString());
        this.setBlockCode(obj.getProperty("BlockCode").toString());
        this.setBlockName(obj.getProperty("BlockName").toString());
        this.setPanchayatCode(obj.getProperty("PanchayatCode").toString());
        this.setPanchayatName(obj.getProperty("PanchayatName").toString());
        this.setPassword(obj.getProperty("Password").toString());
        this.setIsRegistered(obj.getProperty("Isregistred").toString());





    }


    public static Class<UserDetails> getUserClass() {
        return USER_CLASS;
    }

    public static void setUserClass(Class<UserDetails> userClass) {
        USER_CLASS = userClass;
    }


    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 13;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }

    public String getEntrydate() {
        return Entrydate;
    }

    public void setEntrydate(String entrydate) {
        Entrydate = entrydate;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDistrictCode() {
        return DistrictCode;
    }

    public void setDistrictCode(String districtCode) {
        DistrictCode = districtCode;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }




    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(String isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
