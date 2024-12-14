package junior.correa.nascimento.rubens.galeriapublica.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import junior.correa.nascimento.rubens.galeriapublica.R;
import junior.correa.nascimento.rubens.galeriapublica.fragments.GridViewFragment;
import junior.correa.nascimento.rubens.galeriapublica.fragments.ListViewFragment;
import junior.correa.nascimento.rubens.galeriapublica.model.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_REQUEST_PERMISSION = 2;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);

        bottomNavigationView = findViewById(R.id.btNav);
        bottomNavigationView.setOnItemSelectedListener((MenuItem item) -> {
            vm.setNavigationOpSelected(item.getItemId());
            System.out.println(item.getItemId());
            System.out.println(R.id.gridViewOp);
            System.out.println(R.id.listViewOp);
            if(item.getItemId() == R.id.gridViewOp){
                System.out.println("Renderizando gridViewFragment");
                GridViewFragment gridViewFragment = GridViewFragment.newInstance(MainActivity.this);
                setFragment(gridViewFragment);
            } else if (item.getItemId() == R.id.listViewOp) {
                System.out.println("Renderizando ListViewFragment");
                ListViewFragment listViewFragment = ListViewFragment.newInstance();
                setFragment(listViewFragment);
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkForPermissions(permissions);
    }

    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();
        for (String permission : permissions) {
            if (hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }
        if (!permissionsNotGranted.isEmpty()) {
            requestPermissions(permissionsNotGranted.toArray(new String[0]), RESULT_REQUEST_PERMISSION);
        }
        else {
            MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
            int navigationOpSelected = vm.getNavigationOpSelected();
            bottomNavigationView.setSelectedItemId(navigationOpSelected);
        }
    }

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final List<String> permissionsRejected = new ArrayList<>();
        if (requestCode == RESULT_REQUEST_PERMISSION) {
            for (String permission : permissions) {
                if (hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }
        if (!permissionsRejected.isEmpty()) {
            if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                new AlertDialog.Builder(MainActivity.this).
                        setMessage("Para usar essa app é preciso conceder essas permissões").
                        setPositiveButton("OK", (dialog, which) -> requestPermissions(permissionsRejected.toArray(new String[0]), RESULT_REQUEST_PERMISSION)).create().show();
            }
        }
        else {
            MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
            int navigationOpSelected = vm.getNavigationOpSelected();
            bottomNavigationView.setSelectedItemId(navigationOpSelected);
        }
    }

    void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}