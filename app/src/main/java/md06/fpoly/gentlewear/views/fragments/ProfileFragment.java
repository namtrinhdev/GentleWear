package md06.fpoly.gentlewear.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.InformationActivity;
import md06.fpoly.gentlewear.activitys.Login_Activity;
import md06.fpoly.gentlewear.activitys.QLDH_Activity;
import md06.fpoly.gentlewear.activitys.QLDonNapActivity;
import md06.fpoly.gentlewear.activitys.ViMoneyActivity;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.Cart2;


public class ProfileFragment extends Fragment {
    private SessionManager sessionManager;
    private TextView tv_name, tv_email;
    private ImageView img;

    private CardView btn_your_wallet;
    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img = view.findViewById(R.id.img_avatar);
        tv_name = view.findViewById(R.id.tv_nameUser_account);
        tv_email = view.findViewById(R.id.tv_email_account);
//        btn_your_wallet = view.findViewById(R.id.id_your_wallet);

        //khoitao
        sessionManager = new SessionManager(getActivity());

        //set info user
        setInfoUser();
        //set visibility

        // ql don hang
        view.findViewById(R.id.id_qldh).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), QLDH_Activity.class));
        });

        // vi cua ban
//        btn_your_wallet.setOnClickListener(v -> {
//            startActivity(new Intent(getActivity(), ViMoneyActivity.class));
//        });

        // thong tin ca nhan
        view.findViewById(R.id.id_thong_tin_ca_nhan).setOnClickListener(v ->{
            startActivity(new Intent(getActivity(), InformationActivity.class));
        });

        //dang xuat
        view.findViewById(R.id.id_logout).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Login_Activity.class));
            sessionManager.logoutUser();
            Cart2.getInstance().clear();
            getActivity().finish();
        });
        view.findViewById(R.id.id_login).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Login_Activity.class));
            sessionManager.logoutUser();
            Cart2.getInstance().clear();
            getActivity().finish();
        });
    }


    private void setInfoUser() {
        if (!sessionManager.getAvatar().equals("")){
            String url = APIClass.URL+"uploads/"+sessionManager.getAvatar();
            Glide.with(getActivity()).load(url).placeholder(R.drawable.img_default_user).into(img);
        }
        tv_name.setText(sessionManager.getFullName());
        tv_email.setText(sessionManager.getEmail());
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfoUser();
    }
}