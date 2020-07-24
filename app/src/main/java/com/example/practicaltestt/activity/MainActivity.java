package com.example.practicaltestt.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.practicaltestt.R;
import com.example.practicaltestt.adapter.UserListAdapter;
import com.example.practicaltestt.model.UserInfo;
import com.example.practicaltestt.provider.RetrofitClientInstance;
import com.example.practicaltestt.wsinterface.GetDataService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMain;
    LinearLayoutManager rvLayout;
    ProgressDialog progressDoalog;
    int PAGE_SIZE = 10;
    boolean isLoading, isLastPage;
    int currentPage;
    UserListAdapter userListAdapter;
    ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = findViewById(R.id.activity_main_rv);
        rvLayout = new LinearLayoutManager(this);
        rvMain.setLayoutManager(rvLayout);
        userListAdapter = new UserListAdapter(MainActivity.this, userInfoArrayList);
        rvMain.setAdapter(userListAdapter);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        getUserInfo();

        rvMain.addOnScrollListener(new PaginationScrollListener(rvLayout) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                Log.i("MainActivity", "isLoading? " + isLoading + " currentPage " + currentPage);
                getUserInfo();
            }

            @Override
            public int getTotalPageCount() {
                return PAGE_SIZE;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }


    /*
     * This method will display user Info
     * */
    private void getUserInfo() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.getUserInfoList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody res = response.body();
                try {
                    if (res != null) {
                        progressDoalog.dismiss();
                        String msg = res.string();
                        JSONObject mainObj = new JSONObject(msg);
                        if (mainObj.getBoolean("status")) {
                            Gson gson = new Gson();
                            JSONObject userListObj = mainObj.getJSONObject("data");
                            JSONArray userListArray = userListObj.getJSONArray("users");
                            Type listType = new TypeToken<ArrayList<UserInfo>>() {
                            }.getType();
                            ArrayList<UserInfo> infoArrayList = gson.fromJson(userListArray.toString(), listType);
                            userInfoArrayList.addAll(infoArrayList);
                            userListAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "error : " + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
        private final String TAG = PaginationScrollListener.class.getSimpleName();
        private LinearLayoutManager layoutManager;

        protected PaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /*
         Method gets callback when user scroll the search list
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (!isLoading() && !isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    Log.i(TAG, "Loading more items");
                    loadMoreItems();
                }
            }

        }

        protected abstract void loadMoreItems();

        //optional
        public abstract int getTotalPageCount();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();
    }


}