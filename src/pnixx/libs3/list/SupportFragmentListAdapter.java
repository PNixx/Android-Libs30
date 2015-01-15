package pnixx.libs3.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 22.03.13
 * Time: 16:31
 */
public abstract class SupportFragmentListAdapter<A extends FragmentActivity, Row> extends Fragment implements AdapterView.OnItemClickListener {

	protected ListView list;
	protected A activity;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected AbstractAdapter adapter;
	protected View progress;
	protected View footer_loader;
	protected int page = 1;
	protected View refresh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.list, container, false);

		//Получаем объекты
		list = (ListView) view.findViewById(R.id.list);
		progress = view.findViewById(R.id.progress);
		refresh = view.findViewById(R.id.refresh);

		//Биндим клик
		list.setOnItemClickListener(this);
		if( refresh != null ) {
			refresh.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					OnRefreshListener();
				}
			});
		}

		//Устанавливаем параметры для списка
		list.setDividerHeight(0);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isActive = true;
		activity = (A) getActivity();

		//Получаем шаблон для загрузчика
		View view_footer = activity.getLayoutInflater().inflate(R.layout.footer_loader, list, false);
		footer_loader = view_footer.findViewById(R.id.footer_layout);
		list.addFooterView(footer_loader);
		removeFooterLoader();

		//Биндим скроллинг
		pageScrolling = new PageScrolling(activity);
		list.setOnScrollListener(pageScrolling);

		//Восстановление состояния
		if( savedInstanceState != null ) {
			onRestoreInstanceState(savedInstanceState);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putInt("page", page);
		super.onSaveInstanceState(bundle);
	}

	//Восстановление данных
	public void onRestoreInstanceState(Bundle bundle) {
		page = bundle.getInt("page");
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
		if( isActive && isAdded() ) {
			pageScrolling.setAdapter(adapter);
			list.setAdapter(adapter);
		}
	}

	//Получение списка
	protected ListView getListView() {
		return list;
	}

	//Добавление футера
	protected void addFooterLoader() {
		footer_loader.setVisibility(View.VISIBLE);
	}

	//Удаление футера
	protected void removeFooterLoader() {
		footer_loader.setVisibility(View.GONE);
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

	protected void showRefreshButton() {
		if( refresh != null ) {
			refresh.setVisibility(View.VISIBLE);
		}
	}

	protected void hideRefreshButton() {
		if( refresh != null ) {
			refresh.setVisibility(View.GONE);
		}
	}

	//Листенер на обновление
	protected void OnRefreshListener() {}
}
