package turma_android.com.br.appnotificacao;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.Uri;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void criarNotificacao(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle("Título");
        builder.setContentText("Mensagem");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(Color.BLUE);
        builder.setOngoing(true);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000});

        Uri uriSom = Uri.parse("android.resource://" + getPackageName() + "/raw/audio");
        builder.setSound(uriSom);

        Intent it = new Intent(this, DetalheActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, 0);
        builder.setContentIntent(pi);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(id, builder.build());
    }

    public void cancelarNotificacao(View view) {
        NotificationManagerCompat manager =
                NotificationManagerCompat.from(this);

        manager.cancel(id);
    }

    public void baixarImagem(View v) {
        new Tarefa().execute("https://raw.githubusercontent.com/florenciofvf/AppHttp/master/app/src/main/res/mipmap-hdpi/ic_launcher.png");
    }

    class Tarefa extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String s = strings[0];

            try {
                URL url = new URL(s);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                byte[] bytes = new byte[1024];
                int lidos = is.read(bytes);

                while(lidos > 0) {
                    buffer.write(bytes, 0, lidos);
                    lidos = is.read(bytes);
                }

                is.close();

                criarNotificacao(buffer.toByteArray());
            } catch(Exception e) {
                e.printStackTrace();
                Log.i("ERRO", e.getMessage());
            }

            return null;
        }
    }

    private void criarNotificacao(byte[] bytes) {
        Intent it = new Intent(this, DetalheActivity.class);
        it.putExtra("bytesImagem", bytes);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setTicker("Imagem baixada");
        builder.setContentTitle("Download concluído");
        builder.setContentText("Click para visualizar");
        builder.setContentIntent(
                PendingIntent.getActivity(this, 0, it,
                    PendingIntent.FLAG_UPDATE_CURRENT));

        NotificationManagerCompat.from(this)
                .notify(0, builder.build());
    }
}
