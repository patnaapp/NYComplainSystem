package com.in.erssGis.utility;

import android.location.Location;

import com.in.erssGis.entity.UserDetails;


public class GlobalVariables {

	public static UserDetails LoggedUser;
	public static boolean isOffline = false;
	public static boolean isOfflineGPS = false;
    public static int uploadNo=0;
    public static int listSize=0;
	public static boolean isLogged = false;


	public static String REPORTTYPE="ReportType";
	public static int rtype=0;


    public static boolean fieldDownloaded=false;
    public static boolean SpinnerDataDownloaded=false;
    public static boolean downloadFyearData=false;
    public static boolean downloadDistrictData=false;
    
	
	public static String MunicipalCorporationId="";
	
	public static String WardId="";
	public static String AreaId="";
	public static String UserId="";
	
	public static String Last_Visited="";
	public static Location glocation=null;


	public static String[] monthNameList={" ","January","February","March","April","May","June","July","August"
			,"September","October","November","December"};


}
