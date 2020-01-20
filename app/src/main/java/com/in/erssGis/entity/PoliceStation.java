package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class PoliceStation implements KvmSerializable {

    public static Class<PoliceStation> PoliceStation_CLASS = PoliceStation.class;
    int _id;
    String PoliceStation_Name, District_Code, District_Name,PoliceStation_Code;

    // Empty constructor
    public PoliceStation() {
    }

    // constructor
    public PoliceStation(SoapObject obj) {
        this.PoliceStation_Code = obj.getProperty("BlockCodeNew").toString();

        this.PoliceStation_Name = obj.getProperty("Blk_Name_Eng").toString();


        this.District_Code = obj.getProperty("District_Code_New").toString();

        this.District_Name = obj.getProperty("State_Code").toString();


    }

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

    public String getPoliceStation_Name() {
        return PoliceStation_Name;
    }

    public void setPoliceStation_Name(String policeStation_Name) {
        PoliceStation_Name = policeStation_Name;
    }

    public String getDistrict_Code() {
        return District_Code;
    }

    public void setDistrict_Code(String district_Code) {
        District_Code = district_Code;
    }

    public String getDistrict_Name() {
        return District_Name;
    }

    public void setDistrict_Name(String district_Name) {
        District_Name = district_Name;
    }

    public String getPoliceStation_Code() {
        return PoliceStation_Code;
    }

    public void setPoliceStation_Code(String policeStation_Code) {
        PoliceStation_Code = policeStation_Code;
    }
}
