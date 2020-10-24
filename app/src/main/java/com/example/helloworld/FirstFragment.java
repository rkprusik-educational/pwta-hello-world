package com.example.helloworld;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment {

    private int n = 0;

    TextView textViewN;
    TextView textViewF;
    ProgressBar progressBar;
    Button btnUp;
    Button btnDown;
    Button btnCalc;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewN = view.findViewById(R.id.textview_nvalue);
        textViewF = view.findViewById(R.id.textview_fvalue);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        btnUp = view.findViewById(R.id.button_one_up);
        btnUp.setOnClickListener(v -> {
            n++;
            if(n > 1000) n = 1000;
            textViewN.setText( getString( R.string.nvalue, n ) );
        });

        btnDown = view.findViewById(R.id.button_one_down);
        btnDown.setOnClickListener(v -> {
            n--;
            if(n < 0) n = 0;
            textViewN.setText( getString( R.string.nvalue, n ) );
        });

        btnCalc = view.findViewById(R.id.button_calculate);
        btnCalc.setOnClickListener(v -> {
            UpdateTextView();
        });
    }

    private void UpdateTextView(){
        if(!progressBar.isShown())
            new FibonacciTask().execute(n);
        else
        {
            Snackbar.make(getView(), "Still calculating!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public static int fib(int n){
        if(n < 1) return 0;
        if ((n==1)||(n==2)) return 1;
        else return fib(n-1)+fib(n-2);
    }

    private class FibonacciTask extends AsyncTask<Integer, Boolean, Integer>{
        @Override
        protected Integer doInBackground(Integer... n) {
            publishProgress(true);
            int value = fib(n[0]);
            if(isCancelled()) return -1;
            publishProgress(false);
            return value;
        }

        @Override
        protected void onProgressUpdate(Boolean... progress){
            if(progress[0])
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Integer result){
            textViewF.setText( getString( R.string.fvalue, result ) );
        }
    }
}