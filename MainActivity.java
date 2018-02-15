package lenovo.firebase;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETemail;
    private EditText ETpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

         mAuthListener = new FirebaseAuth.AuthStateListener() { //слушатель, который срабатывает, когда пользователь зашел или вышел
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user = firebaseAuth.getCurrentUser(); //получаем нашего текущего пользователя
                 if (user !=null) {
                     //пользователь вошел
                 }else{
                     //пользователь вышел
                 }
             }

        };

         ETemail = findViewById(R.id.ETemail);
         ETpassword = findViewById(R.id.ETpassword);
         findViewById(R.id.button_sign).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.button_sign){ signing(ETemail.getText().toString(),ETpassword.getText().toString());

        }else if(view.getId()==R.id.button_register){registration(ETemail.getText().toString(),ETpassword.getText().toString());}

    }

    public void signing(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, /*слушатель выполнения входа(задачи)*/new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {//после выполнения авторизации сработает метод onCompele
                if (task.isSuccessful()){//если задача выполнена успешно
                Toast.makeText(MainActivity.this, "Авторизация выполнена", Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(MainActivity.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();}

            }
        });
    }
    public void registration(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){Toast.makeText(MainActivity.this, "Регистрация выполнена", Toast.LENGTH_SHORT).show();}
            else {Toast.makeText(MainActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();}
            }
        });

    }
}
