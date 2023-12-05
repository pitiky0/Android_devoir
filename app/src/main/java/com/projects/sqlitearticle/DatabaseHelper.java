package com.projects.sqlitearticle;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "article.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE Article (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "libelle TEXT," +
                    "pu INTEGER" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Article");
        onCreate(db);
    }

    // Méthode pour insérer un nouvel article dans la table Article
    public long insertArticle(String libelle, int pu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("libelle", libelle);
        values.put("pu", pu);
        long newRowId = db.insert("Article", null, values);
        db.close();
        return newRowId;
    }

    // Méthode pour récupérer tous les articles de la table Article
    public List<Article> getAllArticles() {
        List<Article> listOfArticles = new ArrayList<>();
        String selectQuery = "SELECT * FROM Article";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String libelle = cursor.getString(cursor.getColumnIndex("libelle"));
                int pu = cursor.getInt(cursor.getColumnIndex("pu"));

                Article article = new Article(id, libelle, pu);
                listOfArticles.add(article);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return listOfArticles;
    }
}