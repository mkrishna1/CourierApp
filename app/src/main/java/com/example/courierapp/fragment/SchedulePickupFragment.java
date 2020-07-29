package com.example.courierapp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.courierapp.R;


public class SchedulePickupFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner servSpinPickUp, itemSpinPickUp, weightSpinPickUp;

    EditText companyNameShipper, addressShipper, contactNameShipper, contactNumberShipper, emailShipper;

    EditText companyNameReceiver, addressReceiver, contactNameReceiver, contactNumberReceiver, emailReceiver;

    EditText instruction;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedulepickup, null);

        companyNameShipper = (EditText) view.findViewById(R.id.shipper_edit_companyName);
        addressShipper = (EditText) view.findViewById(R.id.shipper_edit_companyAddr);
        contactNameShipper = (EditText) view.findViewById(R.id.shipper_edit_contactName);
        contactNumberShipper = (EditText) view.findViewById(R.id.shipper_edit_contactNumber);
        emailShipper = (EditText) view.findViewById(R.id.shipper_edit_Email);

        companyNameReceiver = (EditText) view.findViewById(R.id.receiver_edit_companyName);
        addressReceiver = (EditText) view.findViewById(R.id.receiver_edit_companyAddr);
        contactNameReceiver = (EditText) view.findViewById(R.id.receiver_edit_contactName);
        contactNumberReceiver = (EditText) view.findViewById(R.id.receiver_edit_contactNumber);
        emailReceiver = (EditText) view.findViewById(R.id.receiver_edit_Email);

        instruction = (EditText) view.findViewById(R.id.instr_edit_rider);

        servSpinPickUp = (Spinner) view.findViewById(R.id.selectServ_schePick);
        itemSpinPickUp = (Spinner) view.findViewById(R.id.itemDetails_schePick);
        weightSpinPickUp = (Spinner) view.findViewById(R.id.itemWeight_schePick);


        ArrayAdapter<CharSequence> serviceAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.SelectService, android.R.layout.simple_spinner_item);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servSpinPickUp.setAdapter(serviceAdapter);


        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.SelectItem, android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinPickUp.setAdapter(itemAdapter);


        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.SelectWeight, android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinPickUp.setAdapter(weightAdapter);


        servSpinPickUp.setOnItemSelectedListener(this);
        itemSpinPickUp.setOnItemSelectedListener(this);
        weightSpinPickUp.setOnItemSelectedListener(this);


        companyNameShipper.addTextChangedListener(new MyTextWatcher(companyNameShipper));


        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.selectServ_schePick:
                String serviceSelected = parent.getItemAtPosition(position).toString();
                System.out.println("ItemSelected" + serviceSelected);
                break;

            case R.id.itemDetails_schePick:
                String itemSelected = parent.getItemAtPosition(position).toString();
                System.out.println("ItemSelected1" + itemSelected);
                break;

            case R.id.itemWeight_schePick:
                String weightSelected = parent.getItemAtPosition(position).toString();
                System.out.println("ItemSelected1" + weightSelected);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            String mobile = companyNameShipper.getText().toString().trim();


            // btnSignIn.setEnabled(validateMobile(mobile) && validatePassword(password));

            /*if (btnSignIn.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignIn.setBackground(getDrawable(R.drawable.rectangle_shpae));
                }
            } else if (!btnSignIn.isEnabled()) {
                btnSignIn.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignIn.setBackground(getDrawable(R.color.btn_disable));
                }
            }*/

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
