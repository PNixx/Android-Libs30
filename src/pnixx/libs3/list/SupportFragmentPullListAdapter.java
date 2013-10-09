package pnixx.libs3.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 22.03.13
 * Time: 16:31
 */
public abstract class SupportFragmentPullListAdapter<A extends FragmentActivity, Row> extends Fragment implements AdapterView.OnItemClickListener {

	protected PullToRefreshListView list;
	protected A activity;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected AbstractAdapter adapter;
	protected View progress;
	protected View footer_loader;
	protected int page = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.list_pull, container, false);

		//Получаем объекты
		list = (PullToRefreshListView) view.findViewById(R.id.list);
		progress = view.findViewById(R.id.progress);

		//Биндим клик
		getListView().setOnItemClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isActive = true;
		activity = (A) getActivity();

		//Получаем шаблон для загрузчика
		footer_loader = activity.getLayoutInflater().inflate(R.layout.footer_loader, null);

		//Биндим скроллинг
		pageScrolling = new PageScrolling(activity);
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
		pageScrolling.setAdapter(adapter);
		list.setAdapter(adapter);
	}

	//Получение списка
	protected ListView getListView() {
		return list.getRefreshableView();
	}

	//Добавление футера
	protected void addFooterLoader() {
		getListView().addFooterView(footer_loader);
	}

	//Удаление футера
	protected void removeFooterLoader() {
		getListView().removeFooterView(footer_loader);
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
}
