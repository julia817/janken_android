package jp.ac.hal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    int comHand, myHand, rand, id;
    ImageView comImg, playerImg;
    int ROCK = 0, PAPER = 1, SCISSORS = 2;

    int totalCnt, drawCnt, loseCnt, winCnt;
    double winPer;
    CharSequence history;
    TextView tvResult, tvWinCnt, tvLostCnt, tvDrawCnt, tvTotalCnt, tvWinPer, tvHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        comImg = findViewById(R.id.comHand);
        playerImg = findViewById(R.id.playerHand);
        Intent intent = getIntent();
        id = intent.getIntExtra("MY_HAND", 0);
        totalCnt = intent.getIntExtra("TOTAL", 0);
        winCnt = intent.getIntExtra("WIN", 0);
        loseCnt = intent.getIntExtra("LOSE", 0);
        drawCnt = intent.getIntExtra("DRAW", 0);
        winPer = intent.getDoubleExtra("WINPER", 0);
        history = intent.getCharSequenceExtra("HISTORY")==null ? getResources().getString(R.string.history) : intent.getCharSequenceExtra("HISTORY");

        // コンピュータの手を表示
        comHand();

        // playerが選んだ手を表示
        switch (id){
            case R.id.rock:
                playerImg.setImageResource(R.drawable.rockcom);
                myHand = ROCK;
                break;
            case R.id.scissors:
                playerImg.setImageResource(R.drawable.scissorscom);
                myHand = SCISSORS;
                break;
            case R.id.paper:
                playerImg.setImageResource(R.drawable.papercom);
                myHand = PAPER;
                break;
            default:
                myHand = ROCK;
                break;
        }

        // 結果表示
        result();
    }


    // コンピュータの出す手の処理
    private void comHand() {
        rand = (int) (Math.random() * 3);
        switch (rand) {
            case 0:
                comImg.setImageResource(R.drawable.rockcom);
                comHand = ROCK;
                break;
            case 1:
                comImg.setImageResource(R.drawable.papercom);
                comHand = PAPER;
                break;
            case 2:
                comImg.setImageResource(R.drawable.scissorscom);
                comHand = SCISSORS;
                break;
            default:
                System.out.println("エラー");
        }
    }


    // 結果処理
    private void result(){
        // 勝敗判定
        tvResult = findViewById(R.id.result);
        if (comHand == myHand){
            tvResult.setText("引き分け");
            drawCnt++;
            history = history + "△";
        }else if(myHand == comHand + 1 || myHand + 2 == comHand){
            tvResult.setText("あなたの勝ち");
            winCnt++;
            history = history + "o";
        }else {
            tvResult.setText("あなたの負け");
            loseCnt++;
            history = history + "×";
        }

        // 対戦履歴更新
        tvDrawCnt = findViewById(R.id.drawCnt);
        tvDrawCnt.setText("引き分け：" + String.valueOf(drawCnt));
        tvWinCnt = findViewById(R.id.winCnt);
        tvWinCnt.setText("勝：" + String.valueOf(winCnt));
        tvLostCnt = findViewById(R.id.loseCnt);
        tvLostCnt.setText("敗：" + String.valueOf(loseCnt));
        tvHistory = findViewById(R.id.history);
        tvHistory.setText(history);

        // 対戦回数カウントアップ
        tvTotalCnt = findViewById(R.id.totalCnt);
        totalCnt++;
        tvTotalCnt.setText("回数：" + String.valueOf(totalCnt));

        // 勝率計算
        tvWinPer = findViewById(R.id.winPer);
        winPer = (double) winCnt / (double) totalCnt * 100; //明示的にdoubleに変換して計算
        tvWinPer.setText("勝率：" + String.format("%.2f", winPer) + "%");
    }

    // 画面遷移(戻るボタン)
    public void onReturnClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("WIN", winCnt);
        intent.putExtra("LOSE", loseCnt);
        intent.putExtra("DRAW", drawCnt);
        intent.putExtra("TOTAL", totalCnt);
        intent.putExtra("WINPER", winPer);
        intent.putExtra("HISTORY", history);
        startActivity(intent);
    }

    // リセット処理
    public void onResetClicked(View view) {
        tvWinCnt.setText(getResources().getString(R.string.win));
        tvLostCnt.setText(getResources().getString(R.string.lose));
        tvDrawCnt.setText(getResources().getString(R.string.draw));
        tvHistory.setText(getResources().getString(R.string.history));
        tvTotalCnt.setText(getResources().getString(R.string.total));
        tvWinPer.setText(getResources().getString(R.string.winPer));
        winCnt = 0;
        loseCnt = 0;
        drawCnt = 0;
        totalCnt = 0;
        winPer = 0;
        history = getResources().getString(R.string.history);
    }
}
