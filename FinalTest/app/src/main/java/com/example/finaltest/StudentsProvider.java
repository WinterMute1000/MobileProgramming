package com.example.finaltest;
//Not My Code

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class StudentsProvider extends ContentProvider {

    public StudentsDBManager mDbManager = null;

    public StudentsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        return mDbManager.delete( selection, selectionArgs );
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long rowId = mDbManager.insert( values );

        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int bulkInsert( Uri uri,
                           ContentValues[] values )
    {
        return mDbManager.insertAll( values );
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        mDbManager = StudentsDBManager.getInstance( getContext() );
        return true;
        //return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        return mDbManager.query( projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder );

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return mDbManager.update( values, selection, selectionArgs );
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}