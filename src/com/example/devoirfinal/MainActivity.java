package com.example.devoirfinal;

import java.util.List;

import com.example.devoirfinal.PathFinder.Node;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	String salleD ="";
	String salleA ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	
	/*
	 * creation des listes deroulantes
	 */
		
	final Spinner spin1 = (Spinner) findViewById(R.id.spin1);
	final Spinner spin2 = (Spinner) findViewById(R.id.spin2);
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,from);
	ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,to);
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spin1.setAdapter(adapter);
	spin2.setAdapter(adapter2);
	
	final Button chercher = (Button) findViewById(R.id.chercher);
	chercher.setOnClickListener(new Button.OnClickListener(){
		public void onClick(View v) {
			Option();
		}
	});}

	/*
	 * liste des choix
	 */
	static final String[] from = new String[] {"H0-1010", "H0-1020", "H0-1070", "H0-1090", "H0-1110", "H0-1140",
		"H0-1150", "H0-1160", "H0-1170", "H0-1180",
		"H0-1190", "H0-1260", "H0-1300", "H0-1290"};
	
	static final String[] to = new String[] {"H0-1010", "H0-1020", "H0-1070", "H0-1090", "H0-1110", "H0-1140", "H0-1150", "H0-1160", "H0-1170", "H0-1180",
		"H0-1190", "H0-1260", "H0-1300", "H0-1290"};
	
	String[][] tab = new String[][]{
 			{"H0-1010","1","5"},
 			{"H0-1020","1","4"},
 			{"H0-1070","1","1"},
 			{"H0-1090","3","1"},
 			{"H0-1110","5","1"},
 			{"H0-1140","6","2"},
 			{"H0-1150","6","3"},
 			{"H0-1160","6","3"},
 			{"H0-1170","6","4"},
 			{"H0-1180","6","5"},
 			{"H0-1190","5","5"},
 			{"H0-1260","3","5"}, 
 			{"H0-1300","2","5"},
 			{"H0-1290","2","5"},
	};
	


	public void CheminPlusCourt(){
		final StringBuilder res = new StringBuilder();
		final TextView information = (TextView) findViewById(R.id.information);
		String affichage="";
		/*
		 * creation de la carte
		 * 1 represente les couloirs
		 * 0 represente les murs ou les salles
		 */
		 int y=0;
		 int x=0;
		 int yA=0;
		 int xA=0;
		 
		 if(salleD.equals(salleA)){
			 affichage="Choisir deux salles diffŽrentes"+'\n';
			 information.setText(affichage);
		 }
		 else{
			 
			 //recherche position de la salle de depart
			 x= rechercheX(tab,salleD);
			 y= rechercheY(tab,salleD);
			 
			 //recherche position de la salle d'arrive
			 xA= rechercheX(tab,salleA);
			 yA= rechercheY(tab,salleA);

			
			 if(y==yA && x==xA){
				 affichage="Vous tes devant la salle. Regarder autour de vous"+'\n';
				 information.setText(affichage);
			 }
			 else{
				
				
				 int [][] map = new int[][]{
						 {0, 0, 0, 0, 0, 0, 0, 0},
						 {0, 1, 1, 1, 1, 1, 1, 0},
						 {0, 1, 0, 0, 0, 0, 1, 0},
						 {0, 1, 0, 0, 0, 0, 1, 0},
						 {0, 1, 0, 0, 0, 0, 1, 0},
						 {0, 1, 1, 1, 1, 1, 1, 0},
						 {0, 0, 0, 0, 0, 0, 0, 0},
				 };
				 
				 
				
				 
				 PathFinder pf = new PathFinder(map);
				 pf.setXY(xA, yA);
				 
				 List<Node> nodes = pf.compute(new PathFinder.Node(x, y)); 		//ICI POINT DE DEPART 
			
				 
				 
				 int nb=0;
			
				 if(nodes == null)
					 information.setText("Il n'y a pas de chemin pour vous rendre votre salle...");
				 else{
					
					 String az="";
					 String tr="";
					 nb=0;
					 String[][] mapAff=new String [][]{
							 {"0", "0", "0", "0", "0", "0", "0", "0"},
							 {"0", "1", "1", "1", "1", "1", "1", "0"},
							 {"0", "1", "0", "0", "0", "0", "1", "0"},
							 {"0", "1", "0", "0", "0", "0", "1", "0"},
							 {"0", "1", "0", "0", "0", "0", "1", "0"},
							 {"0", "1", "1", "1", "1", "1", "1", "0"},
							 {"0", "0", "0", "0", "0", "0", "0", "0"},
					 };
					 for(Node n : nodes)
					 {
						nb++;
						res.append(n);
						StringBuilder t =new StringBuilder("");
						t.append(n);
						az=n.toString();
						String er="";
						String re="";
						
						for(int k=0; k<az.length();k++)
						{
							tr=az.substring(k, k+1);				
							
							if(tr.equals("0")||tr.equals("1")||tr.equals("2")||tr.equals("3")||tr.equals("4")||
									tr.equals("5")||tr.equals("6")||tr.equals("7")||tr.equals("8")||tr.equals("9"))
							{
									er=er+tr;
							}
							else
							{
								if(tr.equals(","))
								{
									re=er;
									er="";
									
								}
								if(tr.equals(")"))
								{						
									mapAff[Integer.parseInt(er)][Integer.parseInt(re)]="x";				
									re="";
									er="";
								}
							}
						}
					 }
					 
					 mapAff[y][x]="D";
					 mapAff[yA][xA]="A";
						
					int temps=0;
					nb=nb-2;
					temps=nb*10;
					int min=0;
						
					while(temps>60)
					{
						min++;
						temps=temps-60;
					}
						
					String duree="";
					duree= Integer.toString(min)+" min "+ Integer.toString(temps)+ " s";
					affichage="Temps : " + duree + '\n'+"Distance : "+Integer.toString(nb*10)+"m."+'\n';
					for(int k = 0; k < 7; k++){
						for(int j = 0; j < 8; j++){
							 affichage=affichage+mapAff[k][j] + " ";
						}
						affichage=affichage+"\n";
					
				  }
				 }
				 information.setText(affichage);
			 }
		 }
	}
	
	
	public int rechercheY(String[][] tab, String salle)
	{
		for(int i=0; i<14; i++)
		{
			if(tab[i][0].equals(salle))
			{
				return Integer.parseInt(tab[i][2]);
			}
		}
		return 1;
	}
	
	public int rechercheX(String[][] tab, String salle)
	{
		for(int i=0; i<14; i++)
		{
			if(tab[i][0].equals(salle))
			{
				return Integer.parseInt(tab[i][1]);	
			}
		}
		return 1;
	}
	
	public void Option()
	{
		final Button chercher = (Button) findViewById(R.id.chercher);
		if (chercher != null)
		{    
			final Spinner spin = (Spinner) findViewById(R.id.spin1);
			final Spinner spin2 = (Spinner) findViewById(R.id.spin2);
			
			if ((spin != null) && spin.isEnabled())
			{
				salleD=String.valueOf(spin.getSelectedItem());
				salleA=String.valueOf(spin2.getSelectedItem());
				chercher.setText("Voici le chemin :");
				CheminPlusCourt();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}