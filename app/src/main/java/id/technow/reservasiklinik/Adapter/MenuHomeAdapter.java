package id.technow.reservasiklinik.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.technow.reservasiklinik.DataPasien;
import id.technow.reservasiklinik.JadwalActivity;
import id.technow.reservasiklinik.ListPasienAcitivity;
import id.technow.reservasiklinik.Model.MenuHomeModel;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.ReservasiActivity;
import id.technow.reservasiklinik.ScreeningActivity;

public class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.ViewHolder> {
    private List<MenuHomeModel> models;
    private Context mCtx;

    public MenuHomeAdapter(List<MenuHomeModel> models, Context mCtx) {
        this.models = models;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuHomeModel menu = models.get(position);
        holder.layoutCard.setBackgroundResource(menu.getImage());
        // holder.params.height = menu.getMinHeight();
        holder.titleMenu.setText(menu.getTitle());
        holder.layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu.getId() == 1) {
                    Intent i = new Intent(mCtx, ListPasienAcitivity.class);
                    mCtx.startActivity(i);
                } else if (menu.getId() == 2) {
                    Intent i = new Intent(mCtx, JadwalActivity.class);
                    mCtx.startActivity(i);
                } else if (menu.getId() == 3) {
                    Intent i = new Intent(mCtx, DataPasien.class);
                    mCtx.startActivity(i);
                } else if (menu.getId() == 4) {
                    Intent i = new Intent(mCtx, ScreeningActivity.class);
                    mCtx.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleMenu;
        LinearLayout layoutCard;
        FrameLayout.LayoutParams params;

        public ViewHolder(View itemView) {
            super(itemView);
            titleMenu = itemView.findViewById(R.id.titleMenu);
            layoutCard = itemView.findViewById(R.id.layoutCard);
            // layoutCard = new LinearLayout(mCtx);
            params = (FrameLayout.LayoutParams) layoutCard.getLayoutParams();

        }
    }
}
