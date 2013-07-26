package com.example.misprimerospuzzles.listeners;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;

/**
 * Para comenzar a arrastrar cuando se pulsa una pieza del puzzle
 * 
 * @author Luis
 * 
 */
public final class PuzzlePartTouchListener implements OnTouchListener {

	/**
	 * Ejecutado cada vez que se pulsa sobre una pieza de puzzle
	 * 
	 * @param view
	 *            pieza de puzzle que se ha pulsado
	 * @param motionEvent
	 *            tipo de evento (pulsar o levantar el dedo)
	 */
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			return true;
		} else {
			return false;
		}
	}
}
