package com.itc.koperasi;

// android:backgroundTint="#08839E"

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.itc.koperasi.databinding.ActivityRegisterBinding;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private final boolean togglePasswordVisibility = false;
    private ActivityRegisterBinding binding;
    private DatePickerDialog.OnDateSetListener date;
    private String fullName, dateOfBirth, placeOfBirth, address, gender, job, phoneNumber, idCardNumber, email, password, retypePassword;
    private RadioButton rbSelected;

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initViews();
        binding.etDateOfBirth.setOnClickListener(this);

        date = (datePickerView, year, month, dayOfMonth) -> {
            month+=1;
            binding.etDateOfBirth.setText(dayOfMonth + "/" + month + "/" + year);
            Log.d(TAG, "onDateSet: " + dayOfMonth + "/" + month + "/" + year);
        };

        binding.btnRegister.setOnClickListener(this);
        binding.include.ivBackButton.setOnClickListener(this);
        binding.ivTogglePassword.setOnClickListener(this);
        binding.ivToggleRetypePassword.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
    }

    private void getInput() {
        // full name
        if (binding.etFullName.getText().toString().equalsIgnoreCase("")) {
            binding.etFullName.setError("Masukkan nama lengkap anda");
        } else {
            fullName = binding.etFullName.getText().toString();
            binding.etFullName.setError(null);
        }

        // gender
        binding.rgGender.check(binding.rgGender.getChildAt(0).getId());
        int selectedId = binding.rgGender.getCheckedRadioButtonId();
        if (selectedId > 0) {
            rbSelected = findViewById(selectedId);
            gender = rbSelected.getText().toString();
            int lastButton = binding.rgGender.getChildCount() - 1;
            ((RadioButton) binding.rgGender.getChildAt(lastButton)).setError(null);
        }

        if (binding.etDateOfBirth.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etDateOfBirth.setError("Masukkan tanggal lahir anda");
        } else {
            dateOfBirth = binding.etDateOfBirth.getText().toString();
            binding.etDateOfBirth.setError(null);
        }

        // place of birth
        if (binding.etRetypePassword.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etPlaceOfBirth.setError("Masukkan tempat lahir anda");
        } else {
            dateOfBirth = binding.etPlaceOfBirth.getText().toString();
            binding.etPlaceOfBirth.setError(null);
        }

        // dateOfBirth
        if (binding.etDateOfBirth.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etDateOfBirth.setError("Masukkan tempat lahir anda");
        } else {
            address = binding.etDateOfBirth.getText().toString();
            binding.etDateOfBirth.setError(null);
        }

        // address
        if (binding.etAddress.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etAddress.setError("Masukkan alamat anda");
        } else {
            address = binding.etAddress.getText().toString();
        }

        // job
        if (!binding.spJob.getSelectedItem().equals("Pilih")) {
            job = binding.spJob.getSelectedItem().toString();
        } else {
            ((TextView) binding.spJob.getSelectedView()).setError("Masukkan jenis pekerjaan anda");
        }

        if (binding.etIdCardNumber.getText().toString().equalsIgnoreCase("")) {
            binding.etIdCardNumber.setError("Masukkan nomor KTP anda");
        } else if (binding.etIdCardNumber.getText().toString().length() == 19) {
            idCardNumber = binding.etIdCardNumber.getText().toString();
            binding.etIdCardNumber.setError(null);
        } else {
            binding.etIdCardNumber.setError("Masukkan nomor KTP yang valid");
        }

        // phone number
        int phoneNumberLength = binding.etPhoneNumber.getText().toString().length();
        String phoneNumberPrefix = "";

        if (phoneNumberLength > 2) {
            phoneNumberPrefix = binding.etPhoneNumber.getText().toString().substring(0, 2);
        }

        if (binding.etPhoneNumber.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etPhoneNumber.setError("Masukkan nomor telepon anda");
        } else if (!phoneNumberPrefix.equals("08") || phoneNumberLength < 11 || phoneNumberLength > 13) {
            binding.etPhoneNumber.setError("Masukkan nomor telepon yang valid");
        } else {
            phoneNumber = binding.etPhoneNumber.getText().toString();
            binding.etPhoneNumber.setError(null);
        }

        // email
        if (binding.etEmail.getText().toString().equalsIgnoreCase("")) {
            binding.etEmail.setError("Masukkan alamat email anda");
        } else if (isValidEmail(binding.etEmail.getText().toString())) {
            email = binding.etEmail.getText().toString();
            binding.etEmail.setError(null);
        } else {
            binding.etEmail.setError("Masukkan alamat email yang valid");
        }

        // password
        if (binding.etPassword.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etPassword.setError("Masukkan kata sandi anda");
        } else {
            password = binding.etPassword.getText().toString();
            binding.etPassword.setError(null);
        }

        // retype password
        retypePassword = binding.etRetypePassword.getText().toString();
        if (retypePassword.trim().equalsIgnoreCase("")) {
            binding.etRetypePassword.setError("Masukkan ulang kata sandi anda");
        } else if (!password.equals(retypePassword)) {
            binding.etRetypePassword.setError("Kata sandi tidak cocok");
        } else {
            retypePassword = binding.etRetypePassword.getText().toString();
            binding.etRetypePassword.setError(null);
        }

        Log.d(TAG, "getInput: \n"
                + "Full Name : " + fullName + "\n"
                + "Gender : " + gender + "\n"
                + "Place Date of Birth : " + placeOfBirth + "," + dateOfBirth + "\n"
                + "Address : " + address + "\n"
                + "Job : " + job + "\n"
                + "ID Card Number :" + idCardNumber + "\n"
                + "Phone Number : " + phoneNumber + "\n"
                + "Email : " + email + "\n"
                + "Password : " + password + "\n"
                + "Retype Password : " + retypePassword
        );
    }


    private boolean addressIsValid() {
        if (binding.etAddress.getText().toString().trim().equalsIgnoreCase("")) {
            binding.etAddress.setError("Masukkan alamat anda");
            return false;
        } else {
            address = binding.etAddress.getText().toString();
            return true;
        }
    }


    private void initViews() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.job_list,
                R.layout.color_spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.etDateOfBirth) {
            Calendar calendar = Calendar.getInstance();
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog dialog = new DatePickerDialog(
                    RegisterActivity.this,
                    AlertDialog.THEME_HOLO_LIGHT,
                    date, year, month, dayOfMonth
            );

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        } else if (v == binding.btnRegister) {
            boolean checkboxIsChecked = binding.cbPrincipalSavings.isChecked()
                    && binding.cbMandatorySavings.isChecked();
            if (checkboxIsChecked) {
                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                getInput();
            } else {
                Toast.makeText(
                        RegisterActivity.this,
                        "Mohon isi formulir dengan benar",
                        Toast.LENGTH_SHORT
                ).show();
            }
        } else if (v == binding.include.ivBackButton) {
            Toast.makeText(RegisterActivity.this, "Pressed Back Button", Toast.LENGTH_SHORT).show();
        } else if (v == binding.ivTogglePassword) {
            if (binding.etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        } else if (v == binding.ivToggleRetypePassword) {
            if (binding.etRetypePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                binding.etRetypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.etRetypePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}