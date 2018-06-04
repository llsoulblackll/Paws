package com.app.pawapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.DistrictDao;
import com.app.pawapp.DataAccess.DataAccessObject.OwnerDao;
import com.app.pawapp.DataAccess.DataAccessObject.Ws;
import com.app.pawapp.DataAccess.Entity.District;
import com.app.pawapp.DataAccess.Entity.Owner;
import com.app.pawapp.MainActivity;
import com.app.pawapp.R;
import com.app.pawapp.Register.RegisterActivity;
import com.app.pawapp.Util.Util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private OwnerDao ownerDao;

    private TextInputEditText userEditText;
    private TextInputEditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ownerDao = DaoFactory.getOwnerDao(this);

        userEditText = findViewById(R.id.editTextUser);
        passEditText = findViewById(R.id.editTextPass);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        Window window = this.getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void Login(View view) {

        final ProgressDialog pd = ProgressDialog.show(this, "Iniciando sesion", "Espere porfavor");

        ownerDao.login(userEditText.getText().toString(), passEditText.getText().toString(), new Ws.WsCallback<Owner>() {

            @Override
            public void execute(Owner response) {
                if(response != null) {
                    pd.dismiss();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, String.format("Bievenido %s %s", response.getName(), response.getLastName()),
                            Toast.LENGTH_LONG).show();
                } else {
                    pd.dismiss();
                    Util.showAlert("Usuario y/o Contraseña incorrectos", LoginActivity.this);
                }
            }
        });

        pd.show();

    }

    public void Register(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}