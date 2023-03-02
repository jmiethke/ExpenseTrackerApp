package com.c323proj9.jmiethke;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static  final int DATABASE_VERSION = 1;
    private static  final String DATABASE_NAME = "ExpensesDB.db";
    private static  final String TABLE_EXPENSES = "myExpenses";
    private static  final String COLUMN_ID = "id";
    private static  final String COLUMN_NAME = "name";
    private static  final String COLUMN_MONEYSPENT = "moneySpent";
    private static  final String COLUMN_DATE = "date";
    private static  final String COLUMN_CATEGORY = "category";

    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates a databse based off of the final values inside of this class.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE TABLE "+TABLE_EXPENSES+ "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_CATEGORY + " TEXT," + COLUMN_MONEYSPENT + " REAL," + COLUMN_DATE + " TEXT)";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    /**
     * Recreates database
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    /**
     * Adds the expense to the database.
     * @param expense the expense to be added.
     */
    public void addExpenseToDB(Expense expense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, expense.getName());
        contentValues.put(COLUMN_CATEGORY, expense.getCategory());
        contentValues.put(COLUMN_DATE, expense.getDate());
        contentValues.put(COLUMN_MONEYSPENT, expense.getMoneySpent());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXPENSES, null, contentValues);
        db.close();
    }

    /**
     * Finds the Expenses in the database with the category provided in the parameter.
     * @param category
     * @return a ArrayList of Expenses
     */
    public ArrayList<Expense> findExpensesDB(String category) {
        ArrayList<Expense> res = new ArrayList<>();
        String query;
        if (category.equals("All")) {
            query = "SELECT * FROM " + TABLE_EXPENSES;
        } else {
            query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " +COLUMN_CATEGORY + " = \'" + category + "\'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Expense expense;
        while(cursor.moveToNext()){
            expense = new Expense();
            expense.setId(Integer.parseInt(cursor.getString(0)));
            expense.setName(cursor.getString(1));
            expense.setCategory(cursor.getString(2));
            expense.setMoneySpent(Double.parseDouble(cursor.getString(3)));
            expense.setDate(cursor.getString(4));
            res.add(expense);
        }
        return res;
    }

    /**
     * Deletes the expense from the database using its unique ID.
     * @param expense
     */
    public void deleteExpense(Expense expense) {
        String query = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " + COLUMN_ID + " = "+ expense.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Expense expense1 = new Expense();
        if (cursor.moveToFirst()){
            expense1.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_EXPENSES,COLUMN_ID+" = ?", new String[] {String.valueOf(expense1.getId())});
            cursor.close();
        }
        db.close();
    }
}
