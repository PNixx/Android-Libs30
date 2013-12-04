package pnixx.libs3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: P.Nixx
 * Date: 07.11.12
 * Time: 18:02
 */
public class StyleDialogList extends Dialog {
	private Context context;
	private ListView list = null;
	private AdapterView.OnItemClickListener clickListener;
	private int layout = R.layout.style_dialog_list;
	private int row_layout = R.layout.style_dialog_row;
	private ArrayList<Row> items;
	private TextView dlg_title;
	private View progress;

	public static class Row {
		public final String text;
		public final int icon;
		public final int id;

		public Row(String text, int icon) {
			this.text = text;
			this.icon = icon;
			this.id = -1;
		}

		public Row(String text, int icon, int id) {
			this.text = text;
			this.icon = icon;
			this.id = id;
		}

		@Override
		public String toString() {
			return text;
		}

		public static ArrayList<Row> createArray(String[] items) {
			ArrayList<Row> rows = new ArrayList<Row>();
			for( String item : items ) {
				rows.add(new Row(item, -1));
			}
			return rows;
		}
	}

	public StyleDialogList(Context context, int theme, int layout, int row_layout, ArrayList<Row> items, AdapterView.OnItemClickListener clickListener) {
		super(context, theme);
		this.context = context;
		this.clickListener = clickListener;
		this.layout = layout;
		this.row_layout = row_layout;
		this.items = items;
	}

	public StyleDialogList(Context context, ArrayList<Row> items, AdapterView.OnItemClickListener clickListener) {
		super(context, R.style.StyleDialog);
		this.context = context;
		this.clickListener = clickListener;
		this.items = items;
	}

	public StyleDialogList(Context context, String[] items, AdapterView.OnItemClickListener clickListener) {
		super(context, R.style.StyleDialog);
		this.context = context;
		this.clickListener = clickListener;
		this.items = Row.createArray(items);
	}

	public StyleDialogList(Context context, AdapterView.OnItemClickListener clickListener) {
		super(context, R.style.StyleDialog);
		this.context = context;
		this.clickListener = clickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout);
		// ListView
		list = (ListView) findViewById(R.id.list);
		progress = findViewById(R.id.progress);
		dlg_title = (TextView) findViewById(R.id.dialog_title);

		if( items != null ) {
			//Set adapter
			list.setAdapter(new ListAdapter(items));
		} else {
			progress.setVisibility(View.VISIBLE);
			dlg_title.setVisibility(View.GONE);
		}
		//ListView
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if( clickListener != null ) {
					clickListener.onItemClick(parent, view, position, id);
				}
				dismiss();
			}
		});
		list.setDividerHeight(0);
	}

	public void setTitle(String s) {
		dlg_title.setText(s);
	}

	public void setTitle(int s) {
		dlg_title.setText(s);
	}

	public void setAdapter(ArrayList<Row> items) {
		progress.setVisibility(View.GONE);
		list.setAdapter(new ListAdapter(items));
		dlg_title.setVisibility(View.VISIBLE);
	}

	private class ListAdapter extends ArrayAdapter<Row> {

		public ListAdapter(ArrayList<Row> objects) {
			super(context, row_layout, objects);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if( row == null ) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(row_layout, parent, false);
			}

			Row rowObject = getItem(position);

			//User super class to create the View
			TextView textView = (TextView) row.findViewById(R.id.title);
			textView.setText(rowObject.text);

			ImageView imageView = (ImageView) row.findViewById(R.id.image);
			if( imageView != null ) {
				if( rowObject.icon != -1 ) {
					imageView.setImageResource(rowObject.icon);
				} else {
					imageView.setVisibility(View.GONE);
				}
			}

			return row;
		}
	}
}
