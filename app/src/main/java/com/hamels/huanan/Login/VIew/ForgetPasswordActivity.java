package com.hamels.huanan.Login.VIew;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Login.Contract.ForgetPasswordContract;
import com.hamels.huanan.Login.Presenter.ForgetPasswordPresenter;
import com.hamels.huanan.R;
import com.hamels.huanan.Utils.IntentUtils;

import java.util.Objects;

import static com.hamels.huanan.Constant.Constant.PHONE;

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {
    public static final String TAG = ForgetPasswordActivity.class.getSimpleName();

    private EditText etVerify, etPassword, etPasswordCheck;
    private TextView tvSend;

    private String cellphone = "";
    private ForgetPasswordContract.Presenter forgetPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        cellphone = Objects.requireNonNull(getIntent().getExtras()).getString(PHONE, "");
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.forget_password);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        etVerify = findViewById(R.id.et_verify_code);
        etPassword = findViewById(R.id.et_password);
        etPasswordCheck = findViewById(R.id.et_check_password);
        tvSend = findViewById(R.id.tv_send);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPresenter.checkInputValue(cellphone, etVerify.getText().toString(),
                        etPassword.getText().toString(), etPasswordCheck.getText().toString());
            }
        });

        forgetPresenter = new ForgetPasswordPresenter(this, getRepositoryManager(this));
    }

    @Override
    public void intentToRegister() {
        IntentUtils.intentToRegister(this);
    }

    @Override
    public void showAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showGoRegistAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgetPresenter.register();
                    }
                })
                .show();
    }

    @Override
    public void showSUCCESSToLogin(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishActivity();
                    }
                })
                .show();
    }

    @Override
    public void showEmptyVerifyCodeAlert() {
        showAlert(getString(R.string.empty_verify_code));
    }

    @Override
    public void showPasswordFormatAlert() {
        showAlert(getString(R.string.password_format_error));
    }

    @Override
    public void showPasswordCheckFormatAlert() {
        showAlert(getString(R.string.password_check_format_error));
    }

    @Override
    public void showPasswordDifferentAlert() {
        showAlert(getString(R.string.password_different));
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
