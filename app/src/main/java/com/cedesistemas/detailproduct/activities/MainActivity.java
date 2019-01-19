package com.cedesistemas.detailproduct.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cedesistemas.detailproduct.R;
import com.cedesistemas.detailproduct.adapters.AdapterProducts;
import com.cedesistemas.detailproduct.helper.ValidateInternet;
import com.cedesistemas.detailproduct.models.Product;
import com.cedesistemas.detailproduct.services.Repository;
import com.cedesistemas.detailproduct.viewsinterface.IMainActivity;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements IMainActivity{

    private RecyclerView recyclerView;
    private AdapterProducts adapterProducts;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        repository = new Repository();
        validateInternet();
    }

    private void validateInternet(){
        final ValidateInternet validateInternet = new ValidateInternet(this);
        if (validateInternet.isConnected()){
            createThreadGetProduct();
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.title_validate_internet);
            alertDialog.setMessage(R.string.message_validate_internet);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(R.string.text_again, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    validateInternet();
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    private void getProducts() {
        try {
            ArrayList<Product> products = repository.getProducts();
            loadAdapter(products);
        } catch (final IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void loadAdapter(final ArrayList<Product> products) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterProducts = new AdapterProducts(products, MainActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterProducts);
            }
        });
    }

    private void createThreadGetProduct(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getProducts();
            }
        });
        thread.start();
    }

    @Override
    public void intentToDetailActivity(Product product) {
        Intent intent =  new Intent(this, DetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        validateInternet();
    }
}
