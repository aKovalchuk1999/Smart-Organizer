package com.android.rivchat.localDatabase.lessonsTime;

/**
 * Created by Andriy on 31.05.2018.
 */

public class LessonTime {
    public static final String TABLE_NAME = "time_time";

    public static final String COLUMN_ID = "time_id";
    public static final String COLUMN_NUMBER = "time_number";
    public static final String COLUMN_START = "time_start";
    public static final String COLUMN_END = "time_end";

    private int id;
    private int number;
    private String start;
    private String end;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NUMBER + " INTEGER,"
                    + COLUMN_START + " TEXT,"
                    + COLUMN_END + " TEXT"
                    + ")";

    public LessonTime() {}

    public LessonTime(int id, int number, String start, String end) {
        this.id = id;
        this.number = number;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
