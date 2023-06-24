package com.example.organizze.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.Config.ConfiguracaoFirebase;
import com.example.organizze.Model.Usuario;
import com.example.organizze.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome,campoEmail,campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticação;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoSenha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            CadastrarUsuario();
                        }else{
                            Toast.makeText(getApplicationContext(),"é obrigatório preencher nome",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"é obrigatório preencher email",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"é obrigatório preencher senha",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public  void  CadastrarUsuario(){
        autenticação = ConfiguracaoFirebase.getFirebaseAutentication();
        autenticação.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     finish();
                 }else{
                     String excecao;
                     try {
                         throw task.getException();
                     }catch (FirebaseAuthWeakPasswordException e){
                        excecao ="Digite uma senha mais forte";
                     }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor digitar email válido";
                     }catch (FirebaseAuthUserCollisionException e){
                         excecao = "Está conta já foi cadastrada";
                     }catch (Exception e){
                         excecao = "Erro ao cadastrar usuário" + e.getMessage();
                         e.printStackTrace();
                     }
                     Toast.makeText(getApplicationContext(),excecao,Toast.LENGTH_LONG).show();
                 }
            }
        });
    }
}
