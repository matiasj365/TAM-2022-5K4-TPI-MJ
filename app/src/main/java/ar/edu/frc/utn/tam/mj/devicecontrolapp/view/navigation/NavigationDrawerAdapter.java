package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.Result;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.DesktopActivity;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIUtils;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.device.DeviceFragment;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationDrawerViewHolder> {
    private List<NavigationDrawerItem> mDataList;  // = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context) {
        mDataList=new ArrayList<>();
        inflater = LayoutInflater.from(context);
        this.context = context;
        SharedPreferences sp = UIUtils.getSharedPreferences(context);
        int lastDeviceId = sp.getInt(UIConstants.SP_LAST_DEVICE_ID, 0);
        if (lastDeviceId != 0 && mDataList.size() > 0) {
            for (NavigationDrawerItem dItem : mDataList) {

                if (lastDeviceId != 0 && dItem.getDeviceId() == lastDeviceId) {
                    updateFragmentDesktop(dItem.getDevice());
                }
            }
        }
    }

    public void setmDataList(Result<List<Device>> dataList) {
        if (dataList instanceof Result.Success) {
            List<Device> devices = ((Result.Success<List<Device>>) dataList).getData();
            if(devices==null || devices.size()==0)
                return;
            mDataList.clear();
            for(Device device:devices)
            {
                NavigationDrawerItem item=new NavigationDrawerItem(device);
                mDataList.add(item);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public NavigationDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_list_item, parent, false);
        NavigationDrawerViewHolder holder = new NavigationDrawerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NavigationDrawerViewHolder holder, int position) {
        NavigationDrawerItem current = mDataList.get(position);
        holder.setData(current, position);

        // click listener on RecyclerView items
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = UIUtils.getSharedPreferences(context).edit();
                editor.putInt(UIConstants.SP_LAST_DEVICE_ID, current.getDeviceId());
                editor.commit();
                updateFragmentDesktop(current.getDevice());
            }
        });
    }

    public void updateFragmentDesktop(Device device) {
        DesktopActivity activity = (DesktopActivity) context;
        activity.getSupportActionBar().setTitle(device.getName());
        activity.closeDrawers();

        Bundle bundle = new Bundle();
        bundle.putSerializable(UIConstants.DEVICE, device);
        Fragment fragment = new DeviceFragment();
        fragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_desktop, fragment).addToBackStack(null).commit();

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class NavigationDrawerViewHolder extends RecyclerView.ViewHolder {
        TextView txDeviceName;
        ImageView imgApiDeviceIcon;
        ImageView imgSmsDeviceIcon;
        ImageView imgConnectionStatusIcon;
        ImageView imgPowerStatusIcon;

        public NavigationDrawerViewHolder(View itemView) {
            super(itemView);
            txDeviceName = (TextView) itemView.findViewById(R.id.deviceName);
            imgApiDeviceIcon = (ImageView) itemView.findViewById(R.id.apiDeviceIcon);
            imgSmsDeviceIcon = (ImageView) itemView.findViewById(R.id.smsDeviceIcon);
            imgConnectionStatusIcon = (ImageView) itemView.findViewById(R.id.connectionStatusIcon);
            imgPowerStatusIcon = (ImageView) itemView.findViewById(R.id.powerStatusIcon);
        }

        public void setData(NavigationDrawerItem current, int position) {
            txDeviceName.setText(current.getDeviceName());
            imgApiDeviceIcon.setImageResource(current.getApiImageId());
            imgSmsDeviceIcon.setImageResource(current.getSmsImageId());
            imgConnectionStatusIcon.setImageResource(current.getConnectionStatusIcon());
            imgPowerStatusIcon.setImageResource(current.getPowerStatusIcon());
        }
    }
}