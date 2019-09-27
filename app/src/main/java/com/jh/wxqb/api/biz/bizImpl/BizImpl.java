package com.jh.wxqb.api.biz.bizImpl;

import com.jh.wxqb.api.ServerApi;
import com.jh.wxqb.api.biz.Biz;
import com.jh.wxqb.base.BaseBiz;
import com.jh.wxqb.base.OnBaseListener;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2018/6/9 0009.
 */

public class BizImpl extends BaseBiz implements Biz {


    /**
     * 注册
     *
     * @param map
     * @param listener
     */
    @Override
    public void userRegister(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).userRegister(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz注册成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 登录
     *
     * @param map
     * @param listener
     */
    @Override
    public void userLogin(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).userLogin(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz登录成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param listener
     */
    @Override
    public void getMessageCode(String mobile, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getMessageCode(mobile).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取短信验证码成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param listener
     */
    @Override
    public void getUserInfo(final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getUserInfo().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取用户信息成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 我的買入
     *
     * @param pageNum
     * @param type
     * @param listener
     */
    @Override
    public void myDividend(int pageNum, int type, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).myDividend(pageNum, type).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz我的買入成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 停止買入
     *
     * @param map
     * @param listener
     */
    @Override
    public void stopDividend(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).stopDividend(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz停止買入成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 撤消挂卖/停止排队
     *
     * @param map
     * @param listener
     */
    @Override
    public void stopQueuing(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).stopQueuing(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz撤消挂卖/停止排队成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 忘记密码
     *
     * @param mobile
     * @param newOnePwd
     * @param smsCode
     * @param listener
     */
    @Override
    public void forgetLoginPwd(String mobile, String newOnePwd, String smsCode, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).forgetLoginPwd(mobile, newOnePwd, smsCode).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz忘记密码成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 修改登录/交易密码
     *
     * @param type
     * @param newOnePwd
     * @param newTwoPwd
     * @param smsCode
     * @param listener
     */
    @Override
    public void udpLoginPwd(int type, String newOnePwd, String newTwoPwd, String smsCode, String mobile, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).udpLoginPwd(type, newOnePwd, newTwoPwd, smsCode, mobile).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz修改登录密码成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 绑定账户邮箱
     *
     * @param map
     * @param listener
     */
    @Override
    public void bandingEmail(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).bandingEmail(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz绑定账户邮箱成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 我的团队
     *
     * @param pageNum
     * @param listener
     */
    @Override
    public void myTeam(int pageNum, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).myTeam(pageNum).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz我的团队成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 查询用户上传的头像
     *
     * @param listener
     */
    @Override
    public void selUserImage(final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).selUserImage().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz查询用户上传的头像成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 上传用户头像
     *
     * @param type
     * @param file
     * @param listener
     */
    @Override
    public void uploadUserImage(@Part("type") int type, @Part MultipartBody.Part file, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).uploadUserImage(type, file).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz上传用户头像成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 查看新闻咨询详情
     *
     * @param id
     * @param listener
     */
    @Override
    public void newsInfo(String id, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).newsInfo(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz查看新闻咨询详情成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 新闻咨询列表
     *
     * @param type
     * @param pageNum
     * @param listener
     */
    @Override
    public void newsList(String type, int pageNum, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).newsList(type, pageNum).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz新闻咨询列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 我的消息列表
     *
     * @param pageNum
     * @param listener
     */
    @Override
    public void myMessage(int pageNum, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).myMessage(pageNum).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz我的消息列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 我的消息详情
     *
     * @param id
     * @param listener
     */
    @Override
    public void myMessageDetails(String id, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).myMessageDetails(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz我的消息详情成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 保存用户反馈
     *
     * @param file
     * @param content
     * @param listener
     */
    @Override
    public void saveUserFeedback(List<MultipartBody.Part> file, String content, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).saveUserFeedback(file, content).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz保存用户反馈成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 用户反馈列表
     *
     * @param pageNum
     * @param listener
     */
    @Override
    public void userFeedbackList(int pageNum, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).userFeedbackList(pageNum).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz用户反馈列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 查询用户反馈详情
     *
     * @param id
     * @param listener
     */
    @Override
    public void queryUserFeedback(String id, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).queryUserFeedback(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz查询用户反馈详情成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取财务明细类型
     *
     * @param listener
     */
    @Override
    public void financialDetailsType(final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).financialDetailsType().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取财务明细类型成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取财务明细列表
     *
     * @param pageNum
     * @param type
     * @param listener
     */
    @Override
    public void getFinancialDetails(int pageNum, int type, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getFinancialDetails(pageNum, type).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取财务明细列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 資產管理
     *
     * @param pageNum
     * @param type
     * @param listener
     */
    @Override
    public void getQueryaccountassets(int pageNum, int type, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getQueryaccountassets(pageNum, type).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("活动资产明细列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        LogUtils.e("code======>" + response.raw().code());
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 提币
     *
     * @param map
     * @param listener
     */
    @Override
    public void withdrawMoney(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).withdrawMoney(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz提币成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 買入市場--買入顶部列表
     *
     * @param type
     * @param listener
     */
    @Override
    public void dividendMarketDividend(int type, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).dividendMarketDividend(type).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz買入市場--買入顶部列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 買入市場--買入底部列表
     *
     * @param pageNum
     * @param type
     * @param direction
     * @param listener
     */
    @Override
    public void dividendMarketTopDividend(int pageNum, int type, int direction, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).dividendMarketTopDividend(pageNum, type, direction).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz買入市場--買入底部列表成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 购买订单
     *
     * @param map
     * @param listener
     */
    @Override
    public void purchaseOrder(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).purchaseOrder(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz购买订单成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 買入
     *
     * @param map
     * @param listener
     */
    @Override
    public void sellOut(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).sellOut(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz買入成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 卖出
     *
     * @param map
     * @param listener
     */
    @Override
    public void marketSell(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).marketSell(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz卖出成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取以太币和子币的价格
     *
     * @param map
     * @param listener
     */
    @Override
    public void getCurrentPrice(Map<String, String> map, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getCurrentPrice(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取以太币和子币的价格成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取邮箱验证码
     *
     * @param email
     * @param listener
     */
    @Override
    public void getEmailCode(String email, final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getEmailCode(email).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取邮箱验证码成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取安全标示
     *
     * @param listener
     */
    @Override
    public void safetyMarking(final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).safetyMarking().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取安全标示成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 版本更新
     *
     * @param listener
     */
    @Override
    public void versionUpdate(final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).versionUpdate().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz版本更新成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }

    /**
     * 获取token失效时间
     *
     * @param listener
     */
    @Override
    public void getTime(final OnBaseListener listener) {
        getStringRetrofit().create(ServerApi.class).getTime().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (call != null) {
                    if (response.body() != null) {
                        LogUtils.e("Biz获取token失效时间成功==>" + GsonUtil.GsonString(response.body()));
                        if (response.isSuccessful()) {
                            listener.onResponse(response.body());
                        } else {
                            listener.onFailure(response.message(), response.raw().code());
                        }
                    } else {
                        listener.onFailure("服务器繁忙,请稍后再试", response.raw().code());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (call.isExecuted()) {
                    call.cancel();
                }
                LogUtils.e("Biz服务器未响应请求失败==>" + GsonUtil.GsonString(t.getMessage()));
                listener.onFailure("服务器繁忙,请稍后再试", 0);
            }
        });
    }


}
