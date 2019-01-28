package co.example.lutfillahmafazi.crudapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.example.lutfillahmafazi.crudapi.api.ApiClient;
import co.example.lutfillahmafazi.crudapi.api.ApiInterface;
import co.example.lutfillahmafazi.crudapi.model.LoginBody;
import co.example.lutfillahmafazi.crudapi.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    // Mebuat variable yang dibutuhkna
    // membuat variable untuk animasi loading menggunakan loading progress dialog
    private ProgressDialog progressDialog;
    private LoginBody loginBody;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {

        showPorgress();
        getData();
        login();

    }

    private void login() {

        progressDialog.dismiss();

        // Membuat Object Api Interface
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.postLogin(loginBody);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
              if (response.body().getError() != null && response.body().getToken() !=null){
                  Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();

              }else {
                  Toast.makeText(LoginActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
              }
              // Menampilkan response messege
                Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();

              startActivity(new Intent(getBaseContext(), MainActivity.class));
              finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        // Membuat Object Login
        loginBody = new LoginBody();
        // Mengisi Login body
        loginBody.setEmail(edtEmail.getText().toString());
        loginBody.setPassword(edtPassword.getText().toString());
    }

    private void showPorgress() {
        // Membuat object Progress Dialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        // Membuat messege pada dialog
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Harap tunggu");
        progressDialog.setMessage("Loading...");
        // Menampilkan ProgressDialog
        progressDialog.show();
    }
}
