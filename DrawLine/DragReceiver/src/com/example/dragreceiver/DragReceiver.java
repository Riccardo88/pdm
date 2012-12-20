package com.example.dragreceiver;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;




import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class DragReceiver extends Activity {
	private XMPPConnection connection;
    private Main view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        
        connection = new XMPPConnection (config);
        try {
			connection.connect();
			Toast toast=Toast.makeText(this,"Connessione al Server eseguita",Toast.LENGTH_LONG);
			toast.show();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			connection.login("fioravanti", "qwerty");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        view = new Main(this,connection);
        setContentView(view);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_drag_receiver, menu);
        
        return true;
    }
    
    
	
}
