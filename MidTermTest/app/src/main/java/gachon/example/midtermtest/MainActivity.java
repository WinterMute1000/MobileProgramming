package gachon.example.midtermtest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity

{
    static final int EDIT_REQUEST=100;
    private ArrayAdapter<String> adapter;
    private TextView text_view;
    private ListView list_view;
    private RelativeLayout r_layout;
    private int pos=0;
    static final int TEXT_ID=1000;
    String[] menus={"korea","france","japan"};


    class ItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            Intent intent=new Intent(MainActivity.this,ItemActivity.class);
            String string=(String)list_view.getAdapter().getItem(i);
            pos=i;
            intent.putExtra("position",pos);
            intent.putExtra("string",string);
            startActivityForResult(intent,EDIT_REQUEST);
        }
    }
    class ItemLongClickListener implements ListView.OnItemLongClickListener
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            pos=i;
            return false;
        }
    }

    ItemClickListener item_click;
    ItemLongClickListener item_long_click;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        item_click=new ItemClickListener();
        item_long_click=new ItemLongClickListener();

        super.onCreate(savedInstanceState);
        setRelativeLayout();
    }

    protected void setRelativeLayout()
    {
        r_layout=new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        r_layout.setLayoutParams(params);

        setTextView();
        setListView();
        r_layout.addView(text_view);
        r_layout.addView(list_view);

        setContentView(r_layout);
    }

    protected void setListView()
    {
        list_view=new ListView(this);

        this.adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,menus);

        list_view.setAdapter(this.adapter);

        RelativeLayout.LayoutParams list_params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        list_params.addRule(RelativeLayout.BELOW,text_view.getId());
        list_view.setLayoutParams(list_params);
        list_view.setOnItemClickListener(item_click);
        list_view.setOnItemLongClickListener(item_long_click);
        registerForContextMenu(list_view);
    }

    protected void setTextView()
    {
        text_view=new TextView(this);
        text_view.setId(TEXT_ID);
        text_view.setText("Intent sample");
        text_view.setTextSize(30);
        RelativeLayout.LayoutParams text_params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        text_view.setLayoutParams(text_params);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.add(0,1,0,"update");
        menu.add(0,2,0,"delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case 1:
                Intent intent=new Intent(MainActivity.this,ItemActivity.class);
                String string=(String)list_view.getAdapter().getItem(pos);
                intent.putExtra("position",pos);
                intent.putExtra("string",string);
                startActivityForResult(intent,EDIT_REQUEST);
                return true;
        }
        return false;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                int position=data.getIntExtra("position",0);
                String edit_string=data.getStringExtra("edit_string");
                menus[position]=edit_string;

                this.adapter=new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,menus);

                list_view.setAdapter(this.adapter);
            }
        }
    }
}
