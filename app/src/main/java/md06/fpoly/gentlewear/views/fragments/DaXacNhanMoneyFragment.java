package md06.fpoly.gentlewear.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.DetailDonNapActivity;
import md06.fpoly.gentlewear.apiServices.ChangeStatusDonNapInterface;
import md06.fpoly.gentlewear.apiServices.NapTienInterface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.controller.Adapter.QL_Don_Nap_Adapter;
import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.models.NapTien;
import md06.fpoly.gentlewear.models.ThanhToan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaXacNhanMoneyFragment extends Fragment {
    private ArrayList<NapTien> arrayList = new ArrayList<>();
    private QL_Don_Nap_Adapter adapter;
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private NapTienInterface mInterface;
    private final String TAG = "ConfirmedFrg";

    public DaXacNhanMoneyFragment() {
        // Required empty public constructor
    }


    public static DaXacNhanMoneyFragment newInstance() {
        DaXacNhanMoneyFragment fragment = new DaXacNhanMoneyFragment();
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
        return inflater.inflate(R.layout.fragment_da_xac_nhan_money, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.id_recycle_daXacNhanMoney);

        sessionManager = new SessionManager(getContext());
        mInterface = RetrofitClientAPI.getRetrofitInstance().create(NapTienInterface.class);
        adapter = new QL_Don_Nap_Adapter(getActivity(), new ChangeStatusDonNapInterface() {
            @Override
            public void onChange(String id) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String thoiGian = sdf.format(new Date());
                Call<Messages> callPut = mInterface.putRequestNapTien(id,sessionManager.getVaiTro(),thoiGian);
                callPut.enqueue(new Callback<Messages>() {
                    @Override
                    public void onResponse(Call<Messages> call, Response<Messages> response) {
                        if (response.isSuccessful()){
                            Messages msg = response.body();
                            Toast.makeText(getActivity(), msg.getMsg(), Toast.LENGTH_SHORT).show();
                            onResume();
                        }else {
                            Log.e(TAG, "onResponse error: not successful" );
                        }
                    }

                    @Override
                    public void onFailure(Call<Messages> call, Throwable t) {
                        Log.e(TAG, "onFailure: ",t);
                    }
                });
            }

            @Override
            public void openDetail(ThanhToan item) {

            }

            @Override
            public void openDetailMoney(NapTien item) {
                Intent i = new Intent(getActivity(), DetailDonNapActivity.class);
                i.putExtra("naptien",item);
                startActivity(i);
            }
        });
        loadData();
    }

    private void loadData() {
        Call<List<NapTien>> call;
        if (sessionManager.getVaiTro() == 0) {
            //admin
            call = mInterface.getAllDonDaXacNhan();
            call.enqueue(new Callback<List<NapTien>>() {
                @Override
                public void onResponse(Call<List<NapTien>> call, Response<List<NapTien>> response) {
                    if (response.isSuccessful()) {
                        List<NapTien> listNaptien = response.body();
                        arrayList = (ArrayList<NapTien>) listNaptien;
                        adapter.setData(arrayList);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<List<NapTien>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        } else {
            //user
            String idUser = sessionManager.getIdUser();
            call = mInterface.getAllDonDaXacNhanById(idUser);
            call.enqueue(new Callback<List<NapTien>>() {
                @Override
                public void onResponse(Call<List<NapTien>> call, Response<List<NapTien>> response) {
                    if (response.isSuccessful()) {
                        List<NapTien> listNaptien = response.body();
                        arrayList = (ArrayList<NapTien>) listNaptien;
                        adapter.setData(arrayList);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<List<NapTien>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ",t );
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        arrayList.clear();
        loadData();
    }
}