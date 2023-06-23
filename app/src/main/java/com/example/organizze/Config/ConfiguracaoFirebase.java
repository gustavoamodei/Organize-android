package com.example.organizze.Config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;

    public static FirebaseAuth getFirebaseAutentication(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
