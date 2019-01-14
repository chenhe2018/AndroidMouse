package permission;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.mouse.MyAccessibilityService;


public class AccessibilityServiceUtil {

    private static final String TAG = "ASUtil";

    private static volatile AccessibilityServiceUtil instance;

    private Dialog dialog;

    public static AccessibilityServiceUtil getInstance() {
        if (instance == null) {
            synchronized (AccessibilityServiceUtil.class) {
                if (instance == null) {
                    instance = new AccessibilityServiceUtil();
                }
            }
        }
        return instance;
    }

    public void applyPermission(final Context context) {
        if (checkPermission(context)) {
            Toast.makeText(context, "Accessibility Service Permission Success!", Toast.LENGTH_SHORT).show();
        } else {
            showConfirmDialog(context, new AccessibilityServiceUtil.OnConfirmResult() {
                @Override
                public void confirmResult(boolean confirm) {
                    if (confirm) {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity(intent);
                    } else {
                        Log.e(TAG, "ROM:360, user manually refuse OVERLAY_PERMISSION");
                    }
                }
            });
        }
    }

    private boolean checkPermission(Context context) {
        int accessibilityEnable = 0;
        // TestService为对应的服务
        String serviceName = context.getPackageName() + "/" + MyAccessibilityService.class.getCanonicalName();
        Log.i(TAG, "service:" + serviceName);
        try {
            accessibilityEnable = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 0);
        } catch (Exception e) {
            Log.e(TAG, "get accessibility enable failed, the err:" + e.getMessage());
        }
        if (accessibilityEnable == 1) {
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
            String settingValue = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(serviceName)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.d(TAG, "Accessibility service disable");
        }
        return false;
    }


    private interface OnConfirmResult {
        void confirmResult(boolean confirm);
    }

    private void showConfirmDialog(Context context, AccessibilityServiceUtil.OnConfirmResult result) {
        showConfirmDialog(context, "您的手机没有授予无障碍权限，请开启后再试", result);
    }

    private void showConfirmDialog(Context context, String message, final AccessibilityServiceUtil.OnConfirmResult result) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new AlertDialog.Builder(context).setCancelable(true).setTitle("")
                .setMessage(message)
                .setPositiveButton("现在去开启",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(true);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("暂不开启",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirmResult(false);
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }
}
