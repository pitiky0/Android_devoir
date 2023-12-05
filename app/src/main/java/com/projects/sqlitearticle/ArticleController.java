package com.projects.sqlitearticle;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import android.database.Cursor;
import java.util.ArrayList;

public class ArticleController {

    private DatabaseHelper dbHelper;

    public ArticleController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Méthode pour ajouter un nouvel article à la base de données
    public long addArticle(String libelle, int pu) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("libelle", libelle);
        values.put("pu", pu);

        long newRowId = db.insert("Article", null, values);
        db.close();
        return newRowId;
    }

    public List<Article> getAllArticles() {
        List<Article> listOfArticles = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "Article",
                null,
                null,
                null,
                null,
                null,
                null
        );

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
