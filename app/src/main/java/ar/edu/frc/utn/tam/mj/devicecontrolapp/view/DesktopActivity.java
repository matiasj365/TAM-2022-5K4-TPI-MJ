package ar.edu.frc.utn.tam.mj.devicecontrolapp.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.databinding.ActivityMainBinding;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.AppDatabase;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.deviceevent.DeviceEventFragment;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation.NavigationDrawerFragment;
import lombok.NonNull;

public class DesktopActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppDatabase.getDatabase(this).loadInitialData();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drwr_fragment);
        drawerLayout = binding.drawerLayout;
        drawerFragment.setUpDrawer(R.id.nav_drwr_fragment, drawerLayout, binding.appBarMain.toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        checkForSmsReceivePermissions();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_events:
                Fragment f = new DeviceEventFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_main, f);
                transaction.commit();
                //drawerLayout.closeDrawers();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void closeDrawers() {
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.desktop, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void checkForSmsReceivePermissions(){
        // Check if App already has permissions for receiving SMS
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.RECEIVE_SMS") == PackageManager.PERMISSION_GRANTED) {
            // App has permissions to listen incoming SMS messages
            Log.d("adnan", "checkForSmsReceivePermissions: Allowed");
        } else {
            // App don't have permissions to listen incoming SMS messages
            Log.d("adnan", "checkForSmsReceivePermissions: Denied");

            // Request permissions from user
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS}, 43391);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 43391){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("adnan", "Sms Receive Permissions granted");
            } else {
                Log.d("adnan", "Sms Receive Permissions denied");
            }
        }
    }
}