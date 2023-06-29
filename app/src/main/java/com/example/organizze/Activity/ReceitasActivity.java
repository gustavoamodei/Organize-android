package com.example.organizze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.Config.ConfiguracaoFirebase;
import com.example.organizze.Helper.Base64Custom;
import com.example.organizze.Helper.DateCustom;
import com.example.organizze.Model.Movimentacao;
import com.example.organizze.Model.Usuario;
import com.example.organizze.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceitasActivity extends AppCompatActivity {
    private TextInputEditText campoData,campoCategoria,campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutentication();
    private Double receitaTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

        campoData.setText(DateCustom.dataAtual());
        recuperarReceitaTotal();
    }

    public  void salvarReceitas(View view){
        if(validarCamposReceita()) {
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("R");


            Double receitaAtualizada = (receitaTotal + valorRecuperado);
            atualizarReceita(receitaAtualizada);
            movimentacao.salvar(data);
            finish();
        }
    }

    public static boolean validarFormatoData(String data){
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)data);
        return matcher.matches();
    }
    public Boolean validarCamposReceita(){

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();
        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){
                        if(validarFormatoData(textoData)){
                            return true;
                        }else{
                            Toast.makeText(ReceitasActivity.this,
                                    "formato de data inválido tente dd/MM/AAAA ",Toast.LENGTH_LONG).show();
                            return  false;
                        }
                    }else{
                        Toast.makeText(ReceitasActivity.this,
                                "é obrigatório preencher descricao",Toast.LENGTH_LONG).show();
                        return  false;
                    }
                }else{
                    Toast.makeText(ReceitasActivity.this,
                            "é obrigatório preencher categoria",Toast.LENGTH_LONG).show();
                    return  false;
                }
            }else{
                Toast.makeText(ReceitasActivity.this,
                        "é obrigatório preencher data",Toast.LENGTH_LONG).show();
                return  false;
            }
        }else{
            Toast.makeText(ReceitasActivity.this,
                    "é obrigatório preencher o valor",Toast.LENGTH_LONG).show();
            return  false;
        }

    }
    public void recuperarReceitaTotal (){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void  atualizarReceita(Double receita){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.child("receitaTotal").setValue(receita);
    }

    
}
