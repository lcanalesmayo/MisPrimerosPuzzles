package com.example.misprimerospuzzles;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		findViewById(R.id.puzzlepart1).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart2).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart3).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart4).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.mainlayout).setOnDragListener(
				new DefaultDragListener());
		findViewById(R.id.topleft).setOnDragListener(
				new PuzzlePartDragListener());
		findViewById(R.id.topright).setOnDragListener(
				new PuzzlePartDragListener());
		findViewById(R.id.bottomleft).setOnDragListener(
				new PuzzlePartDragListener());
		findViewById(R.id.bottomright).setOnDragListener(
				new PuzzlePartDragListener());
	}

	// Deshabilitamos el back button
	@Override
	public void onBackPressed() {
		return;
	}

	private final class PuzzlePartTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);
				view.startDrag(data, shadowBuilder, view, 0);
				view.setVisibility(View.INVISIBLE);
				return true;
			} else {
				return false;
			}
		}
	}

	// Drag listener para los huecos de pieza de puzzle (solo se mueven aqui si
	// la posicion es correcta)
	private final class PuzzlePartDragListener implements OnDragListener {
		Drawable enterShape = getResources().getDrawable(
				R.drawable.shape_droptarget);
		Drawable normalShape = getResources().getDrawable(R.drawable.shape);

		private boolean verifyPuzzlePart(View viewToMove, View toParentView) {
			// Verificacion de que la pieza dada en el primer parametro es
			// correcta en la posicion dada
			// en el layout toParentView
			return (viewToMove.getTag().equals(toParentView.getTag()));
		}

		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				v.setBackgroundDrawable(enterShape);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				v.setBackgroundDrawable(normalShape);
				break;
			case DragEvent.ACTION_DROP:
				View view = (View) event.getLocalState();
				if (verifyPuzzlePart(view, v)) {
					// Pieza correcta, la dejamos invisible y dejamos visible la
					// zona en la que la hemos colocado
					v.setVisibility(View.INVISIBLE);
				} else {
					// Pieza incorrecta, la hacemos visible en la posicion
					// inicial de nuevo
					view.setVisibility(View.VISIBLE);
				}
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				v.setBackgroundDrawable(normalShape);
			default:
				break;
			}
			return true;
		}
	}

	// Drag listener por defecto (no mover la pieza)
	private final class DefaultDragListener implements OnDragListener {

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

	// Manejadores de botones
	public void finishActivity(View v) {
		finish();
	}

}
