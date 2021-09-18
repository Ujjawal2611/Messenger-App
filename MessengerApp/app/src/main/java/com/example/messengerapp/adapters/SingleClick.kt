package com.example.messengerapp.adapters

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SingleClick(context: Context?,private var recyclerView: RecyclerView,private var mListener: OnItemClickListener) :RecyclerView.OnItemTouchListener{
    private lateinit var mGestureDetector:GestureDetector
        init {
            mGestureDetector= GestureDetector(context,object : GestureDetector.SimpleOnGestureListener(){

                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    return true
                }
                override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                    return true
                }


            })
        }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView=rv.findChildViewUnder(e.x,e.y)


         if(childView!=null&&mListener!=null&&mGestureDetector.onTouchEvent(e)){
            mListener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView))
             return false
        }
        if(childView!=null&&mListener!=null&&mGestureDetector.onTouchEvent(e)){
            mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView))
            return false
        }

        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View,position: Int)
    }
}



