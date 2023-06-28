package com.example.organizze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organizze.Activity.CadastroActivity;
import com.example.organizze.Activity.LoginActivity;
import com.example.organizze.Config.ConfiguracaoFirebase;
import com.example.organizze.R;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {
   private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        setButtonBackVisible(false);
        setButtonNextVisible(false);
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide)
                .fragment(R.layout.intro_2)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide)
                .fragment(R.layout.intro_3)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide)
                .fragment(R.layout.intro_4)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorSlide)
                .fragment(R.layout.intro_cadastro)
                .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btEntrar(View view){
        startActivity(new Intent( this, LoginActivity.class));
    }
    public void btCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
    public  void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutentication();


        if(autenticacao.getCurrentUser()!= null){
            abrirTelaPrincipal();
        }
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));

    }
}
