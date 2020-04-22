package ie.tudublin;

import processing.data.TableRow;

// Make a class called Task that encapsulates the columns from a single row from the task.csv file 
public class Task {
    private int start;
    private int end;
    private String task;

    // Make a private fields and public accessor methods 
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // Make a constructor that takes initial values and assigns them to the fields.
    public Task(int start, int end, String task) {
        this.start = start;
        this.end = end;
        this.task = task;
    }

    // Make a constructor that takes a processing TableRow field as a parameter
    public Task(TableRow tr) {
        this(tr.getInt("Start"), tr.getInt("End"),tr.getString("Task")); // check csv file 
    }

    // Make an appropiate toString method 
    @Override
    public String toString() {
        return "Task [end=" + end + ", start=" + start + ", task=" + task + "]";
    }

}	