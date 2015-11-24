package com.adziban.adziban.customer;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.adziban.adziban.R;
import com.adziban.adziban.customer.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link HistoryOnFragmentInteractionListener}
 * interface.
 */
public class HistoryFragment extends Fragment implements AbsListView.OnItemClickListener {


    ArrayList<History> orders;
    JSONArray orderarray = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private HistoryOnFragmentInteractionListener mListener;

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
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        orders = new ArrayList<>();
        //readWebpage(view);
        HistoryList historyget = new HistoryList();
        historyget.execute("http://cs.ashesi.edu.gh/class2016/david-tandoh/MobileWeb/androidphp/controller.php?cmd=3&userid=1");



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
            mListener = (HistoryOnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CartOnFragmentInteractionListener");
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
    public interface HistoryOnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private class History {
        public String order;
        public String cost;
        public String date;

        public History(String order,String cost,String date) {
            this.order = order;
            this.cost = cost;
            this.date = date;
        }

        public String getfood(){
            return order;
        }

        public String getcost(){
            return cost;
        }

        public String getdate(){
            return date;
        }



    }

    private class HistoryList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url1 : urls) {
                try {
                    URL url = new URL(url1);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(in));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        System.out.println(s);
                        response += s;
                    }
                    if (response != null) {
                        JSONObject responseTxt = new JSONObject(response);
                        int status = responseTxt.getInt("result");
                        if (status == 1) {
                            orderarray = responseTxt.getJSONArray("transactions");
                            for (int i = 1; i < orderarray.length(); i++) {
                                JSONObject obj = orderarray.getJSONObject(i);

                                String foodname = obj.getString("foodName");
                                String cost = obj.getString("cost");
                                String date = obj.getString("date");

                                History historyitem = new History(foodname,cost,date);

                                //HashMap<String, String> order = new HashMap<>();

                                //order.put("food", foodname);

                                orders.add(historyitem);
                                System.out.println(orders);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }

            return response;


        }

        @Override
        protected void onPostExecute(String result) {
            //textView.setText(result);
            //Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

            mAdapter =  new HistoryAdapter(getActivity(), orders);
            mListView.setAdapter(mAdapter);

            //Log.d("message", "im in post execute");
            //mAdapter = new ArrayAdapter<ArrayList<History>>(getActivity(),
                   // android.R.layout.simple_list_item_1, android.R.id.text1, orders);

        }
    }

    /**
     * public void readWebpage(View view) {
     * System.out.println("clicked");
     * DownloadWebPageTask task = new DownloadWebPageTask();
     * task.execute(new String[] { "http://cs.ashesi.edu.gh/class2016/david-tandoh/MobileWeb/androidphp/controller.php?cmd=3&userid=1" });
     * <p/>
     * <p/>
     * <p/>
     * }
     **/



    private class HistoryAdapter extends ArrayAdapter<History> {
        public HistoryAdapter(Context context, ArrayList<History> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            History historyItem = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
            }
            // Lookup view for data population
            TextView mealName = (TextView) convertView.findViewById(R.id.orderName);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            // Populate the data into the template view using the data object
            mealName.setText(historyItem.getfood());
            price.setText(historyItem.getcost());
            date.setText(historyItem.getdate());
            //  tvHome.setText(user.hometown);
            // Return the completed view to render on screen
            return convertView;
        }


    }
}