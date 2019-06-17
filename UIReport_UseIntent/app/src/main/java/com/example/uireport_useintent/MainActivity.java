package com.example.uireport_useintent;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import gachon.example.uireport_useintent.R;

public class MainActivity extends AppCompatActivity
{

    public static final int INPUT_MENU=Menu.FIRST;
    public static final int STYLE_MENU=Menu.FIRST+1;
    public static final int FONT_BOLD=Menu.FIRST+2;
    public static final int FONT_ITALIC=Menu.FIRST+3;
    public static final int FONT_NORMAL=Menu.FIRST+4;
    public static final int REQUEST_CODE=100;

    //Button bt_ok,bt_cancel;
    TextView name_view;
    //EditText name_edit;
    //RelativeLayout edit_layout;
    Menu menu;
    boolean is_text_exist;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(is_text_exist)
            this.menu.findItem(STYLE_MENU).setEnabled(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        is_text_exist=false;
        setContentView(R.layout.activity_main);
        setDesignInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu=menu;
        menu.add(Menu.NONE,INPUT_MENU,Menu.NONE,"input");

        SubMenu style_menu=menu.addSubMenu(STYLE_MENU,STYLE_MENU,Menu.NONE,"style");

        style_menu.add(STYLE_MENU,FONT_BOLD,Menu.NONE,"Bold");
        style_menu.add(STYLE_MENU,FONT_ITALIC,Menu.NONE,"Italic");
        style_menu.add(STYLE_MENU,FONT_NORMAL,Menu.NONE,"Normal");

        menu.findItem(STYLE_MENU).setEnabled(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case INPUT_MENU:
                Intent edit_intent=new Intent(MainActivity.this,EditActivity.class);
                edit_intent.putExtra("is_text_exist",is_text_exist);
                startActivityForResult(edit_intent,REQUEST_CODE);
                break;
            case FONT_BOLD:
                name_view.setTypeface(null, Typeface.BOLD);
                break;
            case FONT_ITALIC:
                name_view.setTypeface(null, Typeface.ITALIC);
                break;
            case FONT_NORMAL:
                name_view.setTypeface(null, Typeface.NORMAL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDesignInstance()
    {
        name_view=(TextView)findViewById(R.id.name_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                is_text_exist=data.getBooleanExtra("is_text_exist",true);
                name_view.append(data.getStringExtra("append_string"));
            }
        }
    }

}
