package com.android.rivchat.localDatabase.homework;

/**
 * Created by Andriy on 03.06.2018.
 */

public class Homework {
    public static final String TABLE_NAME = "homeworks";

    public static final String COLUMN_ID = "homework_id";
    public static final String COLUMN_SUBJECT = "homework_subject";
    public static final String COLUMN_DATE = "homework_date";
    public static final String COLUMN_HOMEWORK = "homework_homework";
    public static final String COLUMN_PRIORITY = "homework_priority";
    public static final String COLUMN_COMPLETED = "homework_completed";


    private int id;
    private String subject;
    private String date;
    private String homework;
    private String priority;
    private String completed;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SUBJECT + " TEXT,"
                    + COLUMN_HOMEWORK + " TEXT,"
                    + COLUMN_PRIORITY + " TEXT,"
                    + COLUMN_COMPLETED + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public  Homework(){}

    public Homework(int id, String subject, String homework, String priority, String date, String completed) {
        this.id = id;
        this.subject = subject;
        this.homework = homework;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
