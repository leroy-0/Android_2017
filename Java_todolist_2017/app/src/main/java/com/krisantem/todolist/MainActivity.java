package com.krisantem.todolist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private DbHelper                dbHelper;
    private ListView                listTasks;
    private SearchView              searchView;
    private AdapterRow              myAdapter;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        listTasks = findViewById(R.id.allTasks);
        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                Item taskItem = (Item)listTasks.getAdapter().getItem(position);
                Bundle b = new Bundle();
                b.putInt("key", taskItem.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });
        loadAllTasks();
    }

    /**
     * Load all tasks
     */
    public void loadAllTasks() {
        myAdapter = new AdapterRow(this, generateData());
        listTasks.setAdapter(myAdapter);
    }

    /**
     * @return list of items
     */
    private ArrayList<Item> generateData(){
        ArrayList<String[]> allTasks = dbHelper.getAllTasks();

        ArrayList<Item> items = new ArrayList<>();
        for (String[] values: allTasks) {
            items.add(new Item(values));
        }
        return items;
    }

    /**
     * Load Tasks onResume
     */
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        loadAllTasks();
    }

    /**
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search_settings);
        searchView = (SearchView) searchMenuItem.getActionView();

        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    /**
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_settings) {
            searchView.setIconified(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param query
     * @return boolean
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * @param newText
     * @return boolean
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        myAdapter.getFilter().filter(newText);
        return false;
    }
}
