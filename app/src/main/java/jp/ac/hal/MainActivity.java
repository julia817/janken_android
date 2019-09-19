package jp.ac.hal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView rock, scissors, paper;
    int totalCnt; //回数
    int drawCnt; //引き分け数
    int loseCnt; //負け数
    int winCnt; //勝ち数
    double winPer; //勝率
    CharSequence history; //履歴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rock = findViewById(R.id.rock);
        scissors = findViewById(R.id.scissors);
        paper = findViewById(R.id.paper);

        Intent intent = getIntent();
        totalCnt = intent.getIntExtra("TOTAL", 0);
        winCnt = intent.getIntExtra("WIN", 0);
        loseCnt = intent.getIntExtra("LOSE", 0);
        drawCnt = intent.getIntExtra("DRAW", 0);
        winPer = intent.getDoubleExtra("WINPER", 0);
        history = intent.getCharSequenceExtra("HISTORY");
    }

    //画面遷移
    public void onHandClicked(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("MY_HAND",view.getId());
        intent.putExtra("WIN", winCnt);
        intent.putExtra("LOSE", loseCnt);
        intent.putExtra("DRAW", drawCnt);
        intent.putExtra("TOTAL", totalCnt);
        intent.putExtra("WINPER", winPer);
        intent.putExtra("HISTORY", history);
        startActivity(intent);
    }
}
