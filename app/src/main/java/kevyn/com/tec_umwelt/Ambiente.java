package kevyn.com.tec_umwelt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class Ambiente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambiente);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Ambiente.this, Tecno.class);
        startActivity(intent);
        finish();
    }
}
