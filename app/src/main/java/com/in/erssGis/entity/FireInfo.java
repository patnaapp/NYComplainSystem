package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Hashtable;

/**
 * Created by NICSI on 3/13/2018.
 */

public class FireInfo implements KvmSerializable,Serializable {

    private static final long serialVersionUID = 1L;

    public static Class<FireInfo> FireINFO_CLASS = FireInfo.class;



    int id;

    String slno;


    String Latitude="";
    String Longitude="";
    String gps_date="";
    String entry_Date="";
    String Upload_by="";
    private byte[] Photo1;
    //String Photo1;
    String Upload_date;
    String UserName;
    String UserId;
    String DistrictName;
    String DistrictId;
    String OfficerId;
    String OfficerName;
    String Password;
    Blob photonew;

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }

    public Blob getPhotonew() {
        return photonew;
    }

    public void setPhotonew(Blob photonew) {
        this.photonew = photonew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getGps_date() {
        return gps_date;
    }

    public void setGps_date(String gps_date) {
        this.gps_date = gps_date;
    }

    public String getEntry_Date() {
        return entry_Date;
    }

    public void setEntry_Date(String entry_Date) {
        this.entry_Date = entry_Date;
    }

    public String getUpload_by() {
        return Upload_by;
    }

    public void setUpload_by(String upload_by) {
        Upload_by = upload_by;
    }

    public String getUpload_date() {
        return Upload_date;
    }

    public void setUpload_date(String upload_date) {
        Upload_date = upload_date;
    }



    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }

    public String getOfficerId() {
        return OfficerId;
    }

    public void setOfficerId(String officerId) {
        OfficerId = officerId;
    }

    public String getOfficerName() {
        return OfficerName;
    }

    public void setOfficerName(String officerName) {
        OfficerName = officerName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public byte[] getPhoto1() {
        return Photo1;
    }

    public void setPhoto1(byte[] photo1) {
        Photo1 = photo1;
    }
}
