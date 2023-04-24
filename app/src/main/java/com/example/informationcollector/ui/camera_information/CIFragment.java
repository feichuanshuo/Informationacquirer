package com.example.informationcollector.ui.camera_information;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.informationcollector.databinding.FragmentCameraInformationBinding;

import java.util.Collections;

public class CIFragment extends Fragment {
    private FragmentCameraInformationBinding binding;;
    private CameraManager cameraManager;
    private String cameraId;
    String[] cameraIds;
    private CameraDevice mCameraDevice;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCameraInformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context context = getContext();
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        // 获取摄像id列表
        try {
            cameraIds = cameraManager.getCameraIdList();
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                int facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraId = id;
                    break;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        // 切换前置摄像按钮
        Button FC_button = binding.button1;
        // 切换后置摄像按钮
        Button BC_button = binding.button2;

        // 绑定点击事件
        FC_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFC();
            }
        });

        BC_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBC();
            }
        });

        try {
            openCamera();
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    // 加载图像到页面
    private void openCamera() throws CameraAccessException {
        //检查应用是否具有相机权限
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //如果应用没有相机权限，则请求相机权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        //打开相机设备
        cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                //相机设备已经打开，可以使用
                mCameraDevice = cameraDevice;

                //连接到TextureView并启动预览
                try {
                    // 创建一个新的预览请求
                    CaptureRequest.Builder previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

                    // 设置预览目标
                    SurfaceTexture surfaceTexture = binding.textureView.getSurfaceTexture();
                    surfaceTexture.setDefaultBufferSize(1920, 1080);
                    Surface previewSurface = new Surface(surfaceTexture);
                    previewRequestBuilder.addTarget(previewSurface);

                    // 创建一个会话
                    cameraDevice.createCaptureSession(Collections.singletonList(previewSurface), new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            try {
                                // 开始预览
                                cameraCaptureSession.setRepeatingRequest(previewRequestBuilder.build(), null, null);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // 会话创建失败
                        }
                    }, null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                //相机设备已经断开连接，释放资源
                cameraDevice.close();
                mCameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int error) {
                //发生错误，释放资源
                cameraDevice.close();
                mCameraDevice = null;
            }
        }, null);
    }

    // 关闭摄像
    private void closeCamera() {
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }


    // 切换前置摄像
    private void switchFC(){
        try {
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                int facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    cameraId = id;
                    break;
                }
            }
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {
            closeCamera();
            openCamera();
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // 切换后置摄像
    private void switchBC(){
        try {
            for (String id : cameraIds) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                int facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraId = id;
                    break;
                }
            }
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {
            closeCamera();
            openCamera();
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
