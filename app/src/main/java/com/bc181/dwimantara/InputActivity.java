package com.bc181.dwimantara;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.bc181.dwimantara.model.ResponseData;
import com.bc181.dwimantara.services.ApiClient;
import com.bc181.dwimantara.services.ApiContent;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText editNama, editTahun, editStock, editBrand;
    private Button btnSave;
    private RadioButton rbFullbike, rbPedalset, rbWheelset, rbSaddle, rbFrame;
    private ImageView imgProfilePicture;
    private String imgLocation;
    private boolean updateOperation = false;
    private String jenis;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        progressDialog = new ProgressDialog(InputActivity.this);

        editNama = findViewById(R.id.edit_nama);
        editTahun = findViewById(R.id.edit_tahun);
        editStock = findViewById(R.id.edit_stock);
        editBrand = findViewById(R.id.edit_brand);
        btnSave = findViewById(R.id.btn_simpan);
        imgProfilePicture = findViewById(R.id.profile_image);
        rbFullbike = findViewById(R.id.fullbike);
        rbPedalset = findViewById(R.id.pedalset);
        rbWheelset = findViewById(R.id.wheelset);
        rbSaddle = findViewById(R.id.saddle);
        rbFrame = findViewById(R.id.frame);

        Intent receivedData = getIntent();
        Bundle data = receivedData.getExtras();
        if(data.getString("OPERATION").equals("insert")) {
            updateOperation = false;
        } else {
            updateOperation = true;
            id = data.getInt("ID");
            editNama.setText(data.getString("NAMA"));
            editTahun.setText(data.getString("TAHUN"));
            editStock.setText(data.getString("STOCK"));
            editBrand.setText(data.getString("BRAND"));
            imgLocation = data.getString("IMAGE");
            jenis = data.getString("JENIS");
            if(jenis.equals(getString(R.string.fullbike)))
                rbFullbike.setChecked(true);
            else if(jenis.equals(getString(R.string.pedalset)))
                rbPedalset.setChecked(true);
            else if(jenis.equals(getString(R.string.wheelset)))
                rbWheelset.setChecked(true);
            else if(jenis.equals(getString(R.string.saddle)))
                rbSaddle.setChecked(true);
            else if(jenis.equals(getString(R.string.frame)))
                rbFrame.setChecked(true);
            if(!imgLocation.equals(null)){
                Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                builder.downloader(new OkHttp3Downloader(getApplicationContext()));
                builder.build().load(imgLocation)
                        .placeholder((R.drawable.ic_launcher_background))
                        .error(R.drawable.ic_launcher_background)
                        .into(imgProfilePicture);
            }
            imgProfilePicture.setContentDescription(imgLocation);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_menu_delete) {
            deleteData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_delete);

        if(updateOperation==true) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else{
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.fullbike:
                if (checked) {
                    jenis = getString(R.string.fullbike);
                    imgLocation = ApiClient.IMAGE_URL + "fullbike.png";
                    break;
                }
            case R.id.pedalset:
                if (checked) {
                    jenis = getString(R.string.pedalset);
                    imgLocation = ApiClient.IMAGE_URL + "pedalset.png";
                    break;
                }
            case R.id.wheelset:
                if (checked) {
                    jenis = getString(R.string.wheelset);
                    imgLocation = ApiClient.IMAGE_URL + "wheelset.png";
                    break;
                }
            case R.id.saddle:
                if (checked) {
                    jenis = getString(R.string.saddle);
                    imgLocation = ApiClient.IMAGE_URL + "saddle.png";
                    break;
                }
            case R.id.frame:
                if (checked) {
                    jenis = getString(R.string.frame);
                    imgLocation = ApiClient.IMAGE_URL + "frame.png";
                    break;
                }
        }
    }

    private void saveData() {
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String nama = editNama.getText().toString();
        String tahun = editTahun.getText().toString();
        String stock = editStock.getText().toString();
        String brand = editBrand.getText().toString();
        if(!(nama.equals("") && tahun.equals(""))) {
            ApiContent api = ApiClient.getRetrofitInstance().create(ApiContent.class);
            Call<ResponseData> call;
            if(updateOperation == false) {
                call = api.addData(nama, tahun, stock, brand, imgLocation, jenis);
            } else {
                call = api.editData(String.valueOf(id), nama, tahun, stock, brand, imgLocation, jenis);
                updateOperation = false;
            }
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progressDialog.dismiss();
                    if(value.equals("1")) {
                        Toast.makeText(InputActivity.this, "SUKSES: " + message, Toast.LENGTH_LONG).show();
                        finish();
                    } else{
                        Toast.makeText(InputActivity.this, "GAGAL: " + message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(InputActivity.this, "Gagal menghubungi server...", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                    Log.d("Input Data Error", t.toString());

                }
            });
        } else {
            Toast.makeText(this, "Data harus lengkap", Toast.LENGTH_LONG).show();
        }

    }

    private void deleteData() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data");
        builder.setMessage("Apakah anda yakin ingin menghapus data?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setMessage("Deleting...");
                        progressDialog.show();
                        ApiContent api = ApiClient.getRetrofitInstance().create(ApiContent.class);
                        Call<ResponseData> call = api.deleteData(String.valueOf(id));
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                progressDialog.dismiss();

                                if(value.equals("1")) {
                                    Toast.makeText(InputActivity.this, "SUKSES: " + message, Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(InputActivity.this, "GAGAL: " + message, Toast.LENGTH_LONG).show();
                                }

                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(InputActivity.this, "Gagal menghubungi server...", Toast.LENGTH_SHORT).show();
                                t.printStackTrace();
                                Log.d("Detele Data Error", t.toString());
                            }
                        });
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}