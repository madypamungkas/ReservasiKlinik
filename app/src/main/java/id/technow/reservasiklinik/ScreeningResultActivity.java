package id.technow.reservasiklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ScreeningResultActivity extends AppCompatActivity {
    ImageView qrImage;
    String idReservasi, reservasiCode, poli, antrian, jam, counter, namaPasien, status, tanggal, hasilScreening, skorScreening;
    TextView txtIdReservasi, txtNama, txtTanggal, txtJamSesi, txtStatus, txtnoAntrian, txtSkor, txtHasil, txtPoli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_result);
        qrImage = findViewById(R.id.qrImage);
        /*reservasiCode = getIntent().getStringExtra("codeReservasi");
        idReservasi = getIntent().getStringExtra("idReservasi");
        poli = getIntent().getStringExtra("poli");
        antrian = getIntent().getStringExtra("antrian");
        jam = getIntent().getStringExtra("jam");
        counter = getIntent().getStringExtra("counter");
        namaPasien = getIntent().getStringExtra("namaPasien");
        status = getIntent().getStringExtra("status");
        tanggal = getIntent().getStringExtra("tanggal");*/
        hasilScreening = getIntent().getStringExtra("hasilScreening");
        skorScreening = getIntent().getStringExtra("skorScreening");

        txtIdReservasi = findViewById(R.id.txtIdReservasi);
        txtNama = findViewById(R.id.txtNama);
        txtTanggal = findViewById(R.id.txtTanggal);
        txtJamSesi = findViewById(R.id.txtJamSesi);
        txtStatus = findViewById(R.id.txtStatus);
        txtnoAntrian = findViewById(R.id.txtnoAntrian);
        txtPoli = findViewById(R.id.txtPoli);
        txtSkor = findViewById(R.id.txtSkor);
        txtHasil = findViewById(R.id.txtHasil);

       /* txtIdReservasi.setText("ID Reservasi Anda "+reservasiCode);
        txtNama.setText(namaPasien);
        txtTanggal.setText(tanggal);
        txtJamSesi.setText(jam);
        txtStatus.setText(status);
        txtnoAntrian.setText(antrian);*/
        txtSkor.setText("Screening Score Anda"+ " "+ skorScreening);
        txtHasil.setText(hasilScreening);

       // generateQr();
    }

    public void simpan(View view) {
        Intent intent = new Intent(ScreeningResultActivity.this, ListPasienAcitivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void generateQr() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(reservasiCode, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
