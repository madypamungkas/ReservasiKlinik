package id.technow.reservasiklinik.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.technow.reservasiklinik.DetailReservasi;
import id.technow.reservasiklinik.Model.PostScreeningModel;
import id.technow.reservasiklinik.Model.ResultScreeningModel;
import id.technow.reservasiklinik.R;

public class ReservasiAdapter extends RecyclerView.Adapter<ReservasiAdapter.ReservasiVH> {
    ArrayList<PostScreeningModel> reservasi;
    Context mCtx;

    public ReservasiAdapter(ArrayList<PostScreeningModel> reservasi, Context mCtx) {
        this.reservasi = reservasi;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ReservasiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reservasi, parent, false);
        return new ReservasiVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasiVH holder, int position) {
        final PostScreeningModel reservasiModel = reservasi.get(position);
        holder.txtnoAntrian.setText(reservasiModel.getAntrian());
        holder.txtStatus.setText(reservasiModel.getStatus());
        holder.txtTanggal.setText(reservasiModel.getTanggal());
        holder.txtJamSesi.setText(reservasiModel.getJam());
        holder.txtNama.setText(reservasiModel.getCalon_pasien().getNama());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, DetailReservasi.class);
                intent.putExtra("idReservasi", reservasiModel.getId());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservasi.size();
    }

    class ReservasiVH extends RecyclerView.ViewHolder {
        TextView txtNama, txtTanggal, txtJamSesi, txtStatus, txtnoAntrian, txtPoli;
        LinearLayout layout;

        public ReservasiVH(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtTanggal = itemView.findViewById(R.id.txtTanggal);
            txtJamSesi = itemView.findViewById(R.id.txtJamSesi);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtnoAntrian = itemView.findViewById(R.id.txtnoAntrian);
            txtPoli = itemView.findViewById(R.id.txtPoli);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
