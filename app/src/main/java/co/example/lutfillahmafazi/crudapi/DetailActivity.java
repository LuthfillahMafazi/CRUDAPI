package co.example.lutfillahmafazi.crudapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.example.lutfillahmafazi.crudapi.api.ApiClient;
import co.example.lutfillahmafazi.crudapi.api.ApiInterface;
import co.example.lutfillahmafazi.crudapi.model.Data;
import co.example.lutfillahmafazi.crudapi.model.ResponseDetailUser;
import co.example.lutfillahmafazi.crudapi.model.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtFirstName)
    TextView txtFirstName;
    @BindView(R.id.txtLastName)
    TextView txtLastName;

    Data data;
    private Bundle bundle;
    private int id;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        id = bundle.getInt("id");

        showProgress();
        showDetail();

    }

    private void showDetail() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDetailUser> call = apiInterface.getUserData(id);
        call.enqueue(new Callback<ResponseDetailUser>() {
            @Override
            public void onResponse(Call<ResponseDetailUser> call, Response<ResponseDetailUser> response) {
                progressDialog.dismiss();

                ResponseDetailUser responseDetailUser = response.body();

                data = responseDetailUser.getData();

                txtFirstName.setText(data.getFirstName());
                txtLastName.setText(data.getLastName());
                Glide.with(DetailActivity.this).load(data.getAvatar()).into(imgAvatar);
            }

            @Override
            public void onFailure(Call<ResponseDetailUser> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
