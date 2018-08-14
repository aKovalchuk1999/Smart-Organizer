package com.android.rivchat.localDatabase.lessons;

/**
 * Created by Andriy on 01.06.2018.
 */

public class Lesson {

    public static final String TABLE_NAME = "lessons";

    public static final String COLUMN_ID = "lesson_id";
    public static final String COLUMN_NUM = "lesson_num";
    public static final String COLUMN_NAME = "lesson_name";
    public static final String COLUMN_TEACHER = "lesson_teacher";
    public static final String COLUMN_AUDIENCE = "lesson_audience";
    public static final String COLUMN_START = "lesson_start";
    public static final String COLUMN_END = "lesson_end";
    public static final String COLUMN_WEEK = "lesson_week";
    public static final String COLUMN_DAY = "lesson_day";


    private int id;
    private int num;
    private String name;
    private String teacher;
    private String audience;
    private String start;
    private String end;
    private String week;
    private String day;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NUM + " INTEGER,"
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_TEACHER + " TEXT, "
                    + COLUMN_AUDIENCE + " TEXT, "
                    + COLUMN_START + " TEXT, "
                    + COLUMN_END + " TEXT, "
                    + COLUMN_WEEK + " TEXT, "
                    + COLUMN_DAY + " TEXT"
                    + ")";

    public Lesson() {}

    public Lesson(int id, int num, String name, String teacher, String audience, String start, String end, String week, String day) {
        this.id = id;
        this.num = num;
        this.name = name;
        this.teacher = teacher;
        this.audience = audience;
        this.start = start;
        this.end = end;
        this.week = week;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
