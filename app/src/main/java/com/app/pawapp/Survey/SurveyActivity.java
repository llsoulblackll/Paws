package com.app.pawapp.Survey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.OwnerDao;
import com.app.pawapp.DataAccess.DataAccessObject.SurveyDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.DataAccess.Entity.Survey;
import com.app.pawapp.MainActivity;
import com.app.pawapp.R;
import com.app.pawapp.Register.RegisterActivity;

public class SurveyActivity extends AppCompatActivity {

    private RadioButton rbc;
    private RadioButton rbo;
    private RadioButton rbd;

    private RadioGroup homeTypeRadioGroup;
    private RadioGroup peopleAmountRadioGroup;
    private RadioGroup otherPetsRadioGroup;
    private RadioGroup petDescRadioGroup;
    private RadioGroup workTypeRadioGroup;
    private RadioGroup availabilityRadioGroup;

    private Owner ownerToInsert;

    private OwnerDao ownerDao;
    private SurveyDao surveyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setTitle("Completa la encuesta");

        ownerDao = DaoFactory.getOwnerDao(this);
        surveyDao = DaoFactory.getSurveyDao(this);

        ownerToInsert = getIntent().getParcelableExtra(RegisterActivity.OWNER_KEY);

        rbd = findViewById(R.id.rbDog);
        rbc = findViewById(R.id.rbCat);
        rbo = findViewById(R.id.rbOthers);

        homeTypeRadioGroup = findViewById(R.id.rgHomeType);
        peopleAmountRadioGroup = findViewById(R.id.rgPeopleAmount);
        otherPetsRadioGroup = findViewById(R.id.rgOtherPets);
        petDescRadioGroup = findViewById(R.id.rgPetDesc);
        workTypeRadioGroup = findViewById(R.id.rgWorkType);
        availabilityRadioGroup = findViewById(R.id.rgAvailability);

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

    public void noChecked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.noCheck:
                if(checked)
                    rbd.setEnabled(false);
                    rbc.setEnabled(false);
                    rbo.setEnabled(false);
                break;
        }
    }

    public void yesChecked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.yesCheck:
                if(checked)
                    rbd.setEnabled(true);
                    rbc.setEnabled(true);
                    rbo.setEnabled(true);
                break;
        }
    }

    public void End(View view) {

        final Survey survey = new Survey();
        survey.setHomeDescription(((RadioButton)findViewById(homeTypeRadioGroup.getCheckedRadioButtonId())).getText().toString());
        survey.setAmountOfPeople(((RadioButton)findViewById(peopleAmountRadioGroup.getCheckedRadioButtonId())).getText().toString());
        survey.setOtherPets(
                ((RadioButton)findViewById(otherPetsRadioGroup.getCheckedRadioButtonId())).getText().toString().equals("Si")
        );
        survey.setOtherPetsDescription(((RadioButton)findViewById(petDescRadioGroup.getCheckedRadioButtonId())).getText().toString());
        survey.setWorkType(((RadioButton)findViewById(workTypeRadioGroup.getCheckedRadioButtonId())).getText().toString());
        survey.setAvailability(((RadioButton)findViewById(availabilityRadioGroup.getCheckedRadioButtonId())).getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere");
        progressDialog.setMessage("Registrando");

        ownerDao.insert(ownerToInsert, new Ws.WsCallback<Object>() {
            @Override
            public void execute(Object response) {
                surveyDao.insert(survey, new Ws.WsCallback<Object>() {
                    @Override
                    public void execute(Object response) {
                        progressDialog.dismiss();
                        Intent i = new Intent(SurveyActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                });
            }
        });

        progressDialog.show();
    }
}