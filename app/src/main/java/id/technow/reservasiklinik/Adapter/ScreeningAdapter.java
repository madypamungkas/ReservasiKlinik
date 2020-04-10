package id.technow.reservasiklinik.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import id.technow.reservasiklinik.Model.ResponseScreening;
import id.technow.reservasiklinik.Model.ScreeningModel;
import id.technow.reservasiklinik.R;

public class ScreeningAdapter extends RecyclerView.Adapter<ScreeningAdapter.ScreeningVH> {
    Context mCtx;
    ArrayList<ScreeningModel> screeningModels;

    public ScreeningAdapter(Context mCtx, ArrayList<ScreeningModel> screeningModels) {
        this.mCtx = mCtx;
        this.screeningModels = screeningModels;
    }

    @NonNull
    @Override
    public ScreeningVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_screening, parent, false);
        return new ScreeningVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreeningVH holder, int position) {
        ScreeningModel model = screeningModels.get(position);
        holder.tvSoal.setText(model.getPertanyaan());
        holder.tvNum.setText(Integer.toString(position + 1));
        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        final Gson gson = new Gson();
        final SharedPreferences.Editor editorList = sharedPrefs.edit();

        if (holder.rbYes.isChecked()) {
            model.setJawaban("ya");
            String json = sharedPrefs.getString("response", "response");
            Type type = new TypeToken<ResponseScreening>() {
            }.getType();
            ResponseScreening responseScreening = gson.fromJson(json, type);

            String json2 = sharedPrefs.getString("question", "question");
            Type type2 = new TypeToken<ArrayList<ScreeningModel>>() {
            }.getType();
            ArrayList<ScreeningModel> screeningModels = gson.fromJson(json2, type2);

            model.setJawaban("ya");
            model.setCreated_at("tidak");
            model.setUpdated_at("tidak");

            String questionSt = gson.toJson(screeningModels);
            editorList.putString("screeningModel", questionSt);

            responseScreening.setScreening(screeningModels);
            String responseQuiz = gson.toJson(responseScreening);
            editorList.putString("response", responseQuiz);

            editorList.apply();

        }
        if (holder.rbNo.isChecked()) {
            model.setJawaban("tidak");
            String json = sharedPrefs.getString("response", "response");
            Type type = new TypeToken<ResponseScreening>() {
            }.getType();
            ResponseScreening responseScreening = gson.fromJson(json, type);

            String json2 = sharedPrefs.getString("question", "question");
            Type type2 = new TypeToken<ArrayList<ScreeningModel>>() {
            }.getType();
            ArrayList<ScreeningModel> screeningModels = gson.fromJson(json2, type2);


            model.setJawaban("tidak");
            model.setCreated_at("tidak");
            model.setUpdated_at("tidak");;

            String questionSt = gson.toJson(screeningModels);
            editorList.putString("screeningModel", questionSt);

            responseScreening.setScreening(screeningModels);
            String responseQuiz = gson.toJson(responseScreening);
            editorList.putString("response", responseQuiz);

            editorList.apply();
        }
    }

    @Override
    public int getItemCount() {
        return screeningModels.size();
    }

    class ScreeningVH extends RecyclerView.ViewHolder {
        TextView tvSoal, tvAns, tvNum;
        RadioButton rbYes, rbNo;

        public ScreeningVH(@NonNull View itemView) {
            super(itemView);
            tvSoal = itemView.findViewById(R.id.tvSoal);
            tvAns = itemView.findViewById(R.id.tvAns);
            tvNum = itemView.findViewById(R.id.tvNum);
            rbYes = itemView.findViewById(R.id.rBYes);
            rbNo = itemView.findViewById(R.id.rbNo);
        }
    }
}
