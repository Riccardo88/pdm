package com.example.dragsender;



import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class Main extends View {

private String msg = new String();


private XMPPConnection connection;
private Paint paint;
private Path path;

public Main(Context context,XMPPConnection con) {
super(context);
// TODO Auto-generated constructor stub
this.connection = con;




this.paint = new Paint();
this.path = new Path();
paint.setDither(true);
paint.setColor(Color.RED);
paint.setStyle(Paint.Style.FILL_AND_STROKE);
paint.setStrokeJoin(Paint.Join.ROUND);
paint.setStrokeCap(Paint.Cap.ROUND);
paint.setStrokeWidth(15);

}

/**
* @param args
*/
@Override
protected void onDraw(Canvas canvas) {
// TODO Auto-generated method stub
	/*Il canvans(tela)  attraverso 4 elementi premette di eseguire il processo di movimento dell'imaggine,
	 * i 4 elementi esprimono l'immagine bitmap la posizione x y e il colore che noi abbiamo messo null, se
	 * volevamo cololare dovevamo istanziare un oggetto Paint()*/


     canvas.drawPath(path, paint);//viene richiamato dopo un action_move grazie al metodo invalidate()
  }



             //gestiamo l'evento di Touch che viene riportato dal parametro MotionEvent event
              @Override
              public boolean onTouchEvent(MotionEvent event) {
               // TODO Auto-generated method stub

               int eventx = (int) event.getX();//prendo la posizione X dove c'� stato l'evento touch
               int eventy = (int) event.getY();//prendo la posizione Y dove c'� stato l'evento touch
             //verifico che tipo di evento ho e lo gestisco con uno switch
               switch (event.getAction()) {
               case MotionEvent.ACTION_DOWN://ho un tipo d'evento ACTION_DOWN, ovvero ho toccato lo schermo
   				
               path.moveTo(eventx, eventy);
               invalidate();
               msg="CASE0;" + eventx + ";" + eventy;
               invia();

               break;

               case MotionEvent.ACTION_MOVE://ho un tipo d'evento ACTION_DOWN, ovvero dopo che ho toccato lo schermo mi muovo
	       	   path.lineTo(eventx, eventy);
	       	   path.moveTo(eventx, eventy);
	           invalidate();
               msg="CASE1;" + eventx + ";" + eventy;
               invia();
               break;

               case MotionEvent.ACTION_UP://ho un tipo d'evento ACTION_UP, ovvero tolgo il dito sullo schermo
               path.close();
               invalidate();
               msg="CASE2;" + eventx + ";" + eventy;
               invia();
               break;
               default:
               break;
}
return true;
}


public void invia() {
Runnable rn= new Runnable(){


public void run() {
// TODO Auto-generated method stub
Log.d("XMPPChat","Hai scritto: "+msg);
Message mess = new Message();
//La classe Message consente di comunicare informazioni  tra un mittente e un
//destinatario in una rete
mess.setTo("fioravanti@ppl.eln.uniroma2.it");//inserisco destinatario
mess.setBody(msg);
System.out.println(mess.toString());//inserisco il payload
connection.sendPacket(mess);//invio il pacchetto	
}
};
Thread da= new Thread(rn);
da.start();
}


}