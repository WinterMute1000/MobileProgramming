package gachon.example.uireport;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final int INPUT_MENU=Menu.FIRST;
    public static final int STYLE_MENU=Menu.FIRST+1;
    public static final int FONT_BOLD=Menu.FIRST+2;
    public static final int FONT_ITALIC=Menu.FIRST+3;
    public static final int FONT_NORMAL=Menu.FIRST+4;

    Button bt_ok,bt_cancel;
    TextView name_view;
    EditText name_edit;
    RelativeLayout edit_layout;
    Menu menu;
    boolean is_text_exit;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(is_text_exit)
            this.menu.findItem(STYLE_MENU).setEnabled(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        is_text_exit=false;
        setContentView(R.layout.activity_main);
        setDesignInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.menu=menu;
        menu.add(Menu.NONE,INPUT_MENU,Menu.NONE,"input").setIcon(R.drawable.grid_world);

        SubMenu style_menu=menu.addSubMenu(STYLE_MENU,STYLE_MENU,Menu.NONE,"style")
                .setIcon(R.drawable.grid_world);

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
                name_view.setVisibility(View.INVISIBLE);
                edit_layout.setVisibility(View.VISIBLE);
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

    public void onClick(View v)
    {
        edit_layout.setVisibility(View.INVISIBLE);
        name_view.setVisibility(View.VISIBLE);

        switch(v.getId())
        {
            case R.id.button_ok:
                if(!is_text_exit)
                {
                    name_view.append(name_edit.getText());
                    is_text_exit = true;
                }
                else
                    name_view.append("\n"+name_edit.getText());
                break;
        }
        name_edit.setText("");
    }

    private void setDesignInstance()
    {
        bt_ok=(Button)findViewById(R.id.button_ok);
        bt_cancel=(Button)findViewById(R.id.button_cancel);
        name_view=(TextView)findViewById(R.id.name_view);
        name_edit=(EditText)findViewById(R.id.form_name);
        edit_layout=(RelativeLayout)findViewById(R.id.edit_layout);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

}
