package ir.bolive.app.jamisapp.database;

public class DB_Constants {

    //Database and table names
    public static final String DB_NAME="DentalDb";
    public static final int DB_VERSION=1;
    public static final String TABLE_PATIENT="tbl_patients";
    public static final String TABLE_GALLERY="tbl_gallery";
    public static final String TABLE_FACE_ARGS="tbl_face_args";

    //Table columns
    //Patient information
    public static final String PATIENT_ID="pid";
    public static final String FULLNAME="fullname";
    public static final String NATIONAL_CODE="nationalcode";
    public static final String PHONE="phone";
    public static final String REF_DATE="refdate";

    public static final String CREATE_TABLE_PATIENT="CREATE TABLE "+TABLE_PATIENT+" ( "+
            PATIENT_ID+" INT PRIMARY KEY AUTOINCREMENT,"+
            FULLNAME+" TEXT,"+
            NATIONAL_CODE+" TEXT,"+
            PHONE+" TEXT,"+
            REF_DATE+" TEXT"+
            ")";
    public static final String DROP_TABLE_PATIENT="DROP TABLE IF EXISTS "+TABLE_PATIENT;

    //Patient Galley
    public static final String GALLERY_ID="gid";
    public static final String PATIENT_ID_FK_GALLERY="pid";
    public static final String IMAGE_TITLE="title";
    public static final String IMAGE="image";

    public static final String CREATE_TABLE_GALLERY="CREATE TABLE "+TABLE_GALLERY+" ( "+
            GALLERY_ID+" INT PRIMARY KEY AUTOINCREMENT,"+
            PATIENT_ID_FK_GALLERY+" INT,"+
            IMAGE_TITLE+" TEXT,"+
            IMAGE+" BLOB"+
            ")";
    public static final String DROP_TABLE_GALLERY="DROP TABLE IF EXISTS "+TABLE_GALLERY;

    //Patient Face Args
    public static final String ARG_ROW_ID="arg_id";
    public static final String PATIENT_ID_FK_FACE="pid";
    public static final String UPPER_CENTRAL_ANS="upca";
    public static final String LOWER_CENTRAL_ANS="lwca";
    public static final String UPPER_GING="upging";
    public static final String LOWER_GING="lwging";
    public static final String MID_LINE="midline";
    public static final String CHIN_MODE="chinmode";
    public static final String X_EAR="x_ear";
    public static final String Y_EAR="y_ear";
    public static final String X_EYE="x_eye";
    public static final String Y_EYE="y_eye";
    public static final String X_EYEBROW="x_eyebrow";
    public static final String Y_EYEBROW="y_eyebrow";
    public static final String X_RAMUS="x_ramus";
    public static final String Y_RAMUS="y_ramus";

    public static final String CREATE_TABLE_FACE="CREATE TABLE "+TABLE_FACE_ARGS+" ( "+
            ARG_ROW_ID+" INT PRIMARY KEY AUTOINCREMENT,"+
            PATIENT_ID_FK_FACE+" INT,"+
            UPPER_CENTRAL_ANS+" REAL,"+
            LOWER_CENTRAL_ANS+" REAL,"+
            UPPER_GING+" REAL,"+
            LOWER_GING+" REAL,"+
            MID_LINE+" REAL,"+
            CHIN_MODE+" INT,"+
            X_EAR+" REAL,"+
            Y_EAR+" REAL,"+
            X_EYE+" REAL,"+
            Y_EYE+" REAL,"+
            X_EYEBROW+" REAL,"+
            Y_EYEBROW+" REAL,"+
            X_RAMUS+" REAL,"+
            Y_RAMUS+" REAL,"+
            ")";
    public static final String DROP_TABLE_FACE="DROP TABLE IF EXISTS "+TABLE_FACE_ARGS;
}
