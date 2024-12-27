package com.example.bevasarlobeadando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TermekAdapter extends BaseAdapter {

    private List<Termek> termekList;
    private Context context;

    public TermekAdapter(List<Termek> termekList, Context context) {
        this.termekList = termekList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return termekList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.activity_row, viewGroup, false);


        view.setTag(termekList.get(i).getId());
        TextView nev = view.findViewById(R.id.row_tnev);
        TextView ar = view.findViewById(R.id.row_tegysegar);
        TextView mennyiseg = view.findViewById(R.id.row_tmennyiseg);
        TextView mertekegyseg = view.findViewById(R.id.row_tmertekegyseg);

        nev.setText(termekList.get(i).getTermekNev());
        ar.setText(String.valueOf(termekList.get(i).getEgysegAr()));
        mennyiseg.setText(String.valueOf(termekList.get(i).getMennyiseg()));
        mertekegyseg.setText(String.valueOf(termekList.get(i).getMertekegyseg()));

        return view;
    }
}
