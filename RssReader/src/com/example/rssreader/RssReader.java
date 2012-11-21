package com.example.rssreader;




import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

 

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.view.Menu;
import android.view.View;


public class RssReader extends ListActivity {
	public String url[];
	public RssHandler handler[];
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Impostiamo il layout principale
	        setContentView(R.layout.activity_rss_reader);
	        url= new String [8];
	        handler= new RssHandler[8];
	        
	        set_url();
	        set_handler();
	        
	        
	        try {
	        	
	        	
	        	
	        	for (int j=0; j<8; j++)
	        	{
	        	SAXParserFactory factory=SAXParserFactory.newInstance();
	    		SAXParser parser=factory.newSAXParser();
				InputStream in = new URL(url[j]).openStream();			//connessione http al browser
			
				XMLReader reader = parser.getXMLReader();
				reader.setContentHandler(handler[j]);
				reader.parse(new InputSource(in));
				
				
			}
	        }
	        
	        catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	        
	      
	            
	      /* le HashMap sono una implementazione contenuta nelle API 
	        *Java dell’interfaccia java.util.Map. La mappa 
	        *(chiamata Dictionary nel mondo .Net) non è altro che una 
	        *collezione di oggetti il cui scopo principale è quello di 
	        *rendere veloci ed efficienti 
	        *operazioni quali inserimento e ricerca di elementi.
	         
	         */
	       
	            ArrayList<Map<String, Object>> listaDati = new ArrayList<Map<String, Object>>();
	            listaDati = riempilistaDati(listaDati);
	     
	            String[] from = {"titolo", "data"};
	            int[] views = { R.id.text1, R.id.text2};
	           // Creiamo il SimpleAdapter
	            SimpleAdapter mioAdapter = new SimpleAdapter (
	     
	    	this, 			// The context where the View associated with this SimpleAdapter is running
	    	listaDati,		// A List of Maps. Each entry in the List corresponds to one row in the list.
	    	R.layout.item,		// Resource identifier of a view layout
	    	from,	 		// A list of column names that will be added to the Map associated with each item.
	    	views 			// The views that should display data
	            );
	  
	            
	            this.setListAdapter(mioAdapter);
	            
	            ListView ls= getListView();
	            ls.setOnItemClickListener(new OnItemClickListener() {
	            	
	            
	            	 public void onItemClick(AdapterView<?> parent, View v,
	            		        int position, long id)  {
	            	
	            	Intent intent=new Intent(RssReader.this,Rss.class);
	            
	            	
	            	intent.putExtra("titolo", handler[position].title);	            	
	            	intent.putExtra("data", handler[position].pubDate);		            	
	            	intent.putExtra("immagine", handler[position].imageUrl);	            	
	             	intent.putExtra("descrizione", handler[position].description);
	             	/*putExtra permette l'associazione  tra un particolare valore, in questo
					 * caso tiolo, e una chiave di tipo String,"title", questo associazione
					 * di infromazioni ad un intent permettera alla activity chiamata con questo intent
					 * di ricavarne il valore associato attraverso la chiave "title",
					 * e di farlo visualizzare nella seconda activity nel TextView 
					 */
					
	             	startActivity(intent);}//fa partire seconda activity
	           
	            	 });
					
	            	 }
	  
    
	  
	  private ArrayList<Map<String, Object>> riempilistaDati(ArrayList<Map<String, Object>> listaDati) {
		  for (int j=0; j<8; j++){
	        	listaDati.add(creaMappa(handler[j].title, handler[j].pubDate));
		  }
	        	
	        	
	            return listaDati;
	        }
	     
	        private Map<String, Object> creaMappa(String titolo, String data) {
	     
	        	Map<String, Object> map = new HashMap<String, Object>();
	     
	        	map.put("titolo", titolo);
	        	map.put("data", data);
	        	
	        	return map;
	        }   
	       
			
			
	        @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_rss_reader, menu);
	        return true;
	    }
	


protected void set_url(){
    
    
    url [0] = "http://www.nasa.gov/rss/breaking_news.rss" ;
    url [1]	= "http://www.nasa.gov/rss/educationnews.rss" ;
    url [2]	= "http://feeds.tuttochievoverona.it/rss/?c=16";
    url [3] = "http://feeds.tuttomercatoweb.com/rss/?c=1";
    url [4] = "http://feeds.tuttomercatoweb.com/rss/?q=ufficiale";
    url [5] = "http://snowpassion.lastampa.it/taxonomy/term/1039/all/feed";
    url [6] = "http://feeds.fcinternews.it/rss/?c=36";
    url [7] = "http://feeds.fcinternews.it/rss/";
    
    
    }
    
    protected void set_handler(){
    	 for (int j=0; j<8; j++)
    	 {
    		 handler[j]=new RssHandler();
    	 }
    	
    }
}