package edu.ucsd.vis141a.snmillar.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;

public class GOLGrid extends SurfaceView implements SurfaceHolder.Callback{
	
	protected static final int NUM_W = 30;
	protected static final int NUM_H = 40;
	protected static final int NAV_HEIGHT = 50;
	private final int CIRCLE_RADIUS = 16;
	
	private Paint paint = null;
	private Cell[][] cells;
	private int timeInterval = 250;
	private GridThread thread;
	private int circlePos = 6; //0 to 10
	private boolean paused = false;
	
	public GOLGrid(Context context){
		this(context, null);
	}
	
	public GOLGrid(Context context, Cell[][] cs){
		super(context);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		if(cs==null){
			cells = new Cell[NUM_H][NUM_W];
			for(int i=0; i<cells.length; i++){
				for(int j=0; j<cells[i].length; j++){
					cells[i][j] = new Cell(i+1,j+1, this);
				}
			}
		}else{
			cells = cs;
		}
		paint = new Paint();
		paint.setARGB(255,0,255,255);
		
		getCell(15,15).spawn();
		getCell(15,16).spawn();
		getCell(16,14).spawn();
		getCell(16,15).spawn();
		getCell(17,15).spawn();
		
		getHolder().addCallback(this);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		for(Cell[] array: cells){
			for(Cell c: array){
				c.draw(canvas);
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder sh){
		thread = new GridThread(getHolder());
		
		thread.setRunning(true);
        thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder sh){
		boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//if(event.getAction() == MotionEvent.ACTION_DOWN){}
		
		if(event.getY()>NAV_HEIGHT){
			float colWidth = getWidth()/(NUM_W);
			float rowHeight = (getHeight()-NAV_HEIGHT)/(NUM_H);
			int column = (int)((event.getX()/colWidth)+1);
			int row = (int)(((event.getY()-NAV_HEIGHT)/rowHeight)+1);
		
			Cell cts = getCell(row,column);
			if(cts!=null){
				cts.spawn();
			}
		}else if(event.getX()<=NAV_HEIGHT){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				//pause button
				if(paused){
					thread = new GridThread(getHolder());
					thread.setRunning(true);
					thread.start();
					paused = false;
				}else{
					thread.setRunning(false);
					thread.interrupt();
					paused = true;
				}
			}
		}else{
			//slider
			int cPos = (int)(event.getX() - (NAV_HEIGHT+CIRCLE_RADIUS))/((getWidth()-(NAV_HEIGHT+CIRCLE_RADIUS))/10);
			if(cPos>=0 && cPos <=10){
				circlePos = cPos;
				timeInterval = (11-cPos) * 50;
			}
		}
		
		if(paused){
			invalidate();
		}
		
		return true;
	}
	
	public Cell getCell(int a, int b){
		if(a>NUM_H || b>NUM_W || a<1 || b<1){
			//avoid an ArrayIndexOutOfBoundsException
			return null;
		}
		return cells[a-1][b-1];
	}
	
	protected Cell[][] getCellArray(){
		return cells;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	class GridThread extends Thread {
		private SurfaceHolder surfaceHolder;
        private boolean runFlag = false;
 
        public GridThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }
 
        public void setRunning(boolean run) {
            this.runFlag = run;
        }
        
        @Override
        public void run() {
            Canvas c;
            
            while (this.runFlag) {
             
                c = null;
                try {
                   
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {                   
                        //methods for every time
                    	updateCells();
                    	doDraw(c);
                    }
                } finally {
                   
                    if (c != null) {
                        this.surfaceHolder.unlockCanvasAndPost(c);
                        try {
							sleep(timeInterval);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }
            }
        }
        
        public void doDraw(Canvas canvas){
        	canvas.drawARGB(255, 0, 0, 0);
        	drawNavigation(canvas);
        	drawLines(canvas);
        	for(Cell[] array: cells){
				for(Cell c: array){
					c.draw(canvas);
				}
			}
        }
        
        public void drawNavigation(Canvas canvas){
        	paint.setARGB(255, 0, 127, 255);
        	//pause button
        	canvas.drawRect(0, 0, NAV_HEIGHT, NAV_HEIGHT, paint);
        	
        	//slider
        	canvas.drawRect(NAV_HEIGHT, (NAV_HEIGHT/2)-5, canvas.getWidth(), (NAV_HEIGHT/2)+5, paint);
        	canvas.drawCircle((NAV_HEIGHT+CIRCLE_RADIUS)+circlePos*((canvas.getWidth()-CIRCLE_RADIUS)/10), NAV_HEIGHT/2, CIRCLE_RADIUS, paint);
        	
        	//rest of pause button
        	paint.setARGB(255, 0, 0, 0);
        	canvas.drawRect(5, 5, NAV_HEIGHT/2 - 5, NAV_HEIGHT - 5, paint);
        	canvas.drawRect(NAV_HEIGHT/2 + 5, 5, NAV_HEIGHT-5, NAV_HEIGHT-5, paint);
        }
        
        public void drawLines(Canvas canvas){
        	paint.setARGB(255,0,0,255);
    		
    		for(int i=1; i<NUM_W; i++){
    			//draw vertical lines
    			canvas.drawLine(i*(canvas.getWidth()/NUM_W), NAV_HEIGHT, i*(canvas.getWidth()/NUM_W), canvas.getHeight(), paint);
    		}
    		for(int i=1; i<NUM_H; i++){
    			//draw horizontal lines
    			canvas.drawLine(0, NAV_HEIGHT+i*((canvas.getHeight()-NAV_HEIGHT)/NUM_H), canvas.getWidth(), NAV_HEIGHT+i*((canvas.getHeight()-NAV_HEIGHT)/NUM_H), paint);
    		}
        }
        
        public void updateCells(){
        	boolean[][] needsUpdate = new boolean[NUM_H][NUM_W];
        	for(int i=0; i<cells.length; i++){
				for(int j=0; j<cells[i].length; j++){
					if(cells[i][j].numNeighborsAlive() < 2 || cells[i][j].numNeighborsAlive() > 3){
						if(cells[i][j].isAlive()){
							needsUpdate[i][j] = true;
							//System.out.println("cell "+(i+1)+(j+1)+" is now dead");
						}
					}else if(cells[i][j].numNeighborsAlive() == 3 && !cells[i][j].isAlive()){
						needsUpdate[i][j] = true;
						//System.out.println("cell "+(i+1)+(j+1)+" is now alive");
					}
				}
			}
        	for(int i=0; i<cells.length; i++){
				for(int j=0; j<cells[i].length; j++){
					if(needsUpdate[i][j]){
						cells[i][j].toggle();
					}
				}
			}
        }
	}

}
