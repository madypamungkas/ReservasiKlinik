package id.technow.reservasiklinik.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoronaProv {
    @SerializedName("attributes")
    @Expose
    private AttributeCoroneProv attributes;

    public CoronaProv(AttributeCoroneProv attributes) {
        this.attributes = attributes;
    }

    public AttributeCoroneProv getAttributes() {
        return attributes;
    }
}
