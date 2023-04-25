package lk.com.ipccw2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SensorDataAdapter extends RecyclerView.Adapter<SensorDataAdapter.SensorDataViewHolder> {

    private List<SensorData> sensorDataList;

    public SensorDataAdapter(List<SensorData> sensorDataList) {
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
            if(sensorData.getCreatedAt() != null) {
                Date date = new Date(sensorData.getCreatedAt().toDate().getTime());

                // Format date and time strings using SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = dateFormat.format(date);

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String timeString = timeFormat.format(date);

                // Set date and time strings to the corresponding TextViews
                textViewDate.setText("Date:"+(dateString));
                textViewTime.setText("Time:"+(timeString));
            }

        }
    }

}
