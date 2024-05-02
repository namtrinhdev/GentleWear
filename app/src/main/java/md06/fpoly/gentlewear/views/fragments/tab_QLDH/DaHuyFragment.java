package md06.fpoly.gentlewear.views.fragments.tab_QLDH;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.DetailOrderActivity;
import md06.fpoly.gentlewear.apiServices.QLDH_Interface;
import md06.fpoly.gentlewear.apiServices.QLDN_API;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_QLDH;
import md06.fpoly.gentlewear.models.ThanhToan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaHuyFragment extends Fragment {
    private QLDN_API api_service;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private Adapter_QLDH adapter;
    private List<ThanhToan> list = new ArrayList<>();
    private static final String TAG = "waitFragment";

    public DaHuyFragment() {
        // Required empty public constructor
    }

    public static DaHuyFragment newInstance() {
        DaHuyFragment fragment = new DaHuyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_da_huy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        khoitao();
        loadList(list);
        loadData();
    }
    private void khoitao() {
        sessionManager = new SessionManager(getContext());
        api_service = RetrofitClientAPI.getRetrofitInstance().create(QLDN_API.class);
    }
    private void init(View view) {
        recyclerView = view.findViewById(R.id.ryc_daHuy);
    }
    private void loadList(List<ThanhToan> list){
        adapter = new Adapter_QLDH(list, new QLDH_Interface() {
            @Override
            public void onReceive(ThanhToan data) {
                Intent intent = new Intent(getActivity(), DetailOrderActivity.class);
                intent.putExtra("order", data);
                startActivity(intent);
            }

            @Override
            public void onCanceled(ThanhToan data1) {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private void loadData(){
        Call<List<ThanhToan>> call = api_service.getDsDaHuy(sessionManager.getIdUser());
        call.enqueue(new Callback<List<ThanhToan>>() {
            @Override
            public void onResponse(Call<List<ThanhToan>> call, Response<List<ThanhToan>> response) {
                if (response.isSuccessful()){
                    List<ThanhToan> mList = response.body();
                    list.clear();
                    list.addAll(mList);
                    adapter.notifyDataSetChanged();
                }else {
                    Log.e(TAG, "Activity is null");
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ThanhToan>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }
}