package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // if the received intent is null return the error message
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // initialise views
        TextView also_know_tv = findViewById(R.id.also_known_tv);
        TextView origin_tv = findViewById(R.id.origin_tv);
        TextView description_tv = findViewById(R.id.description_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);

        // get the place of origin
        String origin = sandwich.getPlaceOfOrigin();
        // if the origin place is empty show appropriate message
        // else set the data
        if (TextUtils.isEmpty(origin)) {
            origin_tv.setText(R.string.no_data);
        } else {
            origin_tv.setText(origin);
        }

        // get the also_know data
        List<String> alsoKnowsAsList = sandwich.getAlsoKnownAs();
        // if the list is empty show appropriate message
        // else set the data
        if (alsoKnowsAsList.size() == 0) {
            also_know_tv.setText(R.string.no_data);
        } else {
            StringBuilder otherNames = new StringBuilder();

            // For each ingredient in the List, append
            // comma and space ", " between them
            for (String name : alsoKnowsAsList) {
                otherNames.append(name).append("\n");
            }
            // Remove comma and space from the last author
            //otherNames.setLength(otherNames.length() - 2);

            // Set & display the ingredients in the TextView
            also_know_tv.setText(otherNames);
        }

        // get the ingrediens data
        List<String> ingredientsList = sandwich.getIngredients();
        // if the ingrediens list is empty show appropriate message
        // else set the data
        if (ingredientsList.size() == 0) {
            ingredients_tv.setText(R.string.no_data);
        } else {
            StringBuilder ingredients = new StringBuilder();

            // For each ingredient in the List, append
            // comma and space ", " between them
            for (String ingredient : ingredientsList) {
                ingredients.append(ingredient).append("\n");
            }
            // Remove comma and space from the last author
            //ingredients.setLength(ingredients.length() - 2);

            // Set & display the ingredients in the TextView
            ingredients_tv.setText(ingredients);
        }

        // set the sandwich description
        String description = sandwich.getDescription();
        // if the description text is empty show appropriate message
        // else set the data
        if (TextUtils.isEmpty(description)) {
            description_tv.setText(R.string.no_data);
        } else {
            description_tv.setText(description);
        }
    }
}