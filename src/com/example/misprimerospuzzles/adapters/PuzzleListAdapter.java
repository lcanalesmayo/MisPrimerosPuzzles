package com.example.misprimerospuzzles.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.misprimerospuzzles.BaseActivity;
import com.example.misprimerospuzzles.R;
import com.example.misprimerospuzzles.utils.Commons;

public class PuzzleListAdapter extends BaseAdapter {
	private BaseActivity context;
	private final List<List<Drawable>> items;
	private String[] puzzlesString;

	public PuzzleListAdapter(BaseActivity context) {
		this.context = context;

		items = new ArrayList<List<Drawable>>();
		puzzlesString = context.getPropertyUtils().getProperty("puzzles")
				.split(",");
		for (String puzzle : puzzlesString) {
			String[] piezasString = context.getPropertyUtils().getProperty(puzzle)
					.split(",");

			// Imagenes del botón
			List<Drawable> drawableList = new ArrayList<Drawable>();
			int resID;
			for (int i = 0; i < Commons.PUZZLE_PARTS; i++) {
				resID = context.getResources().getIdentifier(piezasString[i],
						"drawable", context.getPackageName());
				drawableList.add(context.getResources().getDrawable(resID));
			}

			items.add(drawableList);
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {
			// get layout from puzzlelistitem_layout.xml
			gridView = inflater.inflate(R.layout.puzzlelistitem_layout, null);
			
			// Tag que identifica el puzzle al que hace referencia el elemento
			gridView.setTag(puzzlesString[position]);

			LinearLayout linearLayout;
			linearLayout = (LinearLayout) gridView.findViewById(R.id.topleft);
			linearLayout.setBackgroundDrawable(items.get(position).get(0));
			linearLayout = (LinearLayout) gridView.findViewById(R.id.topright);
			linearLayout.setBackgroundDrawable(items.get(position).get(1));
			linearLayout = (LinearLayout) gridView
					.findViewById(R.id.bottomleft);
			linearLayout.setBackgroundDrawable(items.get(position).get(2));
			linearLayout = (LinearLayout) gridView
					.findViewById(R.id.bottomright);
			linearLayout.setBackgroundDrawable(items.get(position).get(3));
		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
