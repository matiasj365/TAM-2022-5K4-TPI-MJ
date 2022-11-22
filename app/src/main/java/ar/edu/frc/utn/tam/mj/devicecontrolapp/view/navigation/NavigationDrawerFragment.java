package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;

public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    NavigationViewModel apiNavigationViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        apiNavigationViewModel = new ViewModelProvider(this, new NavigationViewModelFactory())
                .get(NavigationViewModel.class);
        apiNavigationViewModel.getDevices().observe(this.getActivity(), navigationView -> {
            if (navigationView == null) {
                return;
            }
            apiAdapter.setmDataList(navigationView);
        });

        setUpRecyclerView(view);
        return view;
    }

    NavigationDrawerAdapter apiAdapter;

    private void setUpRecyclerView(View view) {
        RecyclerView apiRecyclerView = view.findViewById(R.id.apiDeviceList);
        apiAdapter = new NavigationDrawerAdapter(getActivity());
        apiRecyclerView.setAdapter(apiAdapter);
        apiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            loadApiDevices();
        } catch (Exception ex) {
            Log.v(NavigationDrawerFragment.class.toString(), "", ex);
        }
    }

    private void loadApiDevices() throws DeviceHandlerException {
        apiNavigationViewModel.listDevices((Application) getActivity().getApplicationContext(),
                DeviceConstants.SERVICE_MODE_API);
    }


    public void setUpDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                // Do something of Slide of Drawer
            }
        };
        // this drawer layout is linked with ActionBarDrawerToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // sync the state of Navigation Drawer with the help of Runnable
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}