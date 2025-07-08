package apw.android.myvault;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import apw.android.myvault.components.BottomAppBar;
import androidx.appcompat.app.AppCompatActivity;
import apw.android.myvault.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomLayout.selectTab(BottomAppBar.Tab.PASSWORD);
        binding.bottomLayout.getNavKey().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Passwords selected", Toast.LENGTH_SHORT).show();
            binding.bottomLayout.selectTab(BottomAppBar.Tab.PASSWORD);
        });
        binding.bottomLayout.getNavLink().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Links selected", Toast.LENGTH_SHORT).show();
            binding.bottomLayout.selectTab(BottomAppBar.Tab.LINKS);
        });
        binding.bottomLayout.getNavSettings().setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_SHORT).show();
            binding.bottomLayout.selectTab(BottomAppBar.Tab.SETTINGS);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}