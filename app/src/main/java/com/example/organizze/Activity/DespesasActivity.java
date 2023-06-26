package com.example.organizze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.Helper.DateCustom;
import com.example.organizze.Model.Movimentacao;
import com.example.organizze.R;

import com.google.android.material.textfield.TextInputEditText;

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText campoData,campoCategoria,campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

        campoData.setText(DateCustom.dataAtual());


    }
    public  void  salvarDespesas(View view){
        if(validarCamposDespesa()) {
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("D");
            movimentacao.salvar(data);
        }



    }
    public Boolean validarCamposDespesa(){

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();
        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){
                        return  true;
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "é obrigatório preencher descricao",Toast.LENGTH_LONG).show();
                        return  false;
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "é obrigatório preencher categoria",Toast.LENGTH_LONG).show();
                    return  false;
                }
            }else{
                Toast.makeText(getApplicationContext(),
                        "é obrigatório preencher data",Toast.LENGTH_LONG).show();
                return  false;
            }
        }else{
            Toast.makeText(getApplicationContext(),
                    "é obrigatório preencher o valor",Toast.LENGTH_LONG).show();
            return  false;
        }


    }
}
