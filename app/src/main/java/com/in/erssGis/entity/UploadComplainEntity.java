package com.in.erssGis.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 *
 */
public class UploadComplainEntity implements KvmSerializable
{
	public static Class<UploadComplainEntity> Basicdetail = UploadComplainEntity.class;
	private String _slno = "";
	private String DistrictCode = "";
	private String BlockCode = "";
	private String PanchayatCode = "";
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


	public UploadComplainEntity(){



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
}