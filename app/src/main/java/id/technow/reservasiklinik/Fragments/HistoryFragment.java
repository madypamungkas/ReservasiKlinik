package id.technow.reservasiklinik.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import id.technow.reservasiklinik.Adapter.NotifAdapter;
import id.technow.reservasiklinik.Adapter.ReservasiAdapter;
import id.technow.reservasiklinik.JadwalActivity;
import id.technow.reservasiklinik.Model.NotifModel;
import id.technow.reservasiklinik.Model.PostScreeningModel;
import id.technow.reservasiklinik.R;

public class HistoryFragment extends Fragment {
    public HistoryFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    ViewPager viewPager;
    // ViewPagerAdapter adapter;
    private RecyclerView rvReservasi;
    NotifAdapter adapter;
    ProgressDialog loading;
    LinearLayout layoutAda, layoutTidakAda;
    ArrayList<NotifModel> models;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        rvReservasi = fragmentView.findViewById(R.id.rvReservasi);
        layoutAda = fragmentView.findViewById(R.id.layoutAda);
        layoutTidakAda = fragmentView.findViewById(R.id.layoutTidakAda);

        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final Gson gson = new Gson();
        final SharedPreferences.Editor editorList = sharedPrefs.edit();


        String json = sharedPrefs.getString("notif", "notif");
        Type type = new TypeToken<ArrayList<NotifModel>>() {
        }.getType();

        if (json != "notif") {
            models = gson.fromJson(json, type);
            if (models.size() ==0) {
                Toast.makeText(getActivity(), "Tidak Ada Notifikasi", Toast.LENGTH_SHORT).show();

            } else {
                layoutTidakAda.setVisibility(View.GONE);
                layoutAda.setVisibility(View.VISIBLE);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                adapter = new NotifAdapter(models, getActivity());
                rvReservasi.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvReservasi.setLayoutManager(staggeredGridLayoutManager);
                rvReservasi.setAdapter(adapter);
            }
        }

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new HistorySelfFragment(), "Diri Sendiri");
        adapter.AddFragment(new HistoryOtherFragment(), "Orang Lain");
        viewPager.setAdapter(adapter);*/
    }
}

