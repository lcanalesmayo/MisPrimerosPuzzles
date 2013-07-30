package com.example.misprimerospuzzles.listeners;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.misprimerospuzzles.PuzzleActivity;
import com.example.misprimerospuzzles.R;
import com.example.misprimerospuzzles.utils.Commons;

/**
 * Drag listener para los huecos de pieza de puzzle (solo se mueven aqui si la
 * posicion es correcta)
 * 
 * @author Luis
 * 
 */
public final class PuzzlePartDragListener implements OnDragListener {
	private int piezasRestantes = 4;
	private Activity context;

	public PuzzlePartDragListener(Activity context) {
		this.context = context;
	}

	private boolean verifyPuzzlePart(View viewToMove, View toParentView) {
		// Verificacion de que la pieza dada en el primer parametro es
		// correcta en la posicion dada
		// en el layout toParentView
		return (viewToMove.getTag().equals(toParentView.getTag()));
	}

	/**
	 * Ejecutado cada vez que se arrastra sobre el item que contiene el listener
	 * una pieza de puzzle
	 * 
	 * @param v
	 *            item sobre el que se arrastra una pieza de puzzle
	 * @param event
	 *            tipo de evento (comienza a arrastrarse, termina de
	 *            arrastrarse, la pieza entra en el item, la pieza sale del
	 *            item)
	 */
	@Override
	public boolean onDrag(View v, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			if (Commons.helpMode) {
				v.setBackgroundDrawable(Commons.enterShapeTransparent);
			} else {
				v.setBackgroundDrawable(Commons.enterShape);
			}
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			if (Commons.helpMode) {
				v.setBackgroundDrawable(Commons.normalShapeTransparent);
			} else {
				v.setBackgroundDrawable(Commons.normalShape);
			}
			break;
		case DragEvent.ACTION_DROP:
			View view = (View) event.getLocalState();
			if (verifyPuzzlePart(view, v)) {
				// Pieza correcta, la dejamos invisible y cambiamos el fondo
				// del lugar en el que la hemos colocado
				v.setVisibility(View.INVISIBLE);
				v.setOnDragListener(null);
				v.setTag(null);

				piezasRestantes--;
				if (piezasRestantes == 0) {
					finalizarPuzzle();
				} else {
					piezaBien();
				}
			} else {
				// Pieza incorrecta, la hacemos visible en la posicion
				// inicial de nuevo
				view.setVisibility(View.VISIBLE);

				piezaErronea();
			}
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			if (Commons.helpMode) {
				v.setBackgroundDrawable(Commons.normalShapeTransparent);
			} else {
				v.setBackgroundDrawable(Commons.normalShape);
			}
		default:
			break;
		}
		return true;
	}

	private void piezaBien() {
		Commons.mPlayerNice.start();

		ImageView imageView = (ImageView) context
				.findViewById(R.id.okAnimation);
		AnimationDrawable frameAnimation = (AnimationDrawable) imageView
				.getBackground();

		frameAnimation.stop();
		frameAnimation.start();
	}

	private void piezaErronea() {
		Commons.mPlayerFail.start();
	}

	private void finalizarPuzzle() {
		context.findViewById(R.id.nombrepuzzle).setVisibility(View.VISIBLE);

		context.findViewById(R.id.button_bombilla)
				.setVisibility(View.INVISIBLE);

		context.findViewById(R.id.table).setVisibility(View.INVISIBLE);

		Commons.mPlayerFinal.start();
	}
}
