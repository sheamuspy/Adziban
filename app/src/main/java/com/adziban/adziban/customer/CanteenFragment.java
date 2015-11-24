package com.adziban.adziban.customer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import com.adziban.adziban.customer.models.Canteen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link CanteenOnFragmentInteractionListener}
 * interface.
 */
public class CanteenFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CANTEEN_LIST = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mCanteenList;
    private ArrayList canteenList;
    //private String mParam2;

    private CanteenOnFragmentInteractionListener mListener;

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
    public static CanteenFragment newInstance(String param1) {
        CanteenFragment fragment = new CanteenFragment();
        Bundle args = new Bundle();
        args.putString(CANTEEN_LIST, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CanteenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCanteenList = getArguments().getString(CANTEEN_LIST);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            convertToArrayList();
            mAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, canteenList);
        }

        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canteen, container, false);

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
            mListener = (CanteenOnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CanteenOnFragmentInteractionListener");
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
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
        MenuTask menuTask = new MenuTask(position+1,"Menu");
        menuTask.execute((Void)null);
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
    public interface CanteenOnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private void convertToArrayList(){
        canteenList = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(mCanteenList);
            if (jsonArray!=null){
                for (int i = 0; i<jsonArray.length();i++){
                    /*Canteen canteen = new Canteen();
                    canteen.id = jsonArray.getJSONObject(i).getInt("aid");
                    canteen.name = jsonArray.getJSONObject(i).getString("adminstratorName");
                    canteen.phoneNumber=jsonArray.getJSONObject(i).getString("phoneNumber");
                    canteenList.add(canteen);*/
                    canteenList.add(jsonArray.getJSONObject(i).getString("adminstratorName"));
                }
            }
        }catch (JSONException e){

        }

    }

    public class MenuTask extends AsyncTask<Void, Void, Boolean> {

        private final int mPosition;
        private String result;
        private final String mTitle;

        MenuTask(int position, String title) {
            mPosition = position;
            mTitle = title;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String ip = "http://cs.ashesi.edu.gh/~csashesi/class2016/sheamus-yebisi/mobile_web/Adziban/ajax.php?";
            HttpURLConnection urlConnection=null;
            boolean response = false;
            Log.d("Sheamus", "entered background");

            try {
                URL url;
                    url = new URL(ip + "cmd=1&aid="+mPosition);


                Log.d("Sheamus",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                String s = "";
                String returned ="";
                while ((s = buffer.readLine()) != null) {
                    returned = returned+s;
                }
                System.out.println(returned);
                Log.d("Sheamus", returned);
                JSONObject responseTxt = new JSONObject(returned);
                int status  = responseTxt.getInt("status");
                if(status == 0){
                    result = responseTxt.getJSONArray("menu").toString();
                    response = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }


            // TODO: register the new account here.
            return response;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            /*
            mAuthTask = null;
            showProgress(false);
            */

            if (success) {
                Fragment fragment = null;
                    fragment  = MenuFragment.newInstance(result);

                if(fragment!=null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main, fragment);
                    fragmentTransaction.commit();
                }
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
        }
    }

}
