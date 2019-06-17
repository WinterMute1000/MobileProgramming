package com.example.finaltest;
//Not My Code
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class StudentsDBManager extends SQLiteOpenHelper {

    // DB 명, 테이블 명, DB 버전 정보를 정의한다.
    // ========================================================================
    static final String     DB_STUDENTS     = "Students.db";
    static final String     TABLE_STUDENTS  = "Students";
    static final int        DB_VERSION      = 2;
    // ========================================================================

    Context         mContext    = null;

    public static StudentsDBManager     mDbManager = null;

    // DB 매니저 객체는 싱글톤(singleton)으로 구현하도록 한다.
    // ========================================================================
    public static StudentsDBManager getInstance( Context context )
    {
        if ( mDbManager == null )
        {
            mDbManager = new StudentsDBManager( context,
                    DB_STUDENTS,
                    null,
                    DB_VERSION );
        }

        return mDbManager;
    }
    // ========================================================================

    private StudentsDBManager( Context        context,
                               String         dbName,
                               CursorFactory  factory,
                               int            version )
    {
        super( context, dbName, factory, version );

        mContext = context;
    }

    @Override
    public void onOpen( SQLiteDatabase db )
    {
        super.onOpen( db );
    }

    public long insert( ContentValues addRowValue )
    {
        return getWritableDatabase().insert( TABLE_STUDENTS,
                null,
                addRowValue );
    }

    public int insertAll( ContentValues[] values )
    {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        for( ContentValues contentValues : values )
        {
            db.insert( TABLE_STUDENTS, null, contentValues );
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return values.length;
    }

    public Cursor query( String[] columns,
                         String selection,
                         String[] selectionArgs,
                         String groupBy,
                         String having,
                         String orderBy )
    {
        return getReadableDatabase().query( TABLE_STUDENTS,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
    }

    public int update( ContentValues updateRowValue,
                       String whereClause,
                       String[] whereArgs )
    {
        return getWritableDatabase().update( TABLE_STUDENTS,
                updateRowValue,
                whereClause,
                whereArgs );
    }

    public int delete( String whereClause,
                       String[] whereArgs )
    {
        return getWritableDatabase().delete( TABLE_STUDENTS,
                whereClause,
                whereArgs );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS +
                "(" + "_id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number        TEXT, " +
                "name          TEXT, " +
                "department    TEXT, " +
                "age           TEXT, " +
                "grade         INTEGER ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          int oldVersion, int newVersion) {
        if( oldVersion < newVersion )
        {
            // 기존 테이블을 삭제하고 새로운 테이블을 생성한다.
            // ================================================================
            sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_STUDENTS );
            onCreate( sqLiteDatabase );
            // ================================================================
        }
    }
}

