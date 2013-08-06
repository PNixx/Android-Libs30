package pnixx.libs3.list;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import org.json.JSONException;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 22.03.13
 * Time: 16:31
 */
public abstract class FragmentPullListAdapter<A extends Activity, Row> extends Fragment implements AdapterView.OnItemClickListener {

	protected PullToRefreshListView list;
	protected A activity;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected AbstractAdapter adapter;
	protected View progress;

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
	protected abstract void getPage() throws JSONException;

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
}
