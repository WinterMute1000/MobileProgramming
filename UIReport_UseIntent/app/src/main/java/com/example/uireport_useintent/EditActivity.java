package com.example.uireport_useintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gachon.example.uireport_useintent.R;

public class EditActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText name_edit;
    Button bt_ok,bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setDesignInstance();
    }
    @Override
    public void onClick(View v)
    {
        Intent edit_intent=getIntent();
        boolean is_text_exist=edit_intent.getBooleanExtra("is_text_exist",false);
        String edit_string=name_edit.getText().toString();
        switch(v.getId())
        {
            case R.id.button_ok:
                if (!is_text_exist)
                    edit_intent.putExtra("append_string",edit_string);
                else
                    edit_intent.putExtra("append_string","\n"+edit_string);

                edit_intent.putExtra("is_text_exist",true);
                setResult(RESULT_OK,edit_intent);
                break;

            case R.id.button_cancel:
                setResult(RESULT_CANCELED,edit_intent);
                break;
        }
        finish();
    }

    private void setDesignInstance()
    {
        bt_ok=(Button)findViewById(R.id.button_ok);
        bt_cancel=(Button)findViewById(R.id.button_cancel);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        name_edit=(EditText)findViewById(R.id.form_name);
    }
}
