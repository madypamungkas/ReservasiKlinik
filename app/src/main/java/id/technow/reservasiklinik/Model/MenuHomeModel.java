package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuHomeModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private int image;
    @SerializedName("title")
    @Expose
    private String title;

    public MenuHomeModel(int id, int image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
