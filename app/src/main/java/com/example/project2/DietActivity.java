package com.example.project2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DietActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance(); // 파이어베이스 데이터베이스 연동
    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference mDatabaseRef = mFirebaseDB.getInstance().getReference();
    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 방금 로그인 성공한 유저의 정보를 가져오는 객체
    private static int height, weight, age;
    private static String gender;

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    //String sql;
    //Cursor cursor;

    Double BMR, diet;
    TextView basicText, recomText;
    ProgressBar basicBar, recomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        final UserAccount[] userInfo = {new UserAccount()};
        //데이터 읽기
        mDatabaseRef.child("project").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError error) { //참조에 액세스 할 수 없을 때 호출
                weight = 0;
                height = 0;
                age = 0;
                gender = "man";
                Log.d("정보222", "망함");
            }

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo[0] = snapshot.getValue(UserAccount.class);
                if(userInfo[0] == null || userInfo[0].equals(null)){
                    weight = 0;
                    height = 0;
                    age = 0;
                    gender = "man";
                    Log.d("정보111", "망함");
                    if(gender.equals("man")){
                        BMR = (13.397*weight) + (4.799*height) - (5.677*age) + 88.362;
                    }else if(gender.equals("woman")){
                        BMR =  (9.247*weight) + (3.098*height) - (4.330*age) + 447.593;
                    }

                    diet = (BMR*1.4) * 0.76;

                    String diet2 = String.format("%.0f", diet);
                    String BMR2 = String.format("%.0f", BMR);

                    basicText = findViewById(R.id.basic);
                    basicText.setText("기초                                 "+
                            "                                         "+BMR2); //기초대사량
                    recomText = findViewById(R.id.recommend);
                    recomText.setText("권장                                 "+
                            "                                         "+diet2); //권장섭취량

                    basicBar = findViewById(R.id.basicBar);
                    basicBar.setProgress(Integer.parseInt(BMR2));
                    recomBar = findViewById(R.id.recomBar);
                    recomBar.setProgress(Integer.parseInt(diet2));
                }
                else {
                    weight = userInfo[0].getWeight();
                    height = userInfo[0].getHeight();
                    age = userInfo[0].getAge();
                    gender = userInfo[0].getGender();
                    Log.d("정보", String.valueOf(weight));

                    if(gender.equals("man")){
                        BMR = (13.397*weight) + (4.799*height) - (5.677*age) + 88.362;
                    }else if(gender.equals("woman")){
                        BMR =  (9.247*weight) + (3.098*height) - (4.330*age) + 447.593;
                    }

                    diet = (BMR*1.4) * 0.76;

                    String diet2 = String.format("%.0f", diet);
                    String BMR2 = String.format("%.0f", BMR);

                    basicText = findViewById(R.id.basic);
                    basicText.setText("기초                                 "+
                            "                                         "+BMR2); //기초대사량
                    recomText = findViewById(R.id.recommend);
                    recomText.setText("권장                                 "+
                            "                                         "+diet2); //권장섭취량

                    basicBar = findViewById(R.id.basicBar);
                    basicBar.setProgress(Integer.parseInt(BMR2));
                    recomBar = findViewById(R.id.recomBar);
                    recomBar.setProgress(Integer.parseInt(diet2));
                }
            }
        });

    }
    // 이름 변경을 위한 메소드
    private void read() {

        final UserAccount[] userInfo = {new UserAccount()};
        //데이터 읽기
        mDatabaseRef.child("project").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError error) { //참조에 액세스 할 수 없을 때 호출
                weight = 0;
                height = 0;
                age = 0;
                gender = "man";
                Log.d("정보222", "망함");
            }

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo[0] = snapshot.getValue(UserAccount.class);
                if(userInfo[0] == null || userInfo[0].equals(null)){
                    weight = 0;
                    height = 0;
                    age = 0;
                    gender = "man";
                    Log.d("정보111", "망함");
                }
                else {
                    weight = userInfo[0].getWeight();
                    height = userInfo[0].getHeight();
                    age = userInfo[0].getAge();
                    gender = userInfo[0].getGender();
                    Log.d("정보", String.valueOf(weight));
                }
            }
        });
        /*if(userInfo[0].getName() == null || userInfo[0].getName().length() == 0)
            welcome.setText("회원정보를 불러오지 못했습니다.");
        else if (userInfo[0].getDogName().equals(""))
            tvDogName.setText(userInfo[0].getDogName());*/
    }

}