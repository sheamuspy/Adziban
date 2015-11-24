package com.adziban.adziban.customer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.adziban.adziban.R;
import com.adziban.adziban.customer.models.Cart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CanteenFragment.CanteenOnFragmentInteractionListener, HistoryFragment.HistoryOnFragmentInteractionListener, CartFragment.CartOnFragmentInteractionListener, MenuFragment.MenuOnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteens);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Order Placed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();





            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.canteens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title="";

        if (id == R.id.nav_canteen) {
            // Handle the camera action

//            fragment = new CanteenFragment();
            title="Canteens";
            FragmentTask fragmentTask = new FragmentTask(id,title);
            fragmentTask.execute((Void)null);

        } else if (id == R.id.nav_cart) {
            fragment = CartFragment.newInstance(Cart.getInstance().getCart());
            title="My Cart";
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
            title = "History";
        } else if (id == R.id.nav_logout) {

        }
        if(fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main,fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    public class FragmentTask extends AsyncTask<Void, Void, Boolean> {

        private final int mId;
        private String result;
        private final String mTitle;

        FragmentTask(int id, String title) {
            mId = id;
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
                if(mId == R.id.nav_canteen){
                    url = new URL(ip + "cmd=0");
                }else {
                    return response;
                }

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
                    result = responseTxt.getJSONArray("administrators").toString();
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
                if (mId == R.id.nav_canteen){
                    fragment  = CanteenFragment.newInstance(result);
                }
                if(fragment!=null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main, fragment);
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle(mTitle);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
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
