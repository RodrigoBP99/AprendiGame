package br.com.rodrigo.aprendigame.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;


import java.util.Arrays;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeitorQRFragment extends Fragment implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;

    public LeitorQRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScannerView = new ZXingScannerView(getActivity());
        return ScannerView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleResult(Result rawResult) {
        rawResult.toString();
        String texto = String.valueOf(rawResult);
        String array[] = texto.split("&");
        Toast.makeText(getActivity(), Arrays.toString(array), Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }


    @Override
    public void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }


    @Override
    public void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
