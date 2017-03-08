package com.football.yuedong.happysports.service;


import com.football.yuedong.happysports.domain.BaseResponse;
import com.football.yuedong.happysports.domain.FieldGround;
import com.football.yuedong.happysports.domain.Order;
import com.football.yuedong.happysports.domain.PitchInfo;
import com.football.yuedong.happysports.domain.RankingListInfo;
import com.football.yuedong.happysports.domain.Team;
import com.football.yuedong.happysports.domain.Teams;
import com.football.yuedong.happysports.domain.TeamMemberRelp;
import com.football.yuedong.happysports.domain.User;


import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by liuyj on 2016/2/19.
 */
public interface SportsInterface {

    /**
     * 获取用户验证码
     * @param phoneNumerber
     * @return
     */
    @GET("yue_sport_app/login/getRegisterCode.hl")
    Observable<BaseResponse> getSmsCode(@Query("mobile") String phoneNumerber);


    /**
     * register user;
     * @param phone
     * @param smscode
     * @param pwd
     * @return
     */
    @GET("yue_sport_app/login/registerByCode.hl")
    Observable<User>  registerUser(@Query("mobile") String phone, @Query("code") String smscode, @Query("password") String pwd);


    /**
     * 找回密码，短信验证码验证
     * @param phone
     * @return
     */
    @GET("yue_sport_app/login/getFindPasswordCode.hl")
    Observable<BaseResponse> getSmsCode4Pwd(@Query("mobile") String phone);

    /**
     * 找回密码验证
     * @param phone
     * @param smscode
     * @param pwd
     * @return
     */
    @GET("yue_sport_app/login/findPassword.hl")
    Observable<BaseResponse> findPwdReg(@Query("mobile") String phone, @Query("code") String smscode, @Query("password") String pwd);

    /**
     * login
     * @param username
     * @param password
     * @return
     */
    @GET("yue_sport_app/login/login.hl")
    Observable<User>  login(@Query("passport") String username, @Query("password") String password);

    /**
     * 提交更新用户信息
     * @param user
     * @return
     */
    @GET("yue_sport_app/login/setOrUpdateUserBaseInfo.hl")
    Observable<BaseResponse> submitUserInfo(@Body User.ValueEntity user);

    /**
     * 提交更新用户信息
     * @param user
     * @return
     */
    @GET("yue_sport_app/login/setOrUpdateUserBaseInfo.hl")
    Observable<BaseResponse> submitMapUserInfo(@QueryMap Map<String, Object> user);

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @GET("yue_sport_app/login/getUserBaseInfo.hl")
    Observable<User>  getUser(@Query("uid") String uid);

    /**
     * 获取球队列表
     * @return
     */
    @GET("yue_sport_app/team/getTeams.hl")
    Observable<Teams> getTeams(@Query("pageNo") int pageNo, @Query("num") int count);

    /**
     * 球队创建
     * @return
     */
    @GET("yue_sport_app/team/createTeam.hl")
    Observable<Team> createTeam(@Query("uid") String uid, @Query("name") String teamName,
                                @Query("icon") String iconUrl, @Query("location") String address,
                                @Query("homeCourt") int homeCount, @Query("selfTags") String tags);



    @GET("yue_sport_app/team/applyJoin.hl?")
    Observable<BaseResponse> applyJoinTeam(@Query("uid") String uid, @Query("teamId") String teamId);


    /**
     *
     * @return
     * type = 1;
     * type =0;
     */
    @GET("yue_sport_app/team/agreeOrRefuse.hl")
    Observable<BaseResponse> handleApply(@Query("type") int type, @Query("uid") String uid, @Query("teamId") String teamId, @Query("appUid") String appUid);


    /**
     * uid=10&teamId=0
     * @return
     */
    @GET("yue_sport_app/team/getTeamInfo.hl")
    Observable<Team> getTeamDetails(@Query("uid") String uid , @Query("teamId") String teamId);


    /**
     * @return
     */
    @GET("yue_sport_app/team/sign.hl?")
    Observable<BaseResponse> signTeam(@Query("type") int type , @Query("uid") String uid, @Query("teamId") String teamId);


    /**
     * uid=10&teamId=0&pageNo=0&num=10
     */
    @GET("yue_sport_app/team/getTeamRoles.hl")
    Observable<TeamMemberRelp> getTeamRoles(@Query("uid") String uid, @Query("teamId") String teamId,
                              @Query("pageNo") int pageNo, @Query("num") int num);

    @GET("yue_sport_app/team/ranking.hl")
    Observable<RankingListInfo> getRankingList(@Query("type") int type, @Query("pageNo") int pageNo, @Query("num") int num);


    /**
     * 查询场馆列表
     */
    @GET("yue_sport_app/filed/getFiledList.hl")
    Observable<FieldGround>  queryFields(@Query("type") int type, @Query("pageNo") int pageNo, @Query("num") int num);

    /**
     * 查询获取场馆详情
     */
    @GET("yue_sport_app/filed/getFiledDetails.hl")
    Observable<FieldGround.ValueEntity> queryFieldDetail(@Query("id") String id);


    /**
     * 获取场地列表
     */
    @GET("yue_sport_app/filed/getPitchList.hl")
    Observable<PitchInfo> queryPitches(@Query("filedId") String id, @Query("pageNo") int pageNo, @Query("num") int num);


    /**
     * 获取场地详情
     */
    @GET("yue_sport_app/filed/getPitchDetails.hl")
    Observable<PitchInfo.ValueEntity> getPitchDetail(@Query("id") String id);


    @GET("yue_sport_app/filed/createOrder.hl")
    Observable<Order> createOrder(@Query("pitchId") String pitchId, @Query("uid") String uid , @Query("day") String day, @Query("timeId") String timeid);


    /**
     *
     获取场馆中某一天的所有场地被预定的状态信息
     app端下单


     发布比赛、活动（0、比赛，1、活动）
     http://127.0.0.1:8080/yue_sport_app/publish/publishMatche.hl?id=1&teamId1=1&teamId2=1&time=05 jul 2015&note=note&type=1
     报名、请假等（0、潜水，1、报名，2、请假，3、待定）
     http://127.0.0.1:8080/yue_sport_app/publish/publishSignUp.hl?publishId=1&teamId=1&uid=1&status=1&note=note
     *
     */
}
