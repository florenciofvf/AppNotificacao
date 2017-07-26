package turma_android.com.br.appnotificacao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class DetalheActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhe_layout);

        imageView = (ImageView) findViewById(R.id.imageView);

        Intent it = getIntent();

        if(it != null && it.getByteArrayExtra("bytesImagem") != null) {

            byte[] bytes = it.getByteArrayExtra("bytesImagem");

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "NULO", Toast.LENGTH_SHORT).show();
        }
    }
}
