package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Gantt extends PApplet
{	
	// Declare an ArrayList to hold instances of the Task class 
	ArrayList<Task> tasks = new ArrayList<Task>();

	float BorderLeft,w,h;
	float startXPoint;
	float gap;
	int rectHeight = 35;

	int selected1 = -1;
	int selected2 = -1;

	public void settings()
	{
		size(800, 600);

		startXPoint = width * 0.2f;
		BorderLeft = width * 0.06f;
		gap = height / 29;
		
		w = width * 0.02f;
        h = height * 0.15f;
	}

	// Write a method called loadTasks that populates the ArrayList from the file tasks.csv 
	public void loadTasks()
	{
		Table t = loadTable("tasks.csv", "header");
		for(TableRow row:t.rows()) {
			Task ta = new Task(row);
			tasks.add(ta);
		}
	}

	// Write a method printTasks that prints the elements of the ArrayList 
	public void printTasks()
	{
		for(Task ta:tasks) {
			System.out.println(ta);
		}
	}

	// Write a method called displayTasks() that displays the tasks as in the video.
	public void displayTasks() {

		colorMode(HSB);
		float border = width * 0.08f;

		float numTask = 9;
		float colourGap = 255 / numTask;

		for(int i = 0 ; i < tasks.size() ; i++)
        {
            Task ta = tasks.get(i);

			float x = map(ta.getStart(), 1, 30, startXPoint, width - border);
            float y = map(i, 0, tasks.size(), border, height - border);

			noStroke();
			fill(i * colourGap, 255, 255);
			rect(map(ta.getStart(), 1, 30, x, x + gap), y, (ta.getEnd() - ta.getStart()) * gap, rectHeight, 5);
			fill(255);
            textAlign(LEFT, CENTER);
            text(ta.getTask(), BorderLeft, y); 
		}

	}
	
	public void drawGrid() {

		float border = width * 0.06f;

        for(int i = 1 ; i <= 30 ; i++)
        {	
			float x = map(i, 1, 30, startXPoint, width - border);

            if(i % 2 == 0) {
				stroke(255);
			} else {
				stroke(100);
			}

			line(x, border, x , height - border);

			fill(255);
			textAlign(CENTER, CENTER);
			text(i, x, border / 2);
			 
        }

	}

	/* Write code that allows a user to alter the start day and end day of a task by clicking and dragging on the start 
	or end of a task with the mouse (20 pixels after the start or 20 pixels before the end of the rectangle)
	The user should not be able to set the start and end of the task beyond the range 1-30 and also should not be able to set the duration to 
	be less than 1. You can use methods mousePressed and mouseDragged to implement this functionality.
	The map function will be useful here too. It is not necessary to save the tasks back to the file. 
	*/
	public void mousePressed()
	{
		println("Mouse pressed");	
		float border = width * 0.08f;
		float boundary = 10;

        for(int i=0; i < tasks.size(); i++) {
            Task ta = tasks.get(i);

			float x = map(i, 0, ta.getStart(), startXPoint, width - border); 
			float left = map(ta.getStart(), 1, 30, x, x + gap);
            float right = map(ta.getEnd(), 1, 30, x, x + gap); 

			if(mouseX > left && mouseX < left + boundary &&
			   mouseY > h - w && mouseY < h + w) {
					selected1 = i;
			} else if(mouseX > right - boundary && mouseX < right &&
			          mouseY > h - w && mouseY < h + w){
					selected2 = i;
			} else {
				selected1 = i;
                selected2 = -1;
			}
			boundary += 40;
        }
	}

	public void mouseDragged()
	{
		println("Mouse Dragged");
		float border = width * 0.06f;
		int sz = 0;
		float gap = height/29;

        if(selected1 != -1) {
			Task ta = tasks.get(selected1);

			float x = map(ta.getStart(), 1, 30, startXPoint, width - border); 
			float left = map(ta.getStart(), 1, 30, x, x + gap);

			//drag to the left 
			if(mouseX < left && mouseX > startXPoint) {
				sz = ta.getStart() - 1;
				ta.setStart(sz);
			}
			//drag to the right 
			else if(mouseX > startXPoint && mouseX > left + gap && ta.getEnd() - ta.getStart() != 1) {
				sz = ta.getStart() + 1;
				ta.setStart(sz);
			}
        } else if(selected2 != -1) {
			Task ta = tasks.get(selected2);

			float x = map(ta.getStart(), 1, 30, startXPoint, width - border); 
            float right = map(ta.getEnd(), 1, 30, x, x + gap); 

			//drag to the left 
			if(mouseX > right && mouseX < startXPoint) {
				sz = ta.getEnd() + 1;
				ta.setEnd(sz);
			}
			//drag to the right 
			else if(mouseX < startXPoint && mouseX < right - gap && ta.getEnd() - ta.getStart() != 1) {
				sz = ta.getEnd() - 1;
				ta.setEnd(sz);
			}

		}
	}

	
	public void setup() 
	{
		loadTasks();
		printTasks();
	}
	
	public void draw()
	{			
		background(0);
		drawGrid();
		displayTasks();

	}
}
