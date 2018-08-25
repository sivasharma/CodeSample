package com.example.apple.codingtest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.apple.codingtest.R;
import com.example.apple.codingtest.adapter.CountryRecyclerAdapter;

/**
 * @author  shiv
 */


//helper class for swipe gesture
public class RecyclerSwipeController extends ItemTouchHelper.SimpleCallback {
    private RecyclerSwipeListener listener;
   Context context;

   //passing params here from Activity to use the context and other prop
   public RecyclerSwipeController(Context context, int dragDirs, int swipeDirs, RecyclerSwipeListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
        this.context=context;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {


        return super.getMovementFlags(recyclerView, viewHolder);
    }


    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((CountryRecyclerAdapter.CountryViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

        final View foregroundView = ((CountryRecyclerAdapter.CountryViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CountryRecyclerAdapter.CountryViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
       /* final View foregroundView = ((CountryRecyclerAdapter.CountryViewHolder) viewHolder).viewForeground;
        viewHolder.itemView.setAlpha(0.6f);

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
                */
       try {

            //this block actually paints the swipe child item with the delete icom
            Bitmap icon;
            Paint paint=new Paint();
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 5;
                viewHolder.itemView.setAlpha(0.7f);
                paint.setColor(Color.parseColor("#9400D3"));
                RectF background = new RectF((float) itemView.getRight() + dX / 5, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.delete);
                RectF icon_dest = new RectF((float) (itemView.getRight() + dX /7), (float) itemView.getTop()+width, (float) itemView.getRight()+dX/20, (float) itemView.getBottom()-width);
                c.drawBitmap(icon, null, icon_dest, paint);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    //to keep a track of actions on swipe this must be implemented in the a
    public interface RecyclerSwipeListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
