package com.example.youtubeapp.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.youtubeapp.ActivitySetting;
import com.example.youtubeapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentBtSheetUser extends BottomSheetDialogFragment {

    private ConstraintLayout clContains;
    private ImageView ivSetting;
    private ImageView ivHelp;

    private TextView tvHelp, tvSettings;

    private TextView tvPrivacy, tvTerm;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user, null);
        mapping(view);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View)view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSettingUser();
            }
        });

        ivHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHelpUser();
            }
        });

        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSettingUser();
            }
        });

        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHelpUser();
            }
        });

        tvTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/t/terms")));
            }
        });

        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://policies.google.com/privacy")));
            }
        });

        return bottomSheetDialog;
    }

    public void mapping(@NonNull View view){
        clContains = view.findViewById(R.id.cl_contains_dialog_user);
        ivHelp = view.findViewById(R.id.iv_help_user);
        ivSetting = view.findViewById(R.id.iv_setting_user);
        tvHelp = view.findViewById(R.id.tv_help_user);
        tvSettings = view.findViewById(R.id.tv_setting_user);
        tvTerm = view.findViewById(R.id.tv_terms_user);
        tvPrivacy = view.findViewById(R.id.tv_privacy_policy);
    }

    public void onClickSettingUser(){
        startActivity(new Intent(getContext(), ActivitySetting.class));
    }

    public void onClickHelpUser(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "trinhmanhbkit@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Youtube");

        startActivity(Intent.createChooser(intent, "Send..."));
//        Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
//        feedbackEmail.setType("email");
//        feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"trinhmanhdiv@gmail.com"});
//        feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback Youtube");
//        startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
    }
}
