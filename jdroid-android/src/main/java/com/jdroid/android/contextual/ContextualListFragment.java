package com.jdroid.android.contextual;

import java.util.List;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.google.android.gms.ads.AdSize;
import com.jdroid.android.ActionItem;
import com.jdroid.android.R;
import com.jdroid.android.fragment.AbstractListFragment;
import com.jdroid.android.fragment.OnItemSelectedListener;
import com.jdroid.android.utils.ScreenUtils;
import com.jdroid.java.collections.Lists;

@SuppressLint("ValidFragment")
public class ContextualListFragment extends AbstractListFragment<ActionItem> {
	
	private static final String ACTIONS_EXTRA = "actions";
	private static final String DEFAULT_INDEX_EXTRA = "defaultIndex";
	
	private List<ActionItem> actions;
	private Integer defaultIndex;
	
	public ContextualListFragment() {
	}
	
	public ContextualListFragment(List<? extends ActionItem> actions, ActionItem defaultContextualItem) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(ACTIONS_EXTRA, Lists.newArrayList(actions));
		bundle.putInt(DEFAULT_INDEX_EXTRA, actions.indexOf(defaultContextualItem));
		setArguments(bundle);
	}
	
	/**
	 * @see com.jdroid.android.fragment.AbstractListFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		actions = getArgument(ACTIONS_EXTRA);
		defaultIndex = getArgument(DEFAULT_INDEX_EXTRA);
	}
	
	/**
	 * @see android.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup,
	 *      android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.contextual_list_fragment, container, false);
	}
	
	/**
	 * @see com.jdroid.android.fragment.AbstractListFragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setListAdapter(new ContextualItemsAdapter(this.getActivity(), actions));
		
		if (ScreenUtils.is7InchesOrLarger()) {
			getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
			getListView().setItemChecked(defaultIndex, true);
		} else {
			getListView().setChoiceMode(AbsListView.CHOICE_MODE_NONE);
		}
	}
	
	@Override
	public void onListItemClick(ListView listView, View v, int position, long id) {
		super.onListItemClick(listView, v, position, id);
		getListView().setItemChecked(position, true);
	}
	
	/**
	 * @see com.jdroid.android.fragment.AbstractListFragment#onItemSelected(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onItemSelected(ActionItem action) {
		((OnItemSelectedListener<ActionItem>)getActivity()).onItemSelected(action);
	}
	
	/**
	 * @see com.jdroid.android.fragment.AbstractFragment#getAdSize()
	 */
	@Override
	public AdSize getAdSize() {
		return ScreenUtils.is7InchesOrLarger() ? null : AdSize.SMART_BANNER;
	}
}