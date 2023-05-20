package com.stefanagustohutapeajsleepdn.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.stefanagustohutapeajsleepdn.jsleep_android.model.Facility;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Payment;
import com.stefanagustohutapeajsleepdn.jsleep_android.model.Room;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.BaseApiService;
import com.stefanagustohutapeajsleepdn.jsleep_android.request.UtilsApi;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Javadoc
 * @author Stefan Agusto Hutapea
 */

/**
 * The `DetailRoomActivity` class extends the `AppCompatActivity` class and provides
 * a graphical user interface for displaying details about a particular room, as well
 * as allowing the user to book the room for a specified date range.
 */
public class DetailRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView roomName, roomAddress, roomPrice, roomSize, roomBedType, fromText, toText;
    CheckBox ac, refr, wifi, bath, balcon, rest, pool, fitn;
    Button bookBtn, cancelBtn, bookingBtn, deleteBtn;
    private DatePickerDialog datePickerDialog, datePickerDialog2;
    private Button fromBtn, toBtn;
    public static Room tempRoom;
    public static String dateFrom = "0000-00-00";
    public static String dateTo = "0000-00-00";

    /**
     * The onCreate method is called when the activity is first created. It sets the
     * content view to activity_detail_room and initializes the API service and several
     * UI elements such as buttons and text fields.
     *
     * @param savedInstanceState a bundle containing the state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        mApiService = UtilsApi.getApiService();
        roomName = findViewById(R.id.nameOfRoom);
        roomBedType = findViewById(R.id.bedTypeRoom);
        roomSize = findViewById(R.id.sizeOfRoom);
        roomPrice = findViewById(R.id.priceOfRoom);
        roomAddress = findViewById(R.id.addressOfRoom);
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        fromBtn = findViewById(R.id.fromDate);
        toBtn = findViewById(R.id.toDate);
        bookingBtn = findViewById(R.id.bookingButton);
        deleteBtn = findViewById(R.id.deleteButton);
        bookBtn = findViewById(R.id.confirmBookButton);
        cancelBtn = findViewById(R.id.cancelBookBtn);

        ac = findViewById(R.id.acBox);
        refr = findViewById(R.id.refrigeratorBox);
        wifi = findViewById(R.id.wifiBox);
        bath = findViewById(R.id.bathubBox);
        balcon = findViewById(R.id.balconyBox);
        rest = findViewById(R.id.restaurantBox);
        pool = findViewById(R.id.poolBox);
        fitn = findViewById(R.id.fitnessBox);

        roomName.setText(tempRoom.name);
        roomPrice.setText(String.valueOf(tempRoom.price.price));
        roomAddress.setText(tempRoom.address);
        roomBedType.setText(tempRoom.bedType.toString());
        roomSize.setText(String.valueOf(tempRoom.size));

        for (int i = 0; i < tempRoom.facility.size(); i++) {
            if (tempRoom.facility.get(i).equals(Facility.AC)) {
                ac.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Refrigerator)) {
                refr.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Bathub)) {
                bath.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Balcony)) {
                balcon.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Restaurant)) {
                rest.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitn.setChecked(true);
            }
        }

        bookingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //make fromBtn and toBtn visible
                fromBtn.setVisibility(View.VISIBLE);
                toBtn.setVisibility(View.VISIBLE);
                fromText.setVisibility(View.VISIBLE);
                toText.setVisibility(View.VISIBLE);
                bookingBtn.setVisibility(View.INVISIBLE);
                bookBtn.setVisibility(View.VISIBLE);
                cancelBtn.setVisibility(View.VISIBLE);
            }
        });

        initDatePicker();
        fromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        toBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog2.show();
            }
        });
        fromBtn.setText(getTodayDate());
        //toBtn starts from tomorrow
        toBtn.setText(getTomorrowDate());

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make fromBtn and toBtn invisible
                fromBtn.setVisibility(View.INVISIBLE);
                toBtn.setVisibility(View.INVISIBLE);
                fromText.setVisibility(View.INVISIBLE);
                toText.setVisibility(View.INVISIBLE);
                bookBtn.setVisibility(View.INVISIBLE);
                cancelBtn.setVisibility(View.INVISIBLE);
                if (fromBtn.getText().toString().equals(toBtn.getText().toString())) {
                    Toast.makeText(DetailRoomActivity.this, "Please choose different dates", Toast.LENGTH_SHORT).show();
                } //else if no registered user
                else if (MainActivity.loginAccount.renter == null) {
                    Toast.makeText(DetailRoomActivity.this, "No Account Rentered, please renter on your account", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    create();
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
                Toast.makeText(DetailRoomActivity.this, "Room deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingBtn.setVisibility(View.VISIBLE);
                fromBtn.setVisibility(View.INVISIBLE);
                toBtn.setVisibility(View.INVISIBLE);
                fromText.setVisibility(View.INVISIBLE);
                toText.setVisibility(View.INVISIBLE);
                bookBtn.setVisibility(View.INVISIBLE);
                cancelBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                fromBtn.setText(date);
                dateFrom = year + "-" + month + "-" + day;
            }
        };
        DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                toBtn.setText(date);
                dateTo = year + "-" + month + "-" + day;
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog2 = new DatePickerDialog(this, style, dateSetListener2, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    //get month format
    private String getMonthFormat(int month) {
        if (month == 1) {
            return "Jan";
        }
        if (month == 2) {
            return "Feb";
        }
        if (month == 3) {
            return "Mar";
        }
        if (month == 4) {
            return "Apr";
        }
        if (month == 5) {
            return "May";
        }
        if (month == 6) {
            return "Jun";
        }
        if (month == 7) {
            return "Jul";
        }
        if (month == 8) {
            return "Aug";
        }
        if (month == 9) {
            return "Sep";
        }
        if (month == 10) {
            return "Oct";
        }
        if (month == 11) {
            return "Nov";
        }
        if (month == 12) {
            return "Dec";
        }
        return "Jan";
    }

    /**
     * Create a new booking
     */
    protected Payment create(){
        System.out.println(MainActivity.loginAccount.id);
        System.out.println(MainActivity.loginAccount.renter.id);
        System.out.println(tempRoom.id);
        System.out.println(fromBtn.getText().toString());
        System.out.println(toBtn.getText().toString());

        mApiService.createPayment(MainActivity.loginAccount.id,
                MainActivity.loginAccount.renter.id,
                tempRoom.id,
                dateFrom.toString(),
                dateTo .toString()).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    MainActivity.paymentAccount = response.body();
                    MainActivity.loginAccount.balance -= MainActivity.paymentAccount.totalPrice;
                    System.out.println("Berhasil");
                    Intent move = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                    startActivity(move);
                    Toast toast = Toast.makeText(getApplicationContext(), "Payment Menu", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                System.out.println("Gagal");
                Toast toast = Toast.makeText(getApplicationContext(), "Payment Menu", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return null;
    }

    protected void delete() {
        mApiService.deleteRoom(tempRoom.accountId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Delete Room", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed to delete room", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}

