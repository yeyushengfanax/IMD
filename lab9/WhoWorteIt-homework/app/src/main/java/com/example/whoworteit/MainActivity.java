package com.example.whoworteit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<String> {
    Spinner mySpinner;
    EditText mEditText;
    TextView mTextView;
    String spinenrValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySpinner=findViewById(R.id.mySpinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.spinner_item,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        mEditText=findViewById(R.id.urlField);
        mTextView=findViewById(R.id.soruceInfo);
        mySpinner.setOnItemSelectedListener(this);
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinenrValue= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String value[]= getResources().getStringArray(R.array.spinner_item);
        spinenrValue=value[0];
    }
    public void getSource(View view) {
        String queryString= mEditText.getText().toString();
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager!=null){
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if(connectivityManager!=null){
            networkInfo=connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo!=null && networkInfo.isConnected() && queryString.length()!=0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            queryBundle.putString("Protocol", spinenrValue);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            mTextView.setText("Loading...");
        }else {
            if(queryString.length()==0){
                mTextView.setText("No website to search");
            }
            else{
                mTextView.setText("No connection");
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString= "";
        String transferProtocol="";
        if(args!=null){
            queryString=args.getString("queryString");
            transferProtocol=args.getString("Protocol");
        }

        return new LoaderPage(this,queryString,transferProtocol);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try{
            mTextView.setText(data);
        } catch (Exception e) {
            e.printStackTrace();
            mTextView.setText("No Response");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}