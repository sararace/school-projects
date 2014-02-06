package edu.ucsd.vis141a.snmillar.formart;

import java.util.Hashtable;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class FormArtActivity extends Activity implements OnClickListener{
	
	//fields
	boolean buttonClicked[];
	Map<Integer, int[]> lines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_art);
		
		buttonClicked = new boolean[]{false,false,false};
		
		((CheckBox)findViewById(R.id.check0)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.check1)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.check2)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.check3)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.check4)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.check5)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.check6)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.leftAntenna)).setOnClickListener(this);
		((CheckBox)findViewById(R.id.rightAntenna)).setOnClickListener(this);
		
		((Button)findViewById(R.id.leftArm)).setOnClickListener(this);
		((Button)findViewById(R.id.rightArm)).setOnClickListener(this);
		((Button)findViewById(R.id.leftLeg)).setOnClickListener(this);
		((Button)findViewById(R.id.rightLeg)).setOnClickListener(this);
		
		((RadioButton)findViewById(R.id.radio0)).setOnClickListener(this);
		((RadioButton)findViewById(R.id.radio1)).setOnClickListener(this);
		
		NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker1);
	    String[] nums = new String[10];
	    for(int i=0; i<nums.length; i++)
	           nums[i] = Integer.toString(i+1);

	    np.setMinValue(1);
	    np.setMaxValue(10);
	    np.setWrapSelectorWheel(false);
	    np.setDisplayedValues(nums);
	    np.setValue(1);
	    np.setBackgroundResource(R.color.green);
	    np.setOnClickListener(this);
	    
	    lines = new Hashtable<Integer, int[]>();
	    lines.put(R.id.check0, new int[]{R.id.check1,R.id.check3});
	    lines.put(R.id.check1, new int[]{R.id.check0,R.id.check2,R.id.leftAntenna});
	    lines.put(R.id.check2, new int[]{R.id.check1,R.id.check4});
	    lines.put(R.id.check3, new int[]{R.id.check0,R.id.check6});
	    lines.put(R.id.check4, new int[]{R.id.check2,R.id.check5});
	    lines.put(R.id.check5, new int[]{R.id.check4,R.id.check6,R.id.rightAntenna});
	    lines.put(R.id.check6, new int[]{R.id.check3,R.id.check5});
	    lines.put(R.id.leftAntenna, new int[]{R.id.check1});
	    lines.put(R.id.rightAntenna, new int[]{R.id.check5});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form_art, menu);
		return true;
	}
	
	public void onClick(View view){
		if(view instanceof CheckBox){
			CheckBox clicked = (CheckBox)view;
			/*CheckBoxLine c = new CheckBoxLine(this);
			if(clicked.isChecked()){
				for(int i=0; i<lines.get(clicked.getId()).length; i++){
					CheckBox other = (CheckBox)findViewById(lines.get(clicked.getId())[i]);
					if(other.isChecked()){
						c.addLine(clicked.getId() + lines.get(clicked.getId())[i], clicked.getLeft()+clicked.getWidth()/2, clicked.getTop()+clicked.getHeight(), 
								other.getLeft() + other.getWidth()/2, other.getTop()+other.getHeight());
					}
				}
			}else{
				for(int i=0; i<lines.get(clicked.getId()).length; i++){
					c.removeLine(clicked.getId() + lines.get(clicked.getId())[i]);
				}
			}
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			((RelativeLayout)findViewById(R.layout.activity_form_art)).addView(c, params);
			*/
			switch(clicked.getId()){
			case R.id.check0:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.red);
				break;
			case R.id.check1:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.orange);
				break;
			case R.id.check2:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.yellow);
				break;
			case R.id.check3:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.green);
				break;
			case R.id.check4:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.teal);
				break;
			case R.id.check5:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.blue);
				break;
			case R.id.check6:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.purple);
				break;
			case R.id.leftAntenna:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.pink);
				break;
			case R.id.rightAntenna:
				((NumberPicker)findViewById(R.id.numberPicker1)).setBackgroundResource(R.color.grey);
				break;
			}
		}
		else if(view instanceof RadioButton){
			RadioButton clicked = (RadioButton)view;
			switch(clicked.getId()){
			case R.id.radio0:
			case R.id.radio1:
				clicked.setBackgroundResource(R.color.green);
				break;
			default:
				break;
			}
		}
		else if(view instanceof NumberPicker){
			NumberPicker np = (NumberPicker)view;
			np.getValue();
		}
		else if(view instanceof Button){
			Button clicked = (Button)view;
			switch(clicked.getId()){
			case R.id.leftArm:
				if(buttonClicked[0]){
					clicked.setBackgroundResource(R.color.grey);
					buttonClicked[0] = false;
				}
				else {
					clicked.setBackgroundResource(R.color.green);
					buttonClicked[0] = true;
				}
				break;
			case R.id.rightArm:
				if(buttonClicked[1]){
					clicked.setBackgroundResource(R.color.grey);
					buttonClicked[1] = false;
				}
				else {
					clicked.setBackgroundResource(R.color.green);
					buttonClicked[1] = true;
				}
				break;
			case R.id.leftLeg:
				if(buttonClicked[2]){
					clicked.setBackgroundResource(R.color.grey);
					buttonClicked[2] = false;
				}
				else {
					clicked.setBackgroundResource(R.color.green);
					buttonClicked[2] = true;
				}
				break;
			case R.id.rightLeg:
				Intent intent = new Intent(FormArtActivity.this, FormArtResultsActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}
	

}
