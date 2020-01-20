package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

/**
 *
 */
public class ComplainStatusEntity implements KvmSerializable
{
	public static Class<ComplainStatusEntity> Basicdetail = ComplainStatusEntity.class;
	private String _slno = "";
	private String DistrictCode = "";
	private String BlockCode = "";
	private String PanchayatCode = "";
	private String DistrictName = "";
	private String BlockName = "";
	private String PanchayatName = "";
	private String WardName= "";
	private String MobileNo = "";
	private String NischaeyTypeCode = "";

	private String WardCode = "";
	private String YojnaTypeCode = "";
	private String ComplainRemarks = "";
	private String photo1 = "";
	private String photo2 = "";
	private String photo3 = "";
	private String photo4 = "";
	private String latitude = "";
	private String longitude = "";
	private String complinstatus = "";
	private String entryDate = "";

	//private String f_SchemeId = "";//638
	//private String SchemeNameHN = "";//Gali nali
//	private String SchemeName = "";
	private String NischaeyTypeName = "";
	private String YojnaTypeName = "";
	private String ComplainID = "";


	public ComplainStatusEntity(){

	}


	public ComplainStatusEntity(SoapObject final_object) {

		this.DistrictCode=final_object.getProperty("DistrictCode").toString();
		this.BlockCode=final_object.getProperty("BlockCode").toString();
		this.PanchayatCode=final_object.getProperty("PanchayatCode").toString();
		this.DistrictName=final_object.getProperty("DistrictCode").toString();//
		this.BlockName=final_object.getProperty("BlockName").toString();
		this.PanchayatName=final_object.getProperty("PanchayatName").toString();
		this.WardName=final_object.getProperty("WARDNAME").toString();
		this.MobileNo=final_object.getProperty("mobileNo").toString();
	//	this.NischaeyTypeCode=final_object.getProperty("mobileNo").toString();
		this.WardCode=final_object.getProperty("WARDCODE").toString();
		//this.YojnaTypeCode=final_object.getProperty("mobileNo").toString();
		this.ComplainRemarks=final_object.getProperty("ComplainRemark").toString();
		this.photo1=final_object.getProperty("FeedbackPhoto1").toString();
		this.photo2=final_object.getProperty("FeedbackPhoto2").toString();
		this.photo3=final_object.getProperty("FeedbackPhoto3").toString();
		this.photo4=final_object.getProperty("FeedbackPhoto4").toString();
		//this.latitude=final_object.getProperty("mobileNo").toString();
		//this.longitude=final_object.getProperty("mobileNo").toString();
		this.complinstatus=final_object.getProperty("staust").toString();
		//this.entryDate=final_object.getProperty("mobileNo").toString();
		this.ComplainID=final_object.getProperty("ComplainId1").toString();

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

	public String get_slno() {
		return _slno;
	}

	public void set_slno(String _slno) {
		this._slno = _slno;
	}

	public String getDistrictCode() {
		return DistrictCode;
	}

	public void setDistrictCode(String districtCode) {
		DistrictCode = districtCode;
	}

	public String getBlockCode() {
		return BlockCode;
	}

	public void setBlockCode(String blockCode) {
		BlockCode = blockCode;
	}

	public String getPanchayatCode() {
		return PanchayatCode;
	}

	public void setPanchayatCode(String panchayatCode) {
		PanchayatCode = panchayatCode;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public String getNischaeyTypeCode() {
		return NischaeyTypeCode;
	}

	public void setNischaeyTypeCode(String nischaeyTypeCode) {
		NischaeyTypeCode = nischaeyTypeCode;
	}

	public String getWardCode() {
		return WardCode;
	}

	public void setWardCode(String wardCode) {
		WardCode = wardCode;
	}

	public String getYojnaTypeCode() {
		return YojnaTypeCode;
	}

	public void setYojnaTypeCode(String yojnaTypeCode) {
		YojnaTypeCode = yojnaTypeCode;
	}

	public String getComplainRemarks() {
		return ComplainRemarks;
	}

	public void setComplainRemarks(String complainRemarks) {
		ComplainRemarks = complainRemarks;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}

	public String getPhoto4() {
		return photo4;
	}

	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getComplinstatus() {
		return complinstatus;
	}

	public void setComplinstatus(String complinstatus) {
		this.complinstatus = complinstatus;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}

	public String getBlockName() {
		return BlockName;
	}

	public void setBlockName(String blockName) {
		BlockName = blockName;
	}

	public String getPanchayatName() {
		return PanchayatName;
	}

	public void setPanchayatName(String panchayatName) {
		PanchayatName = panchayatName;
	}

	public String getWardName() {
		return WardName;
	}

	public void setWardName(String wardName) {
		WardName = wardName;
	}



	public String getNischaeyTypeName() {
		return NischaeyTypeName;
	}

	public void setNischaeyTypeName(String nischaeyTypeName) {
		NischaeyTypeName = nischaeyTypeName;
	}

	public String getYojnaTypeName() {
		return YojnaTypeName;
	}

	public void setYojnaTypeName(String yojnaTypeName) {
		YojnaTypeName = yojnaTypeName;
	}

	public String getComplainID() {
		return ComplainID;
	}

	public void setComplainID(String complainID) {
		ComplainID = complainID;
	}
}