package com.example.dragreceiver;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class Main extends View {

int x ;
int y ;
double posx;
double posy;

private String mess[];

private XMPPConnection connection;
private Paint paint;
private Path path;
private Handler handler;

public Main(Context context,XMPPConnection con) {
super(context);
mess = new String[3];
// TODO Auto-generated constructor stub
handler=new Handler();
paint = new Paint();
path = new Path();
paint.setDither(true);
paint.setColor(Color.RED);
paint.setStyle(Paint.Style.FILL_AND_STROKE);
paint.setStrokeJoin(Paint.Join.ROUND);
paint.setStrokeCap(Paint.Cap.ROUND);
paint.setStrokeWidth(15);
       
      //ascolto i pacchetti ricevuti(pacchetti in ingresso)
      connection.addPacketListener(new PacketListener() {
    	
      public void processPacket(Packet arg0) {

      Message msg = (Message) arg0;//prendo il pacchetto ricevuto e ne faccio il casting in oggetto Message 
      final String to = msg.getTo(); 
      final String body = msg.getBody();//acquisisco il payload
      mess=body.split(";");
      x = Integer.parseInt(mess[1]);
      y = Integer.parseInt(mess[2]);
		
      for (int i = 0; i < mess.length; i++) {
      System.out.println(mess[i]);
}
      Log.d("XMPPChat", "Hai ricevuto un messaggio: " + to+ " " + body);
      handler.post(new Runnable() {
     
    	  //lo suddivido nei vari casi
			
			public void run() {
				// TODO Auto-generated method stub

				
				if (mess[0].equals("CASE0")) {
					path.moveTo(x,y);
					invalidate();

				} else if (mess[0].equals("CASE1")) {
					path.lineTo(x,y);
					path.moveTo(x,y);
					invalidate();
					
				} else if (mess[0].equals("CASE2")) {
					path.close();
					invalidate();
									
				}
				
			}
		});

	}
}, new MessageTypeFilter(Message.Type.normal));


}


protected void onDraw(Canvas canvas) {
// TODO Auto-generated method stub


canvas.drawPath(path, paint);
}
}