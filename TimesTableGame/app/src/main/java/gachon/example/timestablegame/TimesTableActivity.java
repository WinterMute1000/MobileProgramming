package gachon.example.timestablegame;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;


public class TimesTableActivity extends AppCompatActivity implements OnClickListener
{

    private Button[] buttons=new Button[12];
    private ProgressBar timeoutBar;
    private int timeProcess=0;
    private Handler timeRunningHandler,buttonClickHandler;
    private TextView problemText,answerText,resultText;
    private int num1,num2;
    private Thread timeoutThread;
    static final int ON_BUTTON_CLICKED=1001;
    static final int TIME_RUNNING=1002;
    static final int MENU_EXIT=2001;
    static final int MENU_CLEAR=MENU_EXIT+1;
    static final int ENTER=10;
    static final int CANCEL=11;
    private static final Random RANDOM = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_table);
        setHandler();
        setButtons();
        setTexts();
        setTimeProgressBar();
        timeoutThread.start();
    }

    private void setTimeProgressBar()
    {
        timeoutBar=(ProgressBar)findViewById(R.id.timeout);
        timeoutThread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    Message message = Message.obtain(timeRunningHandler, TIME_RUNNING, timeProcess, 0);
                    timeRunningHandler.sendMessage(message);

                    try { Thread.sleep(600); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
        });

    }

    private void setTexts()
    {
        problemText=(TextView)findViewById(R.id.problem);
        answerText=(TextView)findViewById(R.id.answer);
        resultText=(TextView)findViewById(R.id.result);

        resultText.setText("0");
        makeNewProblem();
    }


    private void setButtons()
    {
        buttons[0]=(Button)findViewById(R.id.number0);
        buttons[1]=(Button)findViewById(R.id.number1);
        buttons[2]=(Button)findViewById(R.id.number2);
        buttons[3]=(Button)findViewById(R.id.number3);
        buttons[4]=(Button)findViewById(R.id.number4);
        buttons[5]=(Button)findViewById(R.id.number5);
        buttons[6]=(Button)findViewById(R.id.number6);
        buttons[7]=(Button)findViewById(R.id.number7);
        buttons[8]=(Button)findViewById(R.id.number8);
        buttons[9]=(Button)findViewById(R.id.number9);
        buttons[10]=(Button)findViewById(R.id.enter);
        buttons[11]=(Button)findViewById(R.id.cancel);

        for(int i=0;i<buttons.length;i++)
            buttons[i].setOnClickListener(this);

    }

    protected void setHandler()
    {
        timeRunningHandler=new Handler()
        {
            public void handleMessage(Message msg)
            {
                if(msg.what==TIME_RUNNING)
                {

                    if(timeoutBar.getProgress()==timeoutBar.getMax())
                        endGame(RESULT_OK);
                    else
                        {
                            timeProcess++;
                            timeoutBar.setProgress(timeProcess);
                        }
                }
            }
        };
        buttonClickHandler=new Handler()
        {
            public void handleMessage(Message msg)
            {
                if(msg.what==ON_BUTTON_CLICKED)
                {
                    if(msg.arg1==ENTER)
                    {
                        int answer=Integer.parseInt(answerText.getText().toString());

                        if(answer==num1*num2)
                        {
                            int beforeResult=Integer.parseInt(resultText.getText().toString());
                            beforeResult++;
                            resultText.setText(Integer.toString(beforeResult));
                            makeNewProblem();
                        }

                        answerText.setText("");
                    }
                    else if(msg.arg1==CANCEL)
                        answerText.setText("");
                    else
                    {
                        String value=buttons[msg.arg1].getText().toString();
                        answerText.append(value);
                    }
                }
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Menu.NONE,MENU_EXIT,Menu.NONE,"Exit");
        menu.add(Menu.NONE,MENU_CLEAR,Menu.NONE,"Clear");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case MENU_EXIT:
                endGame(RESULT_OK);
                break;
            case MENU_CLEAR:
                answerText.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        int buttonIndex=0;
        switch(view.getId())
        {
            case R.id.number0:
                buttonIndex=0;
                break;
            case R.id.number1:
                buttonIndex=1;
                break;
            case R.id.number2:
                buttonIndex=2;
                break;
            case R.id.number3:
                buttonIndex=3;
                break;
            case R.id.number4:
                buttonIndex=4;
                break;
            case R.id.number5:
                buttonIndex=5;
                break;
            case R.id.number6:
                buttonIndex=6;
                break;
            case R.id.number7:
                buttonIndex=7;
                break;
            case R.id.number8:
                buttonIndex=8;
                break;
            case R.id.number9:
                buttonIndex=9;
                break;
            case R.id.enter:
                buttonIndex=ENTER;
                break;
            case R.id.cancel:
                buttonIndex=CANCEL;
                break;
        }
        Message message=Message.obtain(buttonClickHandler,ON_BUTTON_CLICKED,
                buttonIndex,0);
        buttonClickHandler.sendMessage(message);
    }

    private void makeNewProblem()
    {
        num1=RANDOM.nextInt(7)+2;
        num2=RANDOM.nextInt(7)+2;
        problemText.setText(Integer.toString(num1)+" * "+Integer.toString(num2));
    }

    private void endGame(int result)
    {
        timeoutThread.interrupt();
        Intent i=getIntent();

        timeRunningHandler.removeMessages(TIME_RUNNING);
        i.putExtra("gameResult",resultText.getText().toString());
        setResult(result,i);
        finish();
    }
}
