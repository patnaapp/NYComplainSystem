package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Hashtable;

public class studentList extends ArrayList<studentList> implements KvmSerializable {
 private String id;
    private String officeCode;
    private String mobno;
    private String distCode;
    private String blockCode;
    private String panchayatCode;
    private String complain_status;
    private String complainRemaks;
    private String wardname;

    public static Class<studentList> getdata = studentList.class;
/*
CREATE TABLE "TamilaDetails" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT,
`pinnum` TEXT, `empname` TEXT, `recevingstatus` TEXT, `receivingdate` TEXT,
 `officeCode` TEXT, `designation` TEXT, `dateretirement` TEXT,
 `bloodgroup` TEXT, `contactnum` TEXT )
 */

    public studentList() {
    }

    public studentList(SoapObject final_object) {


        this.distCode=final_object.getProperty("DistrictCode").toString();
        this.blockCode=final_object.getProperty("BlockName").toString();
        this.panchayatCode=final_object.getProperty("PanchayatName").toString();
        this.complain_status=final_object.getProperty("staust").toString();//

        this.mobno=final_object.getProperty("mobileNo").toString();
        this.complainRemaks=final_object.getProperty("ComplainRemark").toString();
        this.wardname=final_object.getProperty("WARDNAME").toString();

    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getPanchayatCode() {
        return panchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        this.panchayatCode = panchayatCode;
    }

    public String getComplain_status() {
        return complain_status;
    }

    public void setComplain_status(String complain_status) {
        this.complain_status = complain_status;
    }

    public String getComplainRemaks() {
        return complainRemaks;
    }

    public void setComplainRemaks(String complainRemaks) {
        this.complainRemaks = complainRemaks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getWardname() {
        return wardname;
    }

    public void setWardname(String wardname) {
        this.wardname = wardname;
    }

    @Override
    public Object getProperty(int index) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int index, Object value) {

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {

    }

    public String getStudentname() {
        return officeCode;
    }

    public void setStudentname(String studentname) {
        officeCode = studentname;
    }




}
