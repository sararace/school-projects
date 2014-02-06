package edu.ucsd.vis141a.snmillar.gameoflife;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameOfLifeActivity extends Activity {
	
	private static Cell[][] oldCells;
	private GOLGrid g;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_game_of_life);
		
		g = new GOLGrid(this, oldCells);
		
		setContentView(g);
        
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		oldCells = g.getCellArray();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game_of_life, menu);
		return true;
	}

}
