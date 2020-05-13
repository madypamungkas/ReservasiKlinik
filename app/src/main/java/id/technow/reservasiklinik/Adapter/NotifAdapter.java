package id.technow.reservasiklinik.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.technow.reservasiklinik.Model.NotifModel;
import id.technow.reservasiklinik.Model.PasienModel;
import id.technow.reservasiklinik.R;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifVH> {

    ArrayList<NotifModel> models;
    Context mCtx;

    public NotifAdapter(ArrayList<NotifModel> models, Context mCtx) {
        this.models = models;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public NotifVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_orang, parent, false);
        return new NotifVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifVH holder, int position) {
        final NotifModel model = models.get(position);
        holder.txtName.setText(model.getTitle());
        holder.txtNomor.setText(model.getMessage());
        holder.txtInisial.setText("");

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class NotifVH extends RecyclerView.ViewHolder {
        TextView txtNomor, txtName, txtInisial;

        public NotifVH(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtInisial = itemView.findViewById(R.id.txtInisial);
            txtNomor = itemView.findViewById(R.id.txtNomor);

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
