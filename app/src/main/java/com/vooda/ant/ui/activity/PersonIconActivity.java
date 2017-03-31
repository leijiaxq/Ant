package com.vooda.ant.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.vooda.ant.R;
import com.vooda.ant.base.RxBaseActivity;
import com.vooda.ant.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by  leijiaxq
 * Date        2017/3/24 16:46
 * Describe
 */
public class PersonIconActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.icon_iv)
    ImageView mIconIv;
    @BindView(R.id.select_btn)
    Button mSelectBtn;
    @BindView(R.id.upload_btn)
    Button mUploadBtn;


    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_ALBUM = 2;
    public static final int REQUEST_CROP = 3;
    private String mImgPath;


    @Override
    public int getLayoutId() {
        return R.layout.activity_person_icon;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mTitleTv.setText("上传");
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_white_img);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.select_btn)
    public void clickSelect(View view) {
        new AlertDialog.Builder(this)
                .setTitle("选择照片")
                .setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            selectCamera();
                        } else {
                            selectAlbum();
                        }
                    }
                })
                .create()
                .show();
    }

    private void selectCamera() {

        new RxPermissions(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            // 所有权限请求被同意
                            mImgPath = createImagePath();
//                            if (!imageFile.exists()) {
//                                return;
//                            }
                            // 指定调用相机拍照后照片的储存路径
                            File imgFile = new File(mImgPath);
                            Uri imgUri = null;
                            if (Build.VERSION.SDK_INT >= 24) {
                                //如果是7.0或以上，使用getUriForFile()获取文件的Uri
                                imgUri = FileProvider.getUriForFile(PersonIconActivity.this, "com.vooda.ant" + ".fileprovider", imgFile);
                            } else {
                                imgUri = Uri.fromFile(imgFile);
                            }

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                            startActivityForResult(intent, REQUEST_CAMERA);

                        } else {
                            ToastUtil.showShort(PersonIconActivity.this, "权限拒绝");
                        }
                    }
                });

    }

    private void selectAlbum() {
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            // 所有权限请求被同意
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                            startActivityForResult(intent, REQUEST_ALBUM);
                        } else {
                            ToastUtil.showShort(PersonIconActivity.this, "权限拒绝");
                        }
                    }
                });
    }


    private String createImagePath() {
        String imagePath = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";
       /* File imageFile = new File(imagePath);
        try {
            imageFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShort(this, "出错啦");
        }*/
        return imagePath;
    }

    /**
     * 发起剪裁图片的请求
     *
     * @param activity    上下文
     * @param srcFile     原文件的File
     * @param output      输出文件的File
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, File srcFile, File output, int requestCode) {

        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(Uri.fromFile(srcFile), "image/*");
        //为了7.0适配问题,不再使用Uri.fromFile()方法获取文件的Uri
        intent.setDataAndType(getImageContentUri(activity, srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);


        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    Uri outputUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK://调用图片选择处理成功
                String zoomImgPath = "";
                Bitmap bm = null;
                File temFile = null;
                File srcFile = null;
                File outPutFile = null;
                switch (requestCode) {
                    case REQUEST_CAMERA:// 拍照后在这里回调
                        srcFile = new File(mImgPath);
                        outPutFile = new File(createImagePath());
                        outputUri = Uri.fromFile(outPutFile);
                        startPhotoZoom(this, srcFile, outPutFile, REQUEST_CROP);// 发起裁剪请求
                        break;
                    case REQUEST_ALBUM:// 选择相册中的图片
                        if (data != null) {
                            Uri sourceUri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};

                            // 好像是android多媒体数据库的封装接口，具体的看Android文档
                            Cursor cursor = managedQuery(sourceUri, proj, null, null, null);

                            // 按我个人理解 这个是获得用户选择的图片的索引值
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                            cursor.moveToFirst();
                            // 最后根据索引值获取图片路径
                            String imgPath = cursor.getString(column_index);

                            srcFile = new File(imgPath);
                            outPutFile = new File(createImagePath());
                            outputUri = Uri.fromFile(outPutFile);
                            startPhotoZoom(this, srcFile, outPutFile, REQUEST_CROP);// 发起裁剪请求
                        }
                        break;
                    case REQUEST_CROP://裁剪后回调
                        if (data != null) {
                            if (outputUri != null) {
                                bm = decodeUriAsBitmap(this, outputUri);

                                mIconIv.setImageBitmap(bm);

//                                String scaleImgPath = FileUtils.saveBitmapByQuality(bm, 80);//复制并压缩到自己的目录并压缩

                                //bm可以用于显示在对应的ImageView中，scaleImgPath是剪裁并压缩后的图片的路径，可以用于上传操作
                                //实现自己的业务逻辑

                            }
                        } else {
                            ToastUtil.showShort(PersonIconActivity.this, "选择图片发生错误，图片可能已经移位或删除");
                        }
                        break;
                }
        }
    }

    public  Bitmap decodeUriAsBitmap(Context context,Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    /**
     * 按质量压缩bm
     * @param bm
     * @param quality 压缩保存率
     * @return
     */
    public  String saveBitmapByQuality(Bitmap bm,int quality) {
        String croppath="";
        try {
            File f = new File(createImagePath());
            //得到相机图片存到本地的图片
            croppath=f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG,quality, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }
}
