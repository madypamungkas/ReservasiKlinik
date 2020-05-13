package id.technow.reservasiklinik.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
import id.technow.reservasiklinik.FetchAddressTask;
import id.technow.reservasiklinik.MainActivity;
import id.technow.reservasiklinik.Model.CoronaProv;
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

public class HomeFragment extends Fragment implements View.OnClickListener/*, FetchAddressTask.OnTaskCompleted*/ {

    MenuHomeAdapter adapter;
    //NearPsikologAdapter nearAdapter;
    ArrayList<MenuHomeModel> models;
    // ArrayList<NearPsikologModel> nearModels;
    RecyclerView menuRV, nearPsikologRV;
    ImageView imgProfile;
    TextView tvName, tvPhone;
    SliderView slider;
    TextView layoutCard, txtLokasi;
    ImageView location;
    Context mCtx;
    ArrayList<CoronaProv> coronaProvs, coronaProvs2;
    private TextView txtKasus, txtSembuh, txtMeninggal, txtValue3loc, txtValue2loc, txtValue1loc;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;


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
        txtLokasi = fragmentView.findViewById(R.id.txtLokasi);
        location = fragmentView.findViewById(R.id.location);
        txtValue2loc = fragmentView.findViewById(R.id.txtValue2loc);
        txtValue1loc = fragmentView.findViewById(R.id.txtValue1loc);
        txtValue3loc = fragmentView.findViewById(R.id.txtValue3loc);

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

        /*mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(TRACKING_LOCATION_KEY);
        }
         mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mTrackingLocation) {
                    new FetchAddressTask(getActivity(), (FetchAddressTask.OnTaskCompleted) getActivity()).execute(locationResult.getLastLocation());
                }
            }
        };*/
        // startTrackingLocation();
        layoutCard.setOnClickListener(this);
        location.setOnClickListener(this);
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

    public void getCoronaProv(final String prov) {
        Call<ArrayList<CoronaProv>> call = RetrofitCorona
                .getInstance()
                .getApi()
                .coronaProv("application/json");
        call.enqueue(new Callback<ArrayList<CoronaProv>>() {
            @Override
            public void onResponse(Call<ArrayList<CoronaProv>> call, Response<ArrayList<CoronaProv>> response) {
                if (response.isSuccessful()) {
                    coronaProvs = response.body();
                    coronaProvs2 = new ArrayList<CoronaProv>();

                    for (CoronaProv OL : coronaProvs) {
                        if (OL.getAttributes().getProvinsi().contains(prov)) {
                            coronaProvs2.add(OL);
                        }
                       /* Toast.makeText(getActivity(), coronaProvs.get(1).getAttributes().getKode_Provi() + " ", Toast.LENGTH_LONG).show();
*/
                    }
                    txtValue1loc.setText(coronaProvs2.get(0).getAttributes().getKasus_Posi() + " Kasus");
                    txtValue2loc.setText(coronaProvs2.get(0).getAttributes().getKasus_Semb() + " Kasus");
                    txtValue3loc.setText(coronaProvs2.get(0).getAttributes().getKasus_Meni() + " Kasus");

                } else {
                    Toast.makeText(getActivity(), response.code() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CoronaProv>> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString() + " ", Toast.LENGTH_LONG).show();

            }
        });
    }

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
            case R.id.location:
                ((MainActivity) getActivity()).loadLocation();
                break;
        }
    }


    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates(getLocationRequest(), mLocationCallback, null);
/*            mLocationButton.setText(R.string.stop_tracking_location);
            mRotateAnim.start();*/
          /*  txtLokasi.setText(getString(R.string.address_text,
                    getString(R.string.loading)
                    ));*/
        }
    }

    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(getActivity(), R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void setLocation(String result) {
        txtLokasi.setText(result);
        getCoronaProv(result);
    }

   /* @Override
    public void onTaskCompleted(String result) {
        Toast.makeText(getActivity(), result + " ", Toast.LENGTH_LONG).show();
        if (mTrackingLocation) {
            txtLokasi.setText(getString(R.string.address_text,
                    result));
        }
    }

    @Override
    public void onPause() {
        if (mTrackingLocation) {
            stopTrackingLocation();
            mTrackingLocation = true;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mTrackingLocation) {
            startTrackingLocation();
        }
        super.onResume();
    }*/
}

