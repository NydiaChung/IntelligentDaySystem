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
import retrofit2.Callback;

/**
 * Created by marco on 2017/8/21.
 * api调用逻辑
 */

public class NetWorkBusiness {
    private ApiService apiService;
    private String accessToken;

    public NetWorkBusiness(String accessToken, String baseUrl) {
        this.apiService = Tools.buildService(baseUrl);
        this.accessToken = accessToken;
    }

    public void signIn(SignIn signIn, Callback<BaseResponseEntity<User>> callback) {
        apiService.signIn(signIn).enqueue(callback);
    }

    public void getProject(String projectId, Callback<BaseResponseEntity<ProjectInfo>> callback) {
        apiService.getProject(projectId, accessToken).enqueue(callback);
    }

    public void getProjects(String Keyword, String ProjectTag, String NetWorkKind, String PageSize, String StartDate, String EndDate, String PageIndex,
            Callback<BaseResponseEntity<BasePager<ProjectInfo>>> callback) {
        apiService.getProjects(Keyword, ProjectTag, NetWorkKind, PageSize, StartDate, EndDate, PageIndex, accessToken).enqueue(callback);
    }

    public void getAllSensors(String projectId, Callback<BaseResponseEntity<List<TargetSensorInfo>>> callback) {
        apiService.getAllSensors(projectId, accessToken).enqueue(callback);
    }

    public void getDevicesDatas(String deviceIds, Callback<BaseResponseEntity<List<ListItemOfDevice>>> callback) {
        apiService.getDevicesDatas(deviceIds, accessToken).enqueue(callback);
    }

    public void getBatchOnLine(String deviceIds, Callback<BaseResponseEntity<List<DeviceState>>> callback) {
        apiService.getBatchOnLine(deviceIds, accessToken).enqueue(callback);

    }

    public void getDeviceInfo(String deviceId, Callback<BaseResponseEntity<Device>> callback) {
        apiService.getDeviceInfo(deviceId, accessToken).enqueue(callback);
    }

    public void getDeviceFuzzy(String Keyword, String DeviceIds, String Tag, String IsOnline, String IsShare, String ProjectKeyWord, String PageSize, String StartDate, String
            EndDate, String PageIndex, Callback<BaseResponseEntity<BasePager<Device>>> callback) {
        apiService.getDeviceFuzzy(Keyword, DeviceIds, Tag, IsOnline, IsShare, ProjectKeyWord, PageSize, StartDate, EndDate, PageIndex, accessToken).enqueue(callback);
    }

    public void postAddDevice(Device device, Callback<BaseResponseEntity> callback) {
        apiService.postAddDevice(device, accessToken).enqueue(callback);
    }

    public void updateDevice(String deviceId, Device device, Callback<BaseResponseEntity<Object>> callback) {
        apiService.updateDevice(deviceId, device, accessToken).enqueue(callback);
    }

    public void deleteDevice(String deviceId, Callback<BaseResponseEntity> callback) {
        apiService.deleteDevice(deviceId, accessToken).enqueue(callback);
    }

    public void getSensor(String deviceId, String apiTag, Callback<BaseResponseEntity> callback) {
        apiService.getSensor(deviceId, apiTag, accessToken).enqueue(callback);
    }

    public void getSensors(String deviceId, String apiTags, Callback<BaseResponseEntity> callback) {
        apiService.getSensors(deviceId, apiTags, accessToken).enqueue(callback);
    }

    public void addSensor(String deviceId, DeviceElement deviceElement, Callback<BaseResponseEntity> callback) {
        apiService.addSensor(deviceId, deviceElement, accessToken).enqueue(callback);
    }

    public void updateSensor(String deviceId, String apiTag, DeviceElement.SensorDeviceElement deviceElement, Callback<BaseResponseEntity> callback) {
        apiService.updateSensor(deviceId, apiTag, deviceElement, accessToken).enqueue(callback);
    }

    public void updateActuator(String deviceId, String apiTag, DeviceElement.ActuatorDeviceElement deviceElement, Callback<BaseResponseEntity> callback) {
        apiService.updateSensor(deviceId, apiTag, deviceElement, accessToken).enqueue(callback);
    }

    public void updateCamera(String deviceId, String apiTag, DeviceElement.CameraDeviceElement deviceElement, Callback<BaseResponseEntity> callback) {
        apiService.updateSensor(deviceId, apiTag, deviceElement, accessToken).enqueue(callback);
    }

    public void deleteDeviceElement(String deviceId, String apiTag, Callback<BaseResponseEntity> callback) {
        apiService.deleteDeviceElement(deviceId, apiTag, accessToken).enqueue(callback);
    }

    public void addSensorData(String deviceId, DeviceData datasDTO, Callback<BaseResponseEntity> callback) {
        apiService.addSensorData(deviceId, datasDTO, accessToken).enqueue(callback);
    }

    public void getSensorData(String deviceId, String ApiTags, String Method, String TimeAgo, String StartDate, String EndDate, String Sort, String PageSize, String PageIndex,
            Callback<BaseResponseEntity> callback) {
        apiService.getSensorData(deviceId, ApiTags, Method, TimeAgo, StartDate, EndDate, Sort, PageSize, PageIndex, accessToken).enqueue(callback);
    }

    public void control(String deviceId, String apiTag, Object data, Callback<BaseResponseEntity> callback) {
        apiService.control(deviceId, apiTag, data, accessToken).enqueue(callback);
    }


}
