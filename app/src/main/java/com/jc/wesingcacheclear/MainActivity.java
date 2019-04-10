package com.jc.wesingcacheclear;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Wsc> wscList=new ArrayList<Wsc>();
    private ListView listView;
    private String packagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("使用须知");
        builder.setMessage("1.全程连接VPN.\n2.本机只保留可支付的Google账号,其他账号请删除.\n3.按照以下步骤完成后,重新安装带有google签名的WeSing版本.");
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

        initWsc();
        WscAdapter wscAdapter=new WscAdapter(MainActivity.this,R.layout.list_view_item,wscList);
        listView=(ListView) findViewById(R.id.wsc_listview);
        listView.setAdapter(wscAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        PackageManager packageManager=getPackageManager();
                        if(checkPackageInfo("com.expressvpn.vpn")){
                            Intent intent=packageManager.getLaunchIntentForPackage("com.expressvpn.vpn");
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Please install Express VPN in Google Play first.",Toast.LENGTH_LONG).show();
                            Uri installExpressVpn=Uri.parse("https://play.google.com/store/apps/details?id=com.expressvpn.vpn");
                            Intent intentInstallExpressVpn=new Intent(Intent.ACTION_VIEW,installExpressVpn);
                            startActivity(intentInstallExpressVpn);
                        }
                    break;

                    case 1:
                        Uri uninstallWeSing=Uri.fromParts("package","com.tencent.wesing",null);
                        Intent uninstallIntent=new Intent(Intent.ACTION_DELETE,uninstallWeSing);
                        startActivity(uninstallIntent);
                    break;

                    case 2:
                        Uri clearGoogle=Uri.fromParts("package","com.google.android.googlequicksearchbox",null);
                        Intent intentGoogle=new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",clearGoogle);
                        startActivity(intentGoogle);
                    break;

                    case 3:
                        Uri clearGoogleServiceCache=Uri.fromParts("package","com.google.android.gms",null);
                        Intent intentGoogleService=new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",clearGoogleServiceCache);
                        startActivity(intentGoogleService);
                    break;

                    case 4:
                        Uri clearGooglePartnerSetup=Uri.fromParts("package","com.google.android.partnersetup",null);
                        Intent intentGooglePartnerSetup=new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",clearGooglePartnerSetup);
                        startActivity(intentGooglePartnerSetup);
                    break;

                    case 5:
                        Uri clearGoogleServiceFramework=Uri.fromParts("package","com.google.android.gsf",null);
                        Intent intentGoogleServiceFramework=new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",clearGoogleServiceFramework);
                        startActivity(intentGoogleServiceFramework);
                    break;

                    case 6:
                        Uri clearGooglePlayCache=Uri.fromParts("package","com.android.vending",null);
                        Intent intentGooglePlay=new Intent("android.settings.APPLICATION_DETAILS_SETTINGS",clearGooglePlayCache);
                        startActivity(intentGooglePlay);
                    break;

                    case 7:
                        Uri installWesingGooglePlay=Uri.parse("https://play.google.com/store/apps/details?id=com.tencent.wesing");
                        Intent intentWesingGooglePlay=new Intent(Intent.ACTION_VIEW,installWesingGooglePlay);
                        startActivity(intentWesingGooglePlay);
                    break;

                    case 8:
                        String sourceFile = "/storage/self/primary/tencent/wns/Logs/com.tencent.wesing/";
                        String zipFile = "/storage/emulated/0/wesing.zip";
                        CompressOperate_zip4j file = new CompressOperate_zip4j();
                        file.compressZip4j(sourceFile, zipFile, null);

                        File file1 = new File(zipFile);
                        if (file1.exists()) {
                            Toast.makeText(MainActivity.this, "Zip WeSing Local log successful", Toast.LENGTH_SHORT).show();
                        }

                        File zipFilePath = new File("/storage/emulated/0/");
                        File zipFiletoShare = new File(zipFilePath, "wesing.zip");

                        Uri contentUri = null;
                        Uri shareUri;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            shareUri = Uri.fromFile(zipFiletoShare);
                        } else {
                            shareUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", zipFiletoShare);
                        }

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.addCategory("android.intent.category.DEFAULT");
                        shareIntent.setType("application/zip");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, shareUri);
                        startActivity(Intent.createChooser(shareIntent, "Share to......"));
                    break;

                    default:

                }


            }
        });


    }

    private boolean checkPackageInfo(String packagename) {
        PackageInfo packageInfo=null;
        try{
            packageInfo=getPackageManager().getPackageInfo(packagename,0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return packageInfo!=null;
    }

    private void initWsc(){
        Wsc wscStartExpressVpn=new Wsc("Start ExpressVPN\n1.Please click here to start VPN first.",R.drawable.express_vpn);
        wscList.add(wscStartExpressVpn);

        Wsc wscUninstallWeSing=new Wsc("Uninstall WeSing\n2.Please click here to uninstall WeSing",R.drawable.wesing);
        wscList.add(wscUninstallWeSing);

        Wsc wscClearGoogleCache=new Wsc("Clear Google Cache\n3.Please click here to clear Google cache.",R.drawable.google);
        wscList.add(wscClearGoogleCache);

        Wsc wscClearGoogleServicesCache=new Wsc("Clear Google Services Cache\n4.Please click here to clear Google Services cache.",R.drawable.googleplay_services);
        wscList.add(wscClearGoogleServicesCache);

        Wsc wscClearGoogleParentsCache=new Wsc("Clear Google Parents Cache\n5.Please click here to clear Google Parents cache.",R.drawable.googleparent);
        wscList.add(wscClearGoogleParentsCache);

        Wsc wscClearGoogleServicesFramework=new Wsc("Clear Google Services Framework Cache\n6.Please click here to clear Google Services Framework cache.",R.drawable.googlefarmework);
        wscList.add(wscClearGoogleServicesFramework);

        Wsc wscClearGooglePlayStoreCache=new Wsc("Clear Google Play Store Cache\n7.Please click here to clear Google Play store cache.",R.drawable.googleplay);
        wscList.add(wscClearGooglePlayStoreCache);

        Wsc wscInstallWeSing=new Wsc("Reinstall WeSing\n8.Reinstall WeSing with GooglePlay store.",R.drawable.wesing);
        wscList.add(wscInstallWeSing);

        Wsc wscZipWeSingLog=new Wsc("ZIP log and share\n9.Zip WeSing Log and share...",R.drawable.log);
        wscList.add(wscZipWeSingLog);
    }
}
