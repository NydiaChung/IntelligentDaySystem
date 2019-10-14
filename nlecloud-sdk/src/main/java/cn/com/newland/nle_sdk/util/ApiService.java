package cn.com.newland.nle_sdk.util;

import java.util.List;

import cn.com.newland.nle_sdk.requestEntity.DeviceData;
import cn.com.newland.nle_sdk.requestEntity.DeviceElement;
import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.Device;
import cn.com.newland.nle_sdk.responseEntity.DeviceState;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfDevice;
import cn.com.newland.nle_sdk.responseEntity.ProjectInfo;
import cn.com.newland.nle_sdk.responseEntity.TargetSensorInfo;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BasePager;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marco on 2017/8/21.
 * api请求接口
 */

public interface ApiService {
    /**
     * 登陆
     */
    @POST("Users/Login")
    Call<BaseResponseEntity<User>> signIn(@Body SignIn signIn);

    /**
     * 查询单个项目
     */
    @GET("Projects/{projectId}")
    Call<BaseResponseEntity<ProjectInfo>> getProject(@Path("projectId") String projectId, @Header("AccessToken") String accessToken);

    /**
     * 模糊查询项目
     */
    @GET("Projects")
    Call<BaseResponseEntity<BasePager<ProjectInfo>>> getProjects(
            @Query("Keyword") String Keyword,
            @Query("ProjectTag") String ProjectTag,
            @Query("NetWorkKind") String NetWorkKind,
            @Query("PageSize") String PageSize,
            @Query("StartDate") String StartDate,
            @Query("EndDate") String EndDate,
            @Query("PageIndex") String PageIndex,
            @Header("AccessToken") String accessToken);

    /**
     * 查询项目所有设备的传感器
     */
    @GET("Projects/{projectId}/Sensors")
    Call<BaseResponseEntity<List<TargetSensorInfo>>> getAllSensors(@Path("projectId") String projectId, @Header("AccessToken") String accessToken);

    /**
     * 批量查询设备最新数据
     */
    @GET("Devices/Datas")
    Call<BaseResponseEntity<List<ListItemOfDevice>>> getDevicesDatas(@Query("devIds") String devIds, @Header("AccessToken") String accessToken);

    /**
     * 批量查询设备的在线状态
     */
    @GET("Devices/Status")
    Call<BaseResponseEntity<List<DeviceState>>> getBatchOnLine(
             @Query("devIds") String devIds, @Header("AccessToken") String accessToken);

    /**
     *查询单个设备
     */
    @GET("Devices/{deviceId}")
    Call<BaseResponseEntity<Device>> getDeviceInfo(@Path("deviceId") String deviceId, @Header("AccessToken") String accessToken);

    /**
     * 模糊查询设备
     */
    @GET("Devices")
    Call<BaseResponseEntity<BasePager<Device>>> getDeviceFuzzy(
            @Query("Keyword") String Keyword,
            @Query("DeviceIds") String DeviceIds,
            @Query("Tag") String Tag,
            @Query("IsOnline") String IsOnline,
            @Query("IsShare") String IsShare,
            @Query("ProjectKeyWord") String ProjectKeyWord,
            @Query("PageSize") String PageSize,
            @Query("StartDate") String StartDate,
            @Query("EndDate") String EndDate,
            @Query("PageIndex") String PageIndex,
            @Header("AccessToken") String accessToken);

    /**
     * 添加1个新设备
     */
    @POST("Devices")
    Call<BaseResponseEntity> postAddDevice(
           @Body Device device,
            @Header("AccessToken") String accessToken);

    /**
     * 更新某个设备
     */
    @PUT("Devices/{deviceId}")
    Call<BaseResponseEntity<Object>> updateDevice(
            @Path("deviceId") String deviceId,
            @Body Device device,
            @Header("AccessToken") String accessToken);

    /**
     * 删除设备
     */
    @DELETE("Devices/{deviceId}")
    Call<BaseResponseEntity> deleteDevice(@Path("deviceId") String deviceId, @Header("AccessToken") String accessToken);

    /**
     * 查询单个传感器
     */
    @GET("devices/{deviceId}/Sensors/{apiTag}")
    Call<BaseResponseEntity> getSensor(
            @Path("deviceId") String deviceId,@Path("apiTag")String apiTag, @Header("AccessToken") String accessToken);

    /**
     * 模糊查询传感器
     */
    @GET("devices/{deviceId}/Sensors")
    Call<BaseResponseEntity> getSensors(@Path("deviceId") String deviceId, @Query("apiTags") String apiTags, @Header("AccessToken") String accessToken);

    /**
     * 添加1个新传感器
     */
    @POST("devices/{deviceId}/Sensors")
    Call<BaseResponseEntity> addSensor(
            @Path("deviceId") String deviceId,
            @Body DeviceElement deviceElement,
            @Header("AccessToken") String accessToken);

    /**
     * 更新某个传感器
     */
    @PUT("devices/{deviceId}/Sensors/{apiTag}")
    Call<BaseResponseEntity> updateSensor(
            @Path("deviceId") String deviceId,
            @Path("apiTag") String apiTag,
            @Body DeviceElement.SensorDeviceElement deviceElement,
            @Header("AccessToken") String accessToken);

    /**
     * 更新某个执行器
     */
    @PUT("devices/{deviceId}/Sensors/{apiTag}")
    Call<BaseResponseEntity> updateSensor(
            @Path("deviceId") String deviceId,
            @Path("apiTag") String apiTag,
            @Body DeviceElement.ActuatorDeviceElement deviceElement,
            @Header("AccessToken") String accessToken);
    /**
     * 更新某个摄像头
     */
    @PUT("devices/{deviceId}/Sensors/{apiTag}")
    Call<BaseResponseEntity> updateSensor(
            @Path("deviceId") String deviceId,
            @Path("apiTag") String apiTag,
            @Body DeviceElement.CameraDeviceElement deviceElement,
            @Header("AccessToken") String accessToken);
    /**
    /**
     * 删除某个传感器
     */
    @DELETE("devices/{deviceId}/Sensors/{apiTag}")
    Call<BaseResponseEntity> deleteDeviceElement(
            @Path("deviceId") String deviceId, @Path("apiTag") String apiTag, @Header("AccessToken") String accessToken);

    /**
     *  新增传感数据
     */
    @POST("devices/{deviceId}/Datas")
    Call<BaseResponseEntity> addSensorData(
            @Path("deviceId") String deviceId,
            @Body DeviceData datasDTO,
            @Header("AccessToken") String accessToken);

    /**
     * 查询传感数据
     */
    @GET("devices/{deviceId}/Datas")
    Call<BaseResponseEntity> getSensorData(
            @Path("deviceId") String deviceId,
            @Query("ApiTags") String ApiTags,
            @Query("Method") String Method,
            @Query("TimeAgo") String TimeAgo,
            @Query("StartDate") String StartDate,
            @Query("EndDate") String EndDate,
            @Query("Sort") String Sort,
            @Query("PageSize") String PageSize,
            @Query("PageIndex") String PageIndex,
            @Header("AccessToken") String accessToken);

    /**
     * 发送命令/控制设备
     */
    @POST("Cmds")
    Call<BaseResponseEntity> control(@Query("deviceId")String deviceId, @Query("apiTag")String apiTag,@Body Object data, @Header("AccessToken") String accessToken);

}
