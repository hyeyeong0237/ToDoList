package com.example.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoAdapter adapter;

    public RecyclerItemTouchHelper(ToDoAdapter adapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;

    }

    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this Task?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.deleteItem(position);
                }
            });

            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }else{
            adapter.editItem(position);
        }

    }
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCorrectlyActive){
        super.onChildDraw(c,recyclerView,viewHolder,dX,dY, actionState,isCorrectlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemview = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if(dX >0){
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(),R.color.design_default_color_secondary));

        }else{
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete);
            background = new ColorDrawable(Color.RED);

        }

        int iconMargin = (itemview.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemview.getTop() + (itemview.getHeight() - icon.getIntrinsicHeight()) / 2 ;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if(dX >0){
            int iconLeft = itemview.getLeft() + iconMargin;
            int iconRight = itemview.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemview.getLeft(), itemview.getTop(), itemview.getLeft() + ((int)dX) + backgroundCornerOffset,itemview.getBottom());

        }else if(dX < 0){
            int iconLeft = itemview.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemview.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemview.getRight() + ((int)dX) - backgroundCornerOffset, itemview.getTop(), itemview.getRight() ,itemview.getBottom());

        }else{
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);

    }



    }

