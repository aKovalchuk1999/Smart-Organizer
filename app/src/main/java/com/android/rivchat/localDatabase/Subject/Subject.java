package com.android.rivchat.localDatabase.Subject;

/**
 * Created by Andriy on 31.05.2018.
 */

public class Subject {
    public static final String TABLE_NAME = "subjects";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_TEACHER = "teacher";

    private int id;
    private String subject;
    private String teacher;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SUBJECT + " TEXT,"
                    + COLUMN_TEACHER + " TEXT"
                    + ")";

    public Subject() {}

    public Subject(int id, String subject, String teacher) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
