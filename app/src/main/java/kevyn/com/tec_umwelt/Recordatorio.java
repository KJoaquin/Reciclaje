package kevyn.com.tec_umwelt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Recordatorio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Recordatorio.this, Tecno.class);
        startActivity(intent);
        finish();
    }
}
