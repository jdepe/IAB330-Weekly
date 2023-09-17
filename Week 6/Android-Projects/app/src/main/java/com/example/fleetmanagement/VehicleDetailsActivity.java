package com.example.fleetmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleetmanagement.DB.Vehicle;
import com.example.fleetmanagement.DB.VehicleDao;
import com.example.fleetmanagement.Utils.MyApp;

import java.nio.channels.AsynchronousByteChannel;

public class VehicleDetailsActivity extends AppCompatActivity {

    TextView tvVehicleName, tvVehicleType;
    VehicleDao vehicleDao;
    int vehicleId;
    Vehicle vehicle;
    Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        tvVehicleName = findViewById(R.id.tvVehicleName);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        vehicleDao = MyApp.getAppDatabase().vehicleDao();

        if (getIntent().getIntExtra("vehicleId", -1) != -1) {
            vehicleId = getIntent().getIntExtra("vehicleId", -1);

            vehicleDao.getVehicleById(vehicleId).observe(this, dbVehicle -> {
                if (dbVehicle != null) {
                    this.vehicle = dbVehicle;
                    tvVehicleName.setText("Vehicle Name: " + this.vehicle.getName());
                    tvVehicleType.setText("Vehicle Type: " + this.vehicle.getType());
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vehicle != null) {
                        updateTheVehicle();
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vehicle != null) {
                        deleteTheVehicle();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "No Vehicle Id Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTheVehicle() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_vehicle, null);
        EditText editTextVehicleName = dialogView.findViewById(R.id.editTextVehicleName);
        EditText editTextVehicleType = dialogView.findViewById(R.id.editTextVehicleType);

        editTextVehicleName.setText(vehicle.getName());
        editTextVehicleType.setText(vehicle.getType());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String vehicleName = editTextVehicleName.getText().toString();
            String vehicleType = editTextVehicleType.getText().toString();

            vehicle.setName(vehicleName);
            vehicle.setType(vehicleType);

            AsyncTask.execute(() -> {
                vehicleDao.update(vehicle);
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteTheVehicle() {
        AsyncTask.execute(() -> {
            vehicleDao.delete(vehicle);
            finish();
        });
    }
}