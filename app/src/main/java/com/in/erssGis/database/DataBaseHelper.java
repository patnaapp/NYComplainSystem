package com.in.erssGis.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.in.erssGis.entity.Block;
import com.in.erssGis.entity.District;
import com.in.erssGis.entity.FireInfo;
import com.in.erssGis.entity.PoiInfo;
import com.in.erssGis.entity.PoiSubType;
import com.in.erssGis.entity.PoliceStation;
import com.in.erssGis.entity.UserDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by NICSI on 3/15/2018.
 */

public class DataBaseHelper  extends SQLiteOpenHelper {


    private static String DB_PATH = "";// "/data/data/com.bih.nic.app.biharmunicipalcorporation/databases/";
    private static String DB_NAME = "PACSDB2";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {


            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist


        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS
                            | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public FireInfo getFireImage(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT Photo1,Latitude,Longitude  FROM UploadData where Id = '" + Integer.toString(id) + "'", null);
        FireInfo progress = new FireInfo();


        try {
            if (cur.moveToNext()) {

                progress.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);
                progress.setLatitude(cur.isNull(cur.getColumnIndex("Latitude")) == false ? cur.getString(cur.getColumnIndex("Latitude")) : "");
                progress.setLongitude(cur.isNull(cur.getColumnIndex("Longitude")) == false ? cur.getString(cur.getColumnIndex("Longitude")) : "");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cur.close();
            db.close();
        }
        return progress;
    }

    public long getPendingUploadCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UploadData where Latitude IS NOT NULL ", null);
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {


        }

        return x;
    }

    public ArrayList<FireInfo> getAllFireDetails() {

        ArrayList<FireInfo> allfacilityDetails = new ArrayList<>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db.rawQuery(
                    "Select * from UploadData", null);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                FireInfo fireDetails = new FireInfo();

                fireDetails.setOfficerId(cur.getString(cur.getColumnIndex("OfficeId")));
                fireDetails.setOfficerName(cur.getString(cur.getColumnIndex("OfficeName")));
                fireDetails.setPhoto1(cur.getBlob(cur.getColumnIndex("Photo1")));
                fireDetails.setEntry_Date(cur.getString(cur.getColumnIndex("EntryDate")));

                fireDetails.setId(cur.getInt(cur.getColumnIndex("Id")));
                allfacilityDetails.add(fireDetails);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            allfacilityDetails = null;
        }
        return allfacilityDetails;
    }

    public long deleteFromDB(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        long c = db.delete("UploadData", "Id = " + id, null);
        if (c > 0) return c;
        else return 0;
    }
    public long deleteFromDB(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        long c = db.delete("NewPOIEntry", "id = " + id, null);
        if (c > 0) return c;
        else return 0;
    }


    public ArrayList<FireInfo> getAllProgressList() {

        ArrayList<FireInfo> progressList = new ArrayList<FireInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select Id,Photo1,EntryDate,Latitude,Longitude,OfficeId from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL ", null);

        try {

            while (cur.moveToNext()) {

                FireInfo progress = new FireInfo();

                progress.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);

                //progress.setId(cur.getString(cur.getColumnIndex("Id")));
                progress.setId(Integer.parseInt(cur.getString(cur.getColumnIndex("Id"))));


                progress.setEntry_Date(cur.isNull(cur.getColumnIndex("EntryDate")) == false ? cur.getString(cur.getColumnIndex("EntryDate")) : "");
                progress.setLatitude(cur.isNull(cur.getColumnIndex("Latitude")) == false ? cur.getString(cur.getColumnIndex("Latitude")) : "");
                progress.setLongitude(cur.isNull(cur.getColumnIndex("Longitude")) == false ? cur.getString(cur.getColumnIndex("Longitude")) : "");
                progress.setOfficerId(cur.isNull(cur.getColumnIndex("OfficeId")) == false ? cur.getString(cur.getColumnIndex("OfficeId")) : "");

                progressList.add(progress);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        } finally {
            cur.close();
            db.close();
        }
        return progressList;

    }
    public boolean deleterow(int name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("UploadData", "Id" + "=" + name, null) > 0;
    }
    public ArrayList<PoiInfo> getAllFacilityDetails() {

        ArrayList<PoiInfo> allfacilityDetails = new ArrayList<>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db.rawQuery(
                    "Select * from POIEntry", null);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                PoiInfo facilityDetails = new PoiInfo();

                facilityDetails.setMobile_Number(cur.getString(cur.getColumnIndex("Mobile_Number")));
                facilityDetails.setDistrictcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                facilityDetails.setDistrictName(cur.getString(cur.getColumnIndex("DistrictName")));
                facilityDetails.setThana_Name(cur.getString(cur.getColumnIndex("Thana_Name")));
                facilityDetails.setDesignation(cur.getString(cur.getColumnIndex("Designation")));
                facilityDetails.setPOI(cur.getString(cur.getColumnIndex("Poi_Name")));
                facilityDetails.setPoi_Type(cur.getString(cur.getColumnIndex("Poi_Type")));
                facilityDetails.setPoi_SubType(cur.getString(cur.getColumnIndex("Poi_SubType")));
                facilityDetails.setPoi_SubType_Name(cur.getString(cur.getColumnIndex("Poi_SubType_Name")));
                facilityDetails.setPoiName(cur.getString(cur.getColumnIndex("PoiName")));
                facilityDetails.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                facilityDetails.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                facilityDetails.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                facilityDetails.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);
                facilityDetails.setPhoto2(cur.isNull(cur.getColumnIndex("Photo2")) == false ? cur.getBlob(cur.getColumnIndex("Photo2")) : null);
                //facilityDetails.setPhoto1(cur.getBlob(cur.getColumnIndex("Photo1")));
                //facilityDetails.setPhoto2(cur.getBlob(cur.getColumnIndex("Photo2")));
                facilityDetails.setEntry_Date(cur.getString(cur.getColumnIndex("Entry_Date")));
                facilityDetails.setSlno(cur.getString(cur.getColumnIndex("id")));
                allfacilityDetails.add(facilityDetails);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            allfacilityDetails = null;
        }
        return allfacilityDetails;
    }
    public ArrayList<Block> getBlockLocal(String distId) {
        ArrayList<Block> bdetail = new ArrayList<Block>();
        try {
            String[] param = {distId};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Blocks where DistCode = ? order by BlockName", param);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Block block = new Block();
                block.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
                block.setBlockName((cur.getString(cur.getColumnIndex("BlockName"))));
                block.setDistrictCode((cur.getString(cur.getColumnIndex("DistCode"))));
                bdetail.add(block);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }
    public ArrayList<PoiInfo> getAllEntryById(String slno, String keyId){
        ArrayList<PoiInfo> dewormingEntities=new ArrayList<PoiInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            //Cursor cursor=sqLiteDatabase.rawQuery("select * from InsertDeworming where Id=?",new String[]{slno,keyId});
            Cursor cursor=sqLiteDatabase.rawQuery("select * from NewPOIEntry where User_Id=?"+" AND "+" id=? "+" ORDER BY " + "id " +"ASC",new String[]{slno,keyId});
            int x = cursor.getCount();

            while (cursor.moveToNext())
            {
                PoiInfo dewormingEntity=new PoiInfo();
                dewormingEntity.setSlno((cursor.getString(cursor.getColumnIndex("id"))));
                dewormingEntity.setUpload_by((cursor.getString(cursor.getColumnIndex("User_Id"))));
                dewormingEntity.setDistrictcode((cursor.getString(cursor.getColumnIndex("DistrictCode"))));
                dewormingEntity.setDistrictName((cursor.getString(cursor.getColumnIndex("DistrictName"))));
                dewormingEntity.setBlockcode((cursor.getString(cursor.getColumnIndex("BlockCode"))));
                dewormingEntity.setBlockName((cursor.getString(cursor.getColumnIndex("BlockName"))));
                dewormingEntity.setPoiName((cursor.getString(cursor.getColumnIndex("POIName"))));
                dewormingEntity.setDetails((cursor.getString(cursor.getColumnIndex("Details"))));
                dewormingEntity.setRemarks((cursor.getString(cursor.getColumnIndex("Remarks"))));
                dewormingEntity.setLatitude((cursor.getString(cursor.getColumnIndex("Latitude"))));
                dewormingEntity.setLongitude((cursor.getString(cursor.getColumnIndex("Longitude"))));
                dewormingEntity.setEntry_Date((cursor.getString(cursor.getColumnIndex("Entry_Date"))));
                dewormingEntity.setPhoto(cursor.isNull(cursor.getColumnIndex("Photo")) == false ? cursor.getBlob(cursor.getColumnIndex("Photo")) : null);




                dewormingEntities.add(dewormingEntity);

            }

            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return dewormingEntities ;
    }
//    public long updateNewEntry(NewEntryPOIActivity newEntryActivity, PoiInfo result) {
//
//        long c = -1;
//        try {
//            DataBaseHelper placeData = new DataBaseHelper(newEntryActivity);
//            SQLiteDatabase db = placeData.getWritableDatabase();
//            ContentValues values = new ContentValues();
//
//            //values.put("id",result.getSlno());
//            values.put("User_Id", result.getUpload_by());
//            values.put("DistrictCode", result.getDistrictcode());
//            values.put("DistrictName", result.getDistrictName());
//            values.put("BlockCode", result.getBlockcode());
//            values.put("BlockName", result.getBlockName());
//            values.put("POIName", result.getPoiName());
//            //values.put("Details", result.getDetails());
//            values.put("Remarks", result.getRemarks());
//            values.put("Photo", result.getPhoto());
//            values.put("Latitude", result.getLatitude());
//            values.put("Longitude", result.getLongitude());
//            values.put("Entry_Date", result.getEntry_Date());
//            String[] whereArgs = new String[]{(String.valueOf(result.getSlno()))};
//            c = db.update("NewPOIEntry", values, "id=?", whereArgs);
//            // c = db.insert("InsertDeworming", null, values);
//
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return c;
//        }
//
//        return c;
//
//    }

//    public long updateNewEntry_New(EntryActivity newEntryActivity, PoiInfo result) {
//
//        long c = -1;
//        try {
//            DataBaseHelper placeData = new DataBaseHelper(newEntryActivity);
//            SQLiteDatabase db = placeData.getWritableDatabase();
//            ContentValues values = new ContentValues();
//
//            values.put("Mobile_Number", result.getMobile_Number());
//            values.put("DistrictCode", result.getDistrictcode());
//            values.put("DistrictName", result.getDistrictName());
//            values.put("Thana_Name", result.getThana_Name());
//            values.put("Designation", result.getDesignation());
//            values.put("Poi_Name", result.getPOI());
//            values.put("Poi_Type", result.getPoi_Type());
//            values.put("Poi_SubType", result.getPoi_SubType());
//            values.put("Poi_SubType_Name", result.getPoi_SubType_Name());
//            values.put("PoiName", result.getPoiName());
//            values.put("Remarks", result.getRemarks());
//            values.put("Photo1", result.getPhoto1());
//            values.put("Photo2", result.getPhoto2());
//            values.put("Latitude ", result.getLatitude());
//            values.put("Longitude", result.getLongitude());
//            values.put("Entry_Date", result.getEntry_Date());
//            String[] whereArgs = new String[]{(String.valueOf(result.getSlno()))};
//            c = db.update("POIEntry", values, "id=?", whereArgs);
//            // c = db.insert("InsertDeworming", null, values);
//
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return c;
//        }
//
//        return c;
//
//    }
//    public long InsertNewPOIEntry(NewEntryPOIActivity newEntryActivity, PoiInfo result) {
//
//        long c = -1;
//        try {
//            DataBaseHelper placeData = new DataBaseHelper(newEntryActivity);
//            SQLiteDatabase db = placeData.getWritableDatabase();
//            ContentValues values = new ContentValues();
//
//            values.put("User_Id", result.getUpload_by());
//            values.put("DistrictCode", result.getDistrictcode());
//            values.put("DistrictName", result.getDistrictName());
//            values.put("BlockCode", result.getBlockcode());
//            values.put("BlockName", result.getBlockName());
//            values.put("POIName", result.getPoiName());
//            //values.put("Details", result.getDetails());
//            values.put("Remarks", result.getRemarks());
//            values.put("Photo", result.getPhoto());
//            values.put("Latitude", result.getLatitude());
//            values.put("Longitude", result.getLongitude());
//            values.put("Entry_Date", result.getEntry_Date());
//
//            c = db.insert("NewPOIEntry", null, values);
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return c;
//        }
//
//        return c;
//
//    }
//    public long InsertNewPOIEntry_New(EntryActivity newEntryActivity, PoiInfo result) {
//
//        long c = -1;
//        try {
//            DataBaseHelper placeData = new DataBaseHelper(newEntryActivity);
//            SQLiteDatabase db = placeData.getWritableDatabase();
//            ContentValues values = new ContentValues();
//
//            values.put("Mobile_Number", result.getMobile_Number());
//            values.put("DistrictCode", result.getDistrictcode());
//            values.put("DistrictName", result.getDistrictName());
//            values.put("Thana_Name", result.getThana_Name());
//            values.put("Designation", result.getDesignation());
//            values.put("Poi_Name", result.getPOI());
//            values.put("Poi_Type", result.getPoi_Type());
//            values.put("Poi_SubType", result.getPoi_SubType());
//            values.put("Poi_SubType_Name", result.getPoi_SubType_Name());
//            values.put("PoiName", result.getPoiName());
//            values.put("Remarks", result.getRemarks());
//            values.put("Photo1", result.getPhoto1());
//            values.put("Photo2", result.getPhoto2());
//            values.put("Latitude ", result.getLatitude());
//            values.put("Longitude", result.getLongitude());
//            values.put("Entry_Date", result.getEntry_Date());
//
//            c = db.insert("POIEntry", null, values);
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return c;
//        }
//
//        return c;
//
//    }
    public long getPendingUploadCountPOI(String userid) {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from POIEntry where Latitude IS NOT NULL and Mobile_Number=?", new String[]{userid});
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {


        }

        return x;
    }
    public boolean deleterowPOI(int name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("POIEntry", "id" + "=" + name, null) > 0;
    }
    public ArrayList<District> getDistrictLocal() {

        ArrayList<District> districtList = new ArrayList<District>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db
                    .rawQuery(
                            "SELECT * from  Districts order by DistName_Hin_Eng", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                District district = new District();
                district.setDistrictCode(cur.getString(cur
                        .getColumnIndex("DistCode")));
                district.setDistrictName(cur.getString(cur
                        .getColumnIndex("DistName_Hin_Eng")));


                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return districtList;

    }
    public ArrayList<PoliceStation> getPoliceStationLocal(String distCode) {

        ArrayList<PoliceStation> districtList = new ArrayList<PoliceStation>();

        try {
            String[] param = {distCode};

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * from  POLICE_STATIONS WHERE DIST_CODE =?", param);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                PoliceStation district = new PoliceStation();
                district.setPoliceStation_Code(cur.getString(cur.getColumnIndex("PoliceSt_Code")));
                district.setPoliceStation_Name(cur.getString(cur.getColumnIndex("PoliceStation")));
                district.setDistrict_Code(cur.getString(cur.getColumnIndex("DIST_CODE")));
                district.setDistrict_Name(cur.getString(cur.getColumnIndex("DISTRICT")));


                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return districtList;

    }
    public ArrayList<PoiSubType> getPoiLocal(String poiCode) {
        ArrayList<PoiSubType> bdetail = new ArrayList<PoiSubType>();
        try {
            String[] param = {poiCode};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from POI_Sub_Type where POI_Type_Id = ? order by POI_Type_Id", param);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                PoiSubType block = new PoiSubType();
                block.setPOICode(cur.getString(cur.getColumnIndex("POI_Type_Id")));
                block.setPOISubTypeCode((cur.getString(cur.getColumnIndex("POI_SubType_Id"))));
                block.setPOISubTypeName((cur.getString(cur.getColumnIndex("POI_SubType_Name"))));
                bdetail.add(block);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }
    public long deleteSingleRec(String id, String Mobile_Num) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {Mobile_Num, String.valueOf(id)};
            c = db.delete("POIEntry", "Mobile_Number=? and id=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }
    public long deleteRec(String id) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(id)};
            c = db.delete("POIEntry", "id=?", DeleteWhere);

            this.getWritableDatabase().close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;

    }
    public ArrayList<PoiInfo> getAllEntryDetail(String Userid) {
        //public ArrayList<FillQC_Report> getAllQCEntryDetail() {
        ArrayList<PoiInfo> basicdetail = new ArrayList<PoiInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {Userid};
            Cursor cur = sqLiteDatabase.rawQuery("select * From POIEntry where Mobile_Number=?", args);
            int x = cur.getCount();

            while (cur.moveToNext()) {
                PoiInfo facilityDetails = new PoiInfo();
                facilityDetails.setMobile_Number(cur.getString(cur.getColumnIndex("Mobile_Number")));
                facilityDetails.setDistrictcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                facilityDetails.setDistrictName(cur.getString(cur.getColumnIndex("DistrictName")));
                facilityDetails.setThana_Name(cur.getString(cur.getColumnIndex("Thana_Name")));
                facilityDetails.setDesignation(cur.getString(cur.getColumnIndex("Designation")));
                facilityDetails.setPOI(cur.getString(cur.getColumnIndex("Poi_Name")));
                facilityDetails.setPoi_Type(cur.getString(cur.getColumnIndex("Poi_Type")));
                facilityDetails.setPoi_SubType(cur.getString(cur.getColumnIndex("Poi_SubType")));
                facilityDetails.setPoi_SubType_Name(cur.getString(cur.getColumnIndex("Poi_SubType_Name")));
                facilityDetails.setPoiName(cur.getString(cur.getColumnIndex("PoiName")));
                facilityDetails.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                facilityDetails.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                facilityDetails.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                facilityDetails.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);
                facilityDetails.setPhoto2(cur.isNull(cur.getColumnIndex("Photo2")) == false ? cur.getBlob(cur.getColumnIndex("Photo2")) : null);
                //facilityDetails.setPhoto1(cur.getBlob(cur.getColumnIndex("Photo1")));
                //facilityDetails.setPhoto2(cur.getBlob(cur.getColumnIndex("Photo2")));
                facilityDetails.setEntry_Date(cur.getString(cur.getColumnIndex("Entry_Date")));
                facilityDetails.setSlno(cur.getString(cur.getColumnIndex("id")));

                basicdetail.add(facilityDetails);

            }
            this.getReadableDatabase().close();
            cur.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return basicdetail;
    }
    public ArrayList<PoiInfo> getAllEntryByIdUpload(String Mobile_No, String sl_no){
        ArrayList<PoiInfo> dewormingEntities=new ArrayList<PoiInfo>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            //Cursor cursor=sqLiteDatabase.rawQuery("select * from InsertDeworming where Id=?",new String[]{slno,keyId});
            Cursor cur=sqLiteDatabase.rawQuery("select * from POIEntry where Mobile_Number=?"+" AND "+" id=? "+" ORDER BY " + "id " +"ASC",new String[]{Mobile_No,sl_no});
            int x = cur.getCount();

            while (cur.moveToNext())
            {
                PoiInfo facilityDetails = new PoiInfo();
                facilityDetails.setSlno((cur.getString(cur.getColumnIndex("id"))));
                facilityDetails.setMobile_Number(cur.getString(cur.getColumnIndex("Mobile_Number")));
                facilityDetails.setDistrictcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                facilityDetails.setDistrictName(cur.getString(cur.getColumnIndex("DistrictName")));
                facilityDetails.setThana_Name(cur.getString(cur.getColumnIndex("Thana_Name")));
                facilityDetails.setDesignation(cur.getString(cur.getColumnIndex("Designation")));
                facilityDetails.setPOI(cur.getString(cur.getColumnIndex("Poi_Name")));
                facilityDetails.setPoi_Type(cur.getString(cur.getColumnIndex("Poi_Type")));
                facilityDetails.setPoi_SubType(cur.getString(cur.getColumnIndex("Poi_SubType")));
                facilityDetails.setPoi_SubType_Name(cur.getString(cur.getColumnIndex("Poi_SubType_Name")));
                facilityDetails.setPoiName(cur.getString(cur.getColumnIndex("PoiName")));
                facilityDetails.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                facilityDetails.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                facilityDetails.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                facilityDetails.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);
                facilityDetails.setPhoto2(cur.isNull(cur.getColumnIndex("Photo2")) == false ? cur.getBlob(cur.getColumnIndex("Photo2")) : null);
                //facilityDetails.setPhoto1(cur.getBlob(cur.getColumnIndex("Photo1")));
                //facilityDetails.setPhoto2(cur.getBlob(cur.getColumnIndex("Photo2")));
                facilityDetails.setEntry_Date(cur.getString(cur.getColumnIndex("Entry_Date")));

                dewormingEntities.add(facilityDetails);

            }

            cur.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return dewormingEntities ;
    }
    public UserDetails getUserDetails(String userId, String pass) {

        // PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
        UserDetails userInfo = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[] { userId.trim(), pass };

            Cursor cur = db
                    .rawQuery(
                            "Select * from UserDetailsData WHERE mobilenum=? and Password=?",
                            params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                userInfo = new UserDetails();
                userInfo.setMobile(cur.getString(cur.getColumnIndex("mobilenum")));
                userInfo.setPassword(cur.getString(cur
                        .getColumnIndex("Password")));
                userInfo.setName(cur.getString(cur
                        .getColumnIndex("Name")));
                userInfo.setFatherName(cur.getString(cur
                        .getColumnIndex("fathername")));
                userInfo.setDistrictCode(cur.getString(cur
                        .getColumnIndex("DistCode")));
                userInfo.setBlockCode(cur.getString(cur
                        .getColumnIndex("BlockCode")));
                userInfo.setPanchayatCode(cur.getString(cur
                        .getColumnIndex("PanchayatCode")));
//				userInfo.set_BankCode(cur.getString(cur
//						.getColumnIndex("BankCode")));
//				userInfo.set_PACSBankID(cur.getString(cur
//						.getColumnIndex("PACSBankID")));

                userInfo.setAuthenticated(true);

                // ,[LastVisitedDate] Text NULL,[Photo] Text NULL,[Designation]
                // Text NULL,[TotalPending] Text NULL,[BlockCode] Text NULL)");

            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            userInfo = null;
        }
        return userInfo;

    }
    public long getalluserdetails(UserDetails result) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();


            ContentValues values = new ContentValues();
            values.put("Name", result.getName());
            values.put("fathername", result.getFatherName());
            values.put("mobilenum", result.getMobile());
            values.put("address", result.getAddress());
            values.put("DistCode", result.getDistrictCode());
            values.put("BlockCode", result.getBlockCode());
            values.put("PanchayatCode", result.getPanchayatCode());

            values.put("Password", result.getPassword());
            values.put("Registered", result.getIsRegistered());



            //String[] whereArgs = new String[]{CommonPref.getUserDetails(New_Entry.this).getUserID()};
            String[] whereArgs1 = new String[]{result.getMobile()};

            c = db.update("UserDetailsData", values, "mobilenum=? ", whereArgs1);

            if (!(c > 0)) {

                c = db.insert("UserDetailsData", null, values);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }
    public long getUserCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UserDetailsData", null);

            x = cur.getCount();

            cur.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return x;
    }
}
