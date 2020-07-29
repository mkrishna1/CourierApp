package com.example.courierapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courierapp.R;
import com.example.courierapp.model.LoginAuthResponse;
import com.example.courierapp.model.LoginRequest;
import com.example.courierapp.model.LoginResponse;
import com.example.courierapp.retrofit.ApiClient;
import com.example.courierapp.retrofit.ApiInterface;
import com.example.courierapp.utils.AppConstant;
import com.example.courierapp.utils.LoaderUtil;
import com.example.courierapp.utils.MathUtil;
import com.example.courierapp.utils.PreferenceUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.courierapp.utils.AppConstant.RESOLVE_HINT;
import static com.example.courierapp.utils.MathUtil.validateMobile;
import static com.example.courierapp.utils.MathUtil.validatePassword;


public class LoginActivity extends AppCompatActivity {

    private EditText loginMobile, loginPassword;
    private TextInputLayout inputLoginMobile, inputLoginPassword;
    private Button btnSignIn;

    private TextView forgetPassword, loginSignup;

    private GoogleSignInButton gmailSignup;

    private GoogleApiClient mCredentialsApiClient;

    Typeface typeface;
    TextView othersignin;

    Dialog dialog;


/*Dialog showDialog=Utils.showProgressBar(this);
 Utils.dismisProgressBar(getApplicationContext(),showDialog);
 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        typeface = MathUtil.getOctinPrisonFont(LoginActivity.this);

        inputLoginMobile = (TextInputLayout) findViewById(R.id.input_login_mobile);
        inputLoginPassword = (TextInputLayout) findViewById(R.id.input_login_password);

        loginMobile = (EditText) findViewById(R.id.login_mobile);
        loginPassword = (EditText) findViewById(R.id.login_password);

        forgetPassword = (TextView) findViewById(R.id.login_forgetPassword);

        gmailSignup = (GoogleSignInButton) findViewById(R.id.gmailSignup);

        loginSignup = (TextView) findViewById(R.id.loginSignup);
        othersignin = (TextView) findViewById(R.id.othersignin);


        mCredentialsApiClient = new GoogleApiClient.Builder(this)
                /*.addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) SignUpActivity.this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) SignUpActivity.this)*/
                .addApi(Auth.CREDENTIALS_API)
                .setAccountName(IdentityProviders.GOOGLE)
                .build();


        gmailSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHint();
            }
        });

        btnSignIn = (Button) findViewById(R.id.btn_login);

        btnSignIn.setTypeface(typeface);
        othersignin.setTypeface(typeface);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();

            }
        });


        loginMobile.addTextChangedListener(new MyTextWatcher(loginMobile));
        loginPassword.addTextChangedListener(new MyTextWatcher(loginPassword));

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchActivity();
            }
        });

        loginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchSignUpActivity();
            }
        });


    }

    private void loginUser() {

        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        LoginRequest loginRequest = new LoginRequest(loginMobile.getText().toString(), loginPassword.getText().toString());

        Call<LoginAuthResponse> call = apiInterface.doCheckLogin(loginRequest);

        call.enqueue(new Callback<LoginAuthResponse>() {
            @Override
            public void onResponse(Call<LoginAuthResponse> call, Response<LoginAuthResponse> response) {

                LoginAuthResponse loginAuthResponse = response.body();
                if (loginAuthResponse.getAuthToken() != null && loginAuthResponse.getTokenType()!=null) {

                    PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN, loginAuthResponse.getAuthToken());
                    PreferenceUtil.setValueString(LoginActivity.this,PreferenceUtil.BEARER,loginAuthResponse.getTokenType());

                    AppConstant.setAuthToken(PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.BEARER)+" "+PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.AUTH_TOKEN));

                    System.out.println("ReceivedToken"+PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.BEARER)+" "+PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.AUTH_TOKEN));

                    String s=PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.BEARER)+" "+PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.AUTH_TOKEN);

                    Toast.makeText(LoginActivity.this,s, Toast.LENGTH_LONG).show();

                    LoaderUtil.dismisProgressBar(LoginActivity.this,dialog);

                    getUserDetails();



                }


            }

            @Override
            public void onFailure(Call<LoginAuthResponse> call, Throwable t) {

            }
        });

    }

    private void getUserDetails() {
        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        Call<LoginResponse> call=apiInterface.doGetUserDetails(PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.BEARER)+" "+PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.AUTH_TOKEN));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();

                System.out.println("ReceivedUserId"+loginResponse.getUserId());

                LoaderUtil.dismisProgressBar(LoginActivity.this,dialog);


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });



    }


    private void requestHint() {

        mCredentialsApiClient.connect();

        HintRequest hintRequest = new HintRequest.Builder()
                //.setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(true)
                .build();


        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                mCredentialsApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);

                System.out.println("GmailUserID" + credential.getId());
                System.out.println("GmailUser" + credential.getName() + " " + credential.getPassword() + " " + credential.getId() + " " + credential.getIdTokens());

                if (credential.getId() != null) {
                    Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }


    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            String mobile = loginMobile.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            btnSignIn.setEnabled(validateMobile(mobile) && validatePassword(password));

            if (btnSignIn.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignIn.setBackground(getDrawable(R.drawable.rectangle_shpae));
                    btnSignIn.setTextColor(getApplication().getResources().getColor(R.color.white));
                }
            } else if (!btnSignIn.isEnabled()) {
                btnSignIn.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignIn.setBackground(getDrawable(R.color.btn_disable));
                    btnSignIn.setTextColor(R.color.black);
                }
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void launchSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void launchActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

}
