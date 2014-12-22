package edu.sfsu.easymemo.ocr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedList;
import java.util.List;
 

 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TaskDBaAgain";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TASK_TABLE = "CREATE TABLE tasks ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "content TEXT, "+
                "date TEXT )";
 
        // create books table
        db.execSQL(CREATE_TASK_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS tasks");
 
        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------
 
    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
 
    // Books table name
    private static final String TABLE_TASKS = "tasks";
 
    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
 
    private static final String[] COLUMNS = {KEY_ID,KEY_CONTENT,KEY_DATE};
 
    public void addTask(Task task){
        Log.d("addTask", task.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, task.getContent()); // get title 
        values.put(KEY_DATE, task.getDate().toString()); // get author
 
        // 3. insert
        db.insert(TABLE_TASKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
    public Task getTask(int id){
 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_TASKS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        Task task = new Task();
        task.setId(Integer.parseInt(cursor.getString(0)));
        task.setContent(cursor.getString(1));
        task.setDate(cursor.getString(2));
 
        Log.d("getTask("+id+")", task.toString());
 
        // 5. return book
        return task;
    }
 
    // Get All Books
    public List<Task> getAllTasks() {
        List<Task> tasks = new LinkedList<Task>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TASKS;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Task task = null;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setContent(cursor.getString(1));
                task.setDate(cursor.getString(2));
 
                // Add book to books
                tasks.add(task);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllTasks()", tasks.toString());
 
        // return books
        return tasks;
    }
 
    // Get All Books by Date
    public List<Task> getAllTasksByDate(String Date) {
        List<Task> tasks = new LinkedList<Task>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TASKS + " WHERE date = " + Date;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Task task = null;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setContent(cursor.getString(1));
                task.setDate(cursor.getString(2));
 
                // Add book to books
                tasks.add(task);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllTasksByDate()" + query, tasks.toString());
 
        // return books
        return tasks;
    }
 
    
    
    
     // Updating single book
    public int updateTask(Task task) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("content", task.getContent()); // get title 
        values.put("date", task.getDate()); // get author
 
        // 3. updating row
        int i = db.update(TABLE_TASKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(task.getId()) }); //selection args
 
        // 4. close
        db.close();
 
        return i;
 
    }
 
    // Deleting single book
    public void deleteTask(Task task) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_TASKS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(task.getId()) });
 
        // 3. close
        db.close();
 
        Log.d("deleteTask", task.toString());
 
    }
}