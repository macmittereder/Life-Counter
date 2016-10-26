package mitterederstudios.com.lifecounterpaid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseAnalytics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ReviewCurrentGame extends ActionBarActivity {
    TextView display;
    ArrayList<ArrayList<Integer>> lifeOutput;
    int arrayLine1;
    Integer temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityreviewcurrentgame);
        Initialize();
        ParseAnalytics.trackAppOpened(getIntent());
        try{
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("temp")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while((inputString = inputReader.readLine()) != null) {
                char[] charArray = inputString.toCharArray();
                try{
                    if(charArray[3] == '+'){
                        if(charArray[2] == '1'){
                            if(arrayLine1 != 0){
                                if(lifeOutput.get(arrayLine1-1).get(0) == 1 &&
                                        lifeOutput.get(arrayLine1-1).get(1) > 0){
                                    temp = lifeOutput.get(arrayLine1-1).get(1)+1;
                                    lifeOutput.get(arrayLine1-1).set(1, temp);
                                }else{
                                    lifeOutput.add(new ArrayList<Integer>());
                                    lifeOutput.get(arrayLine1).add(0, 1);
                                    lifeOutput.get(arrayLine1).add(1, 1);
                                    arrayLine1++;
                                }
                            }else{
                                lifeOutput.add(new ArrayList<Integer>());
                                lifeOutput.get(arrayLine1).add(0, 1);
                                lifeOutput.get(arrayLine1).add(1, 1);
                                arrayLine1++;
                            }
                        }else{
                            if(arrayLine1 != 0){
                                if(lifeOutput.get(arrayLine1-1).get(0) == 2 &&
                                        lifeOutput.get(arrayLine1-1).get(1) > 0){
                                    temp = lifeOutput.get(arrayLine1-1).get(1)+1;
                                    lifeOutput.get(arrayLine1-1).set(1, temp);
                                }else{
                                    lifeOutput.add(new ArrayList<Integer>());
                                    lifeOutput.get(arrayLine1).add(0, 2);
                                    lifeOutput.get(arrayLine1).add(1, 1);
                                    arrayLine1++;
                                }
                            }else {
                                lifeOutput.add(new ArrayList<Integer>());
                                lifeOutput.get(arrayLine1).add(0, 2);
                                lifeOutput.get(arrayLine1).add(1, 1);
                                arrayLine1++;
                            }
                        }
                    }else {
                        if (charArray[2] == '1') {
                            if (arrayLine1 != 0) {
                                if (lifeOutput.get(arrayLine1-1).get(0) == 1 &&
                                        lifeOutput.get(arrayLine1-1).get(1) < 0){
                                    temp = lifeOutput.get(arrayLine1-1).get(1)-1;
                                    lifeOutput.get(arrayLine1-1).set(1, temp);
                                } else {
                                    lifeOutput.add(new ArrayList<Integer>());
                                    lifeOutput.get(arrayLine1).add(0, 1);
                                    lifeOutput.get(arrayLine1).add(1, -1);
                                    arrayLine1++;
                                }
                            } else {
                                lifeOutput.add(new ArrayList<Integer>());
                                lifeOutput.get(arrayLine1).add(0, 1);
                                lifeOutput.get(arrayLine1).add(1, -1);
                                arrayLine1++;
                            }
                        } else {
                            if (arrayLine1 != 0) {
                                if (lifeOutput.get(arrayLine1-1).get(0) == 2 &&
                                        lifeOutput.get(arrayLine1-1).get(1) < 0) {
                                    temp = lifeOutput.get(arrayLine1-1).get(1)-1;
                                    lifeOutput.get(arrayLine1-1).set(1, temp);
                                } else {
                                    lifeOutput.add(new ArrayList<Integer>());
                                    lifeOutput.get(arrayLine1).add(0, 2);
                                    lifeOutput.get(arrayLine1).add(1, -1);
                                    arrayLine1++;
                                }
                            } else {
                                lifeOutput.add(new ArrayList<Integer>());
                                lifeOutput.get(arrayLine1).add(0, 2);
                                lifeOutput.get(arrayLine1).add(1, -1);
                                arrayLine1++;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String output = "";
        for(int i = 0; i < lifeOutput.size(); i++)
            if(lifeOutput.get(i).get(0) == 1)
                if(lifeOutput.get(i).get(1) > 0)
                    output += String.format("Player 1: <font color='#00E859'>%d</font><br>",
                            lifeOutput.get(i).get(1));
                else
                    output += String.format("Player 1: <font color='#FF1717'>%d</font><br>",
                            lifeOutput.get(i).get(1));
            else
            if(lifeOutput.get(i).get(1) > 0)
                output += String.format("Player 2: <font color='#00E859'>%d</font><br>",
                        lifeOutput.get(i).get(1));
            else
                output += String.format("Player 2: <font color='#FF1717'>%d</font><br>",
                        lifeOutput.get(i).get(1));
        display.setText(Html.fromHtml(output));
    }
    private void Initialize() {
        display = (TextView) findViewById(R.id.reviewCurrentGameDisplay);
        display.setMovementMethod(new ScrollingMovementMethod());
        arrayLine1 = 0;
        lifeOutput = new ArrayList<ArrayList<Integer>>();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
        }
        return super.onOptionsItemSelected(item);
    }
}