package com.example.misprimerospuzzles.listeners;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

/**
 * Drag Listener por defecto (no mover la pieza del puzzle si la soltamos en un
 * punto fuera de los permitidos)
 * 
 * @author Luis
 * 
 */
public final class DefaultDragListener implements OnDragListener {

	/**
	 * Ejecutado cada vez que se suelta una pieza de puzzle en cualquier punto
	 * de la pantalla salvo los definidos para depositar las piezas de puzzle
	 * 
	 * @param v
	 *            pantalla principal
	 * @param event
	 *            tipo de evento (comienza a arrastrarse, termina de
	 *            arrastrarse, la pieza entra en el item, la pieza sale del
	 *            item)
	 */
	@Override
	public boolean onDrag(View v, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DROP:
			View view = (View) event.getLocalState();
			view.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		return true;
	}
}