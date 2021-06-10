package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewAnimeActivity extends AppCompatActivity {
    private TextView animeName;
    private TextView animeInfo;
    private ImageView animeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_anime);

        animeName = findViewById(R.id.anime_name_text);
        animeInfo = findViewById(R.id.anime_info_text);
        animeImage = findViewById(R.id.anime_image);
    }

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, 0x1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                animeImage.setImageURI(data.getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addAmime(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(animeName.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String name = animeName.getText().toString();
            String info = animeInfo.getText().toString();
            replyIntent.putExtra("name", name);
            replyIntent.putExtra("info", info);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}