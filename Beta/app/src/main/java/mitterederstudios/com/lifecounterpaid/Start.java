package mitterederstudios.com.lifecounterpaid;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.os.Vibrator;

import com.parse.Parse;
import com.parse.ParseAnalytics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Start extends ActionBarActivity implements View.OnClickListener{

    Button plOPlus, plOMinis, plTPlus, plTMinis, plTAddToken, plOAddToken;
    TextView nameT, nameO, counterO, counterT, display;
    Boolean isRotated;
    Random rnd;
    Player One, Two;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    LinearLayout oToke, tToke, tButt, oButt, oContToke, tContToke;
    RelativeLayout mainLayout;
    ArrayList<Tokens> oneToke, twoToke;
    Set<String> savedNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Take care of menu button shit
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitystart);
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Parse.initialize(this, "bb4xpvXmjDLpW8ghZarq0RYlE9hDpFY5L3EMv4eS", "1ozKCGbF5mLX1AJflpwDvgQHFeOWijGK5qGx660D");
        ParseAnalytics.trackAppOpened(getIntent());
        Initialize();
    }
    @Override
    public void onClick(View v) {
        Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        switch (v.getId()){
            case R.id.plOMinus:
                One.addLife(-1);
                refreshLife();
                writeToFile(false, One);
                if(One.getLife() == 0 || Two.getLife() == 0){
                    saveGame();
                }
                switch (One.getLife()){
                    case 0:
                        vib.vibrate(100);
                        break;
                    case 1:
                        vib.vibrate(100);
                        break;
                    case 2:
                        vib.vibrate(100);
                        break;
                    case 3:
                        vib.vibrate(100);
                        break;
                    case 4:
                        vib.vibrate(100);
                        break;
                    case 5:
                        vib.vibrate(100);
                        break;
                }
                break;
            case R.id.plOPlus:
                One.addLife(1);
                refreshLife();
                writeToFile(true, One);
                break;
            case R.id.plTMinus:
                Two.addLife(-1);
                refreshLife();
                writeToFile(false, Two);
                if(One.getLife() == 0 || Two.getLife() == 0){
                    saveGame();
                }
                switch (Two.getLife()){
                    case 0:
                        vib.vibrate(100);
                        break;
                    case 1:
                        vib.vibrate(100);
                        break;
                    case 2:
                        vib.vibrate(100);
                        break;
                    case 3:
                        vib.vibrate(100);
                        break;
                    case 4:
                        vib.vibrate(100);
                        break;
                    case 5:
                        vib.vibrate(100);
                        break;
                }
                break;
            case R.id.plTPlus:
                Two.addLife(1);
                refreshLife();
                writeToFile(true, Two);
                break;
            case R.id.plOAddToken:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                LinearLayout ll=new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);
                final EditText attack = new EditText(this);
                final EditText defense = new EditText(this);
                attack.setInputType(InputType.TYPE_CLASS_NUMBER);
                defense.setInputType(InputType.TYPE_CLASS_NUMBER);
                attack.setHint("Power");
                defense.setHint("Toughness");
                ll.addView(attack);
                ll.addView(defense);
                alert.setView(ll);
                alert.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try{
                            One.addToken(
                                    Integer.parseInt(attack.getText().toString()),
                                    Integer.parseInt(defense.getText().toString()));
                            Toast t = Toast.makeText(getApplicationContext(),
                                    attack.getText().toString() + "/" +
                                            defense.getText().toString() + " Token Created",
                                    Toast.LENGTH_SHORT);
                            t.show();
                            addTokens(One, Integer.parseInt(attack.getText().toString()),
                                    Integer.parseInt(defense.getText().toString()));
                        }catch(Exception e){
                            Toast t = Toast.makeText(getApplicationContext(), "Enter a number", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                });

                alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();
                break;
            case R.id.plTAddToken:
                final AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
                LinearLayout ll1=new LinearLayout(this);
                ll1.setOrientation(LinearLayout.VERTICAL);
                final EditText attack1 = new EditText(this);
                final EditText defense1 = new EditText(this);
                attack1.setHint("Power");
                defense1.setHint("Toughness");
                attack1.setInputType(InputType.TYPE_CLASS_NUMBER);
                defense1.setInputType(InputType.TYPE_CLASS_NUMBER);
                ll1.addView(attack1);
                ll1.addView(defense1);
                alert1.setView(ll1);
                alert1.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try{
                            Two.addToken(
                                    Integer.parseInt(attack1.getText().toString()),
                                    Integer.parseInt(defense1.getText().toString()));
                            Toast t = Toast.makeText(getApplicationContext(),
                                    attack1.getText().toString() + "/" +
                                            defense1.getText().toString() + " Token Created",
                                    Toast.LENGTH_SHORT);
                            t.show();
                            addTokens(Two, Integer.parseInt(attack1.getText().toString()),
                                    Integer.parseInt(defense1.getText().toString()));
                        }catch (Exception e){
                            Toast t = Toast.makeText(getApplicationContext(), "Enter a number", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                });

                alert1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert1.show();
                break;
            case R.id.display:
                display.setText("");
                break;
            case R.id.nameO:
                changeName(One);
                break;
            case R.id.nameT:
                changeName(Two);
                break;
        }
    }
    public void changeName(final Player tempPlayer){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        if(tempPlayer == One){
            input.setHint(One.getName());
        }else{
            input.setHint(Two.getName());
        }
        alert.setView(input);
        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                tempPlayer.changeName(input.getText().toString());
                nameO.setText(One.name);
                nameT.setText(Two.name);
                Toast t = Toast.makeText(getApplicationContext(), "Name set to " + input.getText().toString(), Toast.LENGTH_SHORT);
                t.show();
            }
        });

        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }
    public void diceSelection(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setNegativeButton("6", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rnd = new Random();
                Toast t = Toast.makeText(getApplicationContext(), "Rolling...", Toast.LENGTH_SHORT);
                t.show();
                display.setText(String.valueOf(rnd.nextInt(6) + 1));
            }
        });
        alert.setNeutralButton("12", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rnd = new Random();
                Toast t = Toast.makeText(getApplicationContext(), "Rolling...", Toast.LENGTH_SHORT);
                t.show();
                display.setText(String.valueOf(rnd.nextInt(12) + 1));
            }
        });
        alert.setPositiveButton("20", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rnd = new Random();
                Toast t = Toast.makeText(getApplicationContext(), "Rolling...", Toast.LENGTH_SHORT);
                t.show();
                display.setText(String.valueOf(rnd.nextInt(20) + 1));
            }
        });
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menustart, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_rolldice:
                diceSelection();
                break;
            case R.id.action_flipcoin:
                rnd = new Random();
                if(rnd.nextBoolean())
                    display.setText("heads");
                else
                    display.setText("tails");
                break;
            case R.id.action_rotate:
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    Rotate();
                }else{
                    Toast t = Toast.makeText(getApplicationContext(), "Device must be vertical", Toast.LENGTH_SHORT);
                    t.show();
                }
                break;
            case R.id.action_savegame:
                saveGame();
                break;
            case R.id.action_reviewgame:
                try{
                    Intent i = new Intent(this, ReviewCurrentGame.class);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.action_savedgames:
                try{
                    Intent i = new Intent(this, SavedGames.class);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.action_reset:
                Reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void writeToFile(boolean isGaining, Player player){
        try{
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("temp")));
            String textFromFile;
            StringBuffer stringBuffer = new StringBuffer();
            while((textFromFile = inputReader.readLine()) != null)
                stringBuffer.append(textFromFile + "\n");
            if(isGaining){
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        openFileOutput("temp", Context.MODE_PRIVATE));
                if(player == One)
                    if(stringBuffer != null)
                        outputStreamWriter.write(stringBuffer.toString() + "pl1+");
                else
                        outputStreamWriter.write("p1+");
                else
                    if(stringBuffer != null)
                        outputStreamWriter.write(stringBuffer.toString() + "pl2+");
                    else
                        outputStreamWriter.write("pl2+");
                outputStreamWriter.close();
            }else{
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        openFileOutput("temp", Context.MODE_PRIVATE));
                if(player == One)
                    if(stringBuffer != null)
                        outputStreamWriter.write(stringBuffer.toString() + "pl1-");
                    else
                        outputStreamWriter.write("pl1-");
                else
                    if(stringBuffer != null)
                        outputStreamWriter.write(stringBuffer.toString() + "pl2-");
                    else
                        outputStreamWriter.write("pl2-");
                outputStreamWriter.close();
            }
        }catch (Exception e){
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return true;
    }
    public void Reset(){
        One = new Player("Player 1");
        Two = new Player("Player 2");
        refreshLife();
        nameO.setText(One.name);
        nameT.setText(Two.name);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    openFileOutput("temp", Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        }catch (Exception e){
            Log.e("Exception", "File clear failed");
        }
        Toast t = Toast.makeText(getApplicationContext(), "Reset", Toast.LENGTH_SHORT);
        t.show();
        oToke.removeAllViews();
        tToke.removeAllViews();
    }
    public void Initialize(){
        //TODO: Maybe add commander deck options
        isRotated = false;
        oneToke = new ArrayList<Tokens>();
        twoToke = new ArrayList<Tokens>();
        One = new Player("Player 1");
        Two = new Player("Player 2");
        prefs = this.getSharedPreferences("mitterederstudios.com.lifecounter", Context.MODE_PRIVATE);
        editor = prefs.edit();
        plOPlus = (Button) findViewById(R.id.plTPlus);
        plOMinis = (Button) findViewById(R.id.plTMinus);
        plTMinis = (Button) findViewById(R.id.plOMinus);
        plTPlus = (Button) findViewById(R.id.plOPlus);
        plTAddToken = (Button) findViewById(R.id.plTAddToken);
        plOAddToken = (Button) findViewById(R.id.plOAddToken);
        plOPlus.setOnClickListener(this);
        plOMinis.setOnClickListener(this);
        plTMinis.setOnClickListener(this);
        plTPlus.setOnClickListener(this);
        plOAddToken.setOnClickListener(this);
        plTAddToken.setOnClickListener(this);
        nameT = (TextView) findViewById(R.id.nameT);
        nameO = (TextView) findViewById(R.id.nameO);
        display = (TextView) findViewById(R.id.display);
            nameO.setText(One.getName());
            nameT.setText(Two.getName());
            nameT.setOnClickListener(this);
            nameO.setOnClickListener(this);
            display.setOnClickListener(this);
        counterO = (TextView) findViewById(R.id.counterO);
        counterT = (TextView) findViewById(R.id.counterT);
        oToke = (LinearLayout) findViewById(R.id.playerOTokenDisplay);
        oContToke = (LinearLayout) findViewById(R.id.playerContainerTTokenDisplay);
        tContToke = (LinearLayout) findViewById(R.id.playerContainerOTokenDisplay);
        tToke = (LinearLayout) findViewById(R.id.playerTTokenDisplay);
        oButt = (LinearLayout) findViewById(R.id.plTButtons);
        tButt = (LinearLayout) findViewById(R.id.plOButtons);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        savedNames = prefs.getStringSet("SAVEDNAMES", new HashSet<String>());
        refreshLife();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    openFileOutput("temp", Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        }catch (Exception e){
            Log.e("Exception", "File clear failed");
        }
    }
    private void Rotate() {
        if(!isRotated) {
            isRotated = true;
            oToke.setRotation(180f);
            plTAddToken.setRotation(180f);
            nameT.setRotation(180f);
            counterT.setRotation(180f);
        }else{
            isRotated = false;
            oToke.setRotation(0f);
            plTAddToken.setRotation(0f);
            nameT.setRotation(0f);
            counterT.setRotation(0f);
        }
    }
    public void refreshLife(){
        counterO.setText(String.valueOf(One.getLife()));
        counterT.setText(String.valueOf(Two.getLife()));
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void addTokens(Player player, int attack, int defense){
        Button button = new Button(this);
        final Context context = this;
        if(player == One){
            button.setLayoutParams(plOAddToken.getLayoutParams());
            if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    button.setTextSize(30f);
                }else{
                    button.setTextSize(45f);
                }
            }else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    button.setTextSize(20f);
                }else{
                    button.setTextSize(15f);
                }
            }else if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_NORMAL){
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    button.setTextSize(15f);
                }else{
                    button.setTextSize(15f);
                }
            }
            button.setText(String.format("%d/%d",
                    attack,
                    defense));
            button.setTextColor(Color.WHITE);
            if(isRotated)
                button.setRotation(180f);
            button.setBackground(getResources().getDrawable(R.drawable.token));
            final Button finalButton = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            oToke.removeView(finalButton);
                        }
                    });
                    alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            });
            oToke.addView(button);
        }else{
            button.setLayoutParams(plTAddToken.getLayoutParams());
            if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    button.setTextSize(30f);
                }else{
                    button.setTextSize(45f);
                }
            }else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    button.setTextSize(20f);
                }else{
                    button.setTextSize(15f);
                }
            }else if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_NORMAL){
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    button.setTextSize(15f);
                }else{
                    button.setTextSize(15f);
                }
            }
            button.setText(String.format("%d/%d",
                    attack,
                    defense));
            button.setTextColor(Color.WHITE);
            if(isRotated)
                button.setRotation(180f);
            button.setBackground(getResources().getDrawable(R.drawable.token));
            final Button finalButton = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            tToke.removeView(finalButton);
                        }
                    });
                    alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            });
            tToke.addView(button);
        }
    }
    public void saveGame(){
        final Context context = this;
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final TextView tv = new TextView(this);
        final EditText et = new EditText(this);
        tv.setText("Save game? Give it a name: ");
        ll.addView(tv);
        ll.addView(et);
        alert.setView(ll);
        alert.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(et.getText().toString().equals("")){
                    Toast t = Toast.makeText(getApplicationContext(), "Cannot save as nothing" + et.getText().toString(), Toast.LENGTH_SHORT);
                    t.show();
                }else {
                    StringBuffer stringBuffer = new StringBuffer();
                    try{
                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                                openFileInput("temp")));
                        String inputString;
                        stringBuffer = new StringBuffer();
                        while((inputString = inputReader.readLine()) != null) {
                            stringBuffer.append(inputString + "\n");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String copy = stringBuffer.toString();
                    String name = et.getText().toString().replace(" ","");
                    if(savedNames.contains(name)){
                        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        final TextView tv = new TextView(context);
                        tv.setText("Name already used, overwrite?");
                        alert.setView(tv);
                        final String copyCopy = copy;
                        final String nameCopy = name;
                        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                try {
                                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                                            openFileOutput(nameCopy, Context.MODE_PRIVATE));
                                    outputStreamWriter.write(copyCopy);
                                    outputStreamWriter.close();
                                    Toast t = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT);
                                    t.show();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                saveGame();
                            }
                        });
                        alert.show();
                    }else{
                        savedNames.add(et.getText().toString());
                        editor.putStringSet("SAVEDNAMES", savedNames);
                        editor.commit();
                        try{
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                                    openFileOutput(name, Context.MODE_PRIVATE));
                            outputStreamWriter.write(copy);
                            outputStreamWriter.close();
                            Toast t = Toast.makeText(getApplicationContext(), "Saved as " + et.getText().toString(), Toast.LENGTH_SHORT);
                            t.show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }
    /*public static class AdFragment extends Fragment {
        private AdView mAdView;
        public AdFragment() {
        }
        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            mAdView = (AdView) getView().findViewById(R.id.adView);
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_ad, container, false);
        }
        @Override
        public void onPause() {
            if (mAdView != null) {
                mAdView.pause();
            }
            super.onPause();
        }
        @Override
        public void onResume() {
            super.onResume();
            if (mAdView != null) {
                mAdView.resume();
            }
        }
        @Override
        public void onDestroy() {
            if (mAdView != null) {
                mAdView.destroy();
            }
            super.onDestroy();
        }
    }*/
}