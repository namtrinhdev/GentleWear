package md06.fpoly.gentlewear.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.SearchActivity;
import md06.fpoly.gentlewear.activitys.SplashActivity;
import md06.fpoly.gentlewear.classs.SessionManager;


public class ProfileFragment extends Fragment {
    private SessionManager sessionManager;
    FrameLayout btn_logout;
    ImageView img_pprofile;
    TextView tv_name_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_logout = view.findViewById(R.id.btn_logout);
        img_pprofile = view.findViewById(R.id.img_profile);
        tv_name_profile = view.findViewById(R.id.tv_name_Profile);


        // Load avatar image
        sessionManager = new SessionManager(getContext());
        if (!sessionManager.getAvatar().equals("")) {
            Glide.with(this).load(sessionManager.getAvatar()).apply(RequestOptions.centerCropTransform()).into(img_pprofile);

        }
        tv_name_profile.setText(sessionManager.getFullName());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SplashActivity.class);
                startActivity(intent);
            }
        });
    }
}