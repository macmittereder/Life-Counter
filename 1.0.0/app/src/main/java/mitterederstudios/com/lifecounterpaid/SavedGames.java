package mitterederstudios.com.lifecounterpaid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SavedGames extends ActionBarActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Set<String> savedNames;
    ListView displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysavedgames);
        Initialize();
        savedNames = prefs.getStringSet("SAVEDNAMES", new HashSet<String>());
        ArrayList<String> list = new ArrayList<String>();
        Iterator itr = savedNames.iterator();
        while(itr.hasNext()){
            list.add(itr.next().toString());
        }
        refreshList(list);
    }
    private void Initialize() {
        prefs = this.getSharedPreferences("mitterederstudios.com.lifecounter", Context.MODE_PRIVATE);
        editor = prefs.edit();
        displayList = (ListView) findViewById(R.id.displayList);
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
    public void refreshList(ArrayList<String> list){
        final Iterator it = savedNames.iterator();
        final ArrayList<String> listCopy = list;
        final Intent i = new Intent(this, ReviewGame.class);
        final Context context = this;
        displayList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        displayList.setClickable(true);
        displayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle basket = new Bundle();
                basket.putString("savedGameName", listCopy.get(position).toString().replace(" ", ""));
                i.putExtras(basket);
                startActivity(i);
            }
        });
        displayList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final TextView input = new TextView(context);
                input.setText("Delete?");
                alert.setView(input);
                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String toTakeOut = listCopy.get(position).toString().replace(" ", "");
                        Set<String> temp = new HashSet<String>();
                        ArrayList<String> listTemp = new ArrayList<String>();
                        while(it.hasNext()){
                            String gotten = it.next().toString();
                            if(!toTakeOut.equals(gotten.replace(" ", ""))){
                                temp.add(gotten);
                                listTemp.add(gotten);
                            }
                        }
                        editor.putStringSet("SAVEDNAMES", temp);
                        editor.commit();
                        Toast t = Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT);
                        t.show();
                        refreshList(listTemp);
                    }
                });

                alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();
                return true;
            }
        });
    }
}
