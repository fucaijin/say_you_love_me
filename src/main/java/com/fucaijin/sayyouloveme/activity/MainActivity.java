package com.fucaijin.sayyouloveme.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fucaijin.sayyouloveme.R;

import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private boolean isNoneEmoji;
    private Bitmap emojiBitmap;
    private boolean isShowDefaultEmoji;
    private boolean isShowEmoji;
    private boolean isDisableBack;
    private String[] whiteButtonResponseArrays;
    private String redBtnResponseStr;
    private String whiteBtnTextStr;
    private String redBtnTextStr;
    private String bigTextStr;
    private String smallTextStr;
    private int responseIndex = 0;
    private double likeTimes = 0;
    private Handler handler = new Handler();
    private boolean isOpenAnim;
    private boolean isOpenAlphaAnim;
    private boolean isOpenScaleAnim;
    private boolean isOpenTranslateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initUi();
    }

    private void initData() {
        //TODO 获取传过来的数据，并实现相应逻辑
        smallTextStr = getIntent().getStringExtra("smallTextStr");
        bigTextStr = getIntent().getStringExtra("bigTextStr");
        redBtnTextStr = getIntent().getStringExtra("redBtnTextStr");
        whiteBtnTextStr = getIntent().getStringExtra("whiteBtnTextStr");
        redBtnResponseStr = getIntent().getStringExtra("redBtnResponseStr");
        whiteButtonResponseArrays = getIntent().getStringArrayExtra("whiteButtonResponseArray");
        isDisableBack = getIntent().getBooleanExtra("isDisableBack", true);
        isShowEmoji = getIntent().getBooleanExtra("isShowEmoji", true);
        isShowDefaultEmoji = getIntent().getBooleanExtra("isShowDefaultEmoji", true);
        isNoneEmoji = getIntent().getBooleanExtra("isNoneEmoji", false);

        isOpenAnim = getIntent().getBooleanExtra("isOpenAnim", true);
        isOpenAlphaAnim = getIntent().getBooleanExtra("isOpenAlphaAnim", true);
        isOpenScaleAnim = getIntent().getBooleanExtra("isOpenScaleAnim", true);
        isOpenTranslateAnim = getIntent().getBooleanExtra("isOpenTranslateAnim", true);

        if (!isNoneEmoji) {
            Bundle extras = getIntent().getExtras();
            emojiBitmap = extras.getParcelable("customEmojiBitmap");
        }

    }

    private void initUi() {
        TextView smallTextTv = (TextView) findViewById(R.id.main_small_text_tv);
        TextView bigTextTv = (TextView) findViewById(R.id.main_big_text_tv);
        ImageView emojiIv = (ImageView) findViewById(R.id.main_emoji_iv);
        Button btLike = (Button) findViewById(R.id.main_like_btn);
        Button btNotLike = (Button) findViewById(R.id.main_not_like_btn);

        btLike.setOnClickListener(this);
        btNotLike.setOnClickListener(this);

        smallTextTv.setText(smallTextStr);
        bigTextTv.setText(bigTextStr);

        if (!isNoneEmoji) {
            emojiIv.setImageBitmap(emojiBitmap);
        }

        btLike.setText(redBtnTextStr);
        btNotLike.setText(whiteBtnTextStr);

        if (isShowDefaultEmoji) {
            emojiIv.setImageResource(R.drawable.emoji_give_you_flower);
        }

        if (isShowEmoji) {
            emojiIv.setVisibility(View.VISIBLE);
        } else {
            emojiIv.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_not_like_btn:
                // TODO 点了按钮5秒后关闭
                LayoutInflater inflater = LayoutInflater.from(this);
                View dialogView = inflater.inflate(R.layout.main_dialog_view, null);
                TextView tv = dialogView.findViewById(R.id.tv_dialog_text);
                tv.setText(whiteButtonResponseArrays[responseIndex]);
                responseIndex++;
                if (responseIndex == whiteButtonResponseArrays.length) {
                    responseIndex = 0;
                }
                AlertDialog alertDialog = new AlertDialog.Builder(this).
                        setView(dialogView).
                        create();
                alertDialog.show();
                break;
            case R.id.main_like_btn:
                LayoutInflater inflaterNotLike = LayoutInflater.from(this);
                View dialogViewNotLike = inflaterNotLike.inflate(R.layout.main_dialog_view, null);
                TextView tvNotLike = dialogViewNotLike.findViewById(R.id.tv_dialog_text);
                tvNotLike.setText(redBtnResponseStr);
                AlertDialog alertDialogNotLike = new AlertDialog.Builder(this).
                        setView(dialogViewNotLike).
                        create();

                if(isOpenAnim){
                    showAnimate(alertDialogNotLike);
                }

                alertDialogNotLike.show();
                break;
        }
    }

    private void showAnimate(AlertDialog alertDialogNotLike) {
        if (likeTimes <= 3) {
            alertDialogNotLike.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                    FrameLayout mainLl = (FrameLayout) findViewById(R.id.activity_main);
                    Random random = new Random();

                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int windowHeigth = dm.heightPixels;
                    int windowWidth = dm.widthPixels;

//                            1.创建随机范围内（1-66）个数的心形
                    for (int i = 1; i <= random.nextInt(66) + 15; i++) {
                        Log.v("123", "i =" + i);
                        //创建随机随机范围内大小、透明度的心形
                        ImageView imageView = new ImageView(getApplicationContext(), null);
                        imageView.setImageResource(R.mipmap.red_heart);

//                            2.随机大小
                        int size = random.nextInt(300) + 50;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);//两个400分别为添加图片的大小
                        imageView.setLayoutParams(params);

                        mainLl.addView(imageView);

//                            3.设置随机位置(margin值)，必须得在屏幕范围之内
                        int positionHeight = random.nextInt(windowHeigth);
                        int positionWidth = random.nextInt(windowWidth);
                        Log.v("123", "positionHeight =" + positionHeight + "positionWidth =" + positionWidth);
                        setMargins(imageView, positionWidth, positionHeight, 0, 0);//给设置左和上设置了随机位置

//                            4.设置没有动画时候的随机透明度
                        float randomAlpha = random.nextFloat();
                        if (randomAlpha < 0.6) {
                            randomAlpha += 0.3;
                        }
                        imageView.setAlpha(randomAlpha);

                        AnimatorSet animatorSet = new AnimatorSet();//组合动画
                        //设置随机透明度动画、位移、缩放

                        float randomAlphaAnim1 = random.nextFloat();
                        float randomAlphaAnim2 = random.nextFloat();
                        float randomAlphaAnim3 = random.nextFloat();
                        float randomAlphaAnim4 = random.nextFloat();
                        float randomAlphaAnim5 = random.nextFloat();
                        float randomAlphaAnim6 = random.nextFloat();
                        float randomAlphaAnim7 = random.nextFloat();
                        float randomAlphaAnim8 = random.nextFloat();
                        float randomAlphaAnim9 = random.nextFloat();
                        float randomAlphaAnim10 = random.nextFloat();

                        if (randomAlphaAnim1 < 0.6) {
                            randomAlphaAnim1 += 0.3;
                        } else if (randomAlphaAnim2 < 0.6) {
                            randomAlphaAnim2 += 0.3;
                        } else if (randomAlphaAnim3 < 0.6) {
                            randomAlphaAnim3 += 0.3;
                        } else if (randomAlphaAnim4 < 0.6) {
                            randomAlphaAnim4 += 0.3;
                        } else if (randomAlphaAnim5 < 0.6) {
                            randomAlphaAnim5 += 0.3;
                        } else if (randomAlphaAnim6 < 0.6) {
                            randomAlphaAnim6 += 0.3;
                        } else if (randomAlphaAnim7 < 0.6) {
                            randomAlphaAnim7 += 0.3;
                        } else if (randomAlphaAnim8 < 0.6) {
                            randomAlphaAnim8 += 0.3;
                        } else if (randomAlphaAnim9 < 0.6) {
                            randomAlphaAnim9 += 0.3;
                        } else if (randomAlphaAnim10 < 0.6) {
                            randomAlphaAnim10 += 0.3;
                        }

//                        5.随机透明度动画
                        ObjectAnimator alpha = null;
                        if(isOpenAlphaAnim){
                            alpha = ObjectAnimator.ofFloat(imageView, "alpha",
                                    randomAlphaAnim1,
                                    randomAlphaAnim2,
                                    randomAlphaAnim3,
                                    randomAlphaAnim4,
                                    randomAlphaAnim5,
                                    randomAlphaAnim6,
                                    randomAlphaAnim7,
                                    randomAlphaAnim8,
                                    randomAlphaAnim9,
                                    randomAlphaAnim10,
                                    randomAlphaAnim1
                            );
                            alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
                            alpha.setRepeatCount(-1);//设置动画重复次数，这里-1代表无限
//                            alpha.setRepeatMode(Animation.);//设置动画循环模式。
                        }

//                                6.随机缩放动画
                        ObjectAnimator scaleX = null,scaleY = null;
                        if(isOpenScaleAnim){
                            scaleX = ObjectAnimator.ofFloat(imageView, "scaleX",
                                    randomAlphaAnim1,
                                    randomAlphaAnim2,
                                    randomAlphaAnim3,
                                    randomAlphaAnim4,
                                    randomAlphaAnim5,
                                    randomAlphaAnim6,
                                    randomAlphaAnim7,
                                    randomAlphaAnim8,
                                    randomAlphaAnim9,
                                    randomAlphaAnim10,
                                    randomAlphaAnim1);
                            scaleX.setRepeatCount(-1);
                            scaleY = ObjectAnimator.ofFloat(imageView, "scaleY",
                                    randomAlphaAnim1,
                                    randomAlphaAnim2,
                                    randomAlphaAnim3,
                                    randomAlphaAnim4,
                                    randomAlphaAnim5,
                                    randomAlphaAnim6,
                                    randomAlphaAnim7,
                                    randomAlphaAnim8,
                                    randomAlphaAnim9,
                                    randomAlphaAnim10,
                                    randomAlphaAnim1);
                            scaleY.setRepeatCount(-1);
                        }



//                                7.位移动画：将移动距离范围限定在屏幕的1/5到1/8之间，原点就是
                        ObjectAnimator translationUp = null;
                        if(isOpenTranslateAnim){
                            int i1 = windowHeigth / 8;
                            int i12 = windowHeigth / 5;
                            int i2 = random.nextInt(i12);
                            if (i2 + i1 < i2) {
                                i2 += i1;
                            }

                            i2 += positionHeight;
                            translationUp = ObjectAnimator.ofFloat(imageView, "Y",
                                    positionHeight,
                                    i2,
                                    positionHeight,
                                    i2,
                                    positionHeight,
                                    i2,
                                    positionHeight);
                            translationUp.setRepeatCount(-1);
                        }

//                                6.随机动画的持续时间
                        int randomTime = random.nextInt(20);
                        if (randomTime + 7 <= 18) {
                            randomTime += 7;
                        }
                        animatorSet.setDuration(randomTime * 1000);
                        animatorSet.setInterpolator(new DecelerateInterpolator());

                        if(isOpenAlphaAnim&&isOpenScaleAnim&&isOpenTranslateAnim){
                            animatorSet.play(alpha).with(scaleX).with(scaleY).with(translationUp);
                        }else if(isOpenAlphaAnim&&isOpenScaleAnim){
                            animatorSet.play(alpha).with(scaleX).with(scaleY);
                        }else if(isOpenAlphaAnim&&isOpenTranslateAnim){
                            animatorSet.play(alpha).with(translationUp);
                        }else if(isOpenScaleAnim&&isOpenTranslateAnim){
                            animatorSet.play(scaleX).with(scaleY).with(translationUp);
                        }else if(isOpenAlphaAnim){
                            animatorSet.play(alpha);
                        }else if(isOpenScaleAnim){
                            animatorSet.play(scaleY).with(scaleX);
                        }else if(isOpenTranslateAnim){
                            animatorSet.play(translationUp);
                        }
                        animatorSet.start();
                    }
                }
            });
            likeTimes++;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        屏蔽返回键
        if (KeyEvent.KEYCODE_BACK == keyCode && isDisableBack) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 给View设置Margin值
     *
     * @param v View
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
