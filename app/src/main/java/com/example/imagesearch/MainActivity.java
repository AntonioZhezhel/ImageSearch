package com.example.imagesearch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String CLIENT_ID="32fc9da6140e0ab5b0867fdb6584d0567191b2621fccdd7ae91bc41f1e4acf87";
    private Unsplash unsplash;
    private ImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        unsplash=new Unsplash(CLIENT_ID);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

        adapter=new ImageAdapter();
        recyclerView.setAdapter(adapter);


        unsplash.getPhotos(1, 10, Order.LATEST, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {

                adapter.setPhotos(photos);
            }

            @Override
            public void onError(String error) {

            }
        });

    }



    public void search(View view) {

        EditText editText=findViewById(R.id.etImageSearch);
        String query=editText.getText().toString();


        unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {

                Log.d("Photos","Total Results Found"+results.getTotal());
                List<Photo> photos=results.getResults();
                adapter.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
            Log.d("Unsplash",error);

            }
        });
    }
}
