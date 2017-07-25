package turma_android.com.br.appnotificacao;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void criarNotificacao(View view) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);

        builder.setContentTitle("Título");
        builder.setContentText("Mensagem");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        NotificationManagerCompat manager =
                NotificationManagerCompat.from(this);

        manager.notify(id, builder.build());
    }

    public void cancelarNotificacao(View view) {
        NotificationManagerCompat manager =
                NotificationManagerCompat.from(this);

        manager.cancel(id);
    }
}
