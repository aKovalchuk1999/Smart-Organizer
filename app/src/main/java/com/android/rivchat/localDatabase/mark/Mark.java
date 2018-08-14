package com.android.rivchat.localDatabase.mark;

/**
 * Created by Andriy on 05.06.2018.
 */

public class Mark {
    public static final String TABLE_NAME = "marks";

    public static final String COLUMN_ID = "mark_id";
    public static final String COLUMN_SUBJECT = "mark_subject";
    public static final String COLUMN_MARK = "mark_mark";
    public static final String COLUMN_NOTE = "mark_note";
    public static final String COLUMN_DATE = "mark_date";


    private int id;
    private String subject;
    private String mark;
    private String note;
    private String date;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SUBJECT + " TEXT,"
                    + COLUMN_MARK + " TEXT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Mark(){}

    public Mark(int id, String subject, String note, String mark, String date) {
        this.id = id;
        this.subject = subject;
        this.mark = mark;
        this.note = note;
        this.date = date;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
