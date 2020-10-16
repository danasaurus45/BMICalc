package org.aplas.bmicalc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etTinggi;
    EditText etBerat;
    EditText etUmur;
    EditText etHasil;
    Button btnHitung;
    RadioGroup rgGender;
    RadioButton rbGender;
    private static final String TAG = "BMICalcActivity";

    private static String getNilaiText(EditText nilai) {
        return nilai.getText().toString();
    }

    private static Double getNilai(EditText nilai) {
        String nilaiText = getNilaiText(nilai);
        return Double.valueOf(nilaiText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTinggi = (EditText) findViewById(R.id.et_height);
        etBerat = (EditText) findViewById(R.id.et_weight);
        etUmur = (EditText) findViewById(R.id.et_age);
        etHasil = (EditText) findViewById(R.id.et_goal);
        btnHitung = (Button) findViewById(R.id.btn_calculate);
        rgGender = (RadioGroup) findViewById(R.id.rg_gender);

        Integer pilihJK = rgGender.getCheckedRadioButtonId();
        rbGender = findViewById(pilihJK);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitungBMI(view);
            }
        });
    }

    public void hitungBMI(View view){
        Double tinggi;
        Double berat;
        try {
            berat = getNilai(etBerat);
            tinggi = getNilai(etTinggi)/100;
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            Toast.makeText(getApplicationContext(),"ERROR! Your input is not valid!", Toast.LENGTH_LONG).show();
            return;
        }
        double bmi = berat/(tinggi*tinggi);

        displayBMI(bmi);
    }

    private void displayBMI(double bmi) {
        String bmiLabel="";
        String infoUmur = etUmur.getText().toString();

        if (Double.compare(bmi, 15d) <= 0){
            bmiLabel = getString(R.string.terlalu_sangat_kurus);
        } else if (Double.compare(bmi, 15d) > 0 && Double.compare(bmi, 16d) <= 0 ){
            bmiLabel = getString(R.string.sangat_kurus);
        } else if (Double.compare(bmi, 16d) > 0 && Double.compare(bmi, 18.5d) <= 0 ){
            bmiLabel = getString(R.string.kurus);
        } else if (Double.compare(bmi, 18.5d) > 0 && Double.compare(bmi, 25d) <= 0 ){
            bmiLabel = getString(R.string.normal);
        } else if (Double.compare(bmi, 25d) > 0 && Double.compare(bmi, 30d) <= 0 ){
            bmiLabel = getString(R.string.gemuk);
        } else if (Double.compare(bmi, 30d) > 0 && Double.compare(bmi, 35d) <= 0 ){
            bmiLabel = getString(R.string.cukup_gemuk);
        } else if (Double.compare(bmi, 35d) > 0 && Double.compare(bmi, 40d) <= 0 ){
            bmiLabel = getString(R.string.sangat_gemuk);
        } else {
            bmiLabel = getString(R.string.terlalu_sangat_gemuk);
        }

        bmiLabel = "Hasil penghitungan BMI : "+bmi+"\n\n"+" Kategori : "+"("+bmiLabel+")"+"\n\n"+" Umur : "+infoUmur;

        AlertDialog.Builder tampilBMI = new AlertDialog.Builder(this);
        tampilBMI.setTitle("Hasil Penghitungan BMI");
        tampilBMI.setMessage(bmiLabel).setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });

        AlertDialog ad = tampilBMI.create();
        ad.show();
    }
}