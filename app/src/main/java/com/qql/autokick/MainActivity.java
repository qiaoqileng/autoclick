package com.qql.autokick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.qql.autokick.databinding.ActivityMainBinding;
import com.qql.autokick.utils.OsUtils;
import com.qql.autokick.utils.ThreadUtil;
import com.qql.autokick.utils.ToastHelper;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initListener();
    }

    private void initListener() {
        binding.btnTest.setOnClickListener(v -> {
            String cmd = binding.tvCmd.getText().toString().trim();
            if (!TextUtils.isEmpty(cmd)){
                ThreadUtil.executThread(() -> {
                    String result = OsUtils.excCommand(cmd);
                    runOnUiThread(() -> binding.tvResult.setText(result));
                });
            } else {
                ToastHelper.showToast(MainActivity.this,"cmd is empty");
            }
        });
        binding.btnRoot.setOnClickListener(v->{
            String cmd = "su root";
            String result = OsUtils.excCommand(cmd);
            binding.tvResult.setText(result);
        });
        binding.btnTestPdd.setOnClickListener(v->{
            String cmd = "am instrument -w -r -e class com.example.android.testing.uiautomator.BasicSample.PddTest#autoScroll com.example.android.testing.uiautomator.BasicSample.test/androidx.test.runner.AndroidJUnitRunner";
            String result = OsUtils.excCommand(cmd);
            binding.tvResult.setText(cmd);
            binding.tvResult.append(result);
        });
    }
}