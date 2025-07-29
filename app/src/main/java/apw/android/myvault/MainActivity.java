package apw.android.myvault;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import apw.android.myvault.components.ViewPagerAdapter;
import apw.android.myvault.database.LinksDAO;
import apw.android.myvault.database.PasswordsDAO;
import apw.android.myvault.databinding.ActivityMainBinding;
import apw.android.myvault.enums.Tab;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_AUTHENTICATION = 1001;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        adapter = new ViewPagerAdapter(this);
        setContentView(binding.getRoot());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
        binding.bottomLayout.selectTab(Tab.LINKS);
        binding.viewPager.setCurrentItem(1);
        binding.bottomLayout.getNavKey().setOnClickListener(v -> authenticateWithKeyguard());
        binding.bottomLayout.getNavLink().setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(1);
            binding.bottomLayout.selectTab(Tab.LINKS);
        });
        binding.bottomLayout.getNavSettings().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_SHORT).show();
            binding.viewPager.setCurrentItem(2);
            binding.bottomLayout.selectTab(Tab.SETTINGS);
        });
        binding.toolbar.setAddClickButtonListener(() -> showAddBottomSheet(this, binding.bottomLayout.getCurrentField()));
    }

    @SuppressWarnings("deprecation")
    private void authenticateWithKeyguard(){
        var keyGuardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyGuardManager.isKeyguardSecure()) {
            var intent = keyGuardManager.createConfirmDeviceCredentialIntent(
                    "Authentication Required",
                    "Please authenticate to access passwords"
            );
            startActivityForResult(intent, REQUEST_CODE_AUTHENTICATION);
        } else {
            Toast.makeText(this, "No lock screen security set up, please set up lock screen password to maintain the security of your passwords...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AUTHENTICATION) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                binding.viewPager.setCurrentItem(0);
                binding.bottomLayout.selectTab(Tab.PASSWORD);
            } else {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                binding.viewPager.setCurrentItem(1);
                binding.bottomLayout.selectTab(Tab.LINKS);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    public void showAddBottomSheet(Context context, Tab field) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.add_new_dialog, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        EditText input = view.findViewById(R.id.input_url);
        EditText inputTitle = view.findViewById(R.id.input_title);
        EditText inputUsername = view.findViewById(R.id.input_username);
        MaterialButton saveButton = view.findViewById(R.id.save_button);
        TextView title = view.findViewById(R.id.dialog_title);
        inputTitle.setVisibility(View.GONE);
        inputUsername.setVisibility(View.GONE);
        switch (field) {
            case LINKS -> {
                inputTitle.setVisibility(View.GONE);
                inputUsername.setVisibility(View.GONE);
                title.setText("Add Link");
                input.setHint("www.abcd.com");
                saveButton.setOnClickListener(v -> {
                    String url = input.getText().toString().trim();
                    if (!url.isEmpty()) {
                        LinksDAO dao = new LinksDAO(context);
                        dao.insertEncryptedLink(url, getCurrentTime());
                        adapter.getLinksFragment().refresh();
                        bottomSheetDialog.dismiss();
                    } else {
                        Toast.makeText(context, "An error occured...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            case PASSWORD -> {
                inputTitle.setVisibility(View.VISIBLE);
                inputUsername.setVisibility(View.VISIBLE);
                title.setText("Add Password");
                inputUsername.setHint("username");
                input.setHint("password");
                inputTitle.setHint("instagram/github/facebook/etc.");
                saveButton.setOnClickListener(v -> {
                    String password = input.getText().toString().trim();
                    if (!password.isEmpty()) {
                        PasswordsDAO dao = new PasswordsDAO(context);
                        dao.insertEncryptedPassword(String.valueOf(inputTitle.getText()), String.valueOf(inputUsername.getText()), String.valueOf(input.getText()));
                        adapter.getPasswordsFragment().refresh();
                        bottomSheetDialog.dismiss();
                    } else {
                        Toast.makeText(context, "An error occured...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        bottomSheetDialog.show();
    }

    @NotNull
    private static String getCurrentTime() {
        return new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean setTranslucent(boolean translucent) {
        return super.setTranslucent(translucent);
    }
}