package com.foc.libs.topBluration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foc.libs.focTopBluration.BlurItem;
import com.foc.libs.focTopBluration.BlurLayout;

import java.util.ArrayList;
import java.util.List;

public class TestRecyclerView extends AppCompatActivity {

    static String TAG = "test_rec";
    RecyclerView recyclerView;
    public static List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_layout);
        //init
        init();

        recyclerView = findViewById(R.id.recycler_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new AdapterRecycler(list));


    }

    void init() {
        list.add("Benz");
        list.add("Ferrari");
        list.add("Toyota");
        list.add("Ford");
        list.add("Honda");
        list.add("Hyundai");
        list.add("Nissan");
        list.add("Porsche");
        list.add("BMW");
        list.add("Audi");
        list.add("MVM");
        list.add("Challenger");
        list.add("I8");
        list.add("Hadi");
        list.add("Mamad");
        list.add("Reza");
        list.add("Foc");
        list.add("Red");
        list.add("Blue");
        list.add("Green");
        list.add("Dark");
        list.add("Light");
        list.add("Angry");
        list.add("Sorry");
        list.add("Help");
        list.add("Nice");
        list.add("Good");
    }

    public static class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyHolder> {

        private List<String> list;
        private LayoutInflater layoutInflater;

        public AdapterRecycler(List<String> list) {
            this.list = list;
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            layoutInflater = layoutInflater == null ? LayoutInflater.from(parent.getContext()) : layoutInflater;
            return new MyHolder(layoutInflater.inflate(R.layout.item_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.bind(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class MyHolder extends RecyclerView.ViewHolder {

            static  int i=0;
            private TextView textView;
            private BlurItem blurItem;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.testView_test);
                blurItem = itemView.findViewById(R.id.blb);
                i++;
                Log.i(TAG, TestRecyclerView.list.size()+" --> "+i);
            }

            public void bind(String s) {
                textView.setText(s);
                blurItem.setName(s);

                //Log.i(TAG, "bind: -->"+s);
            }
        }
    }
}