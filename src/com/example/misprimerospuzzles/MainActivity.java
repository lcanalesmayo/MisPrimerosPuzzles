package com.example.misprimerospuzzles;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// Sonidos
	private MediaPlayer mPlayerFinal;
	private MediaPlayer mPlayerFail;
	private MediaPlayer mPlayerNice;
	private MediaPlayer mPlayerPista;

	// Modo ayuda
	private boolean helpMode = false;

	// Fondos para piezas de puzzle
	private Drawable enterShape;
	private Drawable normalShape;
	private Drawable enterShapeTransparent;
	private Drawable normalShapeTransparent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Layout principal
		setContentView(R.layout.activity_main);

		// Eliminamos las barras de notificación
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Drag listeners
		PuzzlePartDragListener puzzlePartDragListener = new PuzzlePartDragListener();

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
		findViewById(R.id.topleft).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.topright).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.bottomleft).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.bottomright)
				.setOnDragListener(puzzlePartDragListener);

		// Fondos de piezas de puzzle
		enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		normalShape = getResources().getDrawable(R.drawable.shape);
		enterShapeTransparent = getResources().getDrawable(
				R.drawable.shape_droptarget_transparent);
		normalShapeTransparent = getResources().getDrawable(
				R.drawable.shape_transparent);

		// Para el modo ayuda, la parte que puede quedarse transparente debe ir
		// al frente
		findViewById(R.id.puzzle).bringToFront();

		// Creamos los sonidos
		mPlayerFinal = MediaPlayer.create(this, R.raw.burro);
		mPlayerFail = MediaPlayer.create(this, R.raw.fail);
		mPlayerNice = MediaPlayer.create(this, R.raw.nice);
		mPlayerPista = MediaPlayer.create(this, R.raw.pista);
	}

	// Deshabilitamos el back button
	@Override
	public void onBackPressed() {
		return;
	}

	// Implementación del menú de la aplicación
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exit:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		private int piezasRestantes = 4;

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
				if (helpMode) {
					v.setBackgroundDrawable(enterShapeTransparent);
				} else {
					v.setBackgroundDrawable(enterShape);
				}
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				if (helpMode) {
					v.setBackgroundDrawable(normalShapeTransparent);
				} else {
					v.setBackgroundDrawable(normalShape);
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
				if (helpMode) {
					v.setBackgroundDrawable(normalShapeTransparent);
				} else {
					v.setBackgroundDrawable(normalShape);
				}
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

	private void piezaBien() {
		mPlayerNice.start();
	}

	private void piezaErronea() {
		mPlayerFail.start();
	}

	private void finalizarPuzzle() {
		mPlayerFinal.start();
		
		findViewById(R.id.button_bombilla).setVisibility(View.INVISIBLE);
	}

	// Manejadores de botones
	public void darPista(View v) {
		mPlayerPista.start();

		helpMode = !helpMode;

		View topleft = findViewById(R.id.topleft);
		if (topleft.getTag() != null) {
			if (helpMode) {
				topleft.setBackgroundDrawable(normalShapeTransparent);
			} else {
				topleft.setBackgroundDrawable(normalShape);
			}
		}

		View topright = findViewById(R.id.topright);
		if (topright.getTag() != null) {
			if (helpMode) {
				topright.setBackgroundDrawable(normalShapeTransparent);
			} else {
				topright.setBackgroundDrawable(normalShape);
			}
		}

		View bottomleft = findViewById(R.id.bottomleft);
		if (bottomleft.getTag() != null) {
			if (helpMode) {
				bottomleft.setBackgroundDrawable(normalShapeTransparent);
			} else {
				bottomleft.setBackgroundDrawable(normalShape);
			}
		}

		View bottomright = findViewById(R.id.bottomright);
		if (bottomright.getTag() != null) {
			if (helpMode) {
				bottomright.setBackgroundDrawable(normalShapeTransparent);
			} else {
				bottomright.setBackgroundDrawable(normalShape);
			}
		}
	}

	public void gotoHome(View v) {
	}

}
