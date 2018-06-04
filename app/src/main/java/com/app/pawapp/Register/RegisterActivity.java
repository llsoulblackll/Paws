package com.app.pawapp.Register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.DistrictDao;
import com.app.pawapp.DataAccess.DataAccessObject.SurveyDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.District;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.R;
import com.app.pawapp.Survey.SurveyActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    public static final String OWNER_KEY = "OwnerKey";

    private DistrictDao districtDao;
    private SurveyDao surveyDao;

    private Button birthdate;
    private TextInputEditText userEditText;
    private TextInputEditText passEditText;
    private TextInputEditText nameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText dniEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText addressEditText;
    private EditText Birth;
    private DatePickerDialog datePickerDialog;
    private MaterialBetterSpinner materialDesignSpinner;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;

    private List<District> districts;
    private District selectedDistrict = null;

    //String[] districts = {"Ate","Breña","Cercado de Lima","San Isidro","Surquillos","Villa el Salvador"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Datos Personales");

        districtDao = DaoFactory.getDistrictDao(this);
        surveyDao = DaoFactory.getSurveyDao(this);

        userEditText = findViewById(R.id.Username);
        passEditText = findViewById(R.id.Password);
        nameEditText = findViewById(R.id.Name);
        lastNameEditText = findViewById(R.id.LastName);
        dniEditText = findViewById(R.id.Dni);
        phoneEditText = findViewById(R.id.Phone);
        emailEditText = findViewById(R.id.Email);
        addressEditText = findViewById(R.id.Address);
        birthdate = findViewById(R.id.btnBirthDate);
        Birth = findViewById(R.id.BirthDate);
        materialDesignSpinner = findViewById(R.id.spnDistricts);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                String format = "%d / %d / %d";
                                if(day < 10 && month < 10)
                                    format = "0%d / 0%d / %d";
                                else if(day > 10 && month < 10)
                                    format = "%d / 0%d / %d";
                                else if(day < 10 && month > 10)
                                    format = "0%d / %d / %d";

                                Birth.setText(String.format(Locale.getDefault(),
                                        format, day, month + 1, year));

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        districtDao.findAll(new Ws.WsCallback<List<District>>() {
            @Override
            public void execute(List<District> response) {
                districts = response;
                String[] diss = new String[response.size()];

                for (int i = 0; i < response.size(); i++)
                    diss[i] = response.get(i).getName();

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        diss);
                materialDesignSpinner.setAdapter(arrayAdapter);

                materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedDistrict = districts.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Next(View view) throws ParseException {

        Owner owner = new Owner();
        owner.setUsername(userEditText.getText().toString());
        owner.setPassword(passEditText.getText().toString());
        owner.setName(nameEditText.getText().toString());
        owner.setLastName(lastNameEditText.getText().toString());
        owner.setBirthDate(!Birth.getText().toString().isEmpty() ? new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .parse(Birth.getText().toString()) : new Date());
        owner.setDNI(dniEditText.getText().toString());
        owner.seteMail(emailEditText.getText().toString());
        owner.setAddress(addressEditText.getText().toString());
        owner.setPhoneNumber(phoneEditText.getText().toString());
        owner.setDistrictId(selectedDistrict != null ? selectedDistrict.getId() : 0);

        Intent i = new Intent(this, SurveyActivity.class);
        i.putExtra(OWNER_KEY, owner);
        startActivity(i);
    }
}