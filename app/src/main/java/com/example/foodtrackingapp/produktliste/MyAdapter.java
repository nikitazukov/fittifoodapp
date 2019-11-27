package com.example.foodtrackingapp.produktliste;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtrackingapp.R;

import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Object id[], name[], protein[], kohlenhydrate[], fett[], kcal[];

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Integer> id, List <String> name, List<Integer> protein, List<Integer> kohlenhydrate, List<Integer> fett, List<Integer> kcal) {
        this.id = id.toArray();
        this.name = name.toArray();
        this.protein = protein.toArray();
        this.kohlenhydrate = kohlenhydrate.toArray();
        this.fett = fett.toArray();
        this. kcal = kcal.toArray();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(id[position] + " - " +
                                name[position] + " - " +
                                protein[position] + " - " +
                                kohlenhydrate[position] + " - " +
                                fett[position] + " - " +
                                kcal[position] );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return id.length;
    }
}
