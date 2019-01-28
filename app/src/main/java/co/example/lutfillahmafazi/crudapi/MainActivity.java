package co.example.lutfillahmafazi.crudapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.example.lutfillahmafazi.crudapi.adapter.UserAdapter;
import co.example.lutfillahmafazi.crudapi.api.ApiClient;
import co.example.lutfillahmafazi.crudapi.api.ApiInterface;
import co.example.lutfillahmafazi.crudapi.model.UserData;
import co.example.lutfillahmafazi.crudapi.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvUser)
    RecyclerView rvUser;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    // TODO 1 Membuat variable yg dibutuhkan
    // Membuat variable List
    private List<UserData> userDataList;
    private ApiInterface apiInterfaced;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Membuat object list
        userDataList = new ArrayList<>();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        showProgress();
        // mengambil data dari rest Api
        getData();
    }

    private void getData() {
        // Membuat object api Interface
        apiInterfaced = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiInterfaced.getUser(12);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                // Respon sukses, hilangkan progress dialog
                progressDialog.dismiss();
                // Menghilangkan icon refresh
                swipeRefresh.setRefreshing(false);

                // Mengambil body object utk merespon
                UserResponse userResponse = response.body();
                // Mengabil json aray dgn nama data
                userDataList = userResponse.getData();

                // Mensetting layout recyclerview
                rvUser.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvUser.setAdapter(new UserAdapter(MainActivity.this, userDataList));
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showProgress() {
        // Membuat object progress dailog untuk  kita gunakan
        progressDialog = new ProgressDialog(MainActivity.this);
        // Mengatur ProgressDialog
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
}
