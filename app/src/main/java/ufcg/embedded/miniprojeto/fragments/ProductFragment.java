package ufcg.embedded.miniprojeto.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ufcg.embedded.miniprojeto.R;

/**
 * Created by treinamento-09 on 02/06/16.
 */
public class ProductFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.product_layout, container, false);
        return view;
    }
}
