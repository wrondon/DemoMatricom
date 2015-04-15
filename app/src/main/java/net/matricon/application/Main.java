package net.matricon.application;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Main extends Activity implements SideFragment.OnSideFragmentInteractionListener,
        DynamicXMLFragment.OnDynamicXMLFragmentInteractionListener,
        DownloadImageFragment.OnDownloadFragmentInteractionListener,
        ListViewFragment.OnListViewFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    MyLocalBoundService myService;
    boolean isBound = false;
    TextView myTextView;

    private ServiceConnection myConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MyLocalBoundService.MyLocalBinder binder = (MyLocalBoundService.MyLocalBinder) service;
            myService = binder.getService();
            isBound = true;
            Log.i(TAG,"on service  connecting (creation) >>>>>> Hi");
        }

        public void onServiceDisconnected(ComponentName arg0) {
            myService = null;
            isBound = false;
            Log.i(TAG,"on service  desconecting (destroy) >>>>>> By");
        }

    };

    public void showTime(View view)
    {
        String currentTime = myService.getCurrentTime();

        myTextView.setText(currentTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main);
        if (savedInstanceState == null) {
            SideFragment sideFragment = SideFragment.newInstance("","");
            getFragmentManager().beginTransaction()
                    .add(R.id.side, sideFragment).commit();
        }
        myTextView = (TextView)findViewById(R.id.textView01);
        Intent intent = new Intent(this, MyLocalBoundService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on Destroy, trying to disconnect unbinding !!!!!!!");
        // Unbind from the service
        if (isBound) {
            unbindService(myConnection);
            isBound = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.action_test_service) {
            String currentTime = myService.getCurrentTime();
            myTextView = (TextView)findViewById(R.id.textView01);
            myTextView.setText(currentTime);
            Log.i(TAG, "on service  current time is/was : " + currentTime);
            Intent intent = new Intent("MyCustomIntent");
            intent.setAction("net.matricom.application.MATRICOM.EMPLOYMENT");
            sendBroadcast(intent);
            Log.i(TAG, "on service  just sent message to broadcast receiver : net.matricom.application.MATRICOM.EMPLOYMENT !!!!!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSideFragmentInteraction(Fragment fragment) {

        getFragmentManager().beginTransaction()
                .replace(R.id.main, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onDynamicXMLFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDownloadFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListViewFragmentInteraction(String id) {

    }
}
