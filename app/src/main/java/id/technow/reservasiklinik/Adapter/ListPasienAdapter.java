package id.technow.reservasiklinik.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import id.technow.reservasiklinik.EditDataPasien;
import id.technow.reservasiklinik.ListPasienAcitivity;
import id.technow.reservasiklinik.Model.PasienModel;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.ReservasiActivity;

public class ListPasienAdapter extends RecyclerView.Adapter<ListPasienAdapter.ListPasienVH> {
    private int mSelectedItem = -1;
    Context mCtx;
    ArrayList<PasienModel> dataPasienModels;

    public ListPasienAdapter(Context mCtx, ArrayList<PasienModel> dataPasienModels) {
        this.mCtx = mCtx;
        this.dataPasienModels = dataPasienModels;
    }

    @NonNull
    @Override
    public ListPasienVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_orang, parent, false);
        return new ListPasienVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListPasienVH holder, int position) {
        final PasienModel model = dataPasienModels.get(position);
        holder.txtName.setText(model.getNama());
        holder.txtNomor.setText("NIK : " + model.getNik());
        holder.txtInisial.setText(getInitials(model.getNama()));
        holder.id = model.getId();
        holder.rbChoose.setChecked(position == mSelectedItem);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rbChoose.setChecked(true);
            }
        });
        if (position == mSelectedItem) {
            if (model.getNik() != null) {
                Intent intent = new Intent(mCtx, ReservasiActivity.class);
                intent.putExtra("idPasien", model.getId().toString());
                intent.putExtra("namaPasien", model.getNama());
                intent.putExtra("nikPasien", model.getNik());
                intent.putExtra("bpjsPasien", model.getNo_bpjs());
                intent.putExtra("hpPasien", model.getNo_telepon());
                mCtx.startActivity(intent);
            } else {
                Toast.makeText(mCtx, "Lengkapi Data Diri Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mCtx, EditDataPasien.class);
                intent.putExtra("idPasien", model.getId().toString());
                intent.putExtra("namaPasien", model.getNama());
                intent.putExtra("nikPasien", model.getNik());
                intent.putExtra("bpjsPasien", model.getNo_bpjs());
                intent.putExtra("hpPasien", model.getNo_telepon());
                mCtx.startActivity(intent);
            }
            // holder.layoutMore.setVisibility(View.VISIBLE);
        } else {
            //holder.layoutMore.setVisibility(View.GONE);
        }
        if (holder.rbChoose.isChecked()) {
            if (model.getNik() != null) {
                Intent intent = new Intent(mCtx, ReservasiActivity.class);
                intent.putExtra("idPasien", model.getId().toString());
                intent.putExtra("namaPasien", model.getNama());
                intent.putExtra("nikPasien", model.getNik());
                intent.putExtra("bpjsPasien", model.getNo_bpjs());
                intent.putExtra("hpPasien", model.getNo_telepon());
                mCtx.startActivity(intent);
            } else {
                Toast.makeText(mCtx, "Lengkapi Data Diri Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mCtx, EditDataPasien.class);
                intent.putExtra("idPasien", model.getId().toString());
                intent.putExtra("namaPasien", model.getNama());
                intent.putExtra("nikPasien", model.getNik());
                intent.putExtra("bpjsPasien", model.getNo_bpjs());
                intent.putExtra("hpPasien", model.getNo_telepon());
                mCtx.startActivity(intent);
            }

            //holder.layoutMore.setVisibility(View.VISIBLE);
        }
         /*
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, EditDataPasien.class);
                intent.putExtra("idPasien", model.getId().toString());
                intent.putExtra("namaPasien", model.getNama());
                intent.putExtra("nikPasien", model.getNik());
                intent.putExtra("bpjsPasien", model.getNo_bpjs());
                intent.putExtra("hpPasien", model.getNo_telepon());
                mCtx.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataPasienModels.size();
    }

    class ListPasienVH extends RecyclerView.ViewHolder {
        TextView txtNomor, txtName, txtInisial;
        ImageButton image;
        LinearLayout layoutMore;
        String id;
        RadioButton rbChoose;
        MaterialButton btnLaporan, btnEdit, btnDelete;

        public ListPasienVH(@NonNull View itemView) {
            super(itemView);
            rbChoose = itemView.findViewById(R.id.rbChoose);
            txtNomor = itemView.findViewById(R.id.txtNomor);
            btnLaporan = itemView.findViewById(R.id.btnLaporan);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            image = itemView.findViewById(R.id.image);
            layoutMore = itemView.findViewById(R.id.layoutMore);
            txtName = itemView.findViewById(R.id.txtName);
            txtInisial = itemView.findViewById(R.id.txtInisial);
            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, dataPasienModels.size());

                }
            };
            itemView.setOnClickListener(l);
        }
    }

    private String getInitials(String name) {
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0];
        char firstNameChar = firstName.charAt(0);
        if (nameParts.length > 1) {
            String lastName = nameParts[nameParts.length - 1];
            char lastNameChar = lastName.charAt(0);
            return firstNameChar + "" + lastNameChar;
        } else {
            return firstNameChar + "";
        }
    }
}
