package com.c323proj9.jmiethke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String searchCategory, searchInput;
    EditText et_Search;
    Button btn_Search;
    ArrayList<Expense> expenses, displayExpenses;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    /**
     * Sets up the layout for the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        expenses = new ArrayList<>();
        displayExpenses = new ArrayList<>();

        Spinner spinner = findViewById(R.id.spinner_Activity2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.searchCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        et_Search = findViewById(R.id.et_Search);
        btn_Search = findViewById(R.id.btn_Search);
        searchCategory = "All";
        displayExpenses();

    }

    /**
     * Method for spinner onItemSelectedListener. Makes the string searchCategory equal to what was
     * selected in the spinner.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        searchCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Filters the displayed expense list using the category selected in the spinner and the
     * user input in the editText et_Search. Uses a RecyclerView to display the expenses.
     */
    public void displayExpenses(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        expenses = dbHandler.findExpensesDB(searchCategory);
        displayExpenses = new ArrayList<>();
        searchInput = et_Search.getText().toString();
        if (searchInput.equals("")) {
            for (Expense e: expenses) {
                if (displayExpenses.size() == 0) {
                    displayExpenses.add(e);
                } else {
                    for (int i = 0; i < displayExpenses.size(); i++) {
                        if(e.isBefore(displayExpenses.get(i))){
                            displayExpenses.add(i,e);
                            break;
                        }
                        // Gone all the way through and it has not been added yet.
                        if (i == displayExpenses.size()-1) {
                            displayExpenses.add(e);
                            break;
                        }
                    }
                }
            }
        } else {
            for (Expense e: expenses) {
                if ( e.getDate().toLowerCase().contains(searchInput.toLowerCase())
                        || ("" + e.getMoneySpent()).contains(searchInput)
                        || e.getName().toLowerCase().contains(searchInput.toLowerCase()) )
                {

                    if (displayExpenses.size() == 0) {
                        displayExpenses.add(e);
                    } else {
                        for (int i = 0; i < displayExpenses.size(); i++) {
                            if (e.isBefore(displayExpenses.get(i))) {
                                displayExpenses.add(i, e);
                                break;
                            }
                            // Gone all the way through and it has not been added yet.
                            if (i == displayExpenses.size() - 1) {
                                displayExpenses.add(e);
                                break;
                            }
                        }
                    }
                }
            }
        }

        recyclerView = findViewById(R.id.rv_Activity2);
        layoutManager = new LinearLayoutManager(this);
        MyRVAdapter adapter1 = new MyRVAdapter(displayExpenses);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter1);
        if (displayExpenses.size() == 0) {
            Toast.makeText(this, "No expenses found.", Toast.LENGTH_SHORT).show();
        }

        adapter1.setOnItemClickListener(new MyRVAdapter.OnItemClickListener() {
            /**
             * Deletes the item from the database and displayExpenses. Notifies the adapter.
             * @param position the position of the item selected.
             */
            @Override
            public void onItemDelete(int position) {
                MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
                dbHandler.deleteExpense(displayExpenses.get(position));
                displayExpenses.remove(position);
                adapter1.notifyItemRemoved(position);
            }

            /**
             * Starts MainActivity to "edit" an item by creating a new item and deleting the old item.
             * @param position the position of the item selected.
             */
            @Override
            public void onItemEdit(int position) {
                MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
                dbHandler.deleteExpense(displayExpenses.get(position));
                displayExpenses.remove(position);
                adapter1.notifyItemRemoved(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Edit", "Yes");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }
    /**
     * Filters the displayed expense list using the category selected in the spinner and the
     * user input in the editText et_Search. Uses a RecyclerView to display the expenses.
     * @param view
     */
    public void searchExpenses(View view) {
        displayExpenses();
    }

}