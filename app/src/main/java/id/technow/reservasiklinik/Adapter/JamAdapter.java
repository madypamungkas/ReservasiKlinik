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
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.ReservasiActivity;

public class JamAdapter extends RecyclerView.Adapter<JamAdapter.JamVH> {
    Context mCtx;
    ArrayList<JamModel> jamModels;
    private int mSelectedItem = -1;

    public JamAdapter(Context mCtx, ArrayList<JamModel> jamModels) {
        this.mCtx = mCtx;
        this.jamModels = jamModels;
    }

    @NonNull
    @Override
    public JamVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jam_sesi, parent, false);
        JamVH holder = new JamVH(view);
        return new JamVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JamVH holder, int position) {
        JamModel jam = jamModels.get(position);
        holder.tvSchool.setText(jam.getJam());
        if (position == mSelectedItem) {
            holder.placeA.setCardBackgroundColor(Color.parseColor("#FFB100"));
            if (mCtx instanceof ReservasiActivity) {
                ((ReservasiActivity) mCtx).jamSetting(jam.getJam()+" WIB", jam.getId());
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
        return jamModels.size();
    }

    class JamVH extends RecyclerView.ViewHolder {
        TextView tvSchool;
        RadioButton rbChoose;
        public int id;
        public String school;
        CardView placeA;

        public JamVH(@NonNull View itemView) {
            super(itemView);
            tvSchool = itemView.findViewById(R.id.tvSchool);
            rbChoose = itemView.findViewById(R.id.rbChoose);
            placeA = itemView.findViewById(R.id.placeA);

            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, jamModels.size());
                    notifyDataSetChanged();

                }
            };

            rbChoose.setOnClickListener(l);
            itemView.setOnClickListener(l);
            placeA.setOnClickListener(l);
        }
    }
}
