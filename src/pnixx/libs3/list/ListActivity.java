package pnixx.libs3.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 23.09.13
 * Time: 17:41
 */
public abstract class ListActivity<Row> extends Activity implements AdapterView.OnItemClickListener {

	protected ListView list;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected AbstractAdapter adapter;
	protected View progress;
	protected View footer_loader;
	protected View undo_layout;
	protected View undo;
	protected Row undo_row;
	protected int page = 1;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.list);

		//Получаем объекты
		list = (ListView) findViewById(R.id.list);
		progress = findViewById(R.id.progress);
		undo_layout = findViewById(R.id.undo_layout);
		undo = findViewById(R.id.undo);

		//Получаем шаблон для загрузчика
		View view_footer = getLayoutInflater().inflate(R.layout.footer_loader, list, false);
		footer_loader = view_footer.findViewById(R.id.footer_layout);

		//Биндим клик
		list.setOnItemClickListener(this);
		undo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				undoClick();
			}
		});

		//Устанавливаем параметры для списка
		list.setDividerHeight(0);

		//Биндим скроллинг
		pageScrolling = new PageScrolling(this);
		list.setOnScrollListener(pageScrolling);
	}

	@Override
	public void onPause() {
		isActive = false;
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		isActive = true;
	}

	//Получаем данные страницы
	protected abstract void getPage();

	//Скрыть прогресс бар
	protected void progressHide() {
		progress.setVisibility(View.GONE);
	}

	//Установка адаптера
	protected void setAdapter(AbstractAdapter adapter) {
		list.addFooterView(footer_loader);
		pageScrolling.setAdapter(adapter);
		list.setAdapter(adapter);
		list.removeFooterView(footer_loader);
	}

	//Получение списка
	protected ListView getListView() {
		return list;
	}

	//Добавление футера
	protected void addFooterLoader() {
		list.addFooterView(footer_loader);
	}

	//Удаление футера
	protected void removeFooterLoader() {
		list.removeFooterView(footer_loader);
	}

	//Устанавливаем коллбек на прокрутку страницы
	protected void setCallbackOnEndPageTrack() {
		if( isActive ) {
			adapter.setOnEndPage(new AbstractAdapter.OnEndPage() {
				@Override
				public void run() {
					super.run();
					page += 1;
					addFooterLoader();
					getPage();
				}
			});
		}
	}

	//Показывает кнопку возврата
	public void removeRow(Row row) {
		undo_row = row;
		rows.remove(row);
		adapter.notifyDataSetChanged();
		undo_layout.setVisibility(View.VISIBLE);
	}

	//Нажали на кнопку возврата
	private void undoClick() {
		undo_layout.setVisibility(View.GONE);
		onUndoRow(undo_row);
	}

	//Срабатывает при нажатии на кнопку возврата
	public void onUndoRow(Row row) {
		rows.add(row);
		adapter.notifyDataSetChanged();
	}
}
