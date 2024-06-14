package ai.ftech.fstt;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ai.ftech.fsttsdk.domain.exceptions.AppException;
import ai.ftech.fsttsdk.sdk.ISTTCallback;
import ai.ftech.fsttsdk.sdk.STTManager;

public class TestActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        STTManager.init(getApplicationContext());

        STT

        STTManager.registerSTTCallback(new ISTTCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onRecording() {

            }

            @Override
            public void onFail(@Nullable AppException error) {

            }

            @Override
            public void onSuccess(@NonNull String result) {

            }
        });
    }
}
