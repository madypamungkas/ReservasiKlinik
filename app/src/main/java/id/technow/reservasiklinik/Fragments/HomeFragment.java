package id.technow.reservasiklinik.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.technow.reservasiklinik.API.RetrofitClient;
import id.technow.reservasiklinik.API.RetrofitCorona;
import id.technow.reservasiklinik.Adapter.MenuHomeAdapter;
import id.technow.reservasiklinik.Model.MenuHomeModel;
import id.technow.reservasiklinik.Model.ResponseCorona;
import id.technow.reservasiklinik.Model.ResponseEditPasien;
import id.technow.reservasiklinik.Model.UserModel;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.ScreeningView;
import id.technow.reservasiklinik.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    MenuHomeAdapter adapter;
    //NearPsikologAdapter nearAdapter;
    ArrayList<MenuHomeModel> models;
    // ArrayList<NearPsikologModel> nearModels;
    RecyclerView menuRV, nearPsikologRV;
    ImageView imgProfile;
    TextView tvName, tvPhone;
    SliderView slider;
    TextView layoutCard;
    /* HomeSliderViewAdapter sliderAdapter;
     ArrayList<BannerModel> bannerModel = null;*/
    Context mCtx;
    private TextView txtKasus, txtSembuh, txtMeninggal;

    public HomeFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        imgProfile = fragmentView.findViewById(R.id.imgProfile);
        tvName = fragmentView.findViewById(R.id.tvName);
        tvPhone = fragmentView.findViewById(R.id.tvPhone);
        slider = fragmentView.findViewById(R.id.banner_slider);
        nearPsikologRV = fragmentView.findViewById(R.id.nearPsikologRV);
        layoutCard = fragmentView.findViewById(R.id.layoutCard);
        txtKasus = fragmentView.findViewById(R.id.txtValue1A);
        txtSembuh = fragmentView.findViewById(R.id.txtValue2);
        txtMeninggal = fragmentView.findViewById(R.id.txtValue3);

        UserModel userModel = SharedPrefManager.getInstance(getActivity()).getUser();
        tvName.setText("Hello, " + userModel.getName());

        models = new ArrayList<>();
        models.add(new MenuHomeModel(1, R.drawable.btn_daftar_konsultasi, "Reservasi Klinik"));
        models.add(new MenuHomeModel(2, R.drawable.btn_jadwal, "Jadwal Pemerikasaan"));
        models.add(new MenuHomeModel(3, R.drawable.btn_biodata, "List Pasien"));

        menuRV = fragmentView.findViewById(R.id.RVmain);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        adapter = new MenuHomeAdapter(models, getActivity());
        menuRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuRV.setLayoutManager(staggeredGridLayoutManager);
        menuRV.setAdapter(adapter);

        // loadNearPsikolog();
       /* loadBanner();
        loadDetails();*/
        layoutCard.setOnClickListener(this);
        getCorona();
        return fragmentView;
    }

    public void getCorona() {
        Call<ArrayList<ResponseCorona>> call = RetrofitCorona
                .getInstance()
                .getApi()
                .coronaIndo("application/json");
        call.enqueue(new Callback<ArrayList<ResponseCorona>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseCorona>> call, Response<ArrayList<ResponseCorona>> response) {
                if (response.isSuccessful()) {
                    ResponseCorona responseCorona = response.body().get(0);
                    txtKasus.setText(responseCorona.getPositif() + " Kasus");
                    txtSembuh.setText(responseCorona.getSembuh() + " Kasus");
                    txtMeninggal.setText(responseCorona.getMeninggal() + " Kasus");
                } else {
                    Toast.makeText(getActivity(), response.code() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseCorona>> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString() + " ", Toast.LENGTH_LONG).show();

            }
        });
    }
/*

    public void loadBanner() {
        bannerModel = new ArrayList<>();
        bannerModel.add(new BannerModel(1, "banner 1", "5d440cdd72347.jpg", "Banner 2", "1", R.drawable.banner1, "2019-08-02 10:13:49", "2019-08-02 10:13:49"));
        bannerModel.add(new BannerModel(1, "banner 1", "5d440cdd72347.jpg", "Banner 2", "1", R.drawable.banner2, "2019-08-02 10:13:49", "2019-08-02 10:13:49"));
        bannerModel.add(new BannerModel(1, "banner 1", "5d440cdd72347.jpg", "Banner 2", "1", R.drawable.banner3, "2019-08-02 10:13:49", "2019-08-02 10:13:49"));
        sliderAdapter = new HomeSliderViewAdapter(getActivity(), bannerModel);
        slider.startAutoCycle();
        slider.setIndicatorAnimation(IndicatorAnimations.WORM);
        slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        slider.setScrollTimeInSec(6);
        slider.setSliderAdapter(sliderAdapter);
    }

    public void loadNearPsikolog() {
        nearModels = new ArrayList<>();
        nearModels.add(new NearPsikologModel(1, "Psikolog 1", "Jalan Sekip 1 no 89 UGM", "081234123412", "122230494", "https://gmc.ugm.ac.id/wp-content/uploads/sites/536/2013/10/21-300x166.jpg"));
        nearModels.add(new NearPsikologModel(1, "Psikolog 2", "Bulaksumur UGM", "081234123412", "122230494", "https://ugm.ac.id/galleries/crop/84--730x420px.jpg"));
        nearModels.add(new NearPsikologModel(1, "Psikolog 3", "Tegalrejo Jogja", "081234123412", "122230494", "https://ugm.ac.id/galleries/crop/84--730x420px.jpg"));
        nearModels.add(new NearPsikologModel(1, "Psikolog 4", "GMC UGM", "081234123412", "122230494", "https://gmc.ugm.ac.id/wp-content/uploads/sites/536/2013/10/21-300x166.jpg"));*/
/*
        nearModels.add(new NearPsikologModel(1, "Psikolog 5", "RSA UGM", "081234123412", "122230494", "https://ruko.technow.id/storage/banner/1?2019-10-29%2004:31:05"));
        nearModels.add(new NearPsikologModel(1, "Psikolog 6", "RSUP Dr Sardjito", "081234123412", "122230494", "https://ruko.technow.id/storage/banner/1?2019-10-29%2004:31:05"));*//*


        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
        nearAdapter = new NearPsikologAdapter(nearModels, getActivity());
        nearPsikologRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        nearPsikologRV.setLayoutManager(staggeredGridLayoutManager2);
        nearPsikologRV.setAdapter(nearAdapter);

    }
*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutCard:
                Intent i = new Intent(getActivity(), ScreeningView.class);
                startActivity(i);
                break;
            case R.id.imgProfile:
                Intent image = new Intent(getActivity(), ScreeningView.class);
                startActivity(image);
                break;
        }
    }

   /* public void loadDetails() {
        final String accept = "application/json";

        final String token = SharedPrefManager.getInstance(getActivity()).getToken().getToken();
        retrofit2.Call<ResponseDetailKlien> call = RetrofitClient
                .getInstance()
                .getApi()
                .getKlien("Bearer "+token, accept);
        call.enqueue(new retrofit2.Callback<ResponseDetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseDetailKlien> call, retrofit2.Response<ResponseDetailKlien> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(getActivity()).saveDetails(response.body().getDetails());
                    int idUser = response.body().getDetails().getId();
                    tvName.setText(response.body().getDetails().getName());
                    tvPhone.setText(response.body().getDetails().getNo_telepon());
                    String link = "https://psikologi.ridwan.info/images/";
                    Picasso.get().load(response.body().getDetails().getFoto()).into(imgProfile);

                } else {
                    Toast.makeText(getActivity(), "Error, Periksa Kembali Jaringan Anda 2" + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseDetailKlien> call, Throwable t) {
                Toast.makeText(getActivity(), "Error, Periksa Kembali Jaringan Anda 3" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
}

