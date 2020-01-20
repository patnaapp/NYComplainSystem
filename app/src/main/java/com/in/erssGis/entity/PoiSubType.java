package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class PoiSubType implements KvmSerializable {

    public static Class<Block> Block_CLASS = Block.class;
    int _id;
    String POICode, POISubTypeCode, POISubTypeName;

    // Empty constructor
    public PoiSubType() {
    }

    // constructor
    public PoiSubType(SoapObject obj) {
        this.POICode = obj.getProperty("BlockCodeNew").toString();
        this.POISubTypeCode = obj.getProperty("Blk_Name_Eng").toString();
        this.POISubTypeName = obj.getProperty("District_Code_New").toString();




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

    public String getPOICode() {
        return POICode;
    }

    public void setPOICode(String POICode) {
        this.POICode = POICode;
    }

    public String getPOISubTypeCode() {
        return POISubTypeCode;
    }

    public void setPOISubTypeCode(String POISubTypeCode) {
        this.POISubTypeCode = POISubTypeCode;
    }

    public String getPOISubTypeName() {
        return POISubTypeName;
    }

    public void setPOISubTypeName(String POISubTypeName) {
        this.POISubTypeName = POISubTypeName;
    }
}
