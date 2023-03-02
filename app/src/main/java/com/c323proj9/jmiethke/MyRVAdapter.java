package com.c323proj9.jmiethke;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.MyViewHolder> {

    private ArrayList<Expense> displayedExpenses;
    private OnItemClickListener mListener;

    /**
     * An interface that allows for methods to be associated with clicks.
     */
    public interface OnItemClickListener{
        void onItemDelete(int position);
        void onItemEdit(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyRVAdapter(ArrayList<Expense> expenses) {
        this.displayedExpenses = expenses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    /**
     * Binds the correct information to the views inside of the holder.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Expense e = displayedExpenses.get(position);
        holder.tv_name.setText(e.getName());
        holder.tv_moneySpent.setText("$"+e.getMoneySpent()+"");
        String category = e.getCategory();
        holder.tv_category.setText(category);
        holder.tv_date.setText(e.getDate());
        if (category.equals("Shopping")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_shopping_bag_175);
        } else if (category.equals("Gas")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_local_gas_station_175);
        } else if (category.equals("Grocery")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_food_bank_24);
        } else if (category.equals("Miscellaneous")) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_waves_24);
        }
        holder.btnDelete.setImageResource(R.drawable.ic_baseline_delete_24);
        holder.btnEdit.setImageResource(R.drawable.ic_baseline_edit_24);
    }


    @Override
    public int getItemCount() {
        return displayedExpenses.size();
    }

    /**
     * Custom ViewHolder class. Sets up two OnClickListeners that use
     * the listener's method to delete/edit.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_name, tv_moneySpent, tv_date, tv_category;
        public ImageButton btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_expense);
            tv_category = itemView.findViewById(R.id.tv_expense_Category);
            tv_name = itemView.findViewById(R.id.tv_expense_Name);
            tv_date = itemView.findViewById(R.id.tv_expense_Date);
            tv_moneySpent = itemView.findViewById(R.id.tv_expense_MoneySpent);
            btnEdit = itemView.findViewById(R.id.btn_Edit);
            btnDelete = itemView.findViewById(R.id.btn_Delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemDelete(getAdapterPosition());
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemEdit(getAdapterPosition());
                }
            });
        }
    }
}
