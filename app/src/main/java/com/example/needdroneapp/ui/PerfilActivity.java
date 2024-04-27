package com.example.needdroneapp.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.needdroneapp.R;
import com.example.needdroneapp.data.ClienteController;
import com.example.needdroneapp.data.PilotoController;
import com.example.needdroneapp.databinding.ActivityPerfilBinding;
import com.example.needdroneapp.ui.edicao.EditPilotoActivity;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilBinding binding;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userType = prefs.getString("userType", "");
        int userId = prefs.getInt("userId", 0);

        if (userType.equals("cliente")) {
            LinearLayout experiencia = findViewById(R.id.containerExperiencia);
            experiencia.setVisibility(View.GONE);//deixa a experiencia invisivel
            //conexao com o banco
            ClienteController clienteController = new ClienteController(this);
            Cursor dados = clienteController.carregaDadosPorId(userId);
            if (dados.getCount() > 0){
                dados.moveToFirst();
                String fotoPath = dados.getString(dados.getColumnIndex("foto"));
                Bitmap fotoBitmap = EditPilotoActivity.loadImageFromStorage(fotoPath);
                if (fotoBitmap != null) {
                    binding.fotoPerfil.setImageBitmap(fotoBitmap);
                }else {
                    binding.fotoPerfil.setImageResource(android.R.drawable.ic_menu_camera);
                }
                binding.nome.setText(dados.getString(dados.getColumnIndex("nome")));
                binding.biografia.setText(dados.getString(dados.getColumnIndex("biografia")));
                binding.emailPiloto.setText("E-mail :" + dados.getString(dados.getColumnIndex("email")));
                binding.telefonePiloto.setText("Tel :" + dados.getString(dados.getColumnIndex("tel")));
            }

        } else if (userType.equals("piloto")) {
            LinearLayout experiencia = findViewById(R.id.containerExperiencia);
            experiencia.setVisibility(View.VISIBLE);//deixa a experiencia invisivel

            PilotoController pilotoController = new PilotoController(this);
            Cursor dados = pilotoController.carregaDadosPorId(userId);
            if (dados.getCount() > 0) {
                dados.moveToFirst();
                String fotoPath = dados.getString(dados.getColumnIndex("foto"));
                Bitmap fotoBitmap = EditPilotoActivity.loadImageFromStorage(fotoPath);
                if (fotoBitmap != null) {
                    binding.fotoPerfil.setImageBitmap(fotoBitmap);
                }else {
                    binding.fotoPerfil.setImageResource(android.R.drawable.ic_menu_camera);
                }

                binding.nome.setText(dados.getString(dados.getColumnIndex("nome")));
                binding.biografia.setText(dados.getString(dados.getColumnIndex("biografia")));
                binding.licenca.setText("Licença de pilotagem: " + dados.getString(dados.getColumnIndex("licencaPilotagem")));
                binding.emailPiloto.setText("E-mail: " + dados.getString(dados.getColumnIndex("email")));
                binding.telefonePiloto.setText("Tel: " + dados.getString(dados.getColumnIndex("tel")));
            }

        }
    }
}