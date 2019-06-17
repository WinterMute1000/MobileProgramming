package com.example.finaltest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button insertButton,updateButton,deleteButton,queryButton;
    int recordNum=0;
    String columnId;
    String columnNumber;
    String columnName;
    String columnDepartment;
    String columnAge;
    String columnGrade;
    EditText resultText;
    ContentResolver cr;
    Cursor cursor;
    StudentsDBManager dbManager;
    private static final Uri CONTENT_URI=Uri.parse("content://com.example.finaltest");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cr=getContentResolver();
        cursor = cr.query(CONTENT_URI, null, null, null, null);
        columnId=cursor.getColumnName(0);
        columnNumber=cursor.getColumnName(1);
        columnName=cursor.getColumnName(2);
        columnDepartment=cursor.getColumnName(3);
        columnAge=cursor.getColumnName(4);
        columnGrade=cursor.getColumnName(5);

        dbManager=StudentsDBManager.getInstance(this);

        insertButton=(Button)findViewById(R.id.insert);
        updateButton=(Button)findViewById(R.id.update);
        deleteButton=(Button)findViewById(R.id.delete);
        queryButton=(Button)findViewById(R.id.query);
        resultText=(EditText)findViewById(R.id.edit_text);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.insert:
                insertData();
                cursor = cr.query(CONTENT_URI, null, null, null, null);
                recordNum=cursor.getCount();
                String recordInsert="레코드 추가: "+recordNum+"\n";
                resultText.setText(recordInsert);
                break;
            case R.id.update:
                int updateDataNum=updateData();
                String recordUpdate="레코드 갱신: "+updateDataNum+"\n";
                resultText.setText(recordUpdate);
                break;
            case R.id.delete:
                deleteAllData();
                String recordDelete="삭제된 레코드 수: "+recordNum+"\n";
                resultText.setText(recordDelete);
                recordNum=0;
                break;
            case R.id.query:
                cursor = cr.query(CONTENT_URI, null, null, null, null);
                showQueryResult(cursor);
                break;
        }
    }

    private void insertData()
    {
        ContentValues newData=new ContentValues();
        newData.put(columnNumber,"200106054");
        newData.put(columnName,"홍길동");
        newData.put(columnDepartment,"컴퓨터");
        newData.put(columnAge,"18");
        newData.put(columnGrade,3);

        cr.insert(CONTENT_URI,newData);
        recordNum++;

    }

    private int updateData()
    {
        ContentValues newData=new ContentValues();
        newData.put(columnNumber,"200106054");
        newData.put(columnName,"고길동");
        newData.put(columnDepartment,"컴퓨터");
        newData.put(columnAge,"18");
        newData.put(columnGrade,3);

        int result=cr.update(CONTENT_URI,newData,columnName+"= '홍길동'"
                ,null);

        return result;
    }

    private void deleteAllData()
    {
        cr.delete(CONTENT_URI,null,null);
    }


    private void showQueryResult(Cursor cursor)
    {
        int queryCount=cursor.getCount();

        String resultString="";

        if(queryCount!=0)
        {
            cursor.moveToFirst();
            for(int i=0;i<queryCount;i++)
            {
                resultString+="id:"+cursor.getInt(0)+"\n";
                resultString+=columnNumber+":"+cursor.getString(1)+"\n";
                resultString+=columnName+":"+cursor.getString(2)+"\n";
                resultString+=columnDepartment+":"+cursor.getString(3)+"\n";
                resultString+=columnAge+":"+cursor.getString(4)+"\n";
                resultString+=columnGrade+":"+cursor.getInt(5)+"\n";
                resultString+="----------------------------------\n";
                cursor.moveToNext();
            }
        }

        resultString+="Total:"+queryCount+"\n";
        resultText.setText(resultString);
    }
}
