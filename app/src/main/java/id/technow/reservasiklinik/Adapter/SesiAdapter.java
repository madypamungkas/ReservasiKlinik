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

import id.technow.reservasiklinik.Model.JamModel;
import id.technow.reservasiklinik.Model.SesiModel;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.ReservasiActivity;

public class SesiAdapter extends RecyclerView.Adapter<SesiAdapter.SesiVH> {
    Context mCtx;
    ArrayList<SesiModel> sesiModels;
    private int mSelectedItem = -1;

    public SesiAdapter(Context mCtx, ArrayList<SesiModel> sesiModels) {
        this.mCtx = mCtx;
        this.sesiModels = sesiModels;
    }

    @NonNull
    @Override
    public SesiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jam_sesi, parent, false);
        SesiAdapter.SesiVH holder = new SesiAdapter.SesiVH(view);
        return new SesiAdapter.SesiVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SesiVH holder, int position) {
        SesiModel sesi = sesiModels.get(position);
        holder.tvSchool.setText(sesi.getSesi());
        if (position == mSelectedItem) {
            holder.placeA.setCardBackgroundColor(Color.parseColor("#FFB100"));
            if (mCtx instanceof ReservasiActivity) {
                ((ReservasiActivity) mCtx).sesiSetting(sesi.getSesi(), sesi.getId());
            }
           /* if (mCtx instanceof ChangeProfile) {
                ((ChangeProfile) mCtx).setTextSch(schools.getText(), schools.getId());
                fragment.hideState();
            }*/
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return sesiModels.size();
    }

    class SesiVH extends RecyclerView.ViewHolder {
        TextView tvSchool;
        RadioButton rbChoose;
        public int id;
        public String school;
        CardView placeA;
     public SesiVH(@NonNull View itemView) {
         super(itemView);
         tvSchool = itemView.findViewById(R.id.tvSchool);
         rbChoose = itemView.findViewById(R.id.rbChoose);
         placeA = itemView.findViewById(R.id.placeA);

         View.OnClickListener l = new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mSelectedItem = getAdapterPosition();
                 notifyItemRangeChanged(0, sesiModels.size());
                 notifyDataSetChanged();

             }
         };

         rbChoose.setOnClickListener(l);
         itemView.setOnClickListener(l);
         placeA.setOnClickListener(l);
     }
 }
}
