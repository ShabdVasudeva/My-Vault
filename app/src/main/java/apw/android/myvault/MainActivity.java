package apw.android.myvault;

import android.annotation.SuppressLint;
import android.content.Context;
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
        binding.bottomLayout.getNavKey().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Passwords selected", Toast.LENGTH_SHORT).show();
            binding.viewPager.setCurrentItem(0);
            binding.bottomLayout.selectTab(Tab.PASSWORD);
        });
        binding.bottomLayout.getNavLink().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Links selected", Toast.LENGTH_SHORT).show();
            binding.viewPager.setCurrentItem(1);
            binding.bottomLayout.selectTab(Tab.LINKS);
        });
        binding.bottomLayout.getNavSettings().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_SHORT).show();
            binding.viewPager.setCurrentItem(2);
            binding.bottomLayout.selectTab(Tab.SETTINGS);
        });
        if (binding.bottomLayout.getCurrentField() == Tab.LINKS){
            binding.toolbar.setAddClickButtonListener(() -> showAddBottomSheet(this, Tab.LINKS));
        }
    }

    @SuppressLint("SetTextI18n")
    public void showAddBottomSheet(Context context, Tab field) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.add_new_dialog, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);

        EditText input = view.findViewById(R.id.input_url);
        MaterialButton saveButton = view.findViewById(R.id.save_button);
        TextView title = view.findViewById(R.id.dialog_title);

        switch (field) {
            case LINKS -> {
                title.setText("Add Link");
                input.setHint("www.abcd.com");
            }
            case PASSWORD -> {
                title.setText("Add Password");
                input.setHint("********");
            }
        }

        saveButton.setOnClickListener(v -> {
            String url = input.getText().toString().trim();
            if (!url.isEmpty()) {
                LinksDAO dao = new LinksDAO(context);
                dao.insertEncryptedLink(String.valueOf(input.getText()), getCurrentTime());
                adapter.getLinksFragment().refresh();
                bottomSheetDialog.dismiss();
            } else {
                Toast.makeText(context, "An error occured...", Toast.LENGTH_SHORT).show();
            }
        });

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