package com.example.midtermtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gachon.example.midtermtest.R;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText edit_text;
    private Button bt_ok;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        i=getIntent();
        edit_text=(EditText)findViewById(R.id.edit);
        bt_ok=(Button)findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(this);

        edit_text.setText(getIntent().getStringExtra("string"));
    }

    @Override
    public void onClick(View view)
    {
        String edit_string=edit_text.getText().toString();
        i.putExtra("edit_string",edit_string);
        setResult(RESULT_OK,i);
        finish();
    }
}
