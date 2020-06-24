package com.siskopsya.amm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.siskopsya.amm.app.AppController;
import com.siskopsya.amm.server.Url;
import com.siskopsya.amm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    TextInputEditText edit_email, edit_password;
    TextView appversion;
    ProgressDialog pDialog;
    private String url = Url.URL + "login.php";
    int success;
    ConnectivityManager conMgr;
    private static final String TAG = LoginActivity.class.getSimpleName();
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String string_email, string_id, no_token,db;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btn_login = findViewById(R.id.btn_login);
        edit_email = findViewById(R.id.email_input);
        edit_password = findViewById(R.id.password_input);
        appversion = findViewById(R.id.appversion);

        Bundle cek_data = getIntent().getExtras();
        String version = "1.0";

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appversion.setText("App Version " + version);

        String cek_login = cek_data.getString("CEK_LOGIN");
        if(cek_login.equals("baru")){
            Intent belumLogin = new Intent(LoginActivity.this, Launch.class);
            Toast.makeText(LoginActivity.this, "Harap Login Dahulu!",
                    Toast.LENGTH_LONG).show();
            startActivity(belumLogin);
        }


        //---------- CEK KONEKSI ------------
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            assert conMgr != null;
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        //-------------------------------------
        //-------Cek session login jika TRUE
        // maka langsung buka MainActivity --------------------
        sharedpreferences = getSharedPreferences("siskopsya", Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("session_status", false);
        string_id = sharedpreferences.getString("id", null);
        string_email = sharedpreferences.getString("username", null);
        no_token= sharedpreferences.getString("token", null);
        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", string_id);
            intent.putExtra("username", string_email);
            finish();
            startActivity(intent);
        }
        // ------------------------------
        // ------ BUTTON MASUK KLIK ------------
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ----------- CEK YANG MASIH KOSONG ------
                if (edit_email.getText().toString().trim().length() > 0 && edit_password.getText().toString().trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(edit_email.getText().toString(), edit_password.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    // ------ FUNCTION CEK LOGIN ---------------
    private void checkLogin(final String email, final String password) {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        pDialog.show();
        Log.e(TAG, "Login Response: "+url);
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response string: " + response.toString());
                pDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt("success");
                    String no_anggota = jObj.getString("no_anggota");
                    String no_anggota2 = jObj.getString("no_anggota2");
                    String nama_lengkap = jObj.getString("nama_lengkap");
                    String alamat = jObj.getString("alamat");
                    String kode_login =jObj.getString("kode_login");
                    String db =jObj.getString("db");

                    // Check for error node in json
                    if (success == 1) {
                        Log.e("Successfully Login!", jObj.toString());
                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("session_status", true);
                        editor.putString("no_anggota", no_anggota);
                        editor.putString("no_anggota2", no_anggota2);
                        editor.putString("nama_lengkap", nama_lengkap);
                        editor.putString("alamat", alamat);
                        editor.putString("kode_login", kode_login);
                        editor.putString("db", "yayasan5_"+db);
                        editor.commit();
                        Toast.makeText(getApplicationContext(),
                                "Selamat datang ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    Log.wtf(TAG, e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("no_token", no_token);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "json_obj_req");
    }
    /// -----------------
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
