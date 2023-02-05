package edu.northeastern.numad23sp_parthkhaladkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {

    private EditText l_name;
    private EditText l_url;
    private ArrayList<WebLink> link_list;
    private AlertDialog inp_alert;
    private RecyclerView rv;
    private LinkCollectorAdapter lcvadapter;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String ITEM_COUNT = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        link_list = new ArrayList<>();
        init(savedInstanceState);
        FloatingActionButton addLinkButton = findViewById(R.id.addLinkButton);
        addLinkButton.setOnClickListener(v -> addLink());
        input_popup_box();
        createRecyclerView();
        lcvadapter.setOnLinkClickListener(place -> link_list.get(place).onLinkUnitClicked(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                link_list.remove(position);
                lcvadapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(rv);
    }



    private void addLink() {
        l_name.getText().clear();
        l_url.setText(getString(R.string.Http));
        l_name.requestFocus();
        inp_alert.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = link_list == null? 0 : link_list.size();
        outState.putInt(ITEM_COUNT, size);

        for(int i=0; i<size; i++){
            outState.putString(KEY_OF_INSTANCE + i+ "0", link_list.get(i).getName());
            outState.putString(KEY_OF_INSTANCE + i+ "1", link_list.get(i).getLinkUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState)
    {
        if(savedInstanceState != null && savedInstanceState.containsKey(ITEM_COUNT))
        {
            if(link_list == null || link_list.size() == 0)
            {
                int size = savedInstanceState.getInt(ITEM_COUNT);
                for(int i=0; i<size; i++){
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE+i+"0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE+i+"1");
                    WebLink unit = new WebLink(name, url);
                    link_list.add(unit);
                }
            }
        }
    }

    public void input_popup_box() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_link_input, null);
        l_name = view.findViewById(R.id.link_name_input);
        l_url = view.findViewById(R.id.link_url_input);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setCancelable(false).setPositiveButton(getString(R.string.Add),
                        (dialog, id) -> {
                            WebLink linkUnit = new WebLink(l_name.getText().toString(), l_url.getText().toString());
                            if (linkUnit.Verifylink())
                            {
                                link_list.add(0, linkUnit);
                                lcvadapter.notifyDataSetChanged();
                                Snackbar.make(rv, getString(R.string.link_add_success), Snackbar.LENGTH_LONG).show();
                            } else
                            {
                                Snackbar.make(rv, getString(R.string.invalid_link), Snackbar.LENGTH_LONG).show();
                            }
                        }).setNegativeButton(getString(R.string.Cancel), (dialog, id) -> dialog.cancel());
        inp_alert = alertDialogBuilder.create();
    }

    public void createRecyclerView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv = findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        lcvadapter = new LinkCollectorAdapter(link_list);
        rv.setAdapter(lcvadapter);
        rv.setLayoutManager(layoutManager);
    }




}