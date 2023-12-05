package com.projects.sqlitearticle;


import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {

    private ArticleController articleController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articleController = new ArticleController(this);

        TableLayout tableLayout = findViewById(R.id.tableLayoutArticles);
        EditText editTextLibelle = findViewById(R.id.editTextLibelle);
        EditText editTextPU = findViewById(R.id.editTextPU);
        Button buttonAddArticle = findViewById(R.id.buttonAddArticle);

        buttonAddArticle.setOnClickListener(v -> {
            String libelle = editTextLibelle.getText().toString();
            String puStr = editTextPU.getText().toString();

            if (!libelle.isEmpty() && !puStr.isEmpty()) {
                try {
                    int pu = Integer.parseInt(puStr);
                    long newRowId = articleController.addArticle(libelle, pu);

                    if (newRowId != -1) {
                        updateArticleList(tableLayout);
                        editTextLibelle.setText("");
                        editTextPU.setText("");
                    } else {
                        // Gérer les erreurs d'ajout d'article
                        Toast.makeText(ArticleActivity.this, "Erreur lors de l'ajout de l'article", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Gérer l'erreur de conversion de la chaîne en entier
                    Toast.makeText(ArticleActivity.this, "Veuillez entrer un prix valide", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Gérer les champs vides
                Toast.makeText(ArticleActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
        });

        updateArticleList(tableLayout);
    }

    // Méthode pour afficher les articles dans la TableLayout
    private void displayArticles(TableLayout table, List<Article> articleList) {
        // Clear the existing views to avoid duplicates
        table.removeAllViews();

        // Création de la ligne d'en-tête
        TableRow headerRow = new TableRow(this);
        TextView idHeaderRowTextView = createTextViewWithBorder("ID", 10);
        TextView libelleHeaderRowTextView = createTextViewWithBorder("Libellé", 50);
        TextView puHeaderRowTextView = createTextViewWithBorder("Prix unitaire", 20);

        headerRow.addView(idHeaderRowTextView);
        headerRow.addView(libelleHeaderRowTextView);
        headerRow.addView(puHeaderRowTextView);
        table.addView(headerRow);

        for (Article article : articleList) {
            TableRow row = new TableRow(this);

            TextView idTextView = createTextViewWithBorder(String.valueOf(article.getId()), 10);
            TextView libelleTextView = createTextViewWithBorder(article.getLibelle(), 50);
            TextView puTextView = createTextViewWithBorder(String.valueOf(article.getPu()), 20);

            row.addView(idTextView);
            row.addView(libelleTextView);
            row.addView(puTextView);

            table.addView(row);
        }
    }

    private TextView createTextViewWithBorder(String text, int widthPercent) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                widthPercent
        ));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(10, 10, 10, 10);
        textView.setBackgroundResource(R.drawable.cell_border); // Set border background
        return textView;
    }

    // Méthode pour mettre à jour la liste des articles affichés
    private void updateArticleList(TableLayout table){
        table.removeAllViews();
        List<Article> articles = articleController.getAllArticles();
        displayArticles(table, articles);
    }
}
