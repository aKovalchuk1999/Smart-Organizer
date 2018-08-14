package com.android.rivchat.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.rivchat.localDatabase.Notemark.Notemark;
import com.android.rivchat.localDatabase.Subject.Subject;
import com.android.rivchat.localDatabase.homework.Homework;
import com.android.rivchat.localDatabase.lessons.Lesson;
import com.android.rivchat.localDatabase.lessonsTime.LessonTime;
import com.android.rivchat.localDatabase.mark.Mark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andriy on 10.05.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "organizer_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Notemark.CREATE_TABLE);
        db.execSQL(Subject.CREATE_TABLE);
        db.execSQL(Lesson.CREATE_TABLE);
        db.execSQL(LessonTime.CREATE_TABLE);
        db.execSQL(Homework.CREATE_TABLE);
        db.execSQL(Mark.CREATE_TABLE);
        //db.execSQL(Mark.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Notemark.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Lesson.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LessonTime.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Homework.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Mark.TABLE_NAME);

        // Create tables again
         onCreate(db);

    }

    public long insertNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Notemark.COLUMN_NOTE, note);
        // insert row
        long id = db.insert(Notemark.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public Notemark getNote(long id) {

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Notemark.TABLE_NAME,
                new String[]{Notemark.COLUMN_ID, Notemark.COLUMN_NOTE, Notemark.COLUMN_TIMESTAMP},
                Notemark.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        Notemark note = new Notemark(
                cursor.getInt(cursor.getColumnIndex(Notemark.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Notemark.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Notemark.COLUMN_TIMESTAMP)));
        // close the db connection
        cursor.close();
        return note;
    }

    public List<Notemark> getAllNotes() {
        List<Notemark> notes = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Notemark.TABLE_NAME + " ORDER BY " +
                Notemark.COLUMN_TIMESTAMP + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notemark note = new Notemark();
                note.setId(cursor.getInt(cursor.getColumnIndex(Notemark.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Notemark.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Notemark.COLUMN_TIMESTAMP)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return notes list
        return notes;
    }



    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Notemark.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int updateNote(Notemark note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Notemark.COLUMN_NOTE, note.getNote());
        // updating row
        return db.update(Notemark.TABLE_NAME, values, Notemark.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }


    public void deleteNote(Notemark note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Notemark.TABLE_NAME, Notemark.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }









//------------------------------------------------------------------------------------------------------------------



    public long insertLesson(int num, String name, String teacher, String audience, String week, String start, String end, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Lesson.COLUMN_NUM, num);
        values.put(Lesson.COLUMN_NAME, name);
        values.put(Lesson.COLUMN_TEACHER, teacher);
        values.put(Lesson.COLUMN_AUDIENCE, audience);
        values.put(Lesson.COLUMN_WEEK, week);
        values.put(Lesson.COLUMN_START, start);
        values.put(Lesson.COLUMN_END, end);
        values.put(Lesson.COLUMN_DAY, day);
        long id = db.insert(Lesson.TABLE_NAME, null, values);
        db.close();
        return id;
    }


    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Lesson.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setId(cursor.getInt(cursor.getColumnIndex(Lesson.COLUMN_ID)));
                lesson.setNum(cursor.getInt(cursor.getColumnIndex(Lesson.COLUMN_NUM)));
                lesson.setName(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_NAME)));
                lesson.setTeacher(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_TEACHER)));
                lesson.setAudience(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_AUDIENCE)));
                lesson.setWeek(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_WEEK)));
                lesson.setStart(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_START)));
                lesson.setEnd(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_END)));
                lesson.setDay(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_DAY)));
                lessons.add(lesson);
            } while (cursor.moveToNext());
        }
        db.close();
        return lessons;
    }


    public List<Lesson> getLessonsByDay(String _day) {
        List<Lesson> lessons = new ArrayList<>();

        String selectQuery ="SELECT * FROM " + Lesson.TABLE_NAME +" WHERE " + Lesson.COLUMN_DAY + " = '" + _day + "' ORDER BY " + Lesson.COLUMN_NUM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setId(cursor.getInt(cursor.getColumnIndex(Lesson.COLUMN_ID)));
                lesson.setNum(cursor.getInt(cursor.getColumnIndex(Lesson.COLUMN_NUM)));
                lesson.setName(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_NAME)));
                lesson.setTeacher(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_TEACHER)));
                lesson.setAudience(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_AUDIENCE)));
                lesson.setWeek(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_WEEK)));
                lesson.setStart(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_START)));
                lesson.setEnd(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_END)));
                lesson.setDay(cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_DAY)));
                lessons.add(lesson);
            } while (cursor.moveToNext());
        }
        db.close();
        return lessons;
    }


    public Lesson getLesson(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Lesson.TABLE_NAME,
                new String[]{Lesson.COLUMN_ID, Lesson.COLUMN_NUM, Lesson.COLUMN_NAME, Lesson.COLUMN_TEACHER, Lesson.COLUMN_AUDIENCE, Lesson.COLUMN_WEEK, Lesson.COLUMN_START, Lesson.COLUMN_END, Lesson.COLUMN_DAY},
                Lesson.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        Lesson lesson = new Lesson(
                cursor.getInt(cursor.getColumnIndex(Lesson.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Lesson.COLUMN_NUM)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_TEACHER)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_AUDIENCE)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_WEEK)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_END)),
                cursor.getString(cursor.getColumnIndex(Lesson.COLUMN_DAY)));
        // close the db connection
        cursor.close();
        return lesson;
    }

    public int getLessonsCount() {
        String countQuery = "SELECT * FROM " + Lesson.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    public int getLessonsCountByDay(String _day) {
        String countQuery = "SELECT * FROM " + Lesson.TABLE_NAME + " WHERE " +
                Lesson.COLUMN_DAY + " = '" + _day + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Lesson.COLUMN_NUM, lesson.getNum());
        values.put(Lesson.COLUMN_NAME, lesson.getName());
        values.put(Lesson.COLUMN_TEACHER, lesson.getTeacher());
        values.put(Lesson.COLUMN_AUDIENCE, lesson.getAudience());
        values.put(Lesson.COLUMN_WEEK, lesson.getWeek());
        values.put(Lesson.COLUMN_START, lesson.getStart());
        values.put(Lesson.COLUMN_END, lesson.getEnd());
        values.put(Lesson.COLUMN_DAY, lesson.getDay());
        // updating row
        return db.update(Lesson.TABLE_NAME, values, Lesson.COLUMN_ID + " = ?",
                new String[]{String.valueOf(lesson.getId())});
    }


    public void deleteLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Lesson.TABLE_NAME, Lesson.COLUMN_ID + " = ?",
                new String[]{String.valueOf(lesson.getId())});
        db.close();
    }










    public long insertSubject(String name, String teacher) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject.COLUMN_SUBJECT, name);
        values.put(Subject.COLUMN_TEACHER, teacher);
        long id = db.insert(Subject.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Subject getSubject(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Subject.TABLE_NAME,
                new String[]{Subject.COLUMN_ID, Subject.COLUMN_SUBJECT, Subject.COLUMN_TEACHER},
                Subject.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Subject subject = new Subject(
                cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(Subject.COLUMN_TEACHER)));
        cursor.close();
        return subject;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Subject.TABLE_NAME + " ORDER BY " +
                Subject.COLUMN_SUBJECT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex(Subject.COLUMN_ID)));
                subject.setSubject(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_SUBJECT)));
                subject.setTeacher(cursor.getString(cursor.getColumnIndex(Subject.COLUMN_TEACHER)));
                subjects.add(subject);
            } while (cursor.moveToNext());
        }
        db.close();
        return subjects;
    }



    public int getSubjectsCount() {
        String countQuery = "SELECT * FROM " + Subject.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject.COLUMN_SUBJECT, subject.getSubject());
        values.put(Subject.COLUMN_TEACHER, subject.getTeacher());
        return db.update(Subject.TABLE_NAME, values, Subject.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subject.getId())});
    }

    public void deleteSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Subject.TABLE_NAME, Subject.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subject.getId())});
        db.close();
    }




    //--------------------------------------------------------------------------------

    public long insertTime(int number, String start, String end) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LessonTime.COLUMN_NUMBER, number);
        values.put(LessonTime.COLUMN_START, start);
        values.put(LessonTime.COLUMN_END, end);
        long id = db.insert(LessonTime.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public LessonTime getTime(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(LessonTime.TABLE_NAME,
                new String[]{LessonTime.COLUMN_ID, LessonTime.COLUMN_NUMBER, LessonTime.COLUMN_START, LessonTime.COLUMN_END},
                LessonTime.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        LessonTime lts = new LessonTime(
                cursor.getInt(cursor.getColumnIndex(LessonTime.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(LessonTime.COLUMN_NUMBER)),
                cursor.getString(cursor.getColumnIndex(LessonTime.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(LessonTime.COLUMN_END)));
        cursor.close();
        return lts;
    }

    public LessonTime getTimeByNum(int num) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(LessonTime.TABLE_NAME,
                new String[]{LessonTime.COLUMN_ID, LessonTime.COLUMN_NUMBER, LessonTime.COLUMN_START, LessonTime.COLUMN_END},
                LessonTime.COLUMN_NUMBER + "=?",
                new String[]{String.valueOf(num)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        LessonTime lts = new LessonTime(
                cursor.getInt(cursor.getColumnIndex(LessonTime.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(LessonTime.COLUMN_NUMBER)),
                cursor.getString(cursor.getColumnIndex(LessonTime.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(LessonTime.COLUMN_END)));
        cursor.close();
        return lts;
    }

    public List<LessonTime> getAllTimes() {
        List<LessonTime> lst = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + LessonTime.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                LessonTime ls = new LessonTime();
                ls.setId(cursor.getInt(cursor.getColumnIndex(LessonTime.COLUMN_ID)));
                ls.setNumber(cursor.getInt(cursor.getColumnIndex(LessonTime.COLUMN_NUMBER)));
                ls.setStart(cursor.getString(cursor.getColumnIndex(LessonTime.COLUMN_START)));
                ls.setEnd(cursor.getString(cursor.getColumnIndex(LessonTime.COLUMN_END)));
                lst.add(ls);
            } while (cursor.moveToNext());
        }
        db.close();
        return lst;
    }



    public int getTimesCount() {
        String countQuery = "SELECT * FROM " + LessonTime.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateTime(LessonTime ls) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LessonTime.COLUMN_NUMBER, ls.getNumber());
        values.put(LessonTime.COLUMN_START, ls.getStart());
        values.put(LessonTime.COLUMN_END, ls.getEnd());
        return db.update(LessonTime.TABLE_NAME, values, LessonTime.COLUMN_ID + " = ?",
                new String[]{String.valueOf(ls.getId())});
    }

    public void deleteTime(LessonTime ls) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LessonTime.TABLE_NAME, LessonTime.COLUMN_ID + " = ?",
                new String[]{String.valueOf(ls.getId())});
        db.close();
    }

//--------------------------------------------------------------------------------------------------


    public long insertHomework(String subject, String homework, String priority, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Homework.COLUMN_SUBJECT, subject);
        values.put(Homework.COLUMN_HOMEWORK, homework);
        values.put(Homework.COLUMN_PRIORITY, priority);
        values.put(Homework.COLUMN_DATE, date);
        values.put(Homework.COLUMN_COMPLETED, "NO");
        long id = db.insert(Homework.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Homework getHomework(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Homework.TABLE_NAME,
                new String[]{Homework.COLUMN_ID, Homework.COLUMN_SUBJECT, Homework.COLUMN_HOMEWORK, Homework.COLUMN_PRIORITY, Homework.COLUMN_DATE, Homework.COLUMN_COMPLETED},
                Homework.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        Homework note = new Homework(
                cursor.getInt(cursor.getColumnIndex(Homework.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Homework.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(Homework.COLUMN_HOMEWORK)),
                cursor.getString(cursor.getColumnIndex(Homework.COLUMN_PRIORITY)),
                cursor.getString(cursor.getColumnIndex(Homework.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(Homework.COLUMN_COMPLETED)));
        // close the db connection
        cursor.close();
        return note;
    }

    public List<Homework> getAllHomeworks() {
        List<Homework> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Homework.TABLE_NAME + " ORDER BY " +
                Homework.COLUMN_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Homework note = new Homework();
                note.setId(cursor.getInt(cursor.getColumnIndex(Homework.COLUMN_ID)));
                note.setSubject(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_SUBJECT)));
                note.setHomework(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_HOMEWORK)));
                note.setPriority(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_PRIORITY)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_DATE)));
                note.setCompleted(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_COMPLETED)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return notes;
    }

    public List<Homework> getHomeworksByDate(String _date) {
        List<Homework> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Homework.TABLE_NAME + " WHERE " +
                Homework.COLUMN_DATE + " = '" + _date + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Homework note = new Homework();
                note.setId(cursor.getInt(cursor.getColumnIndex(Homework.COLUMN_ID)));
                note.setSubject(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_SUBJECT)));
                note.setHomework(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_HOMEWORK)));
                note.setPriority(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_PRIORITY)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_DATE)));
                note.setCompleted(cursor.getString(cursor.getColumnIndex(Homework.COLUMN_COMPLETED)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return notes;
    }



    public int getHomeworksCount() {
        String countQuery = "SELECT  * FROM " + Homework.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int getHomeworksCountByDate(String date) {
        String countQuery = "SELECT  * FROM " + Homework.TABLE_NAME + " WHERE " + Homework.COLUMN_DATE + "='" + date + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int updateHomework(Homework note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Homework.COLUMN_SUBJECT, note.getSubject());
        values.put(Homework.COLUMN_HOMEWORK, note.getHomework());
        values.put(Homework.COLUMN_PRIORITY, note.getPriority());
        values.put(Homework.COLUMN_DATE, note.getDate());
        values.put(Homework.COLUMN_COMPLETED, note.getCompleted());
        // updating row
        return db.update(Homework.TABLE_NAME, values, Homework.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }


    public void deleteHomework(Homework note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Homework.TABLE_NAME, Homework.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }






//------------------------------------------------------------------------------------------------------------------------------

    public long insertMark(String subject, String note, String mark, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Mark.COLUMN_SUBJECT, subject);
        values.put(Mark.COLUMN_NOTE, note);
        values.put(Mark.COLUMN_MARK, mark);
        values.put(Mark.COLUMN_DATE, date);
        long id = db.insert(Mark.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Mark getMark(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Mark.TABLE_NAME,
                new String[]{Mark.COLUMN_ID, Mark.COLUMN_SUBJECT, Mark.COLUMN_NOTE, Mark.COLUMN_MARK, Mark.COLUMN_DATE},
                Mark.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        Mark mark = new Mark(
                cursor.getInt(cursor.getColumnIndex(Mark.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_DATE)));
        // close the db connection
        cursor.close();
        return mark;
    }

    public List<Mark> getAllMarks() {
        List<Mark> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Mark.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Mark note = new Mark();
                note.setId(cursor.getInt(cursor.getColumnIndex(Mark.COLUMN_ID)));
                note.setSubject(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_SUBJECT)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_NOTE)));
                note.setMark(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_DATE)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return notes;
    }

    public List<Mark> getMarksBySubject(String _subject) {
        List<Mark> marks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Mark.TABLE_NAME + " WHERE " +
                Mark.COLUMN_SUBJECT + "='" + _subject + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Mark note = new Mark();
                note.setId(cursor.getInt(cursor.getColumnIndex(Mark.COLUMN_ID)));
                note.setSubject(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_SUBJECT)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_NOTE)));
                note.setMark(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_DATE)));
                marks.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return marks;
    }


    public int getMarksCount() {
        String countQuery = "SELECT * FROM " + Mark.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getMarksCountBySubject(String subject) {
        String countQuery = "SELECT * FROM " + Mark.TABLE_NAME + " WHERE " + Mark.COLUMN_SUBJECT + "='" + subject + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateMark(Mark mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Mark.COLUMN_SUBJECT, mark.getSubject());
        values.put(Mark.COLUMN_NOTE, mark.getNote());
        values.put(Mark.COLUMN_MARK, mark.getMark());
        values.put(Mark.COLUMN_DATE, mark.getDate());
        return db.update(Mark.TABLE_NAME, values, Mark.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mark.getId())});
    }


    public void deleteMark(Mark note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Mark.TABLE_NAME, Mark.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }














/*

    public long insertMark(String mark, String subject, String date) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Mark.COLUMN_MARK, mark);
        values.put(Mark.COLUMN_SUBJECT, subject);
        values.put(Mark.COLUMN_DATE, date);
        long id = db.insert(Mark.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Mark getMark(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Mark.TABLE_NAME,
                new String[]{Mark.COLUMN_ID, Mark.COLUMN_MARK, Mark.COLUMN_SUBJECT, Mark.COLUMN_DATE},
                Mark.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Mark mark = new Mark(
                cursor.getInt(cursor.getColumnIndex(Mark.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(Mark.COLUMN_DATE)));
        cursor.close();
        return mark;
    }

    public List<Mark> getAllMarks() {
        List<Mark> marks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Mark.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Mark mark = new Mark();
                mark.setId(cursor.getInt(cursor.getColumnIndex(Mark.COLUMN_ID)));
                mark.setMark(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK)));
                mark.setMark_subject(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_SUBJECT)));
                mark.setMark_date(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_DATE)));
                marks.add(mark);
            } while (cursor.moveToNext());
        }
        db.close();
        return marks;
    }

    public List<Mark> getMarksByDay(String _subject){
        List<Mark> marks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Mark.TABLE_NAME + " WHERE " + Mark.COLUMN_SUBJECT + " = '" + _subject + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Mark mark = new Mark();
                mark.setId(cursor.getInt(cursor.getColumnIndex(Mark.COLUMN_ID)));
                mark.setMark(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK)));
                mark.setMark_subject(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_SUBJECT)));
                mark.setMark_date(cursor.getString(cursor.getColumnIndex(Mark.COLUMN_DATE)));
                marks.add(mark);
            } while (cursor.moveToNext());
        }
        db.close();
        return marks;
    }


    public int getMarksSum(){
        int sum = 0;
        String temp;
        String selectQuery = "SELECT "+Mark.COLUMN_MARK+" FROM " + Mark.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                temp = cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK));
                int temp1 = Integer.parseInt(temp);
                sum += temp1;
            } while (cursor.moveToNext());
        }
        db.close();
        return sum;
    }

    public int getMarksSumByDay(String _subject){
        int sum = 0;
        String temp;
        String selectQuery = "SELECT "+Mark.COLUMN_MARK+" FROM " + Mark.TABLE_NAME + " WHERE " + Mark.COLUMN_SUBJECT + " = '" + _subject + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                temp = cursor.getString(cursor.getColumnIndex(Mark.COLUMN_MARK));
                int temp1 = Integer.parseInt(temp);
                sum += temp1;
            } while (cursor.moveToNext());
        }
        db.close();
        return sum;
    }




    public int getMarkssCount() {
        String countQuery = "SELECT * FROM " + Mark.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateMark(Mark mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Mark.COLUMN_MARK, mark.getMark());
        values.put(Mark.COLUMN_SUBJECT, mark.getMark_subject());
        values.put(Mark.COLUMN_DATE, mark.getMark_date());
        return db.update(Mark.TABLE_NAME, values, Mark.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mark.getId())});
    }

    public void deleteMark(Mark mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Mark.TABLE_NAME, Mark.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mark.getId())});
        db.close();
    }



    */
}
