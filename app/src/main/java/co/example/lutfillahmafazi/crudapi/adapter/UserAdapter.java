package co.example.lutfillahmafazi.crudapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.example.lutfillahmafazi.crudapi.DetailActivity;
import co.example.lutfillahmafazi.crudapi.R;
import co.example.lutfillahmafazi.crudapi.model.UserData;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<UserData> userDataList;
    private Bundle bundle;

    public UserAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Mengambil data list
        final UserData userData = userDataList.get(i);
        // Menampilkan ke layar
        viewHolder.txtFirstName.setText(userData.getFirstName());
        viewHolder.txtLastName.setText(userData.getLastName());

        //Membuat object RequestOpstion
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);

        Glide.with(context).load(userData.getAvatar()).apply(requestOptions).into(viewHolder.imgAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = userData.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id",id);

                context.startActivity(new Intent(context, DetailActivity.class).putExtras(bundle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.txtFirstName)
        TextView txtFirstName;
        @BindView(R.id.txtLastName)
        TextView txtLastName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
