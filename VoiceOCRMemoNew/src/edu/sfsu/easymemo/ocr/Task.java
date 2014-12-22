package edu.sfsu.easymemo.ocr;

import java.sql.Date;

public class Task {
    private int id;
    private String content;
    private String date;
 
    public Task(){}
 
    public Task(String content, String date) {
        super();
        this.content = content;
        this.date = date;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + content + ", date=" + date
                + "]";
    }

	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	public String getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	public void setContent(String string) {
		// TODO Auto-generated method stub
		this.content = string;
	}

	public void setDate(String string) {
		// TODO Auto-generated method stub
		this.date = string;
		
	}

	public void setId(int parseInt) {
		// TODO Auto-generated method stub
		this.id = parseInt;
		
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
