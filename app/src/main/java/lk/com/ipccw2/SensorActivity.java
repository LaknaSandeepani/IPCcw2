package lk.com.ipccw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class SensorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SensorDataAdapter adapter;
    private List<SensorData> sensorDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SensorDataAdapter(sensorDataList);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference sensorDataRef = db.collection("car_data");

        sensorDataRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            private static final String TAG = "test";

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                sensorDataList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.exists()) {
                        String sensorId = doc.getString("car_ID");
                        String speed = doc.getString("Speed");
                        String accelerationx = doc.getString("acceleration_X");
                        String accelerationy = doc.getString("acceleration_Y");
                        String accelerationz = doc.getString("acceleration_Z");
                        String rotationx = doc.getString("rotation_X");
                        String rotationy = doc.getString("rotation_Y");
                        String rotationz = doc.getString("rotation_Z");
                        Timestamp createdAt = doc.getTimestamp("createdAt");

                        SensorData sensorData = new SensorData(sensorId, speed, accelerationx,accelerationy,accelerationz, rotationx, rotationy,rotationz, createdAt);
                        sensorDataList.add(sensorData);
                    }
                }


                adapter.notifyDataSetChanged();
            }

        });
    }

    public class SensorDataViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewSensorId;
        private TextView textViewSpeed;
        private TextView textViewaccx;
        private TextView textViewaccy;
        private TextView textViewaccz;
        private TextView textViewrotx;
        private TextView textViewroty;
        private TextView textViewrotz;
        private TextView textViewDate;
        private TextView textViewTime;

        public SensorDataViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorId = itemView.findViewById(R.id.txt_sensorid);
            textViewSpeed = itemView.findViewById(R.id.txt_speed);
            textViewaccx = itemView.findViewById(R.id.txt_accx);
            textViewaccy = itemView.findViewById(R.id.txt_accy);
            textViewaccz = itemView.findViewById(R.id.txt_accz);
            textViewrotx = itemView.findViewById(R.id.txt_rotationx);
            textViewroty = itemView.findViewById(R.id.txt_rotationy);
            textViewrotz = itemView.findViewById(R.id.txt_rotationz);
            textViewDate = itemView.findViewById(R.id.txt_date);
            textViewTime = itemView.findViewById(R.id.txt_time);
        }

        public void bind(SensorData sensorData) {
            textViewSensorId.setText("Car Number: " + sensorData.getSensorId());
            textViewSpeed.setText("Speed (m/s):" + sensorData.getSpeed());
            textViewaccx.setText("Accelaration(x-axis):"+ sensorData.getAccelerationx());
            textViewaccy.setText("Accelaration(y-axis):"+ sensorData.getAccelerationy());
            textViewaccz.setText("Accelaration(z-axis):"+ sensorData.getAccelerationz());
            textViewrotx.setText("Rotation(x-axis):"+sensorData.getRotationx());
            textViewroty.setText("Rotation(y-axis):"+sensorData.getRotationy());
            textViewrotz.setText("Rotation(z-axis):"+sensorData.getRotationz());
            Timestamp createdAt = sensorData.getCreatedAt();
            Date date = createdAt.toDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = dateFormat.format(date);

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String timeString = timeFormat.format(date);

            textViewDate.setText(dateString);
            textViewTime.setText(timeString);
        }
    }


    private class SensorAdapter extends RecyclerView.Adapter<SensorDataViewHolder> {
        private List<SensorData> sensorDataList;

        public SensorAdapter(List<SensorData> sensorDataList) {
            this.sensorDataList = sensorDataList;
        }

        @NonNull
        @Override
        public SensorDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor_data, parent, false);
            return new SensorDataViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SensorDataViewHolder holder, int position) {
            SensorData sensorData = sensorDataList.get(position);
            holder.bind(sensorData);
        }

        @Override
        public int getItemCount() {
            return sensorDataList.size();
        }
    }
}
