package pro.ayo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActiveGameActivity extends AppCompatActivity {
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_game);
        listView =(ListView)findViewById(R.id.active_game_list);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.listholder,MainActivity.ayoGameList );
        listView.setAdapter(arrayAdapter);
    }
}
