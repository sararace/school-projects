package edu.ucsd.vis141a.snmillar.formart;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CheckBoxLine extends View {
    Paint paint = new Paint();
    Map<Integer, int[]> lines = new Hashtable<Integer, int[]>();

    public CheckBoxLine(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
            //canvas.drawLine(0, 0, 20, 20, paint);
            //canvas.drawLine(20, 0, 0, 20, paint);
    	Iterator<int[]> it = lines.values().iterator();
    	int[] n;
    	while(it.hasNext()){
    		n = it.next();
    		canvas.drawLine(n[0], n[1], n[2], n[3], paint);
    	}
    }
    
    public void addLine(int id, int startx, int starty, int endx, int endy){
    	lines.put(id, new int[]{startx, starty, endx, endy});
    }
    
    public void removeLine(int id){
    	if(lines.containsKey(id))
    		lines.remove(id);
    }

}