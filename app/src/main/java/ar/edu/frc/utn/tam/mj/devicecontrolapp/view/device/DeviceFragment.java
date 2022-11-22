package ar.edu.frc.utn.tam.mj.devicecontrolapp.view.device;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;

import java.util.ArrayList;
import java.util.List;

import ar.edu.frc.utn.tam.mj.devicecontrolapp.R;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandler;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.DeviceHandlerFactory;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.controller.devices.gizwitz.DeviceControlDTORequest;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.databinding.FragmentDeviceBinding;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceControlAppException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.exceptions.DeviceHandlerException;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.model.Device;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.AppDatabase;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.persistence.DeviceDao;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIConstants;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.UIUtils;
import ar.edu.frc.utn.tam.mj.devicecontrolapp.view.navigation.NavigationDrawerItem;

public class DeviceFragment extends Fragment {

    private FragmentDeviceBinding binding;
    static Device device;
    DeviceHandler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        try {
            handler = DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_API_HANDLER);
        } catch (DeviceControlAppException e) {
            //todo
            e.printStackTrace();
        }
        DeviceViewModel galleryViewModel =
                new ViewModelProvider(this).get(DeviceViewModel.class);

        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView imageView2 = binding.imageView2;

        Button btArm = binding.btArm;
        btArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(R.drawable.armed);
                doArm();
            }
        });
        Button btDisarm = binding.btDisarm;
        btDisarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(R.drawable.disarmed);

                doDisarm();
            }
        });

        Button btHomeArm = binding.btHomeArm;
        btHomeArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(R.drawable.home_armed);
                doHomeArm();
            }
        });
        Button btPanic = binding.btPanic;
        btPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(R.drawable.panic);
                doPanic();
            }
        });
        Bundle b = getArguments();
        if (b != null) {
            if (b.containsKey(UIConstants.DEVICE)) {
                device = (Device) b.getSerializable(UIConstants.DEVICE);
            }
        }

        Spinner spinner = binding.spServiceClass;
        loadServiceClassSpinner();
        spinnerArrayAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, serviceClassList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String serviceClass = parent.getSelectedItem().toString();
                    switch (serviceClass) {
                        case "Modo Internet":
                            handler = DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_API_HANDLER);
                            break;
                        case "Modo SMS":
                            handler = DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_SMS_HANDLER);
                            break;
                        default:
                            handler = DeviceHandlerFactory.getInstance(DeviceConstants.CLASS_SERVICE_DUMMY_HANDLER);
                    }
                } catch (DeviceControlAppException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    //loadLastUsedDevice();
        return root;
    }

    ArrayAdapter<String> spinnerArrayAdapter;
    List<String> serviceClassList = new ArrayList<>();

    public void loadLastUsedDevice() {
        SharedPreferences sp = UIUtils.getSharedPreferences(getActivity().getApplicationContext());
        int lastDeviceId = sp.getInt(UIConstants.SP_LAST_DEVICE_ID, 0);
        if (lastDeviceId != 0) {
            DeviceDao deviceDao = AppDatabase.getDatabase(getActivity().getApplicationContext()).deviceDao();
            device = deviceDao.findByDeviceId(lastDeviceId);
            if(device!=null)
                ((AppCompatActivity)getActivity().getApplicationContext()).getSupportActionBar().setTitle(device.getName());
        }
    }

    public void loadServiceClassSpinner() {

        //serviceClassList.clear();
        //if (device != null && device.isServiceModeAllowed(DeviceConstants.SERVICE_MODE_API))
        serviceClassList.add("Modo Internet");
//        if (device != null && device.isServiceModeAllowed(DeviceConstants.SERVICE_MODE_SMS))
        serviceClassList.add("Modo SMS");
//        if (device != null && device.isServiceModeAllowed(DeviceConstants.SERVICE_MODE_DUMMY))
        serviceClassList.add("Modo DUMMY");
    }

    private void doArm() {
        try {
            if (device == null) {
                Toast.makeText(this.getActivity().getApplicationContext(), "Seleccionar Dispositivo", Toast.LENGTH_LONG).show();
                return;
            }
            handler.arm(device.getGizwitzDeviceId(), device.getSmsPhoneNumber(), DeviceControlDTORequest.CONTROL_ARM_NOW);
            Toast.makeText(this.getActivity().getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (DeviceHandlerException e) {
            e.printStackTrace();
        }
    }

    private void doHomeArm() {
        try {
            if (device == null) {
                Toast.makeText(this.getActivity().getApplicationContext(), "Seleccionar Dispositivo", Toast.LENGTH_LONG).show();
                return;
            }
            handler.homeArm(device.getGizwitzDeviceId(), device.getSmsPhoneNumber());
            Toast.makeText(this.getActivity().getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (DeviceHandlerException e) {
            e.printStackTrace();
        }
    }

    private void doDisarm() {
        try {
            if (device == null) {
                Toast.makeText(this.getActivity().getApplicationContext(), "Seleccionar Dispositivo", Toast.LENGTH_LONG).show();
                return;
            }
            handler.disarm(device.getGizwitzDeviceId(), device.getSmsPhoneNumber());
            Toast.makeText(this.getActivity().getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (DeviceHandlerException e) {
            e.printStackTrace();
        }

    }

    private void doPanic() {
        try {
            if (device == null) {
                Toast.makeText(this.getActivity().getApplicationContext(), "Seleccionar Dispositivo", Toast.LENGTH_LONG).show();
                return;
            }
            handler.panic(device.getGizwitzDeviceId(), device.getSmsPhoneNumber());
            Toast.makeText(this.getActivity().getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (DeviceHandlerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}