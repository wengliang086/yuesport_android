package com.football.yuedong.happysports.ui;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.football.yuedong.happysports.BuildVars;
import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.controller.PitchStatusCallBack;
import com.football.yuedong.happysports.domain.BaseResponse;
import com.football.yuedong.happysports.domain.PitchsStatus;
import com.football.yuedong.happysports.domain.User;
import com.football.yuedong.happysports.service.SportsInterface;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.user.UserConfig;
import com.football.yuedong.happysports.utils.AndroidUtilities;
import com.football.yuedong.happysports.utils.FileLog;
import com.football.yuedong.happysports.utils.RxUtils;
import com.football.yuedong.happysports.utils.T;
import com.football.yuedong.happysports.views.LayoutHelper;
import com.football.yuedong.happysports.views.MaterialDialog;
import com.football.yuedong.happysports.views.SlideView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liuyanjun on 2015/12/14.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends FragmentActivity {

    private final  static  String TAG = LoginActivity.class.getSimpleName();

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    @ViewById
    TextView login;

    private SportsInterface sportsInterface;

    private PagerAdapter mAdapter;

    private MaterialDialog registerDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportsInterface = SportsService.createSportsService(UserConfig.ACCESS_TOKEN);
    }


    @Click(R.id.login)
    void loginClicked(){

        final MaterialDialog materialDialog = new MaterialDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_login_dialog, null);
        materialDialog.setContentView(view);
        final MaterialEditText username_txt = (MaterialEditText) view.findViewById(R.id.username);
        final MaterialEditText pwd_txt = (MaterialEditText) view.findViewById(R.id.pwd);
        materialDialog.setPositiveButton(android.R.string.yes,
                        new View.OnClickListener() {
                           @Override
                            public void onClick(View v) {
                               _subscriptions.add(sportsInterface.login(username_txt.getText().toString(), pwd_txt.getText().toString())
                                               .subscribeOn(Schedulers.io())
                                               .observeOn(AndroidSchedulers.mainThread())
                                               .subscribe(new Observer<User>() {
                                                   @Override
                                                   public void onCompleted() {
                                                       FileLog.d(TAG, "login succes!");
                                                       materialDialog.dismiss();
                                                   }

                                                   @Override
                                                   public void onError(Throwable e) {

                                                   }

                                                   @Override
                                                   public void onNext(User s) {
                                                       FileLog.d(TAG, "onNext ..!");
                                                       T.show(LoginActivity.this, s.getDesc());
                                                       if (s.isSuccess()) {
                                                           UserConfig.setCurrentUser(s);
                                                           UserConfig.saveConfig(false);
                                                           startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                           finish();
                                                       }
                                                   }
                                               })
                               );
                            }
                       });
        materialDialog.setNegativeButton(android.R.string.no, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    @Click(R.id.register)
    void registerClicked(){
        registerDialog = new MaterialDialog(this);
        RegFrameLayout view = new RegFrameLayout(this);
        registerDialog.setContentView(view);
        registerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }

    public void loginXmpp(String userName, String password){
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMClient.getInstance().chatManager().loadAllConversations();
                        EMClient.getInstance().groupManager().loadAllGroups();

                        FileLog.d("main", "登陆聊天服务器成功！");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                FileLog.d("main", "登陆聊天服务器失败！");
            }
        });
    }





    public class RegFrameLayout extends FrameLayout {

        private int currentViewNum = 0;
        private SlideView[] views = new SlideView[3];

        public RegFrameLayout(Context context) {
            super(context);

            views[0] = new PhoneView(context);
            views[1] = new SmsCodeView(context);
            views[2] = new SubmitUserInfoView(context);
            for(int i= 0; i< 3; i ++){
                views[i].setVisibility(i == 0 ? View.VISIBLE : View.GONE);
                addView(views[i], LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
            }
        }

        public void setPage(int page, boolean animated, Bundle params, boolean back) {
            if (android.os.Build.VERSION.SDK_INT > 13) {
                final SlideView outView = views[currentViewNum];
                final SlideView newView = views[page];
                currentViewNum = page;
                newView.setParams(params);
                newView.onShow();
                newView.setX(back ? -AndroidUtilities.displaySize.x : AndroidUtilities.displaySize.x);
                outView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        outView.setVisibility(View.GONE);
                        outView.setX(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                }).setDuration(300).translationX(back ? AndroidUtilities.displaySize.x : -AndroidUtilities.displaySize.x).start();
                newView.animate().setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        newView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                }).setDuration(300).translationX(0).start();
            } else {
                views[currentViewNum].setVisibility(View.GONE);
                currentViewNum = page;
                views[page].setParams(params);
                views[page].setVisibility(View.VISIBLE);
                views[page].onShow();
            }
        }



        /**
         * register phone view
         */
        public class PhoneView extends SlideView{


            /**
             *
             * @param context
             */

            private boolean nextPressed = false;

            private MaterialEditText editText;


            public PhoneView(Context context) {
                super(context);
                setOrientation(VERTICAL);
                setBackgroundResource(R.color.white);

                editText = new MaterialEditText(context);
                editText.setHint("phone number");
                editText.setSingleLine();
                editText.setGravity(Gravity.LEFT);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);

                addView(editText, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

                LinearLayout.LayoutParams params = (LayoutParams) editText.getLayoutParams();
                params.bottomMargin = AndroidUtilities.dp(10f);
                editText.setLayoutParams(params);
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_NEXT) {
                            onNextPressed();
                            return true;
                        }
                        return false;
                    }
                });

                TextView next = new TextView(context);
                next.setTextAppearance(context, R.style.pay_item_style_header);
                next.setText("下一步");
                next.setGravity(Gravity.CENTER);
                int paddinglr = AndroidUtilities.dp(5);
                int paddingtb = AndroidUtilities.dp(6);
                next.setPadding(paddinglr, paddingtb, paddinglr, paddingtb);
                next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNextPressed();
                    }
                });
                next.setBackgroundResource(R.color.bg_gray);
                addView(next, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

                LinearLayout.LayoutParams params_next = (LayoutParams) next.getLayoutParams();
                params.gravity = Gravity.CENTER;
                next.setLayoutParams(params_next);

            }


            @Override
            public void onNextPressed() {
                if(nextPressed){
                    return;
                }

                _subscriptions.add(sportsInterface.getSmsCode(editText.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<BaseResponse>() {
                                    @Override
                                    public void onCompleted() {
                                        FileLog.d(TAG, "login succes!");
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(BaseResponse s) {
                                        FileLog.d(TAG, "onNext ..!");
                                        if (s.isSuccess()) {
                                            Bundle params = new Bundle();
                                            params.putString("smscode", "8888888");
                                            params.putString("username", editText.getText().toString());
                                            setPage(1, true, params, false);
                                        } else {
                                            T.show(LoginActivity.this, s.getDesc());
                                        }
                                    }
                                })
                );
            }

            @Override
            public void setParams(Bundle params) {
                super.setParams(params);
            }


            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }

            @Override
            public void onShow() {
                super.onShow();
                if (editText != null) {
                    editText.requestFocus();
                    editText.setSelection(editText.length());
                }
            }
        }

        /**
         * 填写短信验证码界面
         */
        public class SmsCodeView extends  SlideView {

            private MaterialEditText username;

            private MaterialEditText passwd;

            private MaterialEditText smsCode;

            private boolean nextPressed = false;

            public SmsCodeView(Context context) {
                super(context);
                setOrientation(VERTICAL);
                setBackgroundResource(R.color.white);

                LinearLayout layout = new LinearLayout(context);
                smsCode = new MaterialEditText(context);
                smsCode.setId(R.id.sms_code);
                smsCode.setHint("sms code");
                smsCode.setSingleLine();
                smsCode.setGravity(Gravity.LEFT);
                smsCode.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(smsCode, LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 3.0f, 0, 0, 12, 0));


                TextView code = new TextView(context);
                code.setTextAppearance(context, R.style.pay_item_style_header);
                code.setText("59s");
                code.setGravity(Gravity.CENTER);
                int paddinglr = AndroidUtilities.dp(5);
                int paddingtb = AndroidUtilities.dp(6);
                code.setPadding(paddinglr, paddingtb, paddinglr, paddingtb);
                code.setBackgroundResource(R.color.bg_gray);
                layout.addView(code, LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 2.0f));


                addView(layout, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 0, 0, 5));



                passwd = new MaterialEditText(context);
                passwd.setHint("登陆密码");
                passwd.setSingleLine();
                passwd.setInputType(InputType.TYPE_CLASS_NUMBER);
                passwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            username.requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
                addView(passwd, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));


                username = new MaterialEditText(context);
                username.setHint("用户名");
                username.setSingleLine();
                username.setInputType(InputType.TYPE_CLASS_NUMBER);

                username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_NEXT){
                            onNextPressed();
                        }
                        return false;
                    }
                });

                addView(username, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

                TextView next = new TextView(context);
                next.setTextAppearance(context, R.style.pay_item_style_header);
                next.setText("下一步");
                next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNextPressed();
                    }
                });
                next.setGravity(Gravity.CENTER);
                int padding = AndroidUtilities.dp(5);
                next.setPadding(padding, padding, padding, padding);
                next.setBackgroundResource(R.color.bg_gray);
                addView(next, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

                LinearLayout.LayoutParams params_next = (LayoutParams) next.getLayoutParams();
                params_next.gravity = Gravity.CENTER;
                next.setLayoutParams(params_next);
            }

            @Override
            public void onNextPressed() {
                if(nextPressed){
                    return;
                }
                _subscriptions.add(sportsInterface.registerUser(username.getText().toString(), smsCode.getText().toString(), passwd.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<User>() {
                                    @Override
                                    public void onCompleted() {
                                        FileLog.d(TAG, "login succes!");
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(User s) {
                                        FileLog.d(TAG, "onNext ..!");
                                        if (s.isSuccess()) {
                                            UserConfig.setCurrentUser(s);
                                            UserConfig.saveConfig(true);
                                            Bundle params = new Bundle();
                                            params.putString("smscode", "8888888");
                                            setPage(2, true, params, false);
                                        } else {
                                            T.show(LoginActivity.this, s.getDesc());
                                        }
                                    }
                                })
                );
            }


            @Override
            public void setParams(Bundle params) {
                super.setParams(params);
                String userName = params.getString("username");
                username.setText(userName);
                // 下面是为了测试方便
                passwd.setText("123456");
                smsCode.setText("111111");
            }


            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }

            @Override
            public void onShow() {
                super.onShow();
                if (smsCode != null) {
                    smsCode.requestFocus();
                    smsCode.setSelection(smsCode.length());
                }
            }

        }


        public class SubmitUserInfoView extends SlideView {

            private MaterialEditText userHeight;
            private MaterialEditText userWeight;
            private MaterialEditText expert;
            private RadioGroup male;
            private RadioGroup foot;

            private boolean needPressed;

            public SubmitUserInfoView(Context context) {
                super(context);

                View view  = LayoutInflater.from(context).inflate(R.layout.layout_submit_reginfo, this, false);
                userHeight = (MaterialEditText) view.findViewById(R.id.user_height);
                userWeight = (MaterialEditText) view.findViewById(R.id.user_weight);
                expert = (MaterialEditText) view.findViewById(R.id.expert);
                male = (RadioGroup) view.findViewById(R.id.male_group);
                foot = (RadioGroup) view.findViewById(R.id.foot_good);
                addView(view, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
                TextView done_finish = (TextView) view.findViewById(R.id.done_finish);
                done_finish.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNextPressed();
                    }
                });
            }

            @Override
            public void setParams(Bundle params) {
                super.setParams(params);
            }


            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }

            @Override
            public void onShow() {
                super.onShow();
                if (userHeight != null) {
                    userHeight.requestFocus();
                    userHeight.setSelection(userHeight.length());
                }
            }

            @Override
            public void onNextPressed() {
                if(needPressed){
                    return;
                }
                String height = userHeight.getText().toString();
                String weight = userWeight.getText().toString();
                String expertStr = expert.getText().toString();
                int maleId = male.getCheckedRadioButtonId();
                int footId = foot.getCheckedRadioButtonId();
                User.ValueEntity user = new User.ValueEntity();
                try {
                    user.height = Integer.parseInt(height);
                    user.weight = Integer.parseInt(weight);
                    user.sex = ((RadioButton) findViewById(maleId)).getText().toString().equals("男") ? 1 : 0;
                    user.customFoot = ((RadioButton) findViewById(footId)).getText().toString().equals("左脚") ? 1 : 0;
                    user.location = expertStr;
                    user.uid = UserConfig.getClientUserId();
                } catch (Exception e) {
                    T.show(LoginActivity.this, "参数填写错误！");
                    return;
                }
//                _subscriptions.add(sportsInterface.submitUserInfo(user)
                Gson gson = new Gson();
                String uJ = gson.toJson(user);
                Map<String, Object> map = gson.fromJson(uJ, Map.class);
                _subscriptions.add(sportsInterface.submitMapUserInfo(map)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<BaseResponse>() {
                                    @Override
                                    public void onCompleted() {
                                        FileLog.d(TAG, "login succes!");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "", e);
                                    }

                                    @Override
                                    public void onNext(BaseResponse s) {
                                        FileLog.d(TAG, "onNext ..!");
//                                        UserConfig.setCurrentUser(s);
//                                        UserConfig.saveConfig(true);
                                        if (!s.isSuccess()) {
                                            T.show(LoginActivity.this, s.getDesc());
//                                            return;
                                        }
                                        registerDialog.dismiss();
                                        if (true/*s.value.joined*/) {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        } else {
                                            startActivity(new Intent(LoginActivity.this, TeamsListActivity.class));
                                        }
                                        finish();
                                    }
                                })
                );
            }
        }
    }

}
