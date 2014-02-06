package edu.ucsd.vis141a.snmillar.gameoflife;

import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * like the Location class in GridWorld
 */
public class Cell {
	
	public static final int NORTH = 0;
	public static final int EAST = 90;
	public static final int SOUTH = 180;
	public static final int WEST = 270;
	public static final int NORTHEAST = 45;
	public static final int SOUTHEAST = 135;
	public static final int SOUTHWEST = 225;
	public static final int NORTHWEST = 315;
	
	private int row;
	private int column;
	private GOLGrid grid;
	private boolean isAlive;
	private Paint paint;
	
	public Cell(int r, int c, GOLGrid g){
		row = r;
		column = c;
		grid = g;
		paint = new Paint();
		paint.setARGB(255,255,255,255);
		isAlive = false;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	
	public Cell getAdjacentCell(int direction){
		Cell ret;
		switch(direction){
		case NORTH:
			ret = grid.getCell(row, column-1);
			break;
		case EAST:
			ret = grid.getCell(row+1,column);
			break;
		case SOUTH:
			ret = grid.getCell(row,column+1);
			break;
		case WEST:
			ret = grid.getCell(row-1,column);
			break;
		case NORTHEAST:
			ret = grid.getCell(row+1, column-1);
			break;
		case SOUTHEAST:
			ret = grid.getCell(row+1, column+1);
			break;
		case SOUTHWEST:
			ret = grid.getCell(row-1, column+1);
			break;
		case NORTHWEST:
			ret = grid.getCell(row-1, column-1);
			break;
		default:
			ret = grid.getCell(1, 1);
			break;
		}
		return ret;
	}
	
	@Override
	public boolean equals(Object other){
		if(!(other instanceof Cell))
			return false;
		Cell c = (Cell)other;
		if(row == c.getRow() && column == c.getColumn())
			return true;
		else
			return false;
	}
	
	public void die(){
		//make white
		isAlive = false;
		paint.setARGB(255,255,255,255);
	}
	
	public void spawn(){
		//make dark
		isAlive = true;
		paint.setARGB(255,0,0,0);
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void toggle(){
		if(isAlive){
			die();
		}else{
			spawn();
		}
	}
	
	public int numNeighborsAlive(){
		int ret = 0;
		for(int i=0; i<360; i+=45){
			Cell adj = getAdjacentCell(i);
			if(adj!=null && adj.isAlive){
				ret++;
			}
		}
		return ret;
	}
	
	public void draw(Canvas c){
		//paint.setARGB(255, row*20, row*20, column*20);
		float colWidth = c.getWidth()/(GOLGrid.NUM_W);
		float rowHeight = (c.getHeight()-GOLGrid.NAV_HEIGHT)/(GOLGrid.NUM_H);
		float xPos = (column-1) * colWidth;
		float yPos = GOLGrid.NAV_HEIGHT+((row-1) * rowHeight);
		//System.out.println(""+row+column+"colWidth="+colWidth+" rowHeight="+rowHeight+" xPos="+xPos+" yPos="+yPos);
		//left, top, right, bottom
		c.drawRect(xPos+1, yPos+1, xPos+colWidth, yPos+rowHeight, paint);
	}
	
}
