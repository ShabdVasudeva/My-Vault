package apw.android.myvault;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import apw.android.myvault.components.ViewPagerAdapter;
import apw.android.myvault.databinding.ActivityMainBinding;
import apw.android.myvault.enums.Tab;

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
        binding.bottomLayout.selectTab(Tab.PASSWORD);
        binding.viewPager.setCurrentItem(0);
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