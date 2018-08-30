package com.fucaijin.sayyouloveme.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fucaijin.sayyouloveme.R;
import com.fucaijin.sayyouloveme.utils.ConvertUtils;
import com.tencent.stat.StatService;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.fucaijin.sayyouloveme.R.mipmap.alipay;
import static com.fucaijin.sayyouloveme.R.mipmap.weixin_pay;

public class SettingActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView qrCode;
    private FrameLayout alipayBtn;
    private FrameLayout weixinPayBtn;
    private ImageView emojiReviewIv;
    private CheckBox showEmojiCb;
    private CheckBox showDefaultEmojiCb;
    private EditText whiteBtnResponseEt;
    private EditText redBtnResponseEt;
    private EditText smallTextEt;
    private EditText bigTextEt;
    private EditText redBtnTextEt;
    private EditText whiteBtnTextEt;
    private CheckBox backDisableCb;

    private boolean isDisableBack;//true就是禁用
    private boolean isShowEmoji;
    private boolean isShowDefaultEmoji;
    private boolean isNoneEmoji = false;
    private boolean isFirstRun = true;
    private Bitmap customEmojiBitmap;

//    用到此变量的相关代码已注销，因此此处也暂时注销
//    private TextView pleaseContactNetTv;
//    private TextView rewardCountTv;
//    private TextView rewardTv1;
//    private TextView rewardTv2;

    private CheckBox openAnimCb;
    private CheckBox alphaAnimCb;
    private CheckBox scaleAnimCb;
    private CheckBox translateAnimCb;
    private SharedPreferences infoSp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        infoSp = getSharedPreferences("infoSp", MODE_PRIVATE);
        initUi();
        resetLastData();
        isFirstRun = false;
    }

    /**
     * 恢复上次编辑的信息
     */
    private void resetLastData() {
        String smallText = infoSp.getString("smallText", "hey，小姐姐");
        String bigText = infoSp.getString("bigText", "喜欢我吗");
        String redBtnText = infoSp.getString("redBtnText", "喜欢");
        String whiteBtnText = infoSp.getString("whiteBtnText", "不喜欢");
        String redBtnResponse = infoSp.getString("redBtnResponse", "好巧呀，我也是(oﾟ▽ﾟ)o");
        String whiteButtonResponse = infoSp.getString("whiteButtonResponse", "再考虑考虑\n" +
                "我游泳好，一次能救两个\n" +
                "我家快拆迁了\n" +
                "给你买蛋糕\n" +
                "带你看电影\n" +
                "带你去旅行\n" +
                "工资卡给你管\n" +
                "每天给你做好吃的\n" +
                "给你买西瓜\n" +
                "带你吃冰淇淋\n" +
                "带你去看海");

        boolean isDisableBack = infoSp.getBoolean("isDisableBack", true);
        boolean isOpenAnim = infoSp.getBoolean("isOpenAnim", true);
        boolean isOpenAlphaAnim = infoSp.getBoolean("isOpenAlphaAnim", true);
        boolean isOpenScaleAnim = infoSp.getBoolean("isOpenScaleAnim", true);
        boolean isOpenTranslateAnim = infoSp.getBoolean("isOpenTranslateAnim", true);
        boolean isShowEmoji = infoSp.getBoolean("isShowEmoji", true);
        boolean isShowDefaultEmoji = infoSp.getBoolean("isShowDefaultEmoji", true);

        smallTextEt.setText(smallText);
        bigTextEt.setText(bigText);
        redBtnTextEt.setText(redBtnText);
        whiteBtnTextEt.setText(whiteBtnText);
        redBtnResponseEt.setText(redBtnResponse);
        whiteBtnResponseEt.setText(whiteButtonResponse);

        backDisableCb.setChecked(isDisableBack);
        openAnimCb.setChecked(isOpenAnim);
        alphaAnimCb.setChecked(isOpenAlphaAnim);
        scaleAnimCb.setChecked(isOpenScaleAnim);
        translateAnimCb.setChecked(isOpenTranslateAnim);
        showEmojiCb.setChecked(isShowEmoji);
        showDefaultEmojiCb.setChecked(isShowDefaultEmoji);

        if (isShowEmoji) {
            emojiReviewIv.setVisibility(View.VISIBLE);
        } else {
            emojiReviewIv.setVisibility(View.GONE);
        }
    }

    private void initGetGirlData() {
        smallTextEt.setText("hey，小姐姐");
        bigTextEt.setText("喜欢我吗");
        redBtnTextEt.setText("喜欢");
        whiteBtnTextEt.setText("不喜欢");
        redBtnResponseEt.setText("好巧呀，我也是(oﾟ▽ﾟ)o");
        whiteBtnResponseEt.setText("再考虑考虑\n我游泳好，一次能救两个\n我家快拆迁了\n给你买蛋糕\n带你看电影\n带你去旅行\n工资卡给你管\n每天给你做好吃的\n给你买西瓜\n带你吃冰淇淋\n带你去看海");
        resetDefaulConfig();
    }

    /**
     * 设置选项为默认，例如所有的checkBox
     */
    private void resetDefaulConfig() {
        backDisableCb.setChecked(true);
        showEmojiCb.setChecked(true);
        showDefaultEmojiCb.setChecked(true);
        openAnimCb.setChecked(true);
        alphaAnimCb.setChecked(true);
        scaleAnimCb.setChecked(true);
        translateAnimCb.setChecked(true);
        emojiReviewIv.setImageResource(R.drawable.emoji_give_you_flower);
    }

    private void initGetBoyData() {
        smallTextEt.setText("hey，小哥哥");
        bigTextEt.setText("喜欢我吗");
        redBtnTextEt.setText("喜欢");
        whiteBtnTextEt.setText("不喜欢");
        whiteBtnResponseEt.setText("再考虑考虑\n我不会掉河里\n我会游泳\n我这么可爱你真的不要嘛\n再考虑考虑？\n你敢再按一次不喜欢？\n这么听话，你一定是喜欢我");
        redBtnResponseEt.setText("好巧呀，我也是(oﾟ▽ﾟ)o");
        resetDefaulConfig();
    }

    private void initUi() {
        TextView shareTv = (TextView) findViewById(R.id.setting_share_tv);
        TextView rewardTv = (TextView) findViewById(R.id.setting_reward_tv);
        whiteBtnResponseEt = (EditText) findViewById(R.id.setting_white_btn_response_et);
        redBtnResponseEt = (EditText) findViewById(R.id.setting_red_btn_response_et);
        smallTextEt = (EditText) findViewById(R.id.setting_small_text_et);
        bigTextEt = (EditText) findViewById(R.id.setting_big_text_et);
        redBtnTextEt = (EditText) findViewById(R.id.setting_red_btn_text_et);
        whiteBtnTextEt = (EditText) findViewById(R.id.setting_white_btn_text_et);
        showEmojiCb = (CheckBox) findViewById(R.id.setting_show_emoji);
        showDefaultEmojiCb = (CheckBox) findViewById(R.id.setting_use_default_emoji);
        backDisableCb = (CheckBox) findViewById(R.id.setting_back_disable_cb);
        emojiReviewIv = (ImageView) findViewById(R.id.setting_emoji_review_iv);
        Button getOtherEmojiBtn = (Button) findViewById(R.id.setting_select_other_emoji);
        Button startBtn = (Button) findViewById(R.id.setting_start_get_object_btn);
        Button resetGetBoyBtn = (Button) findViewById(R.id.setting_reset_get_boy);
        Button resetGetGirlBtn = (Button) findViewById(R.id.setting_reset_get_girl);

        openAnimCb = (CheckBox) findViewById(R.id.setting_open_anim_cb);
        alphaAnimCb = (CheckBox) findViewById(R.id.setting_alpha_anim_cb);
        scaleAnimCb = (CheckBox) findViewById(R.id.setting_scale_anim_cb);
        translateAnimCb = (CheckBox) findViewById(R.id.setting_translate_anim_cb);

        shareTv.setOnClickListener(this);
        rewardTv.setOnClickListener(this);
        shareTv.setOnLongClickListener(this);
        rewardTv.setOnLongClickListener(this);

        getOtherEmojiBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        resetGetBoyBtn.setOnClickListener(this);
        resetGetGirlBtn.setOnClickListener(this);

        initCheckBox();

    }

    private void initCheckBox() {

//        设置显示表情CheckBox的图案及图案大小
        Drawable showEmojiDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        showEmojiDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        showEmojiCb.setCompoundDrawables(showEmojiDrawable, null, null, null);

//        设置使用表情CheckBox的图案及图案大小
        Drawable showDefaultEmojiDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        showDefaultEmojiDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        showDefaultEmojiCb.setCompoundDrawables(showDefaultEmojiDrawable, null, null, null);

//        设置禁止使用返回键
        Drawable disableBackDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        disableBackDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        backDisableCb.setCompoundDrawables(disableBackDrawable, null, null, null);

//        打开动画
        Drawable openAnimateDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        openAnimateDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        openAnimCb.setCompoundDrawables(openAnimateDrawable, null, null, null);

//        透明度动画
        Drawable alphaAnimateDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        alphaAnimateDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        alphaAnimCb.setCompoundDrawables(alphaAnimateDrawable, null, null, null);

//        缩放动画
        Drawable scaleAnimateDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        scaleAnimateDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        scaleAnimCb.setCompoundDrawables(scaleAnimateDrawable, null, null, null);

//        移动动画
        Drawable translateAnimateDrawable = this.getResources().getDrawable(R.drawable.checkbox_selector, null);
        translateAnimateDrawable.setBounds(0, 0, ConvertUtils.dp2px(this, 20), ConvertUtils.dp2px(this, 21));
        translateAnimCb.setCompoundDrawables(translateAnimateDrawable, null, null, null);

        showEmojiCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isFirstRun) {
                    if (b) {
                        emojiReviewIv.setVisibility(View.VISIBLE);
                    } else {
                        emojiReviewIv.setVisibility(View.GONE);
                    }
                } else {
                    if (b) {
                        StatService.trackCustomKVEvent(SettingActivity.this, "ShowEmoji", null);
                        emojiReviewIv.setVisibility(View.VISIBLE);
                        Toast.makeText(SettingActivity.this, "开启了显示表情", Toast.LENGTH_SHORT).show();
                    } else {
                        StatService.trackCustomKVEvent(SettingActivity.this, "CloseShowEmoji", null);
                        emojiReviewIv.setVisibility(View.GONE);
                        Toast.makeText(SettingActivity.this, "关闭了显示表情", Toast.LENGTH_SHORT).show();
                    }
                }
                isShowEmoji = b;
            }
        });

        showDefaultEmojiCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isFirstRun) {
                    if (b) {
                        emojiReviewIv.setImageResource(R.drawable.emoji_give_you_flower);
                    } else {
                        emojiReviewIv.setImageResource(R.color.colorWhite);
                        isNoneEmoji = true;
                    }
                } else {
                    if (b) {
                        StatService.trackCustomKVEvent(SettingActivity.this, "DefaultEmoji", null);
                        emojiReviewIv.setImageResource(R.drawable.emoji_give_you_flower);
                        Toast.makeText(SettingActivity.this, "使用默认表情图", Toast.LENGTH_SHORT).show();
                    } else {
                        StatService.trackCustomKVEvent(SettingActivity.this, "CloseDefaultEmoji", null);
                        Toast.makeText(SettingActivity.this, "取消使用默认表情图", Toast.LENGTH_SHORT).show();
                        emojiReviewIv.setImageResource(R.color.colorWhite);
                        isNoneEmoji = true;
                    }
                }
                isShowDefaultEmoji = b;
            }
        });

        backDisableCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isFirstRun) {
                    if (b) {
                        StatService.trackCustomKVEvent(SettingActivity.this, "EnableBackBtn", null);
                        Toast.makeText(SettingActivity.this, "已经禁用返回键", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SettingActivity.this, "返回键已可使用", Toast.LENGTH_SHORT).show();
                        StatService.trackCustomKVEvent(SettingActivity.this, "AbleBackBtn", null);
                    }
                }
                isDisableBack = b;
            }
        });

        openAnimCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    StatService.trackCustomKVEvent(SettingActivity.this, "OpenAnimateCb", null);
                }else {
                    StatService.trackCustomKVEvent(SettingActivity.this, "CloseAnimateCb", null);
                }
            }
        });

        alphaAnimCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    StatService.trackCustomKVEvent(SettingActivity.this, "OpenAlphaCb", null);
                }else {
                    StatService.trackCustomKVEvent(SettingActivity.this, "CloseAlphaCb", null);
                }
            }
        });

        scaleAnimCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    StatService.trackCustomKVEvent(SettingActivity.this, "OpenScaleCb", null);
                }else {
                    StatService.trackCustomKVEvent(SettingActivity.this, "CloseScaleCb", null);
                }
            }
        });

        translateAnimCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    StatService.trackCustomKVEvent(SettingActivity.this, "OpenTranslateCb", null);
                }else {
                    StatService.trackCustomKVEvent(SettingActivity.this, "CloseTranslateCb", null);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_share_tv:
                showShareDialog();
                break;
            case R.id.setting_reward_tv:
                showRewardDialog();
                break;
            case R.id.setting_start_get_object_btn:
                StatService.trackCustomKVEvent(this, "PlayBtn", null);//统计点击播放按钮的次数
                startMainActivity();
                break;
            case R.id.reward_alipay_btn_fl:
                StatService.trackCustomKVEvent(this, "Alipay", null);//统计支付宝按钮的点击次数
//                Drawable drawable = getResources().getDrawable(R.drawable.reward_alipay_btn_bg_selected, null);
                qrCode.setImageResource(alipay);
                alipayBtn.setBackgroundResource(R.drawable.reward_alipay_btn_bg_selected);
                weixinPayBtn.setBackgroundResource(R.drawable.reward_weixin_pay_btn_bg_normal);
                break;
            case R.id.reward_weixin_pay_btn_fl:
                StatService.trackCustomKVEvent(this, "Weixinpay", null);//统计支付宝按钮的点击次数
                qrCode.setImageResource(weixin_pay);
                alipayBtn.setBackgroundResource(R.drawable.reward_alipay_btn_bg_normal);
                weixinPayBtn.setBackgroundResource(R.drawable.reward_weixin_pay_btn_bg_selected);
                break;
            case R.id.setting_select_other_emoji:
                if (isShowEmoji) {
                    StatService.trackCustomKVEvent(this, "CustomEmojiBtn", null);
                    showDefaultEmojiCb.setChecked(false);
                    selectImage();
                } else {
                    Toast.makeText(this, "请先打开显示表情图", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.setting_reset_get_boy:
                StatService.trackCustomKVEvent(SettingActivity.this, "GetBoyBtn", null);
                initGetBoyData();
                break;
            case R.id.setting_reset_get_girl:
                StatService.trackCustomKVEvent(SettingActivity.this, "GetGirlBtn", null);
                initGetGirlData();
                break;
        }
    }

    private void startMainActivity() {
        String smallTextStr = smallTextEt.getText().toString();
        String bigTextStr = bigTextEt.getText().toString();
        String redBtnTextStr = redBtnTextEt.getText().toString();
        String whiteBtnTextStr = whiteBtnTextEt.getText().toString();
        String redBtnResponseStr = redBtnResponseEt.getText().toString();

        String whiteButtonResponseStr = whiteBtnResponseEt.getText().toString();
        String[] whiteButtonResponseArray = whiteButtonResponseStr.split("\n");

//        存储数据到本地
        SharedPreferences.Editor edit = infoSp.edit();
        edit.putString("smallText", smallTextStr);
        edit.putString("bigText", bigTextStr);
        edit.putString("redBtnText", redBtnTextStr);
        edit.putString("whiteBtnText", whiteBtnTextStr);
        edit.putString("redBtnResponse", redBtnResponseStr);
        edit.putString("whiteButtonResponse", whiteButtonResponseStr);
        edit.putBoolean("isDisableBack", isDisableBack);
        edit.putBoolean("isOpenAnim", openAnimCb.isChecked());
        edit.putBoolean("isOpenAlphaAnim", alphaAnimCb.isChecked());
        edit.putBoolean("isOpenScaleAnim", scaleAnimCb.isChecked());
        edit.putBoolean("isOpenTranslateAnim", isDisableBack);
        edit.putBoolean("isShowEmoji", isShowEmoji);
        edit.putBoolean("isShowDefaultEmoji", isShowDefaultEmoji);
        edit.apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("smallTextStr", smallTextStr);
        intent.putExtra("bigTextStr", bigTextStr);
        intent.putExtra("redBtnTextStr", redBtnTextStr);
        intent.putExtra("whiteBtnTextStr", whiteBtnTextStr);
        intent.putExtra("redBtnResponseStr", redBtnResponseStr);
        intent.putExtra("whiteButtonResponseArray", whiteButtonResponseArray);
        intent.putExtra("isDisableBack", isDisableBack);
        intent.putExtra("isShowEmoji", isShowEmoji);
        intent.putExtra("isShowDefaultEmoji", isShowDefaultEmoji);
        intent.putExtra("isNoneEmoji", isNoneEmoji);//如果不选用默认表情，也没选择图片的话，就是空图片
        intent.putExtra("isOpenAnim", openAnimCb.isChecked());
        intent.putExtra("isOpenAlphaAnim", alphaAnimCb.isChecked());
        intent.putExtra("isOpenScaleAnim", scaleAnimCb.isChecked());
        intent.putExtra("isOpenTranslateAnim", translateAnimCb.isChecked());
        if (!isNoneEmoji) {//如果不是空图，就传输图像到下一个页面(MainActivity)
            Bundle b = new Bundle();
            b.putParcelable("customEmojiBitmap", customEmojiBitmap);
            intent.putExtras(b);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 弹出赞赏的弹窗
     */
    private void showRewardDialog() {
        StatService.trackCustomKVEvent(this, "RewardBtn", null);//统计赞赏Dialog的被点击次数
        StatService.trackCustomBeginEvent(this, "RewardBtn", "run_time");//开始计算赞赏Dialog的运行时长

        LayoutInflater inflaterRewardView = LayoutInflater.from(this);
        View dialogReward = inflaterRewardView.inflate(R.layout.setting_reward_dialog_view, null);
        initRewardDialogUi(dialogReward);
        AlertDialog alertDialogReward = new AlertDialog.Builder(this).setView(dialogReward).create();
        alertDialogReward.show();

//        监听赞赏弹窗的关闭，用于统计弹窗的显示时长
        alertDialogReward.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                StatService.trackCustomEndEvent(SettingActivity.this, "RewardBtn", "run_time");//结束计算赞赏Dialog的运行时长
            }
        });
    }

    /**
     * 初始化赞赏弹窗Ui,并默认选中的是微信支付
     *
     * @param dialogReward 赞赏弹窗的View
     */
    private void initRewardDialogUi(View dialogReward) {
        TextView rewardTv = dialogReward.findViewById(R.id.setting_dialog_reward_text_tv);

//        pleaseContactNetTv = dialogReward.findViewById(R.id.setting_dialog_reward_contact_net_see_count);
//        rewardTv1 = dialogReward.findViewById(R.id.setting_dialog_reward_text_1);
//        rewardCountTv = dialogReward.findViewById(R.id.setting_dialog_reward_count_tv);
//        rewardTv2 = dialogReward.findViewById(R.id.setting_dialog_reward_text_2);

        qrCode = dialogReward.findViewById(R.id.setting_dialog_reward_qr_code_iv);
        alipayBtn = dialogReward.findViewById(R.id.reward_alipay_btn_fl);
        weixinPayBtn = dialogReward.findViewById(R.id.reward_weixin_pay_btn_fl);
        alipayBtn.setOnClickListener(this);
        weixinPayBtn.setOnClickListener(this);

        qrCode.setImageResource(weixin_pay);
        alipayBtn.setBackgroundResource(R.drawable.reward_alipay_btn_bg_normal);
        weixinPayBtn.setBackgroundResource(R.drawable.reward_weixin_pay_btn_bg_selected);

        String[] randomTextList = {
                "我猜，你一定长得很好看",
                "你这么好，若不幸福天理难容",
                "感谢小哥哥/小姐姐的支持 Mua~",
                "妈快来看！这有个小仙女要赞我啦",
                "妈快来看！这有个小帅哥要赞我啦",
                "嘘~ 偷偷扫，别被对象看到啦",
                "谢谢你请小哥哥吃好吃哒",
                "早餐有着落啦",
                "Thank you very much !",
                "你一定是从天上下来的对吗？",
                "该出手时就出手啊！",
                "你是我的眼，带我领略四季的变换...",
                "午饭有着落啦",
                "晚饭有着落啦",
                "听说，扫码的人都中了彩票，没买彩票的人都能捡到彩票",
                "上次有个人扫了码，他开上了宝马",
                "上次有个人扫了码，他住上了别墅",
                "上次有帅哥扫了码，当天就找到了女朋友",
                "有个姑娘扫了码，有个180的帅哥疯狂追她，开法拉利追",
                "上次有个人扫了码，他开上了路虎",
                "上次有个人扫了码，第二天就涨工资了",
                "听说扫码的人，第二天都找到了对象",
                "谢谢你的赞赏，对我把App做得更好是莫大的鼓励",
                "听说赞赏的小姐姐都变漂亮了呢",
                "听说赞赏的小哥哥都变帅了呢",
                "听说赞赏的都长高了呢",
                "谢谢你请小哥吃冰棍",
                "谢谢你请小哥吃冰淇淋",
                "谢谢你请小哥吃西瓜",
                "我希望有个如你一般的人\n如山间清爽的风\n如古城温暖的光"
        };

//        随机感谢语
        Random random = new Random();
        int num = random.nextInt(randomTextList.length);
        String randomText = randomTextList[num];
        rewardTv.setText(randomText);

//        判断是否有网，没网的话设置默认观看，有网的话设置随机的增长数 TODO 此功能暂时较难实现，暂时不设定
//        if (isNetworkAvalible()) {
//            rewardTv1.setVisibility(View.VISIBLE);
//            rewardCountTv.setVisibility(View.VISIBLE);
//            rewardTv2.setVisibility(View.VISIBLE);
//            pleaseContactNetTv.setVisibility(View.GONE);
//
//            int randomCount = getRandomCount();
//            rewardCountTv.setText(String.valueOf(randomCount));
//        } else {
//            pleaseContactNetTv.setVisibility(View.VISIBLE);
//            pleaseContactNetTv.setText("连接网络可看赞赏人数");
//
//            rewardTv1.setVisibility(View.GONE);
//            rewardCountTv.setVisibility(View.GONE);
//            rewardTv2.setVisibility(View.GONE);
//
//        }

    }

    /**
     * @return 生成一个随机的数，用于模拟赞赏人数，但数字只能变大不能降低，因此暂未实现此功能，暂时注销
     */
    private int getRandomCount() {
//        获取指定时间
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2018, 5, 16);
        long millis = calendar.getTimeInMillis();

//        获取当前时间毫秒
        Date date = new Date();
        long time = date.getTime();

        long l = (time - millis) / 1000 * 60 * 60;//经过了多少小时

        return (int) ((time - millis) / 1000);
    }

    /**
     * @return 判断是否有网络的结果
     */
    private boolean isNetworkAvalible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            //否则调用旧版本方法
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 弹出分享的弹窗
     */
    private void showShareDialog() {
        StatService.trackCustomKVEvent(this, "ShareBtn", null);
        StatService.trackCustomBeginEvent(this, "ShareBtn", "run_time");//开始计算赞赏Dialog的运行时长

        LayoutInflater inflaterShareView = LayoutInflater.from(this);
        View dialogShare = inflaterShareView.inflate(R.layout.setting_share_dialog_view, null);

        AlertDialog alertDialogShare = new AlertDialog.Builder(this).setView(dialogShare).create();
        alertDialogShare.show();
        //        监听赞赏弹窗的关闭，用于统计弹窗的显示时长
        alertDialogShare.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                StatService.trackCustomEndEvent(SettingActivity.this, "ShareBtn", "run_time");//结束计算赞赏Dialog的运行时长
            }
        });
    }

    private void selectImage() {
//        动弹权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            // 激活系统图库，选择一张图片
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
            startActivityForResult(Intent.createChooser(intent, "选择表情图片"), PHOTO_REQUEST_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                customEmojiBitmap = data.getParcelableExtra("data");
                emojiReviewIv.setVisibility(View.VISIBLE);
                emojiReviewIv.setImageBitmap(customEmojiBitmap);
                isNoneEmoji = false;
            }
        }
    }

    /**
     * 裁剪压缩图片
     *
     * @param uri 要裁剪的图片地址
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.setting_share_tv:
                TextView textView = new TextView(this);
                textView.setText("打开网址下载小秘密：\nhttps://github.com/fucaijin/fucaijin_release_app/raw/master/sayyouloveme.apk");
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkGray));
                textView.setTextSize(ConvertUtils.sp2px(getApplicationContext(), 5));
                textView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                AlertDialog alertDialogShare = new AlertDialog.Builder(this).setView(textView).create();
                alertDialogShare.show();
                break;
            case R.id.setting_reward_tv:
//                LayoutInflater inflaterNotLike = LayoutInflater.from(this);
//                View dialogShare = inflaterNotLike.inflate(R.layout.setting_share_dialog_view, null);
//
//                AlertDialog moreButifel = new AlertDialog.Builder(this).setView(dialogShare).create();
//                moreButifel.show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.trackCustomKVEvent(this, "SettingPage", null);// MTA:进入首页事件,统计用户进入首页的次数
        StatService.trackCustomBeginEvent(this, "SettingPage", "run_time");//开始计算本页面的运行时长
    }

    @Override
    protected void onStop() {
        super.onStop();
        StatService.trackCustomEndEvent(this, "SettingPage", "run_time");//结束计算本页面的运行时长
    }
}

