package dahakashow.com.messangerapp;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dahakashow.com.messangerapp.MyGcmListenerService.Communicator1;

public class ChatActivity extends AppCompatActivity {
//dss
    ListView list;
    Button send;
    ArrayAdapter<String> adapter;
    ArrayList<String> listString = new ArrayList<>();;
    EditText sendText;
    SqlDB sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        sqlDB = new SqlDB(this);
        setSupportActionBar(myToolbar);

        list = (ListView)findViewById(R.id.listView);
        send= (Button)findViewById(R.id.send);
        sendText = (EditText)findViewById(R.id.sendText);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));





listString.add("MessengerApp:Hello From MessengerAPP :)");
        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setStackFromBottom(true);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listString);
        list.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(sendText.getText().length()==0){return;}
                String text = sendText.getText().toString();
                Intent i = new Intent(ChatActivity.this, SendMessageToServer.class);
                i.putExtra("text",text);

                startService(i);

                sendText.setText("");

            }
        });

    adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                SqlDB sql= new SqlDB(getApplication());
                sql.deleteAll();
                listString.clear();
                adapter.notifyDataSetChanged();

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            String username = intent.getStringExtra("username");
            Log.d("receiver", "Got message: " + message);
            listString.add(username+":"+message);
            adapter.notifyDataSetChanged();

            SqlDB sql = new SqlDB(context);
            Cursor c=sql.db.rawQuery("SELECT * FROM messages",null);
            if (c.getCount()==0) {

                return;
            }
            StringBuffer buffer=new StringBuffer();
            listString.clear();
            while (c.moveToNext()) {

                listString.add(c.getString(0));

            }
            adapter.notifyDataSetChanged();

        }
    };


}
