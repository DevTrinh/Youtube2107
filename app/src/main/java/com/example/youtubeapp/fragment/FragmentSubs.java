package com.example.youtubeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class FragmentSubs extends Fragment implements InterfaceDefaultValue {
    private Button btSignIn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Fragment Subscription", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_subs, container, false);
        mapping(view);

        return view;
    }

    public void mapping(@NonNull View view){
         btSignIn = view.findViewById(R.id.bt_sign_in_subs);
    }
}
