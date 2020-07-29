package com.example.courierapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.courierapp.R;


public class UserProfileFragment extends Fragment {

    RelativeLayout userNameBlock,mobileNumberBlock,emailBlock,passwordBlock;
    String getMobileNumberFromServer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user_profile, container, false);
        userNameBlock=(RelativeLayout)view.findViewById(R.id.userNameBlock);
        mobileNumberBlock=(RelativeLayout)view.findViewById(R.id.mobileNumberBlock);
        emailBlock=(RelativeLayout)view.findViewById(R.id.emailBlock);
        passwordBlock=(RelativeLayout)view.findViewById(R.id.passwordBlock);

        userNameBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Enter Your Name");
            }
        });
        mobileNumberBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Enter Mobile Number");
            }
        });

        emailBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog("Enter Email");
            }
        });

        passwordBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Enter Password");
            }
        });

        return view;
    }




 private void openDialog(final String hintData) {

     // String toUpdate=new LoaderUtil().showAlertDialog(getActivity());

     //System.out.println("ToUpdate"+toUpdate);


     AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.layout_dialog_userprofile,null );

        builder.setView(view);

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText getUserName=view.findViewById(R.id.updateName);
                getUserName.setHint(hintData);
                Toast.makeText(getActivity(),"Updated Successfully", Toast.LENGTH_LONG).show();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

}