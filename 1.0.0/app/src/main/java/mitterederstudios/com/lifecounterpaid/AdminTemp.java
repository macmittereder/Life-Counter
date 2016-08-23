package mitterederstudios.com.lifecounterpaid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AdminTemp extends ActionBarActivity {

    TextView output, arrayOutput;
    ArrayList<ArrayList<Integer>> lifeOutput;
    int arrayLine1;
    Integer temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityadmintemp);
        Initialize();
        try{
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("temp")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
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
            output.setText(stringBuffer.toString());
            arrayOutput.setText(lifeOutput.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void Initialize(){
        output = (TextView) findViewById(R.id.adminTempOutput);
        arrayOutput = (TextView) findViewById(R.id.adminArrayOutput);
        arrayLine1 = 0;
        lifeOutput = new ArrayList<ArrayList<Integer>>();
    }
}
