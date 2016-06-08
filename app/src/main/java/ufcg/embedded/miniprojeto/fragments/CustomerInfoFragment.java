package ufcg.embedded.miniprojeto.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ufcg.embedded.miniprojeto.R;

/**
 * Created by treinamento-09 on 08/06/16.
 */
public class CustomerInfoFragment extends Fragment {
    private Button dismiss;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.customer_main, container, false);
        dismiss = (Button) view.findViewById(R.id.dismiss);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */
                transaction.replace(R.id.root_frame, new CustomerFragment());

                transaction.commit();
            }
        });
        return view;
    }
}
