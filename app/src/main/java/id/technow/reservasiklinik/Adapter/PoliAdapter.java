package id.technow.reservasiklinik.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.technow.reservasiklinik.Model.PoliModel;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.ReservasiActivity;

public class PoliAdapter extends RecyclerView.Adapter<PoliAdapter.PoliVH> {
    Context mCtx;
    ArrayList<PoliModel> poliModels;
    private int mSelectedItem = -1;

    public PoliAdapter(Context mCtx, ArrayList<PoliModel> poliModels) {
        this.mCtx = mCtx;
        this.poliModels = poliModels;
    }

    @NonNull
    @Override
    public PoliVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jam_sesi, parent, false);
        PoliVH holder = new PoliVH(view);
        return new PoliVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliVH holder, int position) {
        PoliModel Poli = poliModels.get(position);
        holder.tvSchool.setText(Poli.getPoli());
        if (position == mSelectedItem) {
            holder.placeA.setCardBackgroundColor(Color.parseColor("#FFB100"));
            if (mCtx instanceof ReservasiActivity) {
                ((ReservasiActivity) mCtx).PoliSetting("Poli " + Poli.getPoli(), Poli.getId());
            }

        } else {

        }
    }

    @Override
    public int getItemCount() {
        return poliModels.size();
    }

    class PoliVH extends RecyclerView.ViewHolder {
        TextView tvSchool;
        RadioButton rbChoose;
        public int id;
        public String school;
        CardView placeA;

        public PoliVH(@NonNull View itemView) {
            super(itemView);
            tvSchool = itemView.findViewById(R.id.tvSchool);
            rbChoose = itemView.findViewById(R.id.rbChoose);
            placeA = itemView.findViewById(R.id.placeA);

            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, poliModels.size());
                    notifyDataSetChanged();

                }
            };

            rbChoose.setOnClickListener(l);
            itemView.setOnClickListener(l);
            placeA.setOnClickListener(l);
        }
    }
}
