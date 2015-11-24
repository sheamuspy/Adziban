package com.adziban.adziban.customer;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.adziban.adziban.R;
import com.adziban.adziban.customer.dummy.DummyContent;
import com.adziban.adziban.customer.models.MenuAdapter;
import com.adziban.adziban.customer.models.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link MenuOnFragmentInteractionListener}
 * interface.
 */
public class MenuFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MENU_LIST = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mMenuList;
    private ArrayList<MenuItem> menuList;
//    private String mParam2;

    private MenuOnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static MenuFragment newInstance(String param1) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(MENU_LIST, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMenuList = getArguments().getString(MENU_LIST);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            System.out.println("from the menufragmentclass " + mMenuList);
//            convertToArrayList();
            try {
                menuList = MenuItem.fromJson(new JSONArray(mMenuList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("from the array class "+ menuList.get(0).foodPortion);
            mAdapter = new MenuAdapter(getActivity(),menuList);
        }

        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MenuOnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MenuOnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MenuOnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

  /*  private void convertToArrayList(){
        menuList = new ArrayList<MenuItem>();
        try {
            JSONArray jsonArray = new JSONArray(mMenuList);
            if (jsonArray!=null){
                for (int i = 0; i<jsonArray.length();i++){
                    Log.d("Sheamus", "In loooop");
                    MenuItem menuItem = new MenuItem();
                    menuItem.foodPortion = jsonArray.getJSONObject(i).getString("foodPortion");
                    menuItem.foodPrice = jsonArray.getJSONObject(i).getDouble("foodPrice");
                    menuItem.foodName=jsonArray.getJSONObject(i).getString("foodName");
                    menuItem.fId = jsonArray.getJSONObject(i).getInt("fId");
                    menuList.add(menuItem);
                }
            }
        }catch (JSONException e){

        }

    }*/

}
