package com.example.dragsender;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class DragSender extends Activity {
	
	private XMPPConnection connection;
    private Main view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        /*Configuro la connessione imposto il socket del server ppl.eln.uniroma2.it",5222 */
        ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        
        connection = new XMPPConnection (config);//setto la connessione ad un server XMPP(costituisce l'insieme dei protocolli di Instant Messaging)
        try {
			connection.connect();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			connection.login("simeone", "qwerty");//faccio il login al server con nome e password
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        view = new Main(this,connection);
        setContentView(view);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_drag_sender, menu);
        
        return true;
    }
    
    
	
}

