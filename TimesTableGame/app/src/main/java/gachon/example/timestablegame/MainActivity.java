package gachon.example.timestablegame;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView resultView;
    ArrayList<String> result=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    static final int MENU_GAME_START=1;
    static final int TIMES_TABLE_GAME=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView=(ListView)findViewById(R.id.game_result);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                result);
        resultView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case MENU_GAME_START:
                Intent i=new Intent(this,TimesTableActivity.class);
                startActivityForResult(i,TIMES_TABLE_GAME);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Menu.NONE,MENU_GAME_START,Menu.NONE,
                "Game Start");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==TIMES_TABLE_GAME)
        {
            if (resultCode == RESULT_OK)
            {
                String game_result = data.getStringExtra("gameResult");
                result.add(game_result);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
