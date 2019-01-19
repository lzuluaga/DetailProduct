package com.cedesistemas.detailproduct.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cedesistemas.detailproduct.R;
import com.cedesistemas.detailproduct.helper.ValidateInternet;
import com.cedesistemas.detailproduct.models.Product;
import com.cedesistemas.detailproduct.services.Repository;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private Product product;
    private TextView textView;
    private Repository repository;
    private ValidateInternet validateInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        validateInternet = new ValidateInternet(this);
        repository = new Repository();
        textView = findViewById(R.id.description);
        product = (Product) getIntent().getSerializableExtra("prodcut");
        textView.setText(product.getProductDescription());
    }

    public void deleteProduct(View view) {
        if (validateInternet.isConnected()){
            createThreadDeleteProduct();
        }else{
            // mostrar un toast o un alert dialog
        }

    }

    private void createThreadDeleteProduct() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                deleteProductInRepository();
            }
        });
        thread.start();
    }

    private void deleteProductInRepository() {
        try {
            repository.deleteProducts(product.getId());
            //forma 1 correcta     showToast(getResources().getString(R.string.delete_product));
            //forma 2 normal     showToast("Se ha creado el producto");
        } catch (IOException e) {
            showToast(e.getMessage());
        }
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
