package com.example.koperasi_daftarakunatas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText etNama,etTempatLahir,etTglLahir,etAlamat;
    private RadioGroup rgGender;
    private RadioButton rbSelected;
    private Spinner spPekerjaan;
    private ImageView ivBtnBack;
    private Button btnSubmit;
    private DatePickerDialog.OnDateSetListener date;
    private String nama,tempatLahir,tglLahir,gender,pekerjaan,alamat;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initViews();
        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int thn = calendar.get(Calendar.YEAR);
                int bln = calendar.get(Calendar.MONTH);
                int hari = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this,
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        date,
                        thn,bln,hari);
//                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                etTglLahir.setText(dayOfMonth + "/" + month + "/" + year);
                Log.d(TAG, "onDateSet: " + dayOfMonth + "/" + month + "/" + year);
            }
        };
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
            }
        });
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Press Back",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void initViews(){
        etNama = findViewById(R.id.et_namaLengkap);
        etTempatLahir = findViewById(R.id.et_tempat);
        etTglLahir = findViewById(R.id.et_tglLahir);
        etAlamat = findViewById(R.id.et_alamat);

        rgGender = findViewById(R.id.rg_jenisKelamin);

        spPekerjaan = findViewById(R.id.sp_pekerjaan);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.job,
                R.layout.color_spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        spPekerjaan.setAdapter(adapter);

        ivBtnBack = findViewById(R.id.iv_btnBack);

        btnSubmit = findViewById(R.id.btn_submit);
    }

    public void getInput(){
        Log.d(TAG, "getInput: Started");
        if(etNama.getText().toString().trim().equalsIgnoreCase(""))
            etNama.setError("Tulis nama anda");
        else {
            nama = etNama.getText().toString();
            etNama.setError(null);
        }
        if(etTglLahir.getText().toString().trim().equalsIgnoreCase(""))
            etTglLahir.setError("Masukkan tanggal lahir anda");
        else {
            tglLahir = etTglLahir.getText().toString();
            etTglLahir.setError(null);
        }
        if(etTempatLahir.getText().toString().trim().equalsIgnoreCase(""))
            etTempatLahir.setError("Masukkan tempat lahir anda");
        else {
            tempatLahir = etTempatLahir.getText().toString();
            etTempatLahir.setError(null);
        }
        if(etAlamat.getText().toString().trim().equalsIgnoreCase(""))
            etAlamat.setError("Masukkan alamat anda");
        else {
            alamat = etAlamat.getText().toString();
            etAlamat.setError(null);
        }
        int selectedId = rgGender.getCheckedRadioButtonId();
        if(selectedId <= 0){
            int lastBtn = rgGender.getChildCount() - 1;
            ((RadioButton) rgGender.getChildAt(lastBtn)).setError("Pilih jenis kelamin anda");
        }else {
            rbSelected = findViewById(selectedId);
            gender = rbSelected.getText().toString();
            int lastBtn = rgGender.getChildCount() - 1;
            ((RadioButton) rgGender.getChildAt(lastBtn)).setError(null);
        }

        if(!spPekerjaan.getSelectedItem().equals("--- pilih ---")){
            pekerjaan = spPekerjaan.getSelectedItem().toString();
        }else{
            ((TextView)spPekerjaan.getSelectedView()).setError("Masukkan jenis pekerjaan anda");
        }
        Log.d(TAG, "getInput: \n" +"Nama Lengkap : " + nama
                + "\n" + "Jenis Kelamin : " + gender
                + "\n" + "Tempat,Tanggal Lahir : " + tempatLahir + "," + tglLahir
                + "\n" + "Alamat : " + alamat
                + "\n" + "Pekerjaan : " + pekerjaan );
    }
}