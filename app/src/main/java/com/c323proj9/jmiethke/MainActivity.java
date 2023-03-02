package com.c323proj9.jmiethke;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String selectedCategory;
    ImageView imageView;
    EditText et_Name, et_MoneySpent, et_Date;
    Button btnView;
    String edit;

    /**
     * Sets up the layout for the activity. If this activity was started by an Expense's edit button
     * in Activity2, then only allows to add a new expense.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner_Category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        et_Name = findViewById(R.id.et_Name);
        et_MoneySpent = findViewById(R.id.et_MoneySpent);
        et_Date = findViewById(R.id.et_Date);
        btnView = findViewById(R.id.btn_View);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.basicprofile);

        Intent intent = getIntent();
        edit = intent.getStringExtra("Edit");
        if (edit!=null){
            if (edit.equals("Yes")){
                btnView.setEnabled(false);
            }
        }
    }

    /**
     * Method for spinner onItemSelectedListener. Makes the string selectedCategory equal to what was
     * selected in the spinner.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Adds a new expense to the database based off of what the user inputted.
     * @param view
     */
    public void addExpense(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String name = et_Name.getText().toString();
        String date = et_Date.getText().toString();
        Double moneySpent = Double.parseDouble(et_MoneySpent.getText().toString());
        Expense expense = new Expense(name, date, selectedCategory, moneySpent);
        dbHandler.addExpenseToDB(expense);
        et_Name.setText("");
        et_Date.setText("");
        et_MoneySpent.setText("");
        if (edit!=null){
            if (edit.equals("Yes")){
                btnView.setEnabled(true);
            }
        }
    }

    /**
     * Starts Activity2 with the goal of showing the expenses in the database.
     * @param view
     */
    public void viewExpenses(View view) {
        startActivity(new Intent(this, Activity2.class));
    }

}